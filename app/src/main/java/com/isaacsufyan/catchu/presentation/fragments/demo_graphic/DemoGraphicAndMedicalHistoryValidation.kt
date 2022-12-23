package com.isaacsufyan.catchu.presentation.fragments.demo_graphic

import android.view.View
import com.isaacsufyan.catchu.R
import com.isaacsufyan.catchu.domain.enum.EAnswer
import com.isaacsufyan.catchu.utils.isNullOrEmpty

class DemoGraphicAndMedicalHistoryValidation constructor(
    var fragment: DemoGraphicAndMedicalHistoryFragment
) {

    val binding = fragment.binding
    val context = fragment.requireContext()

    fun validate(): Boolean {
        val giveAllAns0 = includeSegment0Validation()
        val giveAllAns1 = includeSegment1Validation()
        val giveAllAns2 = includeSegment2Validation()
        val giveAllAns3 = includeSegment3Validation()
        return giveAllAns0 && giveAllAns1 && giveAllAns2 && giveAllAns3
    }

    private fun includeSegment0Validation(): Boolean {
        var giveAllAns = true
        binding.includeSeg0.edtYearOfEducationLayout.error = null
        binding.includeSeg0.edtRadicalCategoryLayout.error = null

        if (binding.includeSeg0.edtYearOfEducation.isNullOrEmpty()) {
            binding.includeSeg0.edtYearOfEducationLayout.error =
                context.resources.getString(R.string.required)
            giveAllAns = false
        }
        if (fragment.rawMeasurements.isHispanicOrLatin == null) {
            binding.includeSeg0.tvHispanicLatinoError.visibility = View.VISIBLE
            giveAllAns = false
        }
        if (fragment.rawMeasurements.selectedRace == null) {
            binding.includeSeg0.edtRadicalCategoryLayout.error =
                context.resources.getString(R.string.required)
            giveAllAns = false
        }
        return giveAllAns
    }

    private fun includeSegment1Validation(): Boolean {
        binding.includeSeg1.includeDiabetesSubQuestions.edtDiabetesYearLayout.error = null
        var giveAllAns = true

        if (fragment.rawMeasurements.hasHypertension == null) {
            binding.includeSeg1.tvHypertensionError.visibility = View.VISIBLE
            giveAllAns = false
        }

        if (fragment.rawMeasurements.hasArthritis == null) {
            binding.includeSeg1.tvArthritisError.visibility = View.VISIBLE
            giveAllAns = false
        }

        if (fragment.rawMeasurements.hasDiabetes == null) {
            binding.includeSeg1.tvDiabetesError.visibility = View.VISIBLE
            giveAllAns = false
        }

        if (fragment.rawMeasurements.hasDiabetes == EAnswer.YES.value) {
            val view = binding.includeSeg1.includeDiabetesSubQuestions
            if (fragment.rawMeasurements.hasDiabetesVision == null) {
                view.tvDiabetesVisionError.visibility = View.VISIBLE
                giveAllAns = false
            }
            if (fragment.rawMeasurements.hasDiabetesKidney == null) {
                view.tvDiabetesKidneyError.visibility = View.VISIBLE
                giveAllAns = false
            }
            if (fragment.rawMeasurements.hasDiabetesNerve == null) {
                view.tvDiabetesNerveError.visibility = View.VISIBLE
                giveAllAns = false
            }
            if (fragment.rawMeasurements.hasDiabetesTakMedicine == null) {
                view.tvDiabetesTakingMedicineError.visibility = View.VISIBLE
                giveAllAns = false
            }
            if (fragment.rawMeasurements.hasDiabetesTakMedicine == EAnswer.YES.value) {
                if (fragment.rawMeasurements.hasDiabetesTakOralMedicine == null) {
                    view.tvDiabetesOralMedicineError.visibility = View.VISIBLE
                    giveAllAns = false
                }
                if (fragment.rawMeasurements.hasDiabetesTakInsulin == null) {
                    view.tvDiabetesInsulinError.visibility = View.VISIBLE
                    giveAllAns = false
                }
                if (fragment.rawMeasurements.hasDiabetesTakInjection == null) {
                    view.tvDiabetesInjectionError.visibility = View.VISIBLE
                    giveAllAns = false
                }
            }
        }

        if (fragment.rawMeasurements.hasDepression == null) {
            binding.includeSeg1.tvDepressionError.visibility = View.VISIBLE
            giveAllAns = false
        }

        if (fragment.rawMeasurements.hasCOPD == null) {
            binding.includeSeg1.tvChronicError.visibility = View.VISIBLE
            giveAllAns = false
        }

        if (fragment.rawMeasurements.hasOsteoporosis == null) {
            binding.includeSeg1.tvOsteoporosisError.visibility = View.VISIBLE
            giveAllAns = false
        }

        if (fragment.rawMeasurements.hasAnxiety == null) {
            binding.includeSeg1.tvAnxietyError.visibility = View.VISIBLE
            giveAllAns = false
        }

        if (fragment.rawMeasurements.hasStroke == null) {
            binding.includeSeg1.tvStrokeError.visibility = View.VISIBLE
            giveAllAns = false
        }

        if (fragment.rawMeasurements.hasHeartDisease == null) {
            binding.includeSeg1.tvHeartDiseaseError.visibility = View.VISIBLE
            giveAllAns = false
        }

        if (fragment.rawMeasurements.hasHeartDisease == EAnswer.YES.value) {
            if (fragment.rawMeasurements.hasHeartFailure == null) {
                binding.includeSeg1.includeHeartDiseasesSubQuestions.tvHeartFailureError.visibility =
                    View.VISIBLE
                giveAllAns = false
            }
            if (fragment.rawMeasurements.hasMI == null) {
                binding.includeSeg1.includeHeartDiseasesSubQuestions.tvMyocardialError.visibility =
                    View.VISIBLE
                giveAllAns = false
            }
        }

        if (fragment.rawMeasurements.hasPD == null) {
            binding.includeSeg1.tvParkinsonDiseaseError.visibility = View.VISIBLE
            giveAllAns = false
        }

        if (fragment.rawMeasurements.hasMemoryProblems == null) {
            binding.includeSeg1.tvMemoryProblemError.visibility = View.VISIBLE
            giveAllAns = false
        }

        if (fragment.rawMeasurements.hasMemoryProblems == EAnswer.YES.value) {
            if (fragment.rawMeasurements.hasDementia == null) {
                binding.includeSeg1.includeMemoryProblemSubQuestions.tvDementiaError.visibility =
                    View.VISIBLE
                giveAllAns = false
            }
        }

        if (fragment.rawMeasurements.hasHighCholesterol == null) {
            binding.includeSeg1.tvHighCholesterolError.visibility = View.VISIBLE
            giveAllAns = false
        }

        if (fragment.rawMeasurements.hasSeizure == null) {
            binding.includeSeg1.tvSeizureError.visibility = View.VISIBLE
            giveAllAns = false
        }

        if (fragment.rawMeasurements.hasNeuropathy == null) {
            binding.includeSeg1.tvNeuropathyError.visibility = View.VISIBLE
            giveAllAns = false
        }

        return giveAllAns
    }

    private fun includeSegment2Validation(): Boolean {
        var giveAllAns = true
        binding.includeSeg2.includeVisionProblems.edtVisionRateLayout.error = null


        if (fragment.rawMeasurements.hasChronicKidney == null) {
            binding.includeSeg2.tvChronicKidneyError.visibility = View.VISIBLE
            giveAllAns = false
        }
        if(fragment.rawMeasurements.hasChronicKidney == EAnswer.YES.value){
            if (fragment.rawMeasurements.onDialysis == null) {
                binding.includeSeg2.includeKidneyDiseaseSubQuestions.tvDialysisError.visibility = View.VISIBLE
                giveAllAns = false
            }
        }

        if (fragment.rawMeasurements.hasVisionProblem == null) {
            binding.includeSeg2.tvVisionError.visibility = View.VISIBLE
            giveAllAns = false
        }

        if (fragment.rawMeasurements.hasVisionProblem == EAnswer.YES.value) {
            if (binding.includeSeg2.includeVisionProblems.edtVisionRate.isNullOrEmpty()) {
                binding.includeSeg2.includeVisionProblems.edtVisionRateLayout.error =
                    context.resources.getString(R.string.required)
                giveAllAns = false
            }
            if (fragment.rawMeasurements.hasCorrectiveLenses == null) {
                binding.includeSeg2.includeVisionProblems.tvVisionGlassesError.visibility = View.VISIBLE
                giveAllAns = false
            }
        }

        if (fragment.rawMeasurements.hasSevereHearingLoss == null) {
            binding.includeSeg2.tvHearingProblemError.visibility = View.VISIBLE
            giveAllAns = false
        }

        if (fragment.rawMeasurements.hasSevereHearingLoss == EAnswer.YES.value) {
            if (fragment.rawMeasurements.hasHearingAid == null) {
                binding.includeSeg2.includeHearingProblems.tvHearingAidError.visibility = View.VISIBLE
                giveAllAns = false
            }
        }

        if (fragment.rawMeasurements.hasHadAFall == null) {
            binding.includeSeg2.tvFallInPastOneYearError.visibility = View.VISIBLE
            giveAllAns = false
        }
        if(fragment.rawMeasurements.hasHadAFall ==  EAnswer.YES.value){
            if (fragment.rawMeasurements.hasHadAFracture == null) {
                binding.includeSeg2.includeFallInPastOneYearSubQuestions.tvFallInPastOneYearFractureError.visibility = View.VISIBLE
                giveAllAns = false
            }
        }

        if (fragment.rawMeasurements.hasHadAFallPastFiveYears == null) {
            binding.includeSeg2.tvFallInPastFiveYearError.visibility =
                View.VISIBLE
            giveAllAns = false
        }
        if (fragment.rawMeasurements.hasHadAFallPastFiveYears == EAnswer.YES.value) {
            if (fragment.rawMeasurements.hasHadAFracturePastFiveYears == null) {
                binding.includeSeg2.includeFallInPastFiveYearSubQuestions.tvFallInPastFiveYearFractureError.visibility =
                    View.VISIBLE
                giveAllAns = false
            }
        }

        return giveAllAns
    }

    private fun includeSegment3Validation(): Boolean {
        var giveAllAns = true
        binding.includeSeg3.edtRegularlyMedicineLayout.error = null

        if (binding.includeSeg3.edtRegularlyMedicine.isNullOrEmpty()) {
            binding.includeSeg3.edtRegularlyMedicineLayout.error =
                context.resources.getString(R.string.required)
            giveAllAns = false
        }
        if (fragment.rawMeasurements.hasFearOfFalling == null) {
            binding.includeSeg3.tvFearOfFallingError.visibility = View.VISIBLE
            giveAllAns = false
        }
        if (fragment.rawMeasurements.canWalkWithoutBreak == null) {
            binding.includeSeg3.tvWalkWithoutBreakError.visibility = View.VISIBLE
            giveAllAns = false
        }
        if (fragment.rawMeasurements.hasDifficultyWalking == null) {
            binding.includeSeg3.tvWalkingProblemError.visibility = View.VISIBLE
            giveAllAns = false
        }
        if (fragment.rawMeasurements.hasDifficultyWalking == EAnswer.YES.value) {
            if (fragment.rawMeasurements.hasDifficultyWalkingUseCane == null) {
                binding.includeSeg3.includeWalkingProblem.tvWalkingProblemCaneError.visibility = View.VISIBLE
                giveAllAns = false
            }
            if (fragment.rawMeasurements.hasDifficultyWalkingUseWalker == null) {
                binding.includeSeg3.includeWalkingProblem.tvWalkingProblemWalkerError.visibility = View.VISIBLE
                giveAllAns = false
            }
        }
        if (fragment.rawMeasurements.hasDifficultyGettingUp == null) {
            binding.includeSeg3.tvLowChairDifficultyError.visibility = View.VISIBLE
            giveAllAns = false
        }
        if (fragment.rawMeasurements.hasToHoldOntoWalls == null) {
            binding.includeSeg3.tvHoldWallWhileWalkError.visibility = View.VISIBLE
            giveAllAns = false
        }
        if (fragment.rawMeasurements.hasDifficultyWithBalance == null) {
            binding.includeSeg3.tvDifficultyWithBalanceError.visibility = View.VISIBLE
            giveAllAns = false
        }
        return giveAllAns
    }

}