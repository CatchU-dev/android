package com.isaacsufyan.catchu.presentation.fragments.demo_graphic

import android.view.View
import android.view.animation.AnimationUtils
import com.isaacsufyan.catchu.R
import com.isaacsufyan.catchu.domain.enum.EAnswer
import com.isaacsufyan.catchu.domain.model.RawMeasurements
import com.isaacsufyan.catchu.utils.hideSoftKeyboard

class DemoGraphicAndMedicalHistorySeg1 constructor(
    var fragment: DemoGraphicAndMedicalHistoryFragment
) : View.OnClickListener {

    private var binding = fragment.binding.includeSeg1

    init {
        setListener()
    }

    private fun setListener() {

        binding.tvHypertensionYes.setOnClickListener(this)
        binding.tvHypertensionNo.setOnClickListener(this)

        binding.tvArthritisYes.setOnClickListener(this)
        binding.tvArthritisNo.setOnClickListener(this)

        binding.tvDiabetesNo.setOnClickListener(this)
        binding.tvDiabetesYes.setOnClickListener(this)

        binding.includeDiabetesSubQuestions.tvDiabetesVisionYes.setOnClickListener(this)
        binding.includeDiabetesSubQuestions.tvDiabetesVisionNo.setOnClickListener(this)

        binding.includeDiabetesSubQuestions.tvDiabetesKidneyYes.setOnClickListener(this)
        binding.includeDiabetesSubQuestions.tvDiabetesKidneyNo.setOnClickListener(this)

        binding.includeDiabetesSubQuestions.tvDiabetesNerveYes.setOnClickListener(this)
        binding.includeDiabetesSubQuestions.tvDiabetesNerveNo.setOnClickListener(this)

        binding.includeDiabetesSubQuestions.tvDiabetesTakingMedicineYes.setOnClickListener(
            this
        )
        binding.includeDiabetesSubQuestions.tvDiabetesTakingMedicineNo.setOnClickListener(
            this
        )

        binding.includeDiabetesSubQuestions.tvDiabetesOralMedicineYes.setOnClickListener(
            this
        )
        binding.includeDiabetesSubQuestions.tvDiabetesOralMedicineNo.setOnClickListener(
            this
        )

        binding.includeDiabetesSubQuestions.tvDiabetesInsulinYes.setOnClickListener(this)
        binding.includeDiabetesSubQuestions.tvDiabetesInsulinNo.setOnClickListener(this)

        binding.includeDiabetesSubQuestions.tvDiabetesInjectionYes.setOnClickListener(
            this
        )
        binding.includeDiabetesSubQuestions.tvDiabetesInjectionNo.setOnClickListener(
            this
        )

        binding.tvDepressionYes.setOnClickListener(this)
        binding.tvDepressionNo.setOnClickListener(this)

        binding.tvChronicYes.setOnClickListener(this)
        binding.tvChronicNo.setOnClickListener(this)

        binding.tvOsteoporosisYes.setOnClickListener(this)
        binding.tvOsteoporosisNo.setOnClickListener(this)

        binding.tvAnxietyYes.setOnClickListener(this)
        binding.tvAnxietyNo.setOnClickListener(this)

        binding.tvStrokeYes.setOnClickListener(this)
        binding.tvStrokeNo.setOnClickListener(this)

        binding.tvHeartDiseaseYes.setOnClickListener(this)
        binding.tvHeartDiseaseNo.setOnClickListener(this)

        binding.includeHeartDiseasesSubQuestions.tvHeartFailureYes.setOnClickListener(
            this
        )
        binding.includeHeartDiseasesSubQuestions.tvHeartFailureNo.setOnClickListener(
            this
        )

        binding.includeHeartDiseasesSubQuestions.tvMyocardialYes.setOnClickListener(this)
        binding.includeHeartDiseasesSubQuestions.tvMyocardialNo.setOnClickListener(this)

        binding.tvParkinsonDiseaseYes.setOnClickListener(this)
        binding.tvParkinsonDiseaseNo.setOnClickListener(this)

        binding.tvMemoryProblemYes.setOnClickListener(this)
        binding.tvMemoryProblemNo.setOnClickListener(this)

        binding.includeMemoryProblemSubQuestions.tvDementiaYes.setOnClickListener(this)
        binding.includeMemoryProblemSubQuestions.tvDementiaNo.setOnClickListener(this)

        binding.tvHighCholesterolYes.setOnClickListener(this)
        binding.tvHighCholesterolNo.setOnClickListener(this)

        binding.tvSeizureYes.setOnClickListener(this)
        binding.tvSeizureNo.setOnClickListener(this)

        binding.tvNeuropathyYes.setOnClickListener(this)
        binding.tvNeuropathyNo.setOnClickListener(this)

        binding.includeDiabetesSubQuestions.edtDiabetesYear.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus) {
                v.hideSoftKeyboard()
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {

            binding.tvHypertensionYes.id -> {
                setHypertension(EAnswer.YES)
            }
            binding.tvHypertensionNo.id -> {
                setHypertension(EAnswer.NO)
            }

            binding.tvArthritisYes.id -> {
                setArthritics(EAnswer.YES)
            }
            binding.tvArthritisNo.id -> {
                setArthritics(EAnswer.NO)
            }

            binding.tvDiabetesYes.id -> {
                setDiabetes(EAnswer.YES)
            }
            binding.tvDiabetesNo.id -> {
                setDiabetes(EAnswer.NO)
            }

            binding.includeDiabetesSubQuestions.tvDiabetesVisionYes.id -> {
                setDiabetesVision(EAnswer.YES)
            }
            binding.includeDiabetesSubQuestions.tvDiabetesVisionNo.id -> {
                setDiabetesVision(EAnswer.NO)
            }

            binding.includeDiabetesSubQuestions.tvDiabetesKidneyYes.id -> {
                setDiabetesKidney(EAnswer.YES)
            }
            binding.includeDiabetesSubQuestions.tvDiabetesKidneyNo.id -> {
                setDiabetesKidney(EAnswer.NO)
            }

            binding.includeDiabetesSubQuestions.tvDiabetesNerveYes.id -> {
                setDiabetesNerve(EAnswer.YES)
            }
            binding.includeDiabetesSubQuestions.tvDiabetesNerveNo.id -> {
                setDiabetesNerve(EAnswer.NO)
            }

            binding.includeDiabetesSubQuestions.tvDiabetesTakingMedicineYes.id -> {
                setDiabetesTakingMedicine(EAnswer.YES)
            }
            binding.includeDiabetesSubQuestions.tvDiabetesTakingMedicineNo.id -> {
                setDiabetesTakingMedicine(EAnswer.NO)
            }

            binding.includeDiabetesSubQuestions.tvDiabetesOralMedicineYes.id -> {
                setDiabetesOralMedicine(EAnswer.YES)
            }
            binding.includeDiabetesSubQuestions.tvDiabetesOralMedicineNo.id -> {
                setDiabetesOralMedicine(EAnswer.NO)
            }

            binding.includeDiabetesSubQuestions.tvDiabetesInsulinYes.id -> {
                setDiabetesInsulinMedicine(EAnswer.YES)
            }
            binding.includeDiabetesSubQuestions.tvDiabetesInsulinNo.id -> {
                setDiabetesInsulinMedicine(EAnswer.NO)
            }

            binding.includeDiabetesSubQuestions.tvDiabetesInjectionYes.id -> {
                setDiabetesInjectionMedicine(EAnswer.YES)
            }
            binding.includeDiabetesSubQuestions.tvDiabetesInjectionNo.id -> {
                setDiabetesInjectionMedicine(EAnswer.NO)
            }

            binding.tvDepressionYes.id -> {
                setDepression(EAnswer.YES)
            }
            binding.tvDepressionNo.id -> {
                setDepression(EAnswer.NO)
            }

            binding.tvChronicYes.id -> {
                setChronic(EAnswer.YES)
            }
            binding.tvChronicNo.id -> {
                setChronic(EAnswer.NO)
            }

            binding.tvOsteoporosisYes.id -> {
                setOsteoporosis(EAnswer.YES)
            }
            binding.tvOsteoporosisNo.id -> {
                setOsteoporosis(EAnswer.NO)
            }
            binding.tvAnxietyYes.id -> {
                setAnxiety(EAnswer.YES)
            }
            binding.tvAnxietyNo.id -> {
                setAnxiety(EAnswer.NO)
            }
            binding.tvStrokeYes.id -> {
                setStroke(EAnswer.YES)
            }
            binding.tvStrokeNo.id -> {
                setStroke(EAnswer.NO)
            }
            binding.tvHeartDiseaseYes.id -> {
                setHeartDisease(EAnswer.YES)
            }
            binding.tvHeartDiseaseNo.id -> {
                setHeartDisease(EAnswer.NO)
            }
            binding.includeHeartDiseasesSubQuestions.tvHeartFailureYes.id -> {
                setHeartFailure(EAnswer.YES)
            }
            binding.includeHeartDiseasesSubQuestions.tvHeartFailureNo.id -> {
                setHeartFailure(EAnswer.NO)
            }
            binding.includeHeartDiseasesSubQuestions.tvMyocardialYes.id -> {
                setMI(EAnswer.YES)
            }
            binding.includeHeartDiseasesSubQuestions.tvMyocardialNo.id -> {
                setMI(EAnswer.NO)
            }
            binding.tvParkinsonDiseaseYes.id -> {
                setParkinson(EAnswer.YES)
            }
            binding.tvParkinsonDiseaseNo.id -> {
                setParkinson(EAnswer.NO)
            }
            binding.tvMemoryProblemYes.id -> {
                setMemoryProblem(EAnswer.YES)
            }
            binding.tvMemoryProblemNo.id -> {
                setMemoryProblem(EAnswer.NO)
            }
            binding.includeMemoryProblemSubQuestions.tvDementiaYes.id -> {
                setDementia(EAnswer.YES)
            }
            binding.includeMemoryProblemSubQuestions.tvDementiaNo.id -> {
                setDementia(EAnswer.NO)
            }
            binding.tvHighCholesterolYes.id -> {
                setHighCholesterol(EAnswer.YES)
            }
            binding.tvHighCholesterolNo.id -> {
                setHighCholesterol(EAnswer.NO)
            }
            binding.tvSeizureYes.id -> {
                setSeizure(EAnswer.YES)
            }
            binding.tvSeizureNo.id -> {
                setSeizure(EAnswer.NO)
            }
            binding.tvNeuropathyYes.id -> {
                setNeuropathy(EAnswer.YES)
            }
            binding.tvNeuropathyNo.id -> {
                setNeuropathy(EAnswer.NO)
            }
        }
    }

    private fun setHypertension(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasHypertension = eAnswer.value
        binding.tvHypertensionError.visibility = View.GONE
        when (eAnswer) {
            EAnswer.NO -> {
                binding.tvHypertensionNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvHypertensionYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvHypertensionNo.setTextColor(fragment.getColor(R.color.white))
                binding.tvHypertensionYes.setTextColor(fragment.getPrimaryColor())
            }
            EAnswer.YES -> {
                binding.tvHypertensionYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvHypertensionNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvHypertensionYes.setTextColor(fragment.getColor(R.color.white))
                binding.tvHypertensionNo.setTextColor(fragment.getPrimaryColor())
            }
        }
    }

    private fun setArthritics(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasArthritis = eAnswer.value
        binding.tvArthritisError.visibility = View.GONE
        when (eAnswer) {
            EAnswer.NO -> {
                binding.tvArthritisNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvArthritisYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvArthritisNo.setTextColor(fragment.getColor(R.color.white))
                binding.tvArthritisYes.setTextColor(fragment.getPrimaryColor())
            }
            EAnswer.YES -> {
                binding.tvArthritisYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvArthritisNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvArthritisYes.setTextColor(fragment.getColor(R.color.white))
                binding.tvArthritisNo.setTextColor(fragment.getPrimaryColor())
            }
        }
    }

    private fun setDiabetes(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasDiabetes = eAnswer.value
        if(eAnswer.value == 0){
            fragment.rawMeasurements.hasDiabetesTakOralMedicine = eAnswer.value
            fragment.rawMeasurements.hasDiabetesVision = eAnswer.value
            fragment.rawMeasurements.hasDiabetesKidney = eAnswer.value
            fragment.rawMeasurements.hasDiabetesNerve = eAnswer.value
            fragment.rawMeasurements.hasDiabetesTakOralMedicine = eAnswer.value
            fragment.rawMeasurements.hasDiabetesTakOralMedicine = eAnswer.value
            fragment.rawMeasurements.hasDiabetesTakInsulin = eAnswer.value
        }
        binding.tvDiabetesError.visibility = View.GONE
        val view = binding.includeDiabetesSubQuestions.root
        when (eAnswer) {
            EAnswer.NO -> {
                binding.tvDiabetesNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvDiabetesYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvDiabetesNo.setTextColor(fragment.getColor(R.color.white))
                binding.tvDiabetesYes.setTextColor(fragment.getPrimaryColor())

                if (view.visibility == View.VISIBLE) {
                    val animSlideDown =
                        AnimationUtils.loadAnimation(fragment.requireContext(), R.anim.slide_up)
                    view.startAnimation(animSlideDown)
                    view.visibility = View.GONE
                }
            }
            EAnswer.YES -> {
                binding.tvDiabetesYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvDiabetesNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvDiabetesYes.setTextColor(fragment.getColor(R.color.white))
                binding.tvDiabetesNo.setTextColor(fragment.getPrimaryColor())
                if (view.visibility == View.GONE) {
                    val animSlideDown =
                        AnimationUtils.loadAnimation(fragment.requireContext(), R.anim.slide_down)
                    view.startAnimation(animSlideDown)
                    view.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setDiabetesVision(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasDiabetesVision = eAnswer.value
        binding.includeDiabetesSubQuestions.tvDiabetesVisionError.visibility = View.GONE
        when (eAnswer) {
            EAnswer.NO -> {
                binding.includeDiabetesSubQuestions.tvDiabetesVisionNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.includeDiabetesSubQuestions.tvDiabetesVisionYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.includeDiabetesSubQuestions.tvDiabetesVisionNo.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.includeDiabetesSubQuestions.tvDiabetesVisionYes.setTextColor(fragment.getPrimaryColor())

            }
            EAnswer.YES -> {
                binding.includeDiabetesSubQuestions.tvDiabetesVisionYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.includeDiabetesSubQuestions.tvDiabetesVisionNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.includeDiabetesSubQuestions.tvDiabetesVisionYes.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.includeDiabetesSubQuestions.tvDiabetesVisionNo.setTextColor(fragment.getPrimaryColor())
            }
        }
    }

    private fun setDiabetesKidney(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasDiabetesKidney = eAnswer.value
        binding.includeDiabetesSubQuestions.tvDiabetesKidneyError.visibility = View.GONE
        when (eAnswer) {
            EAnswer.NO -> {
                binding.includeDiabetesSubQuestions.tvDiabetesKidneyNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.includeDiabetesSubQuestions.tvDiabetesKidneyYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.includeDiabetesSubQuestions.tvDiabetesKidneyNo.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.includeDiabetesSubQuestions.tvDiabetesKidneyYes.setTextColor(fragment.getPrimaryColor())

            }
            EAnswer.YES -> {
                binding.includeDiabetesSubQuestions.tvDiabetesKidneyYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.includeDiabetesSubQuestions.tvDiabetesKidneyNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.includeDiabetesSubQuestions.tvDiabetesKidneyYes.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.includeDiabetesSubQuestions.tvDiabetesKidneyNo.setTextColor(fragment.getPrimaryColor())
            }
        }
    }

    private fun setDiabetesNerve(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasDiabetesNerve = eAnswer.value
        binding.includeDiabetesSubQuestions.tvDiabetesNerveError.visibility = View.GONE
        when (eAnswer) {
            EAnswer.NO -> {
                binding.includeDiabetesSubQuestions.tvDiabetesNerveNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.includeDiabetesSubQuestions.tvDiabetesNerveYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.includeDiabetesSubQuestions.tvDiabetesNerveNo.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.includeDiabetesSubQuestions.tvDiabetesNerveYes.setTextColor(fragment.getPrimaryColor())

            }
            EAnswer.YES -> {
                binding.includeDiabetesSubQuestions.tvDiabetesNerveYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.includeDiabetesSubQuestions.tvDiabetesNerveNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.includeDiabetesSubQuestions.tvDiabetesNerveYes.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.includeDiabetesSubQuestions.tvDiabetesNerveNo.setTextColor(fragment.getPrimaryColor())
            }
        }
    }

    private fun setDiabetesTakingMedicine(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasDiabetesTakMedicine = eAnswer.value
        binding.includeDiabetesSubQuestions.tvDiabetesTakingMedicineError.visibility = View.GONE
        val view = binding.includeDiabetesSubQuestions.clDiabetesTakingMedicine
        when (eAnswer) {
            EAnswer.NO -> {
                binding.includeDiabetesSubQuestions.tvDiabetesTakingMedicineNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.includeDiabetesSubQuestions.tvDiabetesTakingMedicineYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.includeDiabetesSubQuestions.tvDiabetesTakingMedicineNo.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.includeDiabetesSubQuestions.tvDiabetesTakingMedicineYes.setTextColor(
                    fragment.getPrimaryColor()
                )

                if (view.visibility == View.VISIBLE) {
                    val animSlideDown =
                        AnimationUtils.loadAnimation(fragment.requireContext(), R.anim.slide_up)
                    view.startAnimation(animSlideDown)
                    view.visibility = View.GONE
                }
            }
            EAnswer.YES -> {
                binding.includeDiabetesSubQuestions.tvDiabetesTakingMedicineYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.includeDiabetesSubQuestions.tvDiabetesTakingMedicineNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.includeDiabetesSubQuestions.tvDiabetesTakingMedicineYes.setTextColor(
                    fragment.getColor(R.color.white)
                )
                binding.includeDiabetesSubQuestions.tvDiabetesTakingMedicineNo.setTextColor(fragment.getPrimaryColor())
                if (view.visibility == View.GONE) {
                    val animSlideDown =
                        AnimationUtils.loadAnimation(fragment.requireContext(), R.anim.slide_down)
                    view.startAnimation(animSlideDown)
                    view.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setDiabetesOralMedicine(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasDiabetesTakOralMedicine = eAnswer.value
        binding.includeDiabetesSubQuestions.tvDiabetesOralMedicineError.visibility = View.GONE
        when (eAnswer) {
            EAnswer.NO -> {
                binding.includeDiabetesSubQuestions.tvDiabetesOralMedicineNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.includeDiabetesSubQuestions.tvDiabetesOralMedicineYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.includeDiabetesSubQuestions.tvDiabetesOralMedicineNo.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.includeDiabetesSubQuestions.tvDiabetesOralMedicineYes.setTextColor(fragment.getPrimaryColor())

            }
            EAnswer.YES -> {
                binding.includeDiabetesSubQuestions.tvDiabetesOralMedicineYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.includeDiabetesSubQuestions.tvDiabetesOralMedicineNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.includeDiabetesSubQuestions.tvDiabetesOralMedicineYes.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.includeDiabetesSubQuestions.tvDiabetesOralMedicineNo.setTextColor(fragment.getPrimaryColor())
            }
        }
    }

    private fun setDiabetesInsulinMedicine(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasDiabetesTakInsulin = eAnswer.value
        binding.includeDiabetesSubQuestions.tvDiabetesInsulinError.visibility = View.GONE
        when (eAnswer) {
            EAnswer.NO -> {
                binding.includeDiabetesSubQuestions.tvDiabetesInsulinNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.includeDiabetesSubQuestions.tvDiabetesInsulinYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.includeDiabetesSubQuestions.tvDiabetesInsulinNo.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.includeDiabetesSubQuestions.tvDiabetesInsulinYes.setTextColor(fragment.getPrimaryColor())

            }
            EAnswer.YES -> {
                binding.includeDiabetesSubQuestions.tvDiabetesInsulinYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.includeDiabetesSubQuestions.tvDiabetesInsulinNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.includeDiabetesSubQuestions.tvDiabetesInsulinYes.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.includeDiabetesSubQuestions.tvDiabetesInsulinNo.setTextColor(fragment.getPrimaryColor())
            }
        }
    }

    private fun setDiabetesInjectionMedicine(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasDiabetesTakInjection = eAnswer.value
        binding.includeDiabetesSubQuestions.tvDiabetesInjectionError.visibility = View.GONE
        when (eAnswer) {
            EAnswer.NO -> {
                binding.includeDiabetesSubQuestions.tvDiabetesInjectionNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.includeDiabetesSubQuestions.tvDiabetesInjectionYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.includeDiabetesSubQuestions.tvDiabetesInjectionNo.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.includeDiabetesSubQuestions.tvDiabetesInjectionYes.setTextColor(fragment.getPrimaryColor())

            }
            EAnswer.YES -> {
                binding.includeDiabetesSubQuestions.tvDiabetesInjectionYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.includeDiabetesSubQuestions.tvDiabetesInjectionNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.includeDiabetesSubQuestions.tvDiabetesInjectionYes.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.includeDiabetesSubQuestions.tvDiabetesInjectionNo.setTextColor(fragment.getPrimaryColor())
            }
        }
    }

    private fun setDepression(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasDepression = eAnswer.value
        binding.tvDepressionError.visibility = View.GONE
        when (eAnswer) {
            EAnswer.NO -> {
                binding.tvDepressionNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvDepressionYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvDepressionNo.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.tvDepressionYes.setTextColor(fragment.getPrimaryColor())

            }
            EAnswer.YES -> {
                binding.tvDepressionYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvDepressionNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvDepressionYes.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.tvDepressionNo.setTextColor(fragment.getPrimaryColor())
            }
        }
    }

    private fun setChronic(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasCOPD = eAnswer.value
        binding.tvChronicError.visibility = View.GONE
        when (eAnswer) {
            EAnswer.NO -> {
                binding.tvChronicNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvChronicYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvChronicNo.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.tvChronicYes.setTextColor(fragment.getPrimaryColor())

            }
            EAnswer.YES -> {
                binding.tvChronicYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvChronicNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvChronicYes.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.tvChronicNo.setTextColor(fragment.getPrimaryColor())
            }
        }
    }

    private fun setOsteoporosis(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasOsteoporosis = eAnswer.value
        binding.tvOsteoporosisError.visibility = View.GONE
        when (eAnswer) {
            EAnswer.NO -> {
                binding.tvOsteoporosisNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvOsteoporosisYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvOsteoporosisNo.setTextColor(fragment.getColor(R.color.white))
                binding.tvOsteoporosisYes.setTextColor(fragment.getPrimaryColor())

            }
            EAnswer.YES -> {
                binding.tvOsteoporosisYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvOsteoporosisNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvOsteoporosisYes.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.tvOsteoporosisNo.setTextColor(fragment.getPrimaryColor())
            }
        }
    }

    private fun setAnxiety(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasAnxiety = eAnswer.value
        binding.tvAnxietyError.visibility = View.GONE
        when (eAnswer) {
            EAnswer.NO -> {
                binding.tvAnxietyNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvAnxietyYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvAnxietyNo.setTextColor(fragment.getColor(R.color.white))
                binding.tvAnxietyYes.setTextColor(fragment.getPrimaryColor())

            }
            EAnswer.YES -> {
                binding.tvAnxietyYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvAnxietyNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvAnxietyYes.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.tvAnxietyNo.setTextColor(fragment.getPrimaryColor())
            }
        }
    }

    private fun setStroke(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasStroke = eAnswer.value
        binding.tvStrokeError.visibility = View.GONE
        when (eAnswer) {
            EAnswer.NO -> {
                binding.tvStrokeNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvStrokeYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvStrokeNo.setTextColor(fragment.getColor(R.color.white))
                binding.tvStrokeYes.setTextColor(fragment.getPrimaryColor())

            }
            EAnswer.YES -> {
                binding.tvStrokeYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvStrokeNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvStrokeYes.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.tvStrokeNo.setTextColor(fragment.getPrimaryColor())
            }
        }
    }

    private fun setHeartDisease(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasHeartDisease = eAnswer.value
        if(eAnswer.value == 0){
            fragment.rawMeasurements.hasHeartFailure = eAnswer.value
            fragment.rawMeasurements.hasMI = eAnswer.value
        }
        binding.tvHeartDiseaseError.visibility = View.GONE
        val view = binding.includeHeartDiseasesSubQuestions.root
        when (eAnswer) {
            EAnswer.NO -> {
                binding.tvHeartDiseaseNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvHeartDiseaseYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvHeartDiseaseNo.setTextColor(fragment.getColor(R.color.white))
                binding.tvHeartDiseaseYes.setTextColor(fragment.getPrimaryColor())

                if (view.visibility == View.VISIBLE) {
                    val animSlideDown =
                        AnimationUtils.loadAnimation(fragment.requireContext(), R.anim.slide_up)
                    view.startAnimation(animSlideDown)
                    view.visibility = View.GONE
                }

            }
            EAnswer.YES -> {
                binding.tvHeartDiseaseYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvHeartDiseaseNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvHeartDiseaseYes.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.tvHeartDiseaseNo.setTextColor(fragment.getPrimaryColor())

                if (view.visibility == View.GONE) {
                    val animSlideDown =
                        AnimationUtils.loadAnimation(fragment.requireContext(), R.anim.slide_down)
                    view.startAnimation(animSlideDown)
                    view.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setHeartFailure(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasHeartFailure = eAnswer.value
        binding.includeHeartDiseasesSubQuestions.tvHeartFailureError.visibility = View.GONE
        when (eAnswer) {
            EAnswer.NO -> {
                binding.includeHeartDiseasesSubQuestions.tvHeartFailureNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.includeHeartDiseasesSubQuestions.tvHeartFailureYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.includeHeartDiseasesSubQuestions.tvHeartFailureNo.setTextColor(fragment.getColor(R.color.white))
                binding.includeHeartDiseasesSubQuestions.tvHeartFailureYes.setTextColor(fragment.getPrimaryColor())

            }
            EAnswer.YES -> {
                binding.includeHeartDiseasesSubQuestions.tvHeartFailureYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.includeHeartDiseasesSubQuestions.tvHeartFailureNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.includeHeartDiseasesSubQuestions.tvHeartFailureYes.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.includeHeartDiseasesSubQuestions.tvHeartFailureNo.setTextColor(fragment.getPrimaryColor())
            }
        }
    }

    private fun setMI(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasMI = eAnswer.value
        binding.includeHeartDiseasesSubQuestions.tvMyocardialError.visibility = View.GONE
        when (eAnswer) {
            EAnswer.NO -> {
                binding.includeHeartDiseasesSubQuestions.tvMyocardialNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.includeHeartDiseasesSubQuestions.tvMyocardialYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.includeHeartDiseasesSubQuestions.tvMyocardialNo.setTextColor(fragment.getColor(R.color.white))
                binding.includeHeartDiseasesSubQuestions.tvMyocardialYes.setTextColor(fragment.getPrimaryColor())
            }
            EAnswer.YES -> {
                binding.includeHeartDiseasesSubQuestions.tvMyocardialYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.includeHeartDiseasesSubQuestions.tvMyocardialNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.includeHeartDiseasesSubQuestions.tvMyocardialYes.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.includeHeartDiseasesSubQuestions.tvMyocardialNo.setTextColor(fragment.getPrimaryColor())
            }
        }
    }

    private fun setParkinson(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasPD = eAnswer.value
        binding.tvParkinsonDiseaseError.visibility = View.GONE
        when (eAnswer) {
            EAnswer.NO -> {
                binding.tvParkinsonDiseaseNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvParkinsonDiseaseYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvParkinsonDiseaseNo.setTextColor(fragment.getColor(R.color.white))
                binding.tvParkinsonDiseaseYes.setTextColor(fragment.getPrimaryColor())
            }
            EAnswer.YES -> {
                binding.tvParkinsonDiseaseYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvParkinsonDiseaseNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvParkinsonDiseaseYes.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.tvParkinsonDiseaseNo.setTextColor(fragment.getPrimaryColor())
            }
        }
    }

    private fun setMemoryProblem(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasMemoryProblems = eAnswer.value
        if(eAnswer.value == 0){
            fragment.rawMeasurements.hasDementia = eAnswer.value
        }
        binding.tvMemoryProblemError.visibility = View.GONE
        val view = binding.includeMemoryProblemSubQuestions.root
        when (eAnswer) {
            EAnswer.NO -> {
                binding.tvMemoryProblemNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvMemoryProblemYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvMemoryProblemNo.setTextColor(fragment.getColor(R.color.white))
                binding.tvMemoryProblemYes.setTextColor(fragment.getPrimaryColor())

                if (view.visibility == View.VISIBLE) {
                    val animSlideDown =
                        AnimationUtils.loadAnimation(fragment.requireContext(), R.anim.slide_up)
                    view.startAnimation(animSlideDown)
                    view.visibility = View.GONE
                }
            }
            EAnswer.YES -> {
                binding.tvMemoryProblemYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvMemoryProblemNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvMemoryProblemYes.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.tvMemoryProblemNo.setTextColor(fragment.getPrimaryColor())

                if (view.visibility == View.GONE) {
                    val animSlideDown =
                        AnimationUtils.loadAnimation(fragment.requireContext(), R.anim.slide_down)
                    view.startAnimation(animSlideDown)
                    view.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setDementia(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasDementia = eAnswer.value
        binding.includeMemoryProblemSubQuestions.tvDementiaError.visibility = View.GONE
        when (eAnswer) {
            EAnswer.NO -> {
                binding.includeMemoryProblemSubQuestions.tvDementiaNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.includeMemoryProblemSubQuestions.tvDementiaYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.includeMemoryProblemSubQuestions.tvDementiaNo.setTextColor(fragment.getColor(R.color.white))
                binding.includeMemoryProblemSubQuestions.tvDementiaYes.setTextColor(fragment.getPrimaryColor())
            }
            EAnswer.YES -> {
                binding.includeMemoryProblemSubQuestions.tvDementiaYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.includeMemoryProblemSubQuestions.tvDementiaNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.includeMemoryProblemSubQuestions.tvDementiaYes.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.includeMemoryProblemSubQuestions.tvDementiaNo.setTextColor(fragment.getPrimaryColor())
            }
        }
    }

    private fun setHighCholesterol(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasHighCholesterol = eAnswer.value
        binding.tvHighCholesterolError.visibility = View.GONE
        when (eAnswer) {
            EAnswer.NO -> {
                binding.tvHighCholesterolNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvHighCholesterolYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvHighCholesterolNo.setTextColor(fragment.getColor(R.color.white))
                binding.tvHighCholesterolYes.setTextColor(fragment.getPrimaryColor())
            }
            EAnswer.YES -> {
                binding.tvHighCholesterolYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvHighCholesterolNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvHighCholesterolYes.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.tvHighCholesterolNo.setTextColor(fragment.getPrimaryColor())
            }
        }
    }

    private fun setSeizure(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasSeizure = eAnswer.value
        binding.tvSeizureError.visibility = View.GONE
        when (eAnswer) {
            EAnswer.NO -> {
                binding.tvSeizureNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvSeizureYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvSeizureNo.setTextColor(fragment.getColor(R.color.white))
                binding.tvSeizureYes.setTextColor(fragment.getPrimaryColor())
            }
            EAnswer.YES -> {
                binding.tvSeizureYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvSeizureNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvSeizureYes.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.tvSeizureNo.setTextColor(fragment.getPrimaryColor())
            }
        }
    }

    fun applyPrefill(data: RawMeasurements) {
        // Hypertension
        data.hasHypertension?.let { setHypertension(eAnswerFrom(it)) }

        // Arthritis
        data.hasArthritis?.let { setArthritics(eAnswerFrom(it)) }

        // Diabetes + nested sub-questions
        data.hasDiabetes?.let { diabetesValue ->
            setDiabetes(eAnswerFrom(diabetesValue))
            if (diabetesValue == EAnswer.YES.value) {
                data.hasDiabetesVision?.let { setDiabetesVision(eAnswerFrom(it)) }
                data.hasDiabetesKidney?.let { setDiabetesKidney(eAnswerFrom(it)) }
                data.hasDiabetesNerve?.let { setDiabetesNerve(eAnswerFrom(it)) }
                data.hasDiabetesYears?.let {
                    binding.includeDiabetesSubQuestions.edtDiabetesYear.setText(it)
                    fragment.rawMeasurements.hasDiabetesYears = it
                }
                // hasDiabetesTakMedicine may not come from the API;
                // infer from oral/insulin/injection fields
                val takeMedicineValue = data.hasDiabetesTakMedicine
                    ?: if (data.hasDiabetesTakOralMedicine != null ||
                        data.hasDiabetesTakInsulin != null ||
                        data.hasDiabetesTakInjection != null
                    ) EAnswer.YES.value else null
                takeMedicineValue?.let { tv ->
                    setDiabetesTakingMedicine(eAnswerFrom(tv))
                    if (tv == EAnswer.YES.value) {
                        data.hasDiabetesTakOralMedicine?.let { setDiabetesOralMedicine(eAnswerFrom(it)) }
                        data.hasDiabetesTakInsulin?.let { setDiabetesInsulinMedicine(eAnswerFrom(it)) }
                        data.hasDiabetesTakInjection?.let { setDiabetesInjectionMedicine(eAnswerFrom(it)) }
                    }
                }
            }
        }

        data.hasDepression?.let { setDepression(eAnswerFrom(it)) }
        // hasCOPD maps to the "Chronic/COPD" question
        data.hasCOPD?.let { setChronic(eAnswerFrom(it)) }
        data.hasOsteoporosis?.let { setOsteoporosis(eAnswerFrom(it)) }
        // hasAnxiety is @SerializedName("hasAngina") — Anxiety/Angina question on the form
        data.hasAnxiety?.let { setAnxiety(eAnswerFrom(it)) }
        data.hasStroke?.let { setStroke(eAnswerFrom(it)) }

        // Heart disease + nested sub-questions
        // hasHeartDisease may not be in the API response; infer from CHF/MI sub-fields
        val heartDiseaseValue = data.hasHeartDisease
            ?: if (data.hasHeartFailure == EAnswer.YES.value || data.hasMI == EAnswer.YES.value)
                EAnswer.YES.value else null
        heartDiseaseValue?.let { hdValue ->
            setHeartDisease(eAnswerFrom(hdValue))
            if (hdValue == EAnswer.YES.value) {
                data.hasHeartFailure?.let { setHeartFailure(eAnswerFrom(it)) }
                data.hasMI?.let { setMI(eAnswerFrom(it)) }
            }
        }

        data.hasPD?.let { setParkinson(eAnswerFrom(it)) }

        // Memory problems + nested sub-question (Dementia/AD)
        data.hasMemoryProblems?.let { mpValue ->
            setMemoryProblem(eAnswerFrom(mpValue))
            if (mpValue == EAnswer.YES.value) {
                data.hasDementia?.let { setDementia(eAnswerFrom(it)) }
            }
        }

        // hasHighCholesterol is @SerializedName("hasTIA") in the model
        data.hasHighCholesterol?.let { setHighCholesterol(eAnswerFrom(it)) }
        // hasSeizure is @SerializedName("hasEpilepsy")
        data.hasSeizure?.let { setSeizure(eAnswerFrom(it)) }
        data.hasNeuropathy?.let { setNeuropathy(eAnswerFrom(it)) }
    }

    // Helper: converts an Int value (0 or 1) to EAnswer
    private fun eAnswerFrom(value: Int): EAnswer =
        if (value == EAnswer.YES.value) EAnswer.YES else EAnswer.NO

    private fun setNeuropathy(eAnswer: EAnswer) {
        fragment.rawMeasurements.hasNeuropathy = eAnswer.value
        binding.tvNeuropathyError.visibility = View.GONE
        when (eAnswer) {
            EAnswer.NO -> {
                binding.tvNeuropathyNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvNeuropathyYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvNeuropathyNo.setTextColor(fragment.getColor(R.color.white))
                binding.tvNeuropathyYes.setTextColor(fragment.getPrimaryColor())
            }
            EAnswer.YES -> {
                binding.tvNeuropathyYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvNeuropathyNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvNeuropathyYes.setTextColor(
                    fragment.getColor(
                        R.color.white
                    )
                )
                binding.tvNeuropathyNo.setTextColor(fragment.getPrimaryColor())
            }
        }
    }
}