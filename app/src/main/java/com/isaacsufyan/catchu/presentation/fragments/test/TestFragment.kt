package com.isaacsufyan.catchu.presentation.fragments.test

import android.content.Context
import android.content.DialogInterface
import android.media.MediaPlayer
import android.os.*
import android.util.Log
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.isaacsufyan.catchu.BuildConfig
import com.isaacsufyan.catchu.R
import com.isaacsufyan.catchu.base.BaseFragment
import com.isaacsufyan.catchu.databinding.DialogAlertBinding
import com.isaacsufyan.catchu.databinding.FragmentTestBinding
import com.isaacsufyan.catchu.domain.*
import com.isaacsufyan.catchu.domain.enum.EStimulusType
import com.isaacsufyan.catchu.domain.event.StateEvent
import com.isaacsufyan.catchu.domain.model.RawMeasurementModel
import com.isaacsufyan.catchu.domain.model.Stimulus
import com.isaacsufyan.catchu.domain.state.DataState
import com.isaacsufyan.catchu.framework.interfaces.IDialogOnClickListener
import com.isaacsufyan.catchu.presentation.fragments.SharedViewModel
import com.isaacsufyan.catchu.presentation.fragments.test_completed.TestCompletedFragment
import com.isaacsufyan.catchu.utils.indexExists
import com.isaacsufyan.catchu.utils.showDialog
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class TestFragment : BaseFragment() {

    private var _binding: FragmentTestBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<SharedViewModel>()

    private lateinit var generator: Vibrator
    private lateinit var mediaPlayer: MediaPlayer

    private var stims = mutableListOf<Stimulus>()
    private var trailStims = mutableListOf<Stimulus>()

    private var currentStim = 0

    //    private var introShown = false
    private var displayTimeStamp = 0.0
    private var removeDisplayTimeStamp = 0.0
    private var responseTimeStamp = 0.0
    private var trialMode = false
    private var isPaused = false
    private var breakOneTaken = false
    private var breakTwoTaken = false

    private var answersVis = mutableListOf<Double>()
    private var answersSoma = mutableListOf<Double>()
    private var answersVisAndSoma = mutableListOf<Double>()
    private var answersNone = mutableListOf<Double>()
    private var trialAnswersVis = mutableListOf<Double>()
    private var trialAnswersSoma = mutableListOf<Double>()
    private var trialAnswersVisAndSoma = mutableListOf<Double>()
    private var currentType: EStimulusType? = null

    private var canAnswer = false
    private var mainInstructionShown = false

    private var handler: Handler? = null
    private var runnable: Runnable? = null

    private var practiceRetried: Boolean = false
    private var failedAttempts: Int = 0

    private var accuracyThreshold = 0.7 // 70%
    private val params = HashMap<String, Any>()
    private var stateEvent: StateEvent? = null

    private var batchCompleted = false
    private var isOnTestCompleteScreen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.white_noise)
        generator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                mainActivity.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION") mainActivity.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.resetResponseModel()
        generateStimuli()
        showInitialAlert()
        binding.tvStartText.setOnClickListener {
            recordResponse()
        }
        subscriber()
    }

    private fun recordResponse() {

        binding.tvStartText.text = ""

        if (trialMode) {
            if (canAnswer) {
                responseTimeStamp = Date().time.toDouble()
                proceedToNextStimulus()
            }
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                proceedToNextStimulus()
            }, 1000)
        }
    }

    private fun proceedToNextStimulus() {
        runnable?.let { handler?.removeCallbacks(it) }
        canAnswer = false
        setAnswer()
        currentStim += 1
        resetTimeStamps()

        if (currentStim < stims.size) {
            val randomWaitTime = kotlin.random.Random.nextDouble(1.0, 4.0)
            Handler(Looper.getMainLooper()).postDelayed({
                handleStimPresentation()
            }, randomWaitTime.toLong() * 1000)
        } else {
            trialMode = false
            uploadResultsToServer()
        }
    }

    private fun setAnswer() {
        if (currentType == null) {
            return
        }
        var reactionTime = responseTimeStamp - displayTimeStamp
        if (reactionTime < 0) reactionTime = 0.0

        when (currentType) {
            EStimulusType.NONE -> {
                answersNone.add(reactionTime)
            }
            EStimulusType.VISUAL -> {
                if (inTrail()) {
                    trialAnswersVis.add(reactionTime)
                } else {
                    answersVis.add(reactionTime)
                }
            }
            EStimulusType.VIS_AND_SOMA -> {
                if (inTrail()) {
                    trialAnswersVisAndSoma.add(reactionTime)
                } else {
                    answersVisAndSoma.add(reactionTime)
                }
            }
            EStimulusType.SOMA_TO_SENSORY -> {
                if (inTrail()) {
                    trialAnswersSoma.add(reactionTime)
                } else {
                    answersSoma.add(reactionTime)
                }
            }
            else -> {}
        }
    }

    private fun inTrail() = currentStim < 15

    private fun handleStimPresentation() {
        if (isPaused) {
            return
        }
        trialMode = true
        if (currentStim == 0) {
            showInitialAlert()
        } else if (currentStim == 15 && !mainInstructionShown && verifySuccessfulPracticeTrails()) {
            if (mediaPlayer.isPlaying) mediaPlayer.pause()
            val action = TestFragmentDirections.actionTestToPracticeCompleteDialogFragment()
            findNavController().navigate(action)
            showInitialAlert(
                message = resources.getString(R.string.original_test_start_msg),
                heading = resources.getString(R.string.task_instructions)
            )
            trialMode = false
            mainInstructionShown = true
        } else if (currentStim == 15 && !mainInstructionShown && !verifySuccessfulPracticeTrails()) {
            currentStim = 0

            if (practiceRetried) {
                failedAttempts = 2
                uploadResultsToServer()
                mediaPlayer.stop()
                return
            }

            practiceRetried = true
            showInitialAlert(message = resources.getString(R.string.second_practice_test_message))
            canAnswer = true
            trialAnswersVis.clear()
            trialAnswersSoma.clear()
            trialAnswersVisAndSoma.clear()
            currentType = null
        } else if (currentStim == 74 && !breakOneTaken) {
            showBreakAlert(1)
        } else if (currentStim == 134 && !breakTwoTaken) {
            showBreakAlert(2)
        } else {
            if (!mediaPlayer.isPlaying) mediaPlayer.start()

            currentType = stims[currentStim].stimulusType

            when (currentType) {
                EStimulusType.VISUAL -> {
                    showVisualStim {
                        canAnswer = true
                        setStimTimer()
                    }
                }
                EStimulusType.SOMA_TO_SENSORY -> {
                    showSomaStim {
                        canAnswer = true
                        setStimTimer()
                    }
                }
                EStimulusType.VIS_AND_SOMA -> {
                    showVisAndSoma {
                        canAnswer = true
                        setStimTimer()
                    }
                }
                EStimulusType.NONE -> {
                    showNoneStimulus {
                        canAnswer = true
                        setStimTimer()
                    }
                }
                else -> {}
            }
        }
    }

    private fun showBreakAlert(breakNumber: Int) {
        val message: String

        if (breakNumber == 1) {
            message = resources.getString(R.string.task_instructions_first_break_text)
            breakOneTaken = true
        } else {
            message = resources.getString(R.string.task_instructions_second_break_text)
            breakTwoTaken = true
        }

        val dialog = createDialog(message)
        dialog.show()

        val handler = Handler(Looper.getMainLooper())
        val runnableDialog = Runnable { if (dialog.isShowing) dialog.dismiss() }
        val runnableTest = Runnable { handleStimPresentation() }

        dialog.setOnDismissListener {
            handler.removeCallbacks(runnableDialog)
            handler.removeCallbacks(runnableDialog)
        }

        handler.postDelayed(runnableDialog, 15000)
        handler.postDelayed(runnableTest, 21000)
    }

    private fun showNoneStimulus(func: () -> Unit) {
        setDisplayTimeStamp()
        func()
    }

    private fun showVisAndSoma(func: () -> Unit) {
        binding.ivVisStimulusLeft.visibility = View.VISIBLE
        binding.ivVisStimulusRight.visibility = View.VISIBLE
        setDisplayTimeStamp()
        generator.vibrate(100)

        Handler(Looper.getMainLooper()).postDelayed({
            binding.ivVisStimulusLeft.visibility = View.INVISIBLE
            binding.ivVisStimulusRight.visibility = View.INVISIBLE
            setRemoveDisplayTimeStamp()
            func()
        }, 100)
    }

    private fun showSomaStim(func: () -> Unit) {
        generator.vibrate(100)
        setDisplayTimeStamp()
        func()
    }

    private fun showVisualStim(func: () -> Unit) {
        binding.ivVisStimulusLeft.visibility = View.VISIBLE
        binding.ivVisStimulusRight.visibility = View.VISIBLE
        setDisplayTimeStamp()

        Handler(Looper.getMainLooper()).postDelayed({
            binding.ivVisStimulusLeft.visibility = View.INVISIBLE
            binding.ivVisStimulusRight.visibility = View.INVISIBLE
            setRemoveDisplayTimeStamp()
            func()
        }, 100)
    }

    private fun setStimTimer() {
        handler = Handler(Looper.getMainLooper())
        runnable = Runnable {
            proceedToNextStimulus()
        }
        handler?.postDelayed(runnable!!, 2000)
    }

    private fun resetTimeStamps() {
        resetDisplayTimeStamp()
        resetResponseTimeStamp()
    }

    private fun resetDisplayTimeStamp() {
        displayTimeStamp = 0.0
    }

    private fun resetResponseTimeStamp() {
        responseTimeStamp = 0.0
    }

    private fun setDisplayTimeStamp() {
        displayTimeStamp = Date().time.toDouble()
    }

    private fun setRemoveDisplayTimeStamp() {
        removeDisplayTimeStamp = Date().time.toDouble()
    }

    private fun uploadResultsToServer() {
        mainActivity.testInProgress.value = false
        mainActivity.isInternetConnected.observe(viewLifecycleOwner, androidx.lifecycle.Observer { t ->
            if (t && !isOnTestCompleteScreen) {
                sortAnswerArrays()
                var answerNone: Double
                var answerVis: Double
                var answerVisSoma: Double
                var answerSoma: Double
                val params = HashMap<String, Int>()

        for (i in 0 until 45) {
            if (answersNone.indexExists(i)) {
                answerNone = answersNone[i]
                params["C$i"] = answerNone.toInt()
            }
            if (answersVis.indexExists(i)) {
                answerVis = answersVis[i]
                params["V$i"] = answerVis.toInt()
            }
            if (answersVisAndSoma.indexExists(i)) {
                answerVisSoma = answersVisAndSoma[i]
                params["VS$i"] = answerVisSoma.toInt()
            }
            if (answersSoma.indexExists(i)) {
                answerSoma = answersSoma[i]
                params["S$i"] = answerSoma.toInt()
            }
        }

                if (practiceRetried) {
                    for (i in 0 until 45) {
                        params["C$i"] = 0
                        params["V$i"] = 0
                        params["VS$i"] = 0
                        params["S$i"] = 0
                    }
                } else {
                    for (i in 0 until 45) {
                        if (answersNone.indexExists(i)) {
                            answerNone = answersNone[i]
                            params["C$i"] = (answerNone).toInt()
                        }
                        if (answersVis.indexExists(i)) {
                            answerVis = answersVis[i]
                            params["V$i"] = (answerVis).toInt()
                        }
                        if (answersVisAndSoma.indexExists(i)) {
                            answerVisSoma = answersVisAndSoma[i]
                            params["VS$i"] = (answerVisSoma).toInt()
                        }
                        if (answersSoma.indexExists(i)) {
                            answerSoma = answersSoma[i]
                            params["S$i"] = (answerSoma).toInt()
                        }
                    }
                }
                if (practiceRetried) {
                    rawMeasurements.practise_test_status = "fail"
                }

                rawMeasurements.testValues = params
                rawMeasurements.visualImpairments = rawMeasurements.hasVisionProblem

                val rawMeasurementModel = RawMeasurementModel()
                rawMeasurementModel.test = viewModel.testId
                rawMeasurementModel.rawMeasurements = rawMeasurements
                rawMeasurementModel.rawMeasurements = rawMeasurements.apply { practiseTestStatus = "pass" }
                stateEvent = StateEvent.CreateRawMeasurement(rawMeasurementModel)
                viewModel.resetResponseModel()
                viewModel.setStateEvent(stateEvent as StateEvent.CreateRawMeasurement)

            }
        })
    }

    private fun sortAnswerArrays() {
        answersNone.sort()
        answersSoma.sort()
        answersVis.sort()
        answersVisAndSoma.sort()
    }

    private fun generateStimuli() {
        for (i in 1..45) {
            stims.add(Stimulus(EStimulusType.VISUAL))
            stims.add(Stimulus(EStimulusType.VIS_AND_SOMA))
            stims.add(Stimulus(EStimulusType.SOMA_TO_SENSORY))
            stims.add(Stimulus(EStimulusType.NONE))
        }
        stims.shuffle()
        for (i in 1..5) {
            trailStims.add(Stimulus(EStimulusType.VISUAL))
            trailStims.add(Stimulus(EStimulusType.VIS_AND_SOMA))
            trailStims.add(Stimulus(EStimulusType.SOMA_TO_SENSORY))
        }
        trailStims.shuffle()
        trailStims.map { stims.add(0, it) }
    }

    private fun showInitialAlert(
        message: String = resources.getString(R.string.practice_test_start_msg),
        heading: String = resources.getString(R.string.practice_test)
    ) {

        mainActivity.disableBackgroundTouchesHandler()
        mainActivity.showDialog(
            true,
            message,
            R.drawable.practice_icon,
            null,
            resources.getString(R.string.okay),
            object : IDialogOnClickListener {
                override fun onOk(dialog: AlertDialog) {
                    mainActivity.freeBackgroundTouchesHandler()
                    dialog.dismiss()
                    binding.tvStartText.text = resources.getString(R.string.click_here_to_start)
                }
            },
            heading
        )
    }

    private fun subscriber() {
        viewModel.responseModel.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> {
                    mainActivity.showProgress(true)
                }
                is DataState.Success -> {
                    mainActivity.showProgress(false)
                    if (stateEvent is StateEvent.CreateRawMeasurement) {
                        if (it.data.status) {
                            val result = if (practiceRetried) {
                                verifySuccessfulPracticeTrails()
                            } else {
                                !verifySuccessfulTest()
                            }
                            verifyGroupTestAlgo(result)
                        } else {
                            showCompleteTestScreen()
                        }
                    } else if (stateEvent is StateEvent.VerifyGroupTestAlgo) {
                        if (it.data.status) {
                            if (viewModel.isSingleTest) showCompleteTestScreen()
                            else batchTestStatus()
                        }
                    } else if (stateEvent is StateEvent.BatchTestStatus) {
                        if (it.data.status) {
                            batchCompleted = it.data.data?.batchCompleted ?: false
                            showCompleteTestScreen()
                        } else {
                            showOkDialog(
                                it.data.message.toString(),
                                object : IDialogOnClickListener {
                                    override fun onOk(dialog: AlertDialog) {
                                        showCompleteTestScreen()
                                    }
                                })
                        }
                    }
                }
                is DataState.Failure -> {
                    mainActivity.showProgress(false)
//                    showOkDialog("Code: ${it.code} Message: ${it.message}")
                    showOkDialog("Code: ${it.code} Message: ${resources.getString(R.string.test_error_msg)}", object : IDialogOnClickListener {
                        override fun onOk(dialog: AlertDialog) {
                            returnToCommunity(dialog)
                        }
                    })
                }
                is DataState.Error -> {
                    mainActivity.showProgress(false)
//                    showOkDialog(it.exception.message.toString(), object : IDialogOnClickListener {
                    showOkDialog(resources.getString(R.string.test_error_msg), object : IDialogOnClickListener {
                        override fun onOk(dialog: AlertDialog) {
                            returnToCommunity(dialog)
                        }
                    })
                }

                is DataState.Idle -> {
                    mainActivity.showProgress(false)
                }
            }
        }
    }

    private fun verifySuccessfulPracticeTrails(): Boolean {

        val trailAnswerVisCount: Int = trialAnswersVis.filter {
            it > 0.0
        }.size

        val trailAnswerSomaCount: Int = trialAnswersSoma.filter {
            it > 0.0
        }.size

        val trailAnswerVisAndSomaCount: Int = trialAnswersVisAndSoma.filter {
            it > 0.0
        }.size

        val trailAnswerVisPercentage =
            trailAnswerVisCount.toDouble() / trialAnswersVis.size.toDouble()
        val trailAnswerSomaPercentage =
            trailAnswerSomaCount.toDouble() / trialAnswersSoma.size.toDouble()
        val trailAnswerVisAndSomaPercentage =
            trailAnswerVisAndSomaCount.toDouble() / trialAnswersVisAndSoma.size.toDouble()

        return if (practiceRetried && (trailAnswerVisPercentage < accuracyThreshold) || (trailAnswerSomaPercentage < accuracyThreshold) || (trailAnswerVisAndSomaPercentage < accuracyThreshold)) {
            false
        } else {
            (trailAnswerVisPercentage >= accuracyThreshold) && (trailAnswerSomaPercentage >= accuracyThreshold) && (trailAnswerVisAndSomaPercentage >= accuracyThreshold)
        }
    }

    private fun verifySuccessfulTest(): Boolean {

        val answerVisCount: Int = answersVis.filter {
            it > 0.0
        }.size

        val answersSomaCount: Int = answersSoma.filter {
            it > 0.0
        }.size

        val answersVisAndSomaCount: Int = answersVisAndSoma.filter {
            it > 0.0
        }.size

        val answerVisPercentage = answerVisCount.toDouble() / 45.toDouble()
        val answersSomaPercentage = answersSomaCount.toDouble() / 45.toDouble()
        val answersVisAndSomaPercentage = answersVisAndSomaCount.toDouble() / 45.toDouble()


        params["trialAnswersVisCount"] = trialAnswersVis.size

        params["answerVisCount"] = answerVisCount
        params["answersSomaCount"] = answersSomaCount
        params["answersVisAndSomaCount"] = answersVisAndSomaCount

        params["accuracyThreshold"] = accuracyThreshold

        params["answerVisPercentage"] = answerVisPercentage
        params["answersSomaPercentage"] = answersSomaPercentage
        params["answersVisAndSomaPercentage"] = answersVisAndSomaPercentage

        return (answerVisPercentage >= accuracyThreshold && answersSomaPercentage >= accuracyThreshold && answersVisAndSomaPercentage >= accuracyThreshold)
    }

    private fun verifyGroupTestAlgo(invalidReport: Boolean) {
        val url = "https://5ioxsjwd31.execute-api.us-east-1.amazonaws.com/algorithm/algorithm"
        val args = HashMap<String, Any>()
        viewModel.testId?.let {
            args["testId"] = it
            args["debug"] = params
            if (failedAttempts == 2) {
                args["test_status"] = "Failed"
            } else {
//                args["test_status"] = if (invalidReport) "Failed" else "Success"
                args["test_status"] = "Success"
            }
            args["invalidReport"] = invalidReport
            args["environment"] = BuildConfig.Environment
        }
        stateEvent = StateEvent.VerifyGroupTestAlgo(url = url, args)
        viewModel.resetResponseModel()
        viewModel.setStateEvent(stateEvent as StateEvent.VerifyGroupTestAlgo)
    }

    private fun batchTestStatus() {
        viewModel.groupId?.let {
            stateEvent = StateEvent.BatchTestStatus(it)
            viewModel.resetResponseModel()
            viewModel.setStateEvent(stateEvent as StateEvent.BatchTestStatus)
        }
    }

    private fun showCompleteTestScreen() {
        mediaPlayer.stop()
        val dialogListener = object : DialogInterface {
            override fun cancel() {
            }

            override fun dismiss() {
                handleCompleteTest()
            }
        }
        val dialog = TestCompletedFragment(dialogListener)
        isOnTestCompleteScreen = true
        dialog.show(childFragmentManager, "TestCompletedFragment")
    }

    private fun returnToCommunity(dialog: AlertDialog) {
        mediaPlayer.stop()
        if(viewModel.isSingleTest) {
            mainActivity.finish()
        } else {
            dialog.dismiss()
            val action = TestFragmentDirections.actionTestCompleteToCommunityId()
            mainActivity.testInProgress.value = false
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mediaPlayer.stop()
    }

    private fun handleCompleteTest() {
        val action = if (batchCompleted) {
            TestFragmentDirections.actionTestCompleteToCommunityId()
        } else {
            TestFragmentDirections.actionTestCompleteToPatientProfile()
        }
        mainActivity.testInProgress.value = false
        findNavController().navigate(action)
    }

    private fun createDialog(message: String): AlertDialog {
        val dialog = MaterialAlertDialogBuilder(requireContext()).create()

        dialog.window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        )

        val binding = DialogAlertBinding.inflate(layoutInflater)
        dialog.setView(binding.root)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)

        binding.tvDialogHeading.visibility = View.VISIBLE
        binding.tvDialogHeading.text = resources.getString(R.string.time_for_break)
        binding.tvDialogMessage.text = message

        binding.ivDialogHeader.setImageResource(R.mipmap.ic_launcher)
        binding.btnNo.visibility = View.GONE
        binding.btnYes.visibility = View.GONE
        return dialog
    }
    private fun timeIntervalSince1970(): Long {
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val currentTimeMillis = System.currentTimeMillis()
        val currentDate = Date(currentTimeMillis)
        val formattedCurrentDate = dateFormatter.format(currentDate)
        val parsedCurrentDate = dateFormatter.parse(formattedCurrentDate)!!
        return (parsedCurrentDate.time / 1000)
    }
}