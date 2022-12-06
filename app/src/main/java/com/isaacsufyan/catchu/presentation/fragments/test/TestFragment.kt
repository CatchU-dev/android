package com.isaacsufyan.catchu.presentation.fragments.test

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import com.isaacsufyan.catchu.R
import com.isaacsufyan.catchu.base.BaseFragment
import com.isaacsufyan.catchu.databinding.FragmentTestBinding
import com.isaacsufyan.catchu.domain.enum.EStimulusType
import com.isaacsufyan.catchu.domain.model.Stimulus
import com.isaacsufyan.catchu.framework.interfaces.IDialogOnClickListener
import com.isaacsufyan.catchu.presentation.fragments.SharedViewModel
import com.isaacsufyan.catchu.utils.showDialog

class TestFragment : BaseFragment() {

    private var _binding: FragmentTestBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<SharedViewModel>()

    private lateinit var mediaPlayer: MediaPlayer

    var stims = mutableListOf<Stimulus>()
    var currentStim = 0
    var introShown = false
    var displayTimeStamp = 0.0
    var removeDisplayTimeStamp = 0.0
    var responseTimeStamp = 0.0
    var trialMode = false
    var isPaused = false
    var breakOneTaken = false
    var breakTwoTaken = false
    var answersVis = mutableListOf<Double>()
    var answersSoma = mutableListOf<Double>()
    var answersVisAndSoma = mutableListOf<Double>()
    var answersNone = mutableListOf<Double>()
    var trialAnswersVis = mutableListOf<Double>()
    private var trialAnswersSoma = mutableListOf<Double>()
    var trialAnswersVisAndSoma = mutableListOf<Double>()
    var currentType: EStimulusType? = null
    var handler: Handler? = null
    var runnable: Runnable? = null
    var canAnswer = false
    var mainInstructionShown = false
    var demoAnswers = ""

    private lateinit var generator: Vibrator

    var practiceRetried: Boolean = false

    var accuracyThreshold = 0.7 // 70%

    //TODO after completing the batch, api return this value and redirect screen according to true/false - Move to community_screen and patient_details screens
    var batchCompleted = false

    var trailStims = mutableListOf<Stimulus>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        generator = mainActivity.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.white_noise)
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
        showPracticeTestDialog()
        generateStimuli()
        binding.tvStartText.setOnClickListener {

        }
    }

    private fun generateStimuli() {

        for (i in 1..45) stims.add(Stimulus(EStimulusType.VISUAL))
        for (i in 1..45) stims.add(Stimulus(EStimulusType.VIS_AND_SOMA))
        for (i in 1..45) stims.add(Stimulus(EStimulusType.SOMA_TO_SENSORY))
        for (i in 1..45) stims.add(Stimulus(EStimulusType.NONE))

        stims.shuffle()

        for (i in 1 until 5){
            Log.d("TAG", "generateStimuli: Until")
        }

        for (i in 1 .. 5){
            Log.d("TAG", "generateStimuli: Range")
        }

        for (i in 1..5) trailStims.add(Stimulus(EStimulusType.VISUAL))


        for (i in 1..5) trailStims.add(Stimulus(EStimulusType.VIS_AND_SOMA))


        for (i in 1..5) {
            val stimToAdd = Stimulus(EStimulusType.SOMA_TO_SENSORY)
            trailStims.add(stimToAdd)
        }

        trailStims.shuffle()
        Log.d("TAG", "generateStimuli-trailStims: ${trailStims.size}")
        trailStims.map { stims.add(0, it) }

        Log.d("TAG", "generateStimuli: Mix Both List => ${stims.size}")
    }

    private fun showPracticeTestDialog(){
        mainActivity.showDialog(
            isOkDialog = true,
            message = resources.getString(R.string.terminate_test_msg),
            R.drawable.practice_icon,
            null,
            null,
            object : IDialogOnClickListener {
                override fun onOk(dialog: AlertDialog) {
                    dialog.dismiss()
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}