package com.isaacsufyan.catchu.presentation.fragments.demo_graphic

import android.view.View
import android.view.animation.AnimationUtils
import com.isaacsufyan.catchu.R
import com.isaacsufyan.catchu.domain.enum.EAnswer
import com.isaacsufyan.catchu.utils.hideSoftKeyboard

class DemoGraphicAndMedicalHistorySeg3(
    var fragment: DemoGraphicAndMedicalHistoryFragment
) : View.OnClickListener {

    private var binding = fragment.binding.includeSeg3

    init {
        setListener()
    }

    private fun setListener() {

        binding.tvFearOfFallingYes.setOnClickListener(this)
        binding.tvFearOfFallingNo.setOnClickListener(this)

        binding.tvWalkWithoutBreakYes.setOnClickListener(this)
        binding.tvWalkWithoutBreakNo.setOnClickListener(this)

        binding.tvWalkingProblemYes.setOnClickListener(this)
        binding.tvWalkingProblemNo.setOnClickListener(this)
        binding.includeWalkingProblem.tvWalkingProblemCaneYes.setOnClickListener(this)
        binding.includeWalkingProblem.tvWalkingProblemCaneNo.setOnClickListener(this)
        binding.includeWalkingProblem.tvWalkingProblemWalkerYes.setOnClickListener(this)
        binding.includeWalkingProblem.tvWalkingProblemWalkerNo.setOnClickListener(this)

        binding.tvLowChairDifficultyYes.setOnClickListener(this)
        binding.tvLowChairDifficultyNo.setOnClickListener(this)

        binding.tvHoldWallWhileWalkYes.setOnClickListener(this)
        binding.tvHoldWallWhileWalkNo.setOnClickListener(this)

        binding.tvDifficultyWithBalanceYes.setOnClickListener(this)
        binding.tvDifficultyWithBalanceNo.setOnClickListener(this)

        binding.edtRegularlyMedicine.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus) {
                v.hideSoftKeyboard()
            }
        }

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            binding.tvFearOfFallingYes.id -> {
                setFearOfFalling(EAnswer.YES)
            }
            binding.tvFearOfFallingNo.id -> {
                setFearOfFalling(EAnswer.NO)
            }
            binding.tvWalkWithoutBreakYes.id -> {
                setWalkWithoutBreakingBreak(EAnswer.YES)
            }
            binding.tvWalkWithoutBreakNo.id -> {
                setWalkWithoutBreakingBreak(EAnswer.NO)
            }
            binding.tvWalkingProblemYes.id -> {
                setWalkingProblem(EAnswer.YES)
            }
            binding.tvWalkingProblemNo.id -> {
                setWalkingProblem(EAnswer.NO)
            }
            binding.includeWalkingProblem.tvWalkingProblemCaneYes.id -> {
                setWalkingProblemUseCane(EAnswer.YES)
            }
            binding.includeWalkingProblem.tvWalkingProblemCaneNo.id -> {
                setWalkingProblemUseCane(EAnswer.NO)
            }
            binding.includeWalkingProblem.tvWalkingProblemWalkerYes.id -> {
                setWalkingProblemUseWalker(EAnswer.YES)
            }
            binding.includeWalkingProblem.tvWalkingProblemWalkerNo.id -> {
                setWalkingProblemUseWalker(EAnswer.NO)
            }

            binding.tvLowChairDifficultyYes.id -> {
                setLowChairDifficulty(EAnswer.YES)
            }
            binding.tvLowChairDifficultyNo.id -> {
                setLowChairDifficulty(EAnswer.NO)
            }

            binding.tvHoldWallWhileWalkYes.id -> {
                setHoldWallWhileWalk(EAnswer.YES)
            }
            binding.tvHoldWallWhileWalkNo.id -> {
                setHoldWallWhileWalk(EAnswer.NO)
            }

            binding.tvDifficultyWithBalanceYes.id -> {
                setDifficultyWithBalance(EAnswer.YES)
            }
            binding.tvDifficultyWithBalanceNo.id -> {
                setDifficultyWithBalance(EAnswer.NO)
            }
        }
    }

    private fun setFearOfFalling(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasFearOfFalling = eAnswer.value
        binding.tvFearOfFallingError.visibility = View.GONE
        when (eAnswer) {
            EAnswer.NO -> {
                binding.tvFearOfFallingNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvFearOfFallingYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvFearOfFallingNo.setTextColor(fragment.getColor(R.color.white))
                binding.tvFearOfFallingYes.setTextColor(fragment.getPrimaryColor())
            }
            EAnswer.YES -> {
                binding.tvFearOfFallingYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvFearOfFallingNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvFearOfFallingYes.setTextColor(fragment.getColor(R.color.white))
                binding.tvFearOfFallingNo.setTextColor(fragment.getPrimaryColor())
            }
        }
    }

    private fun setWalkWithoutBreakingBreak(eAnswer: EAnswer) {
        fragment.rawMeasurements.canWalkWithoutBreak = eAnswer.value
        binding.tvWalkWithoutBreakError.visibility = View.GONE
        when (eAnswer) {
            EAnswer.NO -> {
                binding.tvWalkWithoutBreakNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvWalkWithoutBreakYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvWalkWithoutBreakNo.setTextColor(fragment.getColor(R.color.white))
                binding.tvWalkWithoutBreakYes.setTextColor(fragment.getPrimaryColor())
            }
            EAnswer.YES -> {
                binding.tvWalkWithoutBreakYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvWalkWithoutBreakNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvWalkWithoutBreakYes.setTextColor(fragment.getColor(R.color.white))
                binding.tvWalkWithoutBreakNo.setTextColor(fragment.getPrimaryColor())
            }
        }
    }

    private fun setWalkingProblem(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasDifficultyWalking = eAnswer.value
        binding.tvWalkingProblemError.visibility = View.GONE
        val view = binding.includeWalkingProblem.root
        when (eAnswer) {
            EAnswer.NO -> {
                fragment.rawMeasurements.hasDifficultyWalkingUseCane = EAnswer.NO.value
                fragment.rawMeasurements.hasDifficultyWalkingUseWalker = EAnswer.NO.value
                binding.tvWalkingProblemNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvWalkingProblemYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvWalkingProblemNo.setTextColor(fragment.getColor(R.color.white))
                binding.tvWalkingProblemYes.setTextColor(fragment.getPrimaryColor())

                if (view.visibility == View.VISIBLE) {
                    val animSlideDown =
                        AnimationUtils.loadAnimation(fragment.requireContext(), R.anim.slide_up)
                    view.startAnimation(animSlideDown)
                    view.visibility = View.GONE
                }
            }
            EAnswer.YES -> {
                binding.tvWalkingProblemYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvWalkingProblemNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvWalkingProblemYes.setTextColor(fragment.getColor(R.color.white))
                binding.tvWalkingProblemNo.setTextColor(fragment.getPrimaryColor())

                if (view.visibility == View.GONE) {
                    val animSlideDown =
                        AnimationUtils.loadAnimation(fragment.requireContext(), R.anim.slide_down)
                    view.startAnimation(animSlideDown)
                    view.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setWalkingProblemUseCane(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasDifficultyWalkingUseCane = eAnswer.value
        binding.includeWalkingProblem.tvWalkingProblemCaneError.visibility = View.GONE
        when (eAnswer) {
            EAnswer.NO -> {
                binding.includeWalkingProblem.tvWalkingProblemCaneNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.includeWalkingProblem.tvWalkingProblemCaneYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.includeWalkingProblem.tvWalkingProblemCaneNo.setTextColor(fragment.getColor(R.color.white))
                binding.includeWalkingProblem.tvWalkingProblemCaneYes.setTextColor(fragment.getPrimaryColor())
            }
            EAnswer.YES -> {
                binding.includeWalkingProblem.tvWalkingProblemCaneYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.includeWalkingProblem.tvWalkingProblemCaneNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.includeWalkingProblem.tvWalkingProblemCaneYes.setTextColor(fragment.getColor(R.color.white))
                binding.includeWalkingProblem.tvWalkingProblemCaneNo.setTextColor(fragment.getPrimaryColor())
            }
        }
    }

    private fun setWalkingProblemUseWalker(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasDifficultyWalkingUseWalker = eAnswer.value
        binding.includeWalkingProblem.tvWalkingProblemWalkerError.visibility = View.GONE
        when (eAnswer) {
            EAnswer.NO -> {
                binding.includeWalkingProblem.tvWalkingProblemWalkerNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.includeWalkingProblem.tvWalkingProblemWalkerYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.includeWalkingProblem.tvWalkingProblemWalkerNo.setTextColor(fragment.getColor(R.color.white))
                binding.includeWalkingProblem.tvWalkingProblemWalkerYes.setTextColor(fragment.getPrimaryColor())
            }
            EAnswer.YES -> {
                binding.includeWalkingProblem.tvWalkingProblemWalkerYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.includeWalkingProblem.tvWalkingProblemWalkerNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.includeWalkingProblem.tvWalkingProblemWalkerYes.setTextColor(fragment.getColor(R.color.white))
                binding.includeWalkingProblem.tvWalkingProblemWalkerNo.setTextColor(fragment.getPrimaryColor())
            }
        }
    }

    private fun setLowChairDifficulty(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasDifficultyGettingUp = eAnswer.value
        binding.tvLowChairDifficultyError.visibility = View.GONE
        when (eAnswer) {
            EAnswer.NO -> {
                binding.tvLowChairDifficultyNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvLowChairDifficultyYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvLowChairDifficultyNo.setTextColor(fragment.getColor(R.color.white))
                binding.tvLowChairDifficultyYes.setTextColor(fragment.getPrimaryColor())
            }
            EAnswer.YES -> {
                binding.tvLowChairDifficultyYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvLowChairDifficultyNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvLowChairDifficultyYes.setTextColor(fragment.getColor(R.color.white))
                binding.tvLowChairDifficultyNo.setTextColor(fragment.getPrimaryColor())
            }
        }
    }

    private fun setHoldWallWhileWalk(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasToHoldOntoWalls = eAnswer.value
        binding.tvHoldWallWhileWalkError.visibility = View.GONE
        when (eAnswer) {
            EAnswer.NO -> {
                binding.tvHoldWallWhileWalkNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvHoldWallWhileWalkYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvHoldWallWhileWalkNo.setTextColor(fragment.getColor(R.color.white))
                binding.tvHoldWallWhileWalkYes.setTextColor(fragment.getPrimaryColor())
            }
            EAnswer.YES -> {
                binding.tvHoldWallWhileWalkYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvHoldWallWhileWalkNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvHoldWallWhileWalkYes.setTextColor(fragment.getColor(R.color.white))
                binding.tvHoldWallWhileWalkNo.setTextColor(fragment.getPrimaryColor())
            }
        }
    }

    private fun setDifficultyWithBalance(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasDifficultyWithBalance = eAnswer.value
        binding.tvDifficultyWithBalanceError.visibility = View.GONE
        when (eAnswer) {
            EAnswer.NO -> {
                binding.tvDifficultyWithBalanceNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvDifficultyWithBalanceYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvDifficultyWithBalanceNo.setTextColor(fragment.getColor(R.color.white))
                binding.tvDifficultyWithBalanceYes.setTextColor(fragment.getPrimaryColor())
            }
            EAnswer.YES -> {
                binding.tvDifficultyWithBalanceYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvDifficultyWithBalanceNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvDifficultyWithBalanceYes.setTextColor(fragment.getColor(R.color.white))
                binding.tvDifficultyWithBalanceNo.setTextColor(fragment.getPrimaryColor())
            }
        }
    }

}