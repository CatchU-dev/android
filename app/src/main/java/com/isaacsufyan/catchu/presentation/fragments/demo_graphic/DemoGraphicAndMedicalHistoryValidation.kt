package com.isaacsufyan.catchu.presentation.fragments.demo_graphic

import android.util.Log
import android.view.View
import com.isaacsufyan.catchu.R
import com.isaacsufyan.catchu.domain.enum.EAnswer
import com.isaacsufyan.catchu.domain.model.RawMeasurements
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
        Log.d("TAG", "validate: $giveAllAns0 - $giveAllAns1 - $giveAllAns2 - $giveAllAns3")
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
        if (RawMeasurements.isHispanicOrLatin == null) {
            binding.includeSeg0.tvHispanicLatinoError.visibility = View.VISIBLE
            giveAllAns = false
        }
        if (RawMeasurements.selectedRace == null) {
            binding.includeSeg0.edtRadicalCategoryLayout.error =
                context.resources.getString(R.string.required)
            giveAllAns = false
        }
        return giveAllAns
    }

    private fun includeSegment1Validation(): Boolean {
        binding.includeSeg1.includeDiabetesSubQuestions.edtDiabetesYearLayout.error = null
        var giveAllAns = true

        if (RawMeasurements.hasHypertension == null) {
            binding.includeSeg1.tvHypertensionError.visibility = View.VISIBLE
            giveAllAns = false
        }

        if (RawMeasurements.hasArthritis == null) {
            binding.includeSeg1.tvArthritisError.visibility = View.VISIBLE
            giveAllAns = false
        }

        if (RawMeasurements.hasDiabetes == null) {
            binding.includeSeg1.tvDiabetesError.visibility = View.VISIBLE
            giveAllAns = false
        }

        if (RawMeasurements.hasDiabetes == EAnswer.YES.value) {
            val view = binding.includeSeg1.includeDiabetesSubQuestions
            if (view.edtDiabetesYear.isNullOrEmpty()) {
                view.edtDiabetesYearLayout.error = context.resources.getString(R.string.required)
                giveAllAns = false
            }
            if (RawMeasurements.hasDiabetesVision == null) {
                view.tvDiabetesVisionError.visibility = View.VISIBLE
                giveAllAns = false
            }
            if (RawMeasurements.hasDiabetesKidney == null) {
                view.tvDiabetesKidneyError.visibility = View.VISIBLE
                giveAllAns = false
            }
            if (RawMeasurements.hasDiabetesNerve == null) {
                view.tvDiabetesNerveError.visibility = View.VISIBLE
                giveAllAns = false
            }
            if (RawMeasurements.hasDiabetesTakMedicine == null) {
                view.tvDiabetesTakingMedicineError.visibility = View.VISIBLE
                giveAllAns = false
            }
            if (RawMeasurements.hasDiabetesTakMedicine == EAnswer.YES.value) {
                if (RawMeasurements.hasDiabetesTakOralMedicine == null) {
                    view.tvDiabetesOralMedicineError.visibility = View.VISIBLE
                    giveAllAns = false
                }
                if (RawMeasurements.hasDiabetesTakInsulin == null) {
                    view.tvDiabetesInsulinError.visibility = View.VISIBLE
                    giveAllAns = false
                }
                if (RawMeasurements.hasDiabetesTakInjection == null) {
                    view.tvDiabetesInjectionError.visibility = View.VISIBLE
                    giveAllAns = false
                }
            }
        }

        if (RawMeasurements.hasDepression == null) {
            binding.includeSeg1.tvDepressionError.visibility = View.VISIBLE
            giveAllAns = false
        }

        if (RawMeasurements.hasCOPD == null) {
            binding.includeSeg1.tvChronicError.visibility = View.VISIBLE
            giveAllAns = false
        }

        if (RawMeasurements.hasOsteoporosis == null) {
            binding.includeSeg1.tvOsteoporosisError.visibility = View.VISIBLE
            giveAllAns = false
        }

        if (RawMeasurements.hasAnxiety == null) {
            binding.includeSeg1.tvAnxietyError.visibility = View.VISIBLE
            giveAllAns = false
        }

        if (RawMeasurements.hasStroke == null) {
            binding.includeSeg1.tvStrokeError.visibility = View.VISIBLE
            giveAllAns = false
        }

        if (RawMeasurements.hasHeartDisease == null) {
            binding.includeSeg1.tvHeartDiseaseError.visibility = View.VISIBLE
            giveAllAns = false
        }

        if (RawMeasurements.hasHeartDisease == EAnswer.YES.value) {
            if (RawMeasurements.hasHeartFailure == null) {
                binding.includeSeg1.includeHeartDiseasesSubQuestions.tvHeartFailureError.visibility =
                    View.VISIBLE
                giveAllAns = false
            }
            if (RawMeasurements.hasMI == null) {
                binding.includeSeg1.includeHeartDiseasesSubQuestions.tvMyocardialError.visibility =
                    View.VISIBLE
                giveAllAns = false
            }
        }

        if (RawMeasurements.hasPD == null) {
            binding.includeSeg1.tvParkinsonDiseaseError.visibility = View.VISIBLE
            giveAllAns = false
        }

        if (RawMeasurements.hasMemoryProblems == null) {
            binding.includeSeg1.tvMemoryProblemError.visibility = View.VISIBLE
            giveAllAns = false
        }

        if (RawMeasurements.hasMemoryProblems == EAnswer.YES.value) {
            if (RawMeasurements.hasDementia == null) {
                binding.includeSeg1.includeMemoryProblemSubQuestions.tvDementiaError.visibility =
                    View.VISIBLE
                giveAllAns = false
            }
        }

        if (RawMeasurements.hasHighCholesterol == null) {
            binding.includeSeg1.tvHighCholesterolError.visibility = View.VISIBLE
            giveAllAns = false
        }

        if (RawMeasurements.hasSeizure == null) {
            binding.includeSeg1.tvSeizureError.visibility = View.VISIBLE
            giveAllAns = false
        }

        if (RawMeasurements.hasNeuropathy == null) {
            binding.includeSeg1.tvNeuropathyError.visibility = View.VISIBLE
            giveAllAns = false
        }

        return giveAllAns
    }

    private fun includeSegment2Validation(): Boolean {
        var giveAllAns = true
        binding.includeSeg2.includeVisionProblems.edtVisionRateLayout.error = null


        if (RawMeasurements.hasChronicKidney == null) {
            binding.includeSeg2.tvChronicKidneyError.visibility = View.VISIBLE
            giveAllAns = false
        }
        if(RawMeasurements.hasChronicKidney == EAnswer.YES.value){
            if (RawMeasurements.onDialysis == null) {
                binding.includeSeg2.includeKidneyDiseaseSubQuestions.tvDialysisError.visibility = View.VISIBLE
                giveAllAns = false
            }
        }

        if (RawMeasurements.hasVisionProblem == null) {
            binding.includeSeg2.tvVisionError.visibility = View.VISIBLE
            giveAllAns = false
        }

        if (RawMeasurements.hasVisionProblem == EAnswer.YES.value) {
            if (binding.includeSeg2.includeVisionProblems.edtVisionRate.isNullOrEmpty()) {
                binding.includeSeg2.includeVisionProblems.edtVisionRateLayout.error =
                    context.resources.getString(R.string.required)
                giveAllAns = false
            }
            if (RawMeasurements.hasCorrectiveLenses == null) {
                binding.includeSeg2.includeVisionProblems.tvVisionGlassesError.visibility = View.VISIBLE
                giveAllAns = false
            }
        }

        if (RawMeasurements.hasSevereHearingLoss == null) {
            binding.includeSeg2.tvHearingProblemError.visibility = View.VISIBLE
            giveAllAns = false
        }

        if (RawMeasurements.hasSevereHearingLoss == EAnswer.YES.value) {
            if (RawMeasurements.hasHearingAid == null) {
                binding.includeSeg2.includeHearingProblems.tvHearingAidError.visibility = View.VISIBLE
                giveAllAns = false
            }
        }

        if (RawMeasurements.hasHadAFall == null) {
            binding.includeSeg2.tvFallInPastOneYearError.visibility = View.VISIBLE
            giveAllAns = false
        }
        if(RawMeasurements.hasHadAFall ==  EAnswer.YES.value){
            if (RawMeasurements.hasHadAFracture == null) {
                binding.includeSeg2.includeFallInPastOneYearSubQuestions.tvFallInPastOneYearFractureError.visibility = View.VISIBLE
                giveAllAns = false
            }
        }

        if (RawMeasurements.hasHadAFallPastFiveYears == null) {
            binding.includeSeg2.tvFallInPastFiveYearError.visibility =
                View.VISIBLE
            giveAllAns = false
        }
        if (RawMeasurements.hasHadAFallPastFiveYears == EAnswer.YES.value) {
            if (RawMeasurements.hasHadAFracturePastFiveYears == null) {
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
        if (RawMeasurements.hasFearOfFalling == null) {
            binding.includeSeg3.tvFearOfFallingError.visibility = View.VISIBLE
            giveAllAns = false
        }
        if (RawMeasurements.canWalkWithoutBreak == null) {
            binding.includeSeg3.tvWalkWithoutBreakError.visibility = View.VISIBLE
            giveAllAns = false
        }
        if (RawMeasurements.hasDifficultyWalking == null) {
            binding.includeSeg3.tvWalkingProblemError.visibility = View.VISIBLE
            giveAllAns = false
        }
        if (RawMeasurements.hasDifficultyWalking == EAnswer.YES.value) {
            if (RawMeasurements.hasDifficultyWalkingUseCane == null) {
                binding.includeSeg3.includeWalkingProblem.tvWalkingProblemCaneError.visibility = View.VISIBLE
                giveAllAns = false
            }
            if (RawMeasurements.hasDifficultyWalkingUseWalker == null) {
                binding.includeSeg3.includeWalkingProblem.tvWalkingProblemWalkerError.visibility = View.VISIBLE
                giveAllAns = false
            }
        }
        if (RawMeasurements.hasDifficultyGettingUp == null) {
            binding.includeSeg3.tvLowChairDifficultyError.visibility = View.VISIBLE
            giveAllAns = false
        }
        if (RawMeasurements.hasToHoldOntoWalls == null) {
            binding.includeSeg3.tvHoldWallWhileWalkError.visibility = View.VISIBLE
            giveAllAns = false
        }
        if (RawMeasurements.hasDifficultyWithBalance == null) {
            binding.includeSeg3.tvDifficultyWithBalanceError.visibility = View.VISIBLE
            giveAllAns = false
        }
        return giveAllAns
    }

}