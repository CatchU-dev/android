package com.isaacsufyan.catchu.presentation.fragments.demo_graphic

import android.view.View
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import com.isaacsufyan.catchu.R
import com.isaacsufyan.catchu.adapter.MaterialArrayAdapter
import com.isaacsufyan.catchu.domain.enum.EAnswer

class DemoGraphicAndMedicalHistorySeg2 constructor(
    var fragment: DemoGraphicAndMedicalHistoryFragment
) : View.OnClickListener {

    private var binding = fragment.binding.includeSeg2

    init {
        setListener()
        setupVisionProblemRate()
    }

    private fun setupVisionProblemRate() {
        val vpRate = listOf(
            "Mild",
            "Moderate",
            "Severe"
        )
        val materialAdapter = MaterialArrayAdapter(
            fragment.requireContext(),
            R.layout.simple_list_item,
            vpRate,
            binding.includeVisionProblems.edtVisionRate.simpleItemSelectedColor,
            binding.includeVisionProblems.edtVisionRate.simpleItemSelectedRippleColor
        )
        binding.includeVisionProblems.edtVisionRate.setAdapter(materialAdapter)

        binding.includeVisionProblems.edtVisionRate.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val selectedText = vpRate[position]
                fragment.rawMeasurements.hasVisionLoss = position + 1
                materialAdapter.updateColor(selectedText)
            }
    }

    private fun setListener() {

        binding.tvChronicKidneyYes.setOnClickListener(this)
        binding.tvChronicKidneyNo.setOnClickListener(this)
        binding.includeKidneyDiseaseSubQuestions.tvDialysisYes.setOnClickListener(this)
        binding.includeKidneyDiseaseSubQuestions.tvDialysisNo.setOnClickListener(this)

        binding.tvVisionYes.setOnClickListener(this)
        binding.tvVisionNo.setOnClickListener(this)
        binding.includeVisionProblems.tvVisionGlassesYes.setOnClickListener(this)
        binding.includeVisionProblems.tvVisionGlassesNo.setOnClickListener(this)

        binding.tvHearingProblemYes.setOnClickListener(this)
        binding.tvHearingProblemNo.setOnClickListener(this)
        binding.includeHearingProblems.tvHearingAidYes.setOnClickListener(this)
        binding.includeHearingProblems.tvHearingAidNo.setOnClickListener(this)

        binding.tvFallInPastOneYearYes.setOnClickListener(this)
        binding.tvFallInPastOneYearNo.setOnClickListener(this)
        binding.includeFallInPastOneYearSubQuestions.tvFallInPastOneYearFractureYes.setOnClickListener(
            this
        )
        binding.includeFallInPastOneYearSubQuestions.tvFallInPastOneYearFractureNo.setOnClickListener(
            this
        )

        binding.tvFallInPastFiveYearYes.setOnClickListener(this)
        binding.tvFallInPastFiveYearNo.setOnClickListener(this)
        binding.includeFallInPastFiveYearSubQuestions.tvFallInPastFiveYearFractureYes.setOnClickListener(
            this
        )
        binding.includeFallInPastFiveYearSubQuestions.tvFallInPastFiveYearFractureNo.setOnClickListener(
            this
        )

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            binding.tvChronicKidneyYes.id -> {
                setChronicDisease(EAnswer.YES)
            }
            binding.tvChronicKidneyNo.id -> {
                setChronicDisease(EAnswer.NO)
            }
            binding.includeKidneyDiseaseSubQuestions.tvDialysisYes.id -> {
                setOnDialysis(EAnswer.YES)
            }
            binding.includeKidneyDiseaseSubQuestions.tvDialysisNo.id -> {
                setOnDialysis(EAnswer.NO)
            }

            binding.tvVisionYes.id -> {
                setVisionProblem(EAnswer.YES)
            }
            binding.tvVisionNo.id -> {
                setVisionProblem(EAnswer.NO)
            }
            binding.includeVisionProblems.tvVisionGlassesYes.id -> {
                setVisionGlasses(EAnswer.YES)
            }
            binding.includeVisionProblems.tvVisionGlassesNo.id -> {
                setVisionGlasses(EAnswer.NO)
            }

            binding.tvHearingProblemYes.id -> {
                setHearingProblem(EAnswer.YES)
            }
            binding.tvHearingProblemNo.id -> {
                setHearingProblem(EAnswer.NO)
            }
            binding.includeHearingProblems.tvHearingAidYes.id -> {
                setHearingAid(EAnswer.YES)
            }
            binding.includeHearingProblems.tvHearingAidNo.id -> {
                setHearingAid(EAnswer.NO)
            }

            binding.tvFallInPastOneYearYes.id -> {
                setFallInOneYear(EAnswer.YES)
            }
            binding.tvFallInPastOneYearNo.id -> {
                setFallInOneYear(EAnswer.NO)
            }
            binding.includeFallInPastOneYearSubQuestions.tvFallInPastOneYearFractureYes.id -> {
                setFallInOneYearFracture(EAnswer.YES)
            }
            binding.includeFallInPastOneYearSubQuestions.tvFallInPastOneYearFractureNo.id -> {
                setFallInOneYearFracture(EAnswer.NO)
            }

            binding.tvFallInPastFiveYearYes.id -> {
                setFallInFiveYear(EAnswer.YES)
            }
            binding.tvFallInPastFiveYearNo.id -> {
                setFallInFiveYear(EAnswer.NO)
            }
            binding.includeFallInPastFiveYearSubQuestions.tvFallInPastFiveYearFractureYes.id -> {
                setFallInFiveYearFracture(EAnswer.YES)
            }
            binding.includeFallInPastFiveYearSubQuestions.tvFallInPastFiveYearFractureNo.id -> {
                setFallInFiveYearFracture(EAnswer.NO)
            }
        }
    }

    private fun setChronicDisease(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasChronicKidney = eAnswer.value
        binding.tvChronicKidneyError.visibility = View.GONE
        val view = binding.includeKidneyDiseaseSubQuestions.root
        when (eAnswer) {
            EAnswer.NO -> {
                binding.tvChronicKidneyNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvChronicKidneyYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvChronicKidneyNo.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.tvChronicKidneyYes.setTextColor(fragment.getPrimaryColor())

                if (view.visibility == View.VISIBLE) {
                    val animSlideDown =
                        AnimationUtils.loadAnimation(fragment.requireContext(), R.anim.slide_up)
                    view.startAnimation(animSlideDown)
                    view.visibility = View.GONE
                }

            }
            EAnswer.YES -> {
                binding.tvChronicKidneyYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvChronicKidneyNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvChronicKidneyYes.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.tvChronicKidneyNo.setTextColor(fragment.getPrimaryColor())

                if (view.visibility == View.GONE) {
                    val animSlideDown =
                        AnimationUtils.loadAnimation(fragment.requireContext(), R.anim.slide_down)
                    view.startAnimation(animSlideDown)
                    view.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setOnDialysis(eAnswer: EAnswer) {
        fragment.rawMeasurements.onDialysis = eAnswer.value
        binding.includeKidneyDiseaseSubQuestions.tvDialysisError.visibility = View.GONE
        when (eAnswer) {
            EAnswer.NO -> {
                binding.includeKidneyDiseaseSubQuestions.tvDialysisNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.includeKidneyDiseaseSubQuestions.tvDialysisYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.includeKidneyDiseaseSubQuestions.tvDialysisNo.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.includeKidneyDiseaseSubQuestions.tvDialysisYes.setTextColor(fragment.getPrimaryColor())

            }
            EAnswer.YES -> {
                binding.includeKidneyDiseaseSubQuestions.tvDialysisYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.includeKidneyDiseaseSubQuestions.tvDialysisNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.includeKidneyDiseaseSubQuestions.tvDialysisYes.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.includeKidneyDiseaseSubQuestions.tvDialysisNo.setTextColor(fragment.getPrimaryColor())
            }
        }
    }

    private fun setVisionProblem(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasVisionProblem = eAnswer.value
        fragment.rawMeasurements.visualImpairments = eAnswer.value
        binding.tvVisionError.visibility = View.GONE
        val view = binding.includeVisionProblems.root
        when (eAnswer) {
            EAnswer.NO -> {
                binding.tvVisionNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvVisionYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvVisionNo.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.tvVisionYes.setTextColor(fragment.getPrimaryColor())
                if (view.visibility == View.VISIBLE) {
                    val animSlideDown =
                        AnimationUtils.loadAnimation(fragment.requireContext(), R.anim.slide_up)
                    view.startAnimation(animSlideDown)
                    view.visibility = View.GONE
                }
            }
            EAnswer.YES -> {
                binding.tvVisionYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvVisionNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvVisionYes.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.tvVisionNo.setTextColor(fragment.getPrimaryColor())

                if (view.visibility == View.GONE) {
                    val animSlideDown =
                        AnimationUtils.loadAnimation(fragment.requireContext(), R.anim.slide_down)
                    view.startAnimation(animSlideDown)
                    view.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setVisionGlasses(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasCorrectiveLenses = eAnswer.value
        binding.includeVisionProblems.tvVisionGlassesError.visibility = View.GONE
        when (eAnswer) {
            EAnswer.NO -> {
                binding.includeVisionProblems.tvVisionGlassesNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.includeVisionProblems.tvVisionGlassesYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.includeVisionProblems.tvVisionGlassesNo.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.includeVisionProblems.tvVisionGlassesYes.setTextColor(fragment.getPrimaryColor())
            }
            EAnswer.YES -> {
                binding.includeVisionProblems.tvVisionGlassesYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.includeVisionProblems.tvVisionGlassesNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.includeVisionProblems.tvVisionGlassesYes.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.includeVisionProblems.tvVisionGlassesNo.setTextColor(fragment.getPrimaryColor())
            }
        }
    }

    private fun setHearingProblem(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasSevereHearingLoss = eAnswer.value
        binding.tvHearingProblemError.visibility = View.GONE
        val view = binding.includeHearingProblems.root
        when (eAnswer) {
            EAnswer.NO -> {
                binding.tvHearingProblemNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvHearingProblemYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvHearingProblemNo.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.tvHearingProblemYes.setTextColor(fragment.getPrimaryColor())
                if (view.visibility == View.VISIBLE) {
                    val animSlideDown =
                        AnimationUtils.loadAnimation(fragment.requireContext(), R.anim.slide_up)
                    view.startAnimation(animSlideDown)
                    view.visibility = View.GONE
                }
            }
            EAnswer.YES -> {
                binding.tvHearingProblemYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvHearingProblemNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvHearingProblemYes.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.tvHearingProblemNo.setTextColor(fragment.getPrimaryColor())

                if (view.visibility == View.GONE) {
                    val animSlideDown =
                        AnimationUtils.loadAnimation(fragment.requireContext(), R.anim.slide_down)
                    view.startAnimation(animSlideDown)
                    view.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setHearingAid(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasHearingAid = eAnswer.value
        binding.includeHearingProblems.tvHearingAidError.visibility = View.GONE
        when (eAnswer) {
            EAnswer.NO -> {
                binding.includeHearingProblems.tvHearingAidNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.includeHearingProblems.tvHearingAidYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.includeHearingProblems.tvHearingAidNo.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.includeHearingProblems.tvHearingAidYes.setTextColor(fragment.getPrimaryColor())
            }
            EAnswer.YES -> {
                binding.includeHearingProblems.tvHearingAidYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.includeHearingProblems.tvHearingAidNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.includeHearingProblems.tvHearingAidYes.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.includeHearingProblems.tvHearingAidNo.setTextColor(fragment.getPrimaryColor())
            }
        }
    }

    private fun setFallInOneYear(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasHadAFall = eAnswer.value
        binding.tvFallInPastOneYearError.visibility = View.GONE
        val view = binding.includeFallInPastOneYearSubQuestions.root
        when (eAnswer) {
            EAnswer.NO -> {
                binding.tvFallInPastOneYearNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvFallInPastOneYearYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvFallInPastOneYearNo.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.tvFallInPastOneYearYes.setTextColor(fragment.getPrimaryColor())
                if (view.visibility == View.VISIBLE) {
                    val animSlideDown =
                        AnimationUtils.loadAnimation(fragment.requireContext(), R.anim.slide_up)
                    view.startAnimation(animSlideDown)
                    view.visibility = View.GONE
                }
            }
            EAnswer.YES -> {
                binding.tvFallInPastOneYearYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvFallInPastOneYearNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvFallInPastOneYearYes.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.tvFallInPastOneYearNo.setTextColor(fragment.getPrimaryColor())

                if (view.visibility == View.GONE) {
                    val animSlideDown =
                        AnimationUtils.loadAnimation(fragment.requireContext(), R.anim.slide_down)
                    view.startAnimation(animSlideDown)
                    view.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setFallInOneYearFracture(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasHadAFracture = eAnswer.value
        binding.includeFallInPastOneYearSubQuestions.tvFallInPastOneYearFractureError.visibility =
            View.GONE
        when (eAnswer) {
            EAnswer.NO -> {
                binding.includeFallInPastOneYearSubQuestions.tvFallInPastOneYearFractureNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.includeFallInPastOneYearSubQuestions.tvFallInPastOneYearFractureYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.includeFallInPastOneYearSubQuestions.tvFallInPastOneYearFractureNo.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.includeFallInPastOneYearSubQuestions.tvFallInPastOneYearFractureYes.setTextColor(
                    fragment.getPrimaryColor()
                )
            }
            EAnswer.YES -> {
                binding.includeFallInPastOneYearSubQuestions.tvFallInPastOneYearFractureYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.includeFallInPastOneYearSubQuestions.tvFallInPastOneYearFractureNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.includeFallInPastOneYearSubQuestions.tvFallInPastOneYearFractureYes.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.includeFallInPastOneYearSubQuestions.tvFallInPastOneYearFractureNo.setTextColor(
                    fragment.getPrimaryColor()
                )
            }
        }
    }

    private fun setFallInFiveYear(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasHadAFallPastFiveYears = eAnswer.value
        binding.tvFallInPastFiveYearError.visibility = View.GONE
        val view = binding.includeFallInPastFiveYearSubQuestions.root
        when (eAnswer) {
            EAnswer.NO -> {
                binding.tvFallInPastFiveYearNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvFallInPastFiveYearYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvFallInPastFiveYearNo.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.tvFallInPastFiveYearYes.setTextColor(fragment.getPrimaryColor())
                if (view.visibility == View.VISIBLE) {
                    val animSlideDown =
                        AnimationUtils.loadAnimation(fragment.requireContext(), R.anim.slide_up)
                    view.startAnimation(animSlideDown)
                    view.visibility = View.GONE
                }
            }
            EAnswer.YES -> {
                binding.tvFallInPastFiveYearYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvFallInPastFiveYearNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvFallInPastFiveYearYes.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.tvFallInPastFiveYearNo.setTextColor(fragment.getPrimaryColor())

                if (view.visibility == View.GONE) {
                    val animSlideDown =
                        AnimationUtils.loadAnimation(fragment.requireContext(), R.anim.slide_down)
                    view.startAnimation(animSlideDown)
                    view.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setFallInFiveYearFracture(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasHadAFracturePastFiveYears = eAnswer.value
        binding.includeFallInPastFiveYearSubQuestions.tvFallInPastFiveYearFractureError.visibility =
            View.GONE
        when (eAnswer) {
            EAnswer.NO -> {
                binding.includeFallInPastFiveYearSubQuestions.tvFallInPastFiveYearFractureNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.includeFallInPastFiveYearSubQuestions.tvFallInPastFiveYearFractureYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.includeFallInPastFiveYearSubQuestions.tvFallInPastFiveYearFractureNo.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.includeFallInPastFiveYearSubQuestions.tvFallInPastFiveYearFractureYes.setTextColor(
                    fragment.getPrimaryColor()
                )
            }
            EAnswer.YES -> {
                binding.includeFallInPastFiveYearSubQuestions.tvFallInPastFiveYearFractureYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.includeFallInPastFiveYearSubQuestions.tvFallInPastFiveYearFractureNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.includeFallInPastFiveYearSubQuestions.tvFallInPastFiveYearFractureYes.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.includeFallInPastFiveYearSubQuestions.tvFallInPastFiveYearFractureNo.setTextColor(
                    fragment.getPrimaryColor()
                )
            }
        }
    }

}