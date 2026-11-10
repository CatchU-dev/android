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
import com.isaacsufyan.catchu.domain.model.LabConfig
import com.isaacsufyan.catchu.domain.model.LabPracticeTrial
import com.isaacsufyan.catchu.domain.model.LabTrial
import com.isaacsufyan.catchu.domain.model.RawMeasurementModel
import com.isaacsufyan.catchu.domain.model.Stimulus
import com.isaacsufyan.catchu.domain.state.DataState
import com.isaacsufyan.catchu.framework.interfaces.IDialogOnClickListener
import com.isaacsufyan.catchu.presentation.fragments.SharedViewModel
import com.isaacsufyan.catchu.presentation.fragments.test_completed.TestCompletedFragment
import com.isaacsufyan.catchu.utils.StimulusSequence
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
    private val labBreaksTaken = mutableSetOf<Int>()

    // Lab build: protocol changes (balanced blocks, tester-chosen block count, per-trial payload).
    private val isLab = BuildConfig.IS_LAB_BUILD
    private val numBlocks get() = viewModel.labNumBlocks
    private val valuesPerType get() = if (isLab) numBlocks * StimulusSequence.PER_TYPE_PER_BLOCK else 45
    private val labTrials = mutableListOf<LabTrial>()
    private val labPracticeTrials = mutableListOf<LabPracticeTrial>()
    private val uiHandler = Handler(Looper.getMainLooper())
    private var advancePending = false

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
    private var practiceAttempt1Values: HashMap<String, Int>? = null

    private var accuracyThreshold = 0.7 // 70%
    private val params = HashMap<String, Any>()
    private var stateEvent: StateEvent? = null

    private var batchCompleted = false
    private var isOnTestCompleteScreen = false

    private val EEG_FLASH_DURATION_MS = 100L

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
        showAllPhotodiodes()
        showInitialAlert()
        binding.tvStartText.setOnClickListener {
            recordResponse()
        }
        subscriber()
    }

    private fun flashPhotodiode(view: View, durationMs: Long = EEG_FLASH_DURATION_MS) {
        view.visibility = View.VISIBLE
        uiHandler.postDelayed({
            view.visibility = View.INVISIBLE
        }, durationMs)
    }

    private fun showAllPhotodiodes() {
        binding.photodiodeRow.vPhotodiode1.visibility = View.VISIBLE
        binding.photodiodeRow.vPhotodiode2.visibility = View.VISIBLE
        binding.photodiodeRow.vPhotodiode3.visibility = View.VISIBLE
        binding.photodiodeRow.vPhotodiode4.visibility = View.VISIBLE
    }

    private fun hideAllPhotodiodes() {
        binding.photodiodeRow.vPhotodiode1.visibility = View.INVISIBLE
        binding.photodiodeRow.vPhotodiode2.visibility = View.INVISIBLE
        binding.photodiodeRow.vPhotodiode3.visibility = View.INVISIBLE
        binding.photodiodeRow.vPhotodiode4.visibility = View.INVISIBLE
    }

    private fun recordResponse() {

        binding.tvStartText.text = ""

        if (trialMode) {
            if (canAnswer) {
                responseTimeStamp = Date().time.toDouble()
                proceedToNextStimulus()
            }
        } else {
            // Repeated taps on the start / instruction screen used to queue several advances, which
            // made stimuli fire together. Only the first tap counts, until the next stimulus
            // presentation (handleStimPresentation) clears the flag.
            if (advancePending) return
            advancePending = true
            hideAllPhotodiodes()
            uiHandler.postDelayed({
                if (isLab) {
                    // Lab: the start tap must not consume a trial.
                    canAnswer = false
                    resetTimeStamps()
                    scheduleNextStimulus()
                } else {
                    proceedToNextStimulus()
                }
            }, 1000)
        }
    }

    private fun proceedToNextStimulus() {
        runnable?.let { handler?.removeCallbacks(it) }
        canAnswer = false
        setAnswer()
        currentStim += 1
        resetTimeStamps()
        scheduleNextStimulus()
    }

    private fun scheduleNextStimulus() {
        if (currentStim < stims.size) {
            val waitMs = if (isLab) {
                kotlin.random.Random.nextLong(1000L, 3001L)
            } else {
                kotlin.random.Random.nextDouble(1.0, 4.0).toLong() * 1000
            }
            uiHandler.postDelayed({
                handleStimPresentation()
            }, waitMs)
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
        if (isLab) recordLabTrial(reactionTime)

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

    private fun labTypeCode(type: EStimulusType) = when (type) {
        EStimulusType.VISUAL -> "V"
        EStimulusType.SOMA_TO_SENSORY -> "S"
        EStimulusType.VIS_AND_SOMA -> "VS"
        EStimulusType.NONE -> "C"
    }

    /** A real response is always > 0 ms (the window opens after the 100 ms stimulus); a miss stays 0. */
    private fun recordLabTrial(reactionTime: Double) {
        val type = currentType ?: return
        val responded = responseTimeStamp > 0.0
        val rtMs = if (responded) reactionMsToPayloadInt(reactionTime) else null
        val code = labTypeCode(type)
        if (inTrail()) {
            labPracticeTrials.add(
                LabPracticeTrial(
                    attempt = if (practiceRetried) 2 else 1,
                    trialNumber = currentStim + 1,
                    type = code,
                    rtMs = rtMs,
                    responded = responded
                )
            )
        } else {
            val mainIndex = currentStim - StimulusSequence.PRACTICE_SIZE
            // Catch trial: no response is the correct behaviour (hit), a response is a false alarm.
            val outcome = if (type == EStimulusType.NONE) {
                if (responded) "false_alarm" else "hit"
            } else {
                if (responded) "hit" else "miss"
            }
            labTrials.add(
                LabTrial(
                    trialNumber = mainIndex + 1,
                    block = mainIndex / StimulusSequence.BLOCK_SIZE + 1,
                    type = code,
                    rtMs = rtMs,
                    responded = responded,
                    outcome = outcome
                )
            )
        }
    }

    /** Lab: the block number that just ended when a block boundary is reached at [index], else null. */
    private fun labBreakNumber(index: Int): Int? {
        val mainIndex = index - StimulusSequence.PRACTICE_SIZE
        return if (mainIndex > 0 && mainIndex % StimulusSequence.BLOCK_SIZE == 0) {
            mainIndex / StimulusSequence.BLOCK_SIZE
        } else null
    }

    private fun handleStimPresentation() {
        advancePending = false
        if (isPaused) {
            return
        }
        trialMode = true
        if (currentStim == 0 && !isLab) {
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

            practiceAttempt1Values = buildPracticeValuesMap()
            practiceRetried = true
            showInitialAlert(message = resources.getString(R.string.second_practice_test_message))
            if (isLab) {
                // Wait for the start tap (which no longer consumes a trial) instead of arming a response.
                canAnswer = false
                trialMode = false
            } else {
                canAnswer = true
            }
            trialAnswersVis.clear()
            trialAnswersSoma.clear()
            trialAnswersVisAndSoma.clear()
            currentType = null
        } else if (isLab && labBreakNumber(currentStim)?.let { it !in labBreaksTaken } == true) {
            showBreakAlert(labBreakNumber(currentStim)!!)
        } else if (!isLab && currentStim == 74 && !breakOneTaken) {
            showBreakAlert(1)
        } else if (!isLab && currentStim == 134 && !breakTwoTaken) {
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

        if (isLab) {
            // The live texts only know "first" and "second of three"; lab runs have numBlocks - 1 breaks.
            message = resources.getString(R.string.lab_break_text, breakNumber, numBlocks)
        } else if (breakNumber == 1) {
            message = resources.getString(R.string.task_instructions_first_break_text)
            breakOneTaken = true
        } else {
            message = resources.getString(R.string.task_instructions_second_break_text)
            breakTwoTaken = true
        }
        labBreaksTaken.add(breakNumber)

        val dialog = createDialog(message)
        dialog.show()

        val handler = uiHandler
        val runnableDialog = Runnable { if (dialog.isShowing) dialog.dismiss() }
        val runnableTest = Runnable { handleStimPresentation() }

        dialog.setOnDismissListener {
            handler.removeCallbacks(runnableDialog)
            handler.removeCallbacks(runnableDialog)
        }

        // Lab: the text promises a 20 second rest, so the dialog and the resume use the same 20 s.
        handler.postDelayed(runnableDialog, if (isLab) 20000 else 15000)
        handler.postDelayed(runnableTest, if (isLab) 20000 else 21000)
    }

    private fun showNoneStimulus(func: () -> Unit) {
        setDisplayTimeStamp()
        flashPhotodiode(binding.photodiodeRow.vPhotodiode4)
        openResponseWindow(func)
    }

    /** Lab: every type opens its 2 s response window after the 100 ms stimulus period, as V and VS do. */
    private fun openResponseWindow(func: () -> Unit) {
        if (isLab) uiHandler.postDelayed({ func() }, EEG_FLASH_DURATION_MS) else func()
    }

    private fun showVisAndSoma(func: () -> Unit) {
        binding.ivVisStimulusLeft.visibility = View.VISIBLE
        binding.ivVisStimulusRight.visibility = View.VISIBLE
        setDisplayTimeStamp()
        generator.vibrate(100)
        flashPhotodiode(binding.photodiodeRow.vPhotodiode3)

        uiHandler.postDelayed({
            binding.ivVisStimulusLeft.visibility = View.INVISIBLE
            binding.ivVisStimulusRight.visibility = View.INVISIBLE
            setRemoveDisplayTimeStamp()
            func()
        }, 100)
    }

    private fun showSomaStim(func: () -> Unit) {
        generator.vibrate(100)
        setDisplayTimeStamp()
        flashPhotodiode(binding.photodiodeRow.vPhotodiode2)
        openResponseWindow(func)
    }

    private fun showVisualStim(func: () -> Unit) {
        binding.ivVisStimulusLeft.visibility = View.VISIBLE
        binding.ivVisStimulusRight.visibility = View.VISIBLE
        setDisplayTimeStamp()
        flashPhotodiode(binding.photodiodeRow.vPhotodiode1)

        uiHandler.postDelayed({
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

    /** Reaction times in ms; avoids NaN/Inf and overflow when sending as Int. */
    private fun reactionMsToPayloadInt(ms: Double): Int {
        if (ms.isNaN() || ms.isInfinite()) return 0
        return ms.coerceIn(Int.MIN_VALUE.toDouble(), Int.MAX_VALUE.toDouble()).toInt()
    }

    /** Snapshot of the current practice attempt's per-trial reaction times, keyed like the real test's V/S/VS values. */
    private fun buildPracticeValuesMap(): HashMap<String, Int> {
        val values = HashMap<String, Int>()
        for (i in trialAnswersVis.indices) values["V$i"] = reactionMsToPayloadInt(trialAnswersVis[i])
        for (i in trialAnswersSoma.indices) values["S$i"] = reactionMsToPayloadInt(trialAnswersSoma[i])
        for (i in trialAnswersVisAndSoma.indices) values["VS$i"] = reactionMsToPayloadInt(trialAnswersVisAndSoma[i])
        return values
    }

    private fun uploadResultsToServer() {
        val testId = viewModel.testId
        if (testId == null) {
            showOkDialog(resources.getString(R.string.something_get_wrong_contact_support_engineer))
            return
        }

        val unsortedParams = HashMap<String, Int>()
        val unsortedVis = respondedOnly(answersVis)
        val unsortedSoma = respondedOnly(answersSoma)
        val unsortedVisAndSoma = respondedOnly(answersVisAndSoma)
        val unsortedNone = respondedOnly(answersNone)
        for (i in unsortedVis.indices) unsortedParams["V$i"] = reactionMsToPayloadInt(unsortedVis[i])
        for (i in unsortedSoma.indices) unsortedParams["S$i"] = reactionMsToPayloadInt(unsortedSoma[i])
        for (i in unsortedVisAndSoma.indices) {
            unsortedParams["VS$i"] = reactionMsToPayloadInt(unsortedVisAndSoma[i])
        }
        for (i in unsortedNone.indices) unsortedParams["C$i"] = reactionMsToPayloadInt(unsortedNone[i])

        // failedAttempts == 2 means the patient failed practice twice and never started the
        // real test; a single retry that was later passed must not wipe real-test data.
        val practiceFailed = failedAttempts == 2

        if (practiceFailed) {
            unsortedParams.clear()
            for (i in 0 until valuesPerType) {
                unsortedParams["C$i"] = 0
                unsortedParams["V$i"] = 0
                unsortedParams["VS$i"] = 0
                unsortedParams["S$i"] = 0
            }
        }

        sortAnswerArrays()
        var answerNone: Double
        var answerVis: Double
        var answerVisSoma: Double
        var answerSoma: Double
        val params = HashMap<String, Int>()

        val sortedNone = respondedOnly(answersNone)
        val sortedVis = respondedOnly(answersVis)
        val sortedVisAndSoma = respondedOnly(answersVisAndSoma)
        val sortedSoma = respondedOnly(answersSoma)

        if (practiceFailed) {
            for (i in 0 until valuesPerType) {
                params["C$i"] = 0
                params["V$i"] = 0
                params["VS$i"] = 0
                params["S$i"] = 0
            }
        } else {
            for (i in 0 until valuesPerType) {
                if (sortedNone.indexExists(i)) {
                    answerNone = sortedNone[i]
                    params["C$i"] = reactionMsToPayloadInt(answerNone)
                }
                if (sortedVis.indexExists(i)) {
                    answerVis = sortedVis[i]
                    params["V$i"] = reactionMsToPayloadInt(answerVis)
                }
                if (sortedVisAndSoma.indexExists(i)) {
                    answerVisSoma = sortedVisAndSoma[i]
                    params["VS$i"] = reactionMsToPayloadInt(answerVisSoma)
                }
                if (sortedSoma.indexExists(i)) {
                    answerSoma = sortedSoma[i]
                    params["S$i"] = reactionMsToPayloadInt(answerSoma)
                }
            }
        }
        rawMeasurements.practiseTestStatus = if (practiceFailed) "fail" else "pass"

        if (practiceRetried) {
            rawMeasurements.practiseTestValues = practiceAttempt1Values
            rawMeasurements.practiseTestValues2 = buildPracticeValuesMap()
        } else {
            rawMeasurements.practiseTestValues = buildPracticeValuesMap()
            rawMeasurements.practiseTestValues2 = null
        }

        rawMeasurements.testValues = params
        rawMeasurements.visualImpairments = rawMeasurements.hasVisionProblem
        if (BuildConfig.IS_LAB_BUILD) {
            rawMeasurements.isLabTest = true
        }

        val rawMeasurementModel = RawMeasurementModel()
        rawMeasurementModel.test = testId
        rawMeasurementModel.rawMeasurements = rawMeasurements
        rawMeasurementModel.unsortedRawMeasurements = unsortedParams
        if (isLab) {
            rawMeasurementModel.labConfig = LabConfig(
                numBlocks = numBlocks,
                trialsPerBlock = StimulusSequence.BLOCK_SIZE,
                perConditionPerBlock = StimulusSequence.PER_TYPE_PER_BLOCK,
                stimulusMs = EEG_FLASH_DURATION_MS.toInt(),
                responseWindowMs = 2000,
                itiMinMs = 1000,
                itiMaxMs = 3000
            )
            rawMeasurementModel.practiceTrials = labPracticeTrials.toList()
            rawMeasurementModel.trials = if (practiceFailed) emptyList() else labTrials.toList()
        }
        stateEvent = StateEvent.CreateRawMeasurement(rawMeasurementModel)
        viewModel.resetResponseModel()
        viewModel.setStateEvent(stateEvent as StateEvent.CreateRawMeasurement)
    }

    /** Lab: a miss is stored as 0 in the working lists; the legacy value maps leave misses out. */
    private fun respondedOnly(list: List<Double>): List<Double> =
        if (isLab) list.filter { it > 0.0 } else list

    private fun sortAnswerArrays() {
        answersNone.sort()
        answersSoma.sort()
        answersVis.sort()
        answersVisAndSoma.sort()
    }

    private fun generateStimuli() {
        if (isLab) {
            stims.addAll(StimulusSequence.practice())
            StimulusSequence.blocks(numBlocks).forEach { stims.addAll(it) }
            return
        }
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
                            if (BuildConfig.IS_LAB_BUILD) {
                                submitLabTestReport()
                            } else {
                                val result = if (practiceRetried) {
                                    verifySuccessfulPracticeTrails()
                                } else {
                                    !verifySuccessfulTest()
                                }
                                verifyGroupTestAlgo(result)
                            }
                        } else {
                            showCompleteTestScreen()
                        }
                    }
                    else if (stateEvent is StateEvent.LabTestReport) {
                        if (it.data.status) {
                            showCompleteTestScreen()
                        }
                    }
                    else if (stateEvent is StateEvent.VerifyGroupTestAlgo) {
                        if (it.data.status) {
                            if (viewModel.isSingleTest) showCompleteTestScreen()
                            else batchTestStatus()
                        }
                    }
                    else if (stateEvent is StateEvent.BatchTestStatus) {
                        if (it.data.status) {
                            batchCompleted = it.data.data?.batchCompleted ?: false
                            showCompleteTestScreen()
                        }
                        else {
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

    private fun submitLabTestReport() {
        val testId = viewModel.testId ?: return
        stateEvent = StateEvent.LabTestReport(testId)
        viewModel.resetResponseModel()
        viewModel.setStateEvent(stateEvent as StateEvent.LabTestReport)
    }

    private fun verifyGroupTestAlgo(invalidReport: Boolean) {
        val url = BuildConfig.ALGORITHM_API_URL
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

    private fun isPostTestResearchFlow(): Boolean =
        viewModel.isResearchPurposeDropdownVisible &&
            rawMeasurements.researchPurpose.equals("yes", ignoreCase = true)

    private fun showCompleteTestScreen() {
        mediaPlayer.stop()
        val dialogListener = object : DialogInterface {
            override fun cancel() {
            }

            override fun dismiss() {
                handleCompleteTest()
            }
        }
        val dialog = TestCompletedFragment(
            dialogListener,
            deferSingleTestFinishForResearchPostFlow = isPostTestResearchFlow()
        )
        isOnTestCompleteScreen = true
        dialog.show(childFragmentManager, "TestCompletedFragment")
    }

    private fun returnToCommunity(dialog: AlertDialog) {
        mediaPlayer.stop()
        dialog.dismiss()
        mainActivity.testInProgress.value = false
        if (BuildConfig.IS_LAB_BUILD) {
            findNavController().navigate(R.id.action_test_to_lab_finalization)
            return
        }
        if (viewModel.isSingleTest) {
            mainActivity.finish()
        } else {
            val action = TestFragmentDirections.actionTestCompleteToCommunityId()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        uiHandler.removeCallbacksAndMessages(null)
        advancePending = false
        runnable?.let { handler?.removeCallbacks(it) }
        _binding = null
        mediaPlayer.stop()
    }

    private fun handleCompleteTest() {
        mainActivity.testInProgress.value = false
        if (BuildConfig.IS_LAB_BUILD) {
            findNavController().navigate(R.id.action_test_to_lab_finalization)
            return
        }
        if (isPostTestResearchFlow()) {
            findNavController().navigate(
                TestFragmentDirections.actionTestFragmentToPostTestResearchOptionsFragment()
            )
            return
        }
        val action = if (batchCompleted) {
            TestFragmentDirections.actionTestCompleteToCommunityId()
        } else {
            TestFragmentDirections.actionTestCompleteToPatientProfile()
        }
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