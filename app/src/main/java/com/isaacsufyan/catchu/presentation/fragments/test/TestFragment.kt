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
import com.isaacsufyan.catchu.BuildConfig
import com.isaacsufyan.catchu.R
import com.isaacsufyan.catchu.base.BaseFragment
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
import java.util.*

class TestFragment : BaseFragment() {

    private var _binding: FragmentTestBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<SharedViewModel>()

    private lateinit var generator: Vibrator
    private lateinit var mediaPlayer: MediaPlayer

    private var stims = mutableListOf<Stimulus>()
    private var trailStims = mutableListOf<Stimulus>()

    private var currentStim = 0
    private var introShown = false
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

    private var accuracyThreshold = 0.7 // 70%
    private val params = HashMap<String, Any>()
    private var stateEvent: StateEvent? = null

    private var batchCompleted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.white_noise)
        generator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                mainActivity.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            mainActivity.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
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
            } else {
                Log.d("TAG", "recordResponse: Cant Answer")
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

        Log.e("TAG", "proceedToNextStimulus: ${stims.size}")
        if (currentStim < stims.size) {
            Log.d("TAG", "Currently On Stim: $currentStim")
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
        Log.d("TAG", "reactionTime: $reactionTime")

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

    private fun inTrail(): Boolean {
        return currentStim < 15
    }

    private fun handleStimPresentation() {
        if (isPaused) {
            return
        }
        trialMode = true
        if (currentStim == 0 && !introShown) {
            showInitialAlert()
        } else if (currentStim == 15 && !mainInstructionShown && verifySuccessfulPracticeTrails()) {
            if (mediaPlayer.isPlaying) mediaPlayer.pause()
            val action = TestFragmentDirections.actionTestToPracticeCompleteDialogFragment()
            findNavController().navigate(action)

            binding.tvStartText.text = resources.getString(R.string.click_here_to_start)
            trialMode = false
            mainInstructionShown = true
        } else if (currentStim == 15 && !mainInstructionShown && !verifySuccessfulPracticeTrails()) {
            currentStim = 0
            introShown = !introShown

            if (practiceRetried) {
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
            Log.d("TAG", "handleStimPresentation: $currentType")

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
            message = "First Break"
            breakOneTaken = true
        } else {
            message = "Second Break"
            breakTwoTaken = true
        }

        showOkDialog(message, object : IDialogOnClickListener {
            override fun onOk(dialog: AlertDialog) {
                dialog.dismiss()
                handleStimPresentation()
            }
        })
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
        sortAnswerArrays()
        var answerNone: Double
        var answerVis: Double
        var answerVisSoma: Double
        var answerSoma: Double

        for (i in 0 until 45) {
            if (answersNone.indexExists(i)) {
                answerNone = answersNone[i]
                rawMeasurements.javaClass.getField("c$i")
                    .set(rawMeasurements, (answerNone * 1000).toInt())
            }
            if (answersVis.indexExists(i)) {
                answerVis = answersVis[i]
                rawMeasurements.javaClass.getField("v$i")
                    .set(rawMeasurements, (answerVis * 1000).toInt())
            }
            if (answersVisAndSoma.indexExists(i)) {
                answerVisSoma = answersVisAndSoma[i]
                rawMeasurements.javaClass.getField("vs$i")
                    .set(rawMeasurements, (answerVisSoma * 1000).toInt())
            }
            if (answersSoma.indexExists(i)) {
                answerSoma = answersSoma[i]
                rawMeasurements.javaClass.getField("s$i")
                    .set(rawMeasurements, (answerSoma * 1000).toInt())
            }
        }
        val rawMeasurementModel = RawMeasurementModel()
        rawMeasurementModel.test = viewModel.testId
        rawMeasurementModel.rawMeasurements = rawMeasurements
        stateEvent = StateEvent.CreateRawMeasurement(rawMeasurementModel)
        viewModel.setStateEvent(stateEvent as StateEvent.CreateRawMeasurement)
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
        message: String = resources.getString(R.string.practice_test_start_msg)
    ) {

        if (!introShown) {
            introShown = true
            mainActivity.showDialog(
                true,
                message,
                R.drawable.practice_icon,
                null,
                resources.getString(R.string.okay),
                object : IDialogOnClickListener {
                    override fun onOk(dialog: AlertDialog) {
                        dialog.dismiss()
                        binding.tvStartText.text = resources.getString(R.string.click_here_to_start)
                    }
                },
                resources.getString(R.string.practice_test)
            )
        }

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
                            showOkDialog(it.data.message.toString())
                        }
                    }
                }
                is DataState.Failure -> {
                    mainActivity.showProgress(false)
                    showOkDialog("Code: ${it.code} Message: ${it.message}")
                }
                is DataState.Error -> {
                    mainActivity.showProgress(false)
                    showOkDialog(it.exception.message.toString())
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

        val answerVisPercentage =
            answerVisCount.toDouble() / 45.toDouble()
        val answersSomaPercentage =
            answersSomaCount.toDouble() / 45.toDouble()
        val answersVisAndSomaPercentage =
            answersVisAndSomaCount.toDouble() / 45.toDouble()


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
            args["test_status"] = if (invalidReport) "Failed" else "Success"
            args["invalidReport"] = invalidReport
            args["environment"] = BuildConfig.Environment
        }
        stateEvent = StateEvent.VerifyGroupTestAlgo(url = url, args)
        viewModel.setStateEvent(stateEvent as StateEvent.VerifyGroupTestAlgo)
    }

    private fun batchTestStatus() {
        viewModel.communityId?.let {
            stateEvent = StateEvent.BatchTestStatus(it)
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
        dialog.show(childFragmentManager, "TestCompletedFragment")
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
        findNavController().navigate(action)
    }
}