package com.isaacsufyan.catchu.domain.model

import android.os.Build
import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Keep
class RawMeasurements {

    @SerializedName("subjectid")
    @Expose
    var subjectid: Int? = null

    @SerializedName("testdate")
    @Expose
    var testdate: String? = null

    @SerializedName("selectedYear")
    @Expose
    var selectedYear: String? = null

    @SerializedName("selectedMonth")
    @Expose
    var selectedMonth: String? = null

    @SerializedName("practise_test_status")
    @Expose
    var practiseTestStatus: String? = null

    @SerializedName("selectedDate")
    @Expose
    var selectedDate: String? = null

    @SerializedName("selectedRace")
    @Expose
    var selectedRace: Int? = null

    @SerializedName("isHispanicOrLatin")
    @Expose
    var isHispanicOrLatin: Int? = null

    @SerializedName("educationYear")
    @Expose
    var educationYear: String? = null

    @SerializedName("selectedGender")
    @Expose
    var selectedGender: String? = null

    @SerializedName("hasHypertension")
    @Expose
    var hasHypertension: Int? = null

    @SerializedName("hasArthritis")
    @Expose
    var hasArthritis: Int? = null

    @SerializedName("hasDiabetes")
    @Expose
    var hasDiabetes: Int? = null

    @SerializedName("hasDiabetesYears")
    @Expose
    var hasDiabetesYears: String? = null

    @SerializedName("hasDiabetesVision")
    @Expose
    var hasDiabetesVision: Int? = null

    @SerializedName("hasDiabetesKidney")
    @Expose
    var hasDiabetesKidney: Int? = null

    @SerializedName("hasDiabetesNerve")
    @Expose
    var hasDiabetesNerve: Int? = null

    @SerializedName("hasDiabetesTakMedicine")
    @Expose
    var hasDiabetesTakMedicine: Int? = null

    @SerializedName("hypoglycemicAgents")
    @Expose
    var hasDiabetesTakOralMedicine: Int? = null

    @SerializedName("isTakingInsulin")
    @Expose
    var hasDiabetesTakInsulin: Int? = null

    @SerializedName("hasDiabetesTakInjection")
    @Expose
    var hasDiabetesTakInjection: Int? = null

    @SerializedName("hasDepression")
    @Expose
    var hasDepression: Int? = null

    @SerializedName("hasCOPD")
    @Expose
    var hasCOPD: Int? = null

    @SerializedName("hasOsteoporosis")
    @Expose
    var hasOsteoporosis: Int? = null

    @SerializedName("hasAngina")
    @Expose
    var hasAnxiety: Int? = null

    @SerializedName("hasStroke")
    @Expose
    var hasStroke: Int? = null

    @SerializedName("hasHeartDisease")
    @Expose
    var hasHeartDisease: Int? = null

    @SerializedName("hasCHF")
    @Expose
    var hasHeartFailure: Int? = null

    @SerializedName("hasMI")
    @Expose
    var hasMI: Int? = null

    @SerializedName("hasPD")
    @Expose
    var hasPD: Int? = null

    @SerializedName("hasMemoryProblems")
    @Expose
    var hasMemoryProblems: Int? = null

    @SerializedName("hasAD")
    @Expose
    var hasDementia: Int? = null

    @SerializedName("hasTIA")
    @Expose
    var hasHighCholesterol: Int? = null

    @SerializedName("hasEpilepsy")
    @Expose
    var hasSeizure: Int? = null

    @SerializedName("hasNeuropathy")
    @Expose
    var hasNeuropathy: Int? = null

    @SerializedName("hasChronicKidney")
    @Expose
    var hasChronicKidney: Int? = null

    @SerializedName("onDialysis")
    @Expose
    var onDialysis: Int? = null

    @SerializedName("hasVI")
    @Expose
    var hasVisionProblem: Int? = null

    @SerializedName("visualImpairments")
    @Expose
    var visualImpairments: Int? = null

    @SerializedName("hasVisionLoss")
    @Expose
    var hasVisionLoss: Int? = null

    @SerializedName("hasCorrectiveLenses")
    @Expose
    var hasCorrectiveLenses: Int? = null

    @SerializedName("hasSevereHearingLoss")
    @Expose
    var hasSevereHearingLoss: Int? = null

    @SerializedName("hasHearingAid")
    @Expose
    var hasHearingAid: Int? = null

    @SerializedName("hasHadAFall")
    @Expose
    var hasHadAFall: Int? = null

    @SerializedName("hasHadAFracture")
    @Expose
    var hasHadAFracture: Int? = null

    @SerializedName("hasHadAFallPastFiveYears")
    @Expose
    var hasHadAFallPastFiveYears: Int? = null

    @SerializedName("hasHadaFracturePastFiveYears")
    @Expose
    var hasHadAFracturePastFiveYears: Int? = null

    @SerializedName("hasFearOfFalling")
    @Expose
    var hasFearOfFalling: Int? = null

    @SerializedName("canWalkWithoutBreak")
    @Expose
    var canWalkWithoutBreak: Int? = null

    @SerializedName("hasDifficultyWalking")
    @Expose
    var hasDifficultyWalking: Int? = null

    @SerializedName("hasDifficultyWalkingUseCane")
    @Expose
    var hasDifficultyWalkingUseCane: Int? = null

    @SerializedName("hasDifficultyWalkingUseWalker")
    @Expose
    var hasDifficultyWalkingUseWalker: Int? = null

    @SerializedName("hasDifficultyGettingUp")
    @Expose
    var hasDifficultyGettingUp: Int? = null

    @SerializedName("hasToHoldOntoWalls")
    @Expose
    var hasToHoldOntoWalls: Int? = null

    @SerializedName("hasDifficultyWithBalance")
    @Expose
    var hasDifficultyWithBalance: Int? = null

    @SerializedName("regularlyScheduledMedicines")
    @Expose
    var regularlyScheduledMedicines: Int? = null

    @SerializedName("StudySite")
    @Expose
    var studySite: String = ""

    @SerializedName("StudyID")
    @Expose
    var studyId: String = ""

    @SerializedName("ResPurp")
    @Expose
    var researchPurpose: String = "NO"

    @SerializedName("Consent")
    @Expose
    var consent: Int = 1

    @SerializedName("test_values")
    @Expose
    var testValues : HashMap<String, Int>? = null

    @SerializedName("practise_test_values")
    @Expose
    var practiseTestValues: HashMap<String, Int>? = null

    @SerializedName("practise_test_values_2")
    @Expose
    var practiseTestValues2: HashMap<String, Int>? = null

    @SerializedName("platform")
    @Expose
    var platform : String = "Android"

    @SerializedName("platform_version")
    @Expose
    var platform_version: String = Build.VERSION.RELEASE

    /**
     * Resets demographics / test payload fields to match a new [RawMeasurements] instance.
     * Used when returning to the language screen for a clean session (singleton instance).
     */
    fun resetForNewSessionAfterExit() {
        val d = RawMeasurements()
        subjectid = d.subjectid
        testdate = d.testdate
        selectedYear = d.selectedYear
        selectedMonth = d.selectedMonth
        practiseTestStatus = d.practiseTestStatus
        selectedDate = d.selectedDate
        selectedRace = d.selectedRace
        isHispanicOrLatin = d.isHispanicOrLatin
        educationYear = d.educationYear
        selectedGender = d.selectedGender
        hasHypertension = d.hasHypertension
        hasArthritis = d.hasArthritis
        hasDiabetes = d.hasDiabetes
        hasDiabetesYears = d.hasDiabetesYears
        hasDiabetesVision = d.hasDiabetesVision
        hasDiabetesKidney = d.hasDiabetesKidney
        hasDiabetesNerve = d.hasDiabetesNerve
        hasDiabetesTakMedicine = d.hasDiabetesTakMedicine
        hasDiabetesTakOralMedicine = d.hasDiabetesTakOralMedicine
        hasDiabetesTakInsulin = d.hasDiabetesTakInsulin
        hasDiabetesTakInjection = d.hasDiabetesTakInjection
        hasDepression = d.hasDepression
        hasCOPD = d.hasCOPD
        hasOsteoporosis = d.hasOsteoporosis
        hasAnxiety = d.hasAnxiety
        hasStroke = d.hasStroke
        hasHeartDisease = d.hasHeartDisease
        hasHeartFailure = d.hasHeartFailure
        hasMI = d.hasMI
        hasPD = d.hasPD
        hasMemoryProblems = d.hasMemoryProblems
        hasDementia = d.hasDementia
        hasHighCholesterol = d.hasHighCholesterol
        hasSeizure = d.hasSeizure
        hasNeuropathy = d.hasNeuropathy
        hasChronicKidney = d.hasChronicKidney
        onDialysis = d.onDialysis
        hasVisionProblem = d.hasVisionProblem
        visualImpairments = d.visualImpairments
        hasVisionLoss = d.hasVisionLoss
        hasCorrectiveLenses = d.hasCorrectiveLenses
        hasSevereHearingLoss = d.hasSevereHearingLoss
        hasHearingAid = d.hasHearingAid
        hasHadAFall = d.hasHadAFall
        hasHadAFracture = d.hasHadAFracture
        hasHadAFallPastFiveYears = d.hasHadAFallPastFiveYears
        hasHadAFracturePastFiveYears = d.hasHadAFracturePastFiveYears
        hasFearOfFalling = d.hasFearOfFalling
        canWalkWithoutBreak = d.canWalkWithoutBreak
        hasDifficultyWalking = d.hasDifficultyWalking
        hasDifficultyWalkingUseCane = d.hasDifficultyWalkingUseCane
        hasDifficultyWalkingUseWalker = d.hasDifficultyWalkingUseWalker
        hasDifficultyGettingUp = d.hasDifficultyGettingUp
        hasToHoldOntoWalls = d.hasToHoldOntoWalls
        hasDifficultyWithBalance = d.hasDifficultyWithBalance
        regularlyScheduledMedicines = d.regularlyScheduledMedicines
        studySite = d.studySite
        studyId = d.studyId
        researchPurpose = d.researchPurpose
        consent = d.consent
        testValues = d.testValues
        practiseTestValues = d.practiseTestValues
        practiseTestValues2 = d.practiseTestValues2
        platform = d.platform
        platform_version = d.platform_version
    }


//    var platform_version: Int = android.os.Build.VERSION.SDK_INT
//
//    @SerializedName("V0")
//    @Expose
//    var v0: Int = 0
//
//    @SerializedName("S0")
//    @Expose
//    var s0: Int = 0
//
//    @SerializedName("VS0")
//    @Expose
//    var vs0: Int = 0
//
//    @SerializedName("C0")
//    @Expose
//    var c0: Int = 0
//
//    @SerializedName("V1")
//    @Expose
//    var v1: Int = 0
//
//    @SerializedName("S1")
//    @Expose
//    var s1: Int = 0
//
//    @SerializedName("VS1")
//    @Expose
//    var vs1: Int = 0
//
//    @SerializedName("C1")
//    @Expose
//    var c1: Int = 0
//
//    @SerializedName("V2")
//    @Expose
//    var v2: Int = 0
//
//    @SerializedName("S2")
//    @Expose
//    var s2: Int = 0
//
//    @SerializedName("VS2")
//    @Expose
//    var vs2: Int = 0
//
//    @SerializedName("C2")
//    @Expose
//    var c2: Int = 0
//
//    @SerializedName("V3")
//    @Expose
//    var v3: Int = 0
//
//    @SerializedName("S3")
//    @Expose
//    var s3: Int = 0
//
//    @SerializedName("VS3")
//    @Expose
//    var vs3: Int = 0
//
//    @SerializedName("C3")
//    @Expose
//    var c3: Int = 0
//
//    @SerializedName("V4")
//    @Expose
//    var v4: Int = 0
//
//    @SerializedName("S4")
//    @Expose
//    var s4: Int = 0
//
//    @SerializedName("VS4")
//    @Expose
//    var vs4: Int = 0
//
//    @SerializedName("C4")
//    @Expose
//    var c4: Int = 0
//
//    @SerializedName("V5")
//    @Expose
//    var v5: Int = 0
//
//    @SerializedName("S5")
//    @Expose
//    var s5: Int = 0
//
//    @SerializedName("VS5")
//    @Expose
//    var vs5: Int = 0
//
//    @SerializedName("C5")
//    @Expose
//    var c5: Int = 0
//
//    @SerializedName("V6")
//    @Expose
//    var v6: Int = 0
//
//    @SerializedName("S6")
//    @Expose
//    var s6: Int = 0
//
//    @SerializedName("VS6")
//    @Expose
//    var vs6: Int = 0
//
//    @SerializedName("C6")
//    @Expose
//    var c6: Int = 0
//
//    @SerializedName("V7")
//    @Expose
//    var v7: Int = 0
//
//    @SerializedName("S7")
//    @Expose
//    var s7: Int = 0
//
//    @SerializedName("VS7")
//    @Expose
//    var vs7: Int = 0
//
//    @SerializedName("C7")
//    @Expose
//    var c7: Int = 0
//
//    @SerializedName("V8")
//    @Expose
//    var v8: Int = 0
//
//    @SerializedName("S8")
//    @Expose
//    var s8: Int = 0
//
//    @SerializedName("VS8")
//    @Expose
//    var vs8: Int = 0
//
//    @SerializedName("C8")
//    @Expose
//    var c8: Int = 0
//
//    @SerializedName("V9")
//    @Expose
//    var v9: Int = 0
//
//    @SerializedName("S9")
//    @Expose
//    var s9: Int = 0
//
//    @SerializedName("VS9")
//    @Expose
//    var vs9: Int = 0
//
//    @SerializedName("C9")
//    @Expose
//    var c9: Int = 0
//
//    @SerializedName("V10")
//    @Expose
//    var v10: Int = 0
//
//    @SerializedName("S10")
//    @Expose
//    var s10: Int = 0
//
//    @SerializedName("VS10")
//    @Expose
//    var vs10: Int = 0
//
//    @SerializedName("C10")
//    @Expose
//    var c10: Int = 0
//
//    @SerializedName("V11")
//    @Expose
//    var v11: Int = 0
//
//    @SerializedName("S11")
//    @Expose
//    var s11: Int = 0
//
//    @SerializedName("VS11")
//    @Expose
//    var vs11: Int = 0
//
//    @SerializedName("C11")
//    @Expose
//    var c11: Int = 0
//
//    @SerializedName("V12")
//    @Expose
//    var v12: Int = 0
//
//    @SerializedName("S12")
//    @Expose
//    var s12: Int = 0
//
//    @SerializedName("VS12")
//    @Expose
//    var vs12: Int = 0
//
//    @SerializedName("C12")
//    @Expose
//    var c12: Int = 0
//
//    @SerializedName("V13")
//    @Expose
//    var v13: Int = 0
//
//    @SerializedName("S13")
//    @Expose
//    var s13: Int = 0
//
//    @SerializedName("VS13")
//    @Expose
//    var vs13: Int = 0
//
//    @SerializedName("C13")
//    @Expose
//    var c13: Int = 0
//
//    @SerializedName("V14")
//    @Expose
//    var v14: Int = 0
//
//    @SerializedName("S14")
//    @Expose
//    var s14: Int = 0
//
//    @SerializedName("VS14")
//    @Expose
//    var vs14: Int = 0
//
//    @SerializedName("C14")
//    @Expose
//    var c14: Int = 0
//
//    @SerializedName("V15")
//    @Expose
//    var v15: Int = 0
//
//    @SerializedName("S15")
//    @Expose
//    var s15: Int = 0
//
//    @SerializedName("VS15")
//    @Expose
//    var vs15: Int = 0
//
//    @SerializedName("C15")
//    @Expose
//    var c15: Int = 0
//
//    @SerializedName("V16")
//    @Expose
//    var v16: Int = 0
//
//    @SerializedName("S16")
//    @Expose
//    var s16: Int = 0
//
//    @SerializedName("VS16")
//    @Expose
//    var vs16: Int = 0
//
//    @SerializedName("C16")
//    @Expose
//    var c16: Int = 0
//
//    @SerializedName("V17")
//    @Expose
//    var v17: Int = 0
//
//    @SerializedName("S17")
//    @Expose
//    var s17: Int = 0
//
//    @SerializedName("VS17")
//    @Expose
//    var vs17: Int = 0
//
//    @SerializedName("C17")
//    @Expose
//    var c17: Int = 0
//
//    @SerializedName("V18")
//    @Expose
//    var v18: Int = 0
//
//    @SerializedName("S18")
//    @Expose
//    var s18: Int = 0
//
//    @SerializedName("VS18")
//    @Expose
//    var vs18: Int = 0
//
//    @SerializedName("C18")
//    @Expose
//    var c18: Int = 0
//
//    @SerializedName("V19")
//    @Expose
//    var v19: Int = 0
//
//    @SerializedName("S19")
//    @Expose
//    var s19: Int = 0
//
//    @SerializedName("VS19")
//    @Expose
//    var vs19: Int = 0
//
//    @SerializedName("C19")
//    @Expose
//    var c19: Int = 0
//
//    @SerializedName("V20")
//    @Expose
//    var v20: Int = 0
//
//    @SerializedName("S20")
//    @Expose
//    var s20: Int = 0
//
//    @SerializedName("VS20")
//    @Expose
//    var vs20: Int = 0
//
//    @SerializedName("C20")
//    @Expose
//    var c20: Int = 0
//
//    @SerializedName("V21")
//    @Expose
//    var v21: Int = 0
//
//    @SerializedName("S21")
//    @Expose
//    var s21: Int = 0
//
//    @SerializedName("VS21")
//    @Expose
//    var vs21: Int = 0
//
//    @SerializedName("C21")
//    @Expose
//    var c21: Int = 0
//
//    @SerializedName("V22")
//    @Expose
//    var v22: Int = 0
//
//    @SerializedName("S22")
//    @Expose
//    var s22: Int = 0
//
//    @SerializedName("VS22")
//    @Expose
//    var vs22: Int = 0
//
//    @SerializedName("C22")
//    @Expose
//    var c22: Int = 0
//
//    @SerializedName("V23")
//    @Expose
//    var v23: Int = 0
//
//    @SerializedName("S23")
//    @Expose
//    var s23: Int = 0
//
//    @SerializedName("VS23")
//    @Expose
//    var vs23: Int = 0
//
//    @SerializedName("C23")
//    @Expose
//    var c23: Int = 0
//
//    @SerializedName("V24")
//    @Expose
//    var v24: Int = 0
//
//    @SerializedName("S24")
//    @Expose
//    var s24: Int = 0
//
//    @SerializedName("VS24")
//    @Expose
//    var vs24: Int = 0
//
//    @SerializedName("C24")
//    @Expose
//    var c24: Int = 0
//
//    @SerializedName("V25")
//    @Expose
//    var v25: Int = 0
//
//    @SerializedName("S25")
//    @Expose
//    var s25: Int = 0
//
//    @SerializedName("VS25")
//    @Expose
//    var vs25: Int = 0
//
//    @SerializedName("C25")
//    @Expose
//    var c25: Int = 0
//
//    @SerializedName("V26")
//    @Expose
//    var v26: Int = 0
//
//    @SerializedName("S26")
//    @Expose
//    var s26: Int = 0
//
//    @SerializedName("VS26")
//    @Expose
//    var vs26: Int = 0
//
//    @SerializedName("C26")
//    @Expose
//    var c26: Int = 0
//
//    @SerializedName("V27")
//    @Expose
//    var v27: Int = 0
//
//    @SerializedName("S27")
//    @Expose
//    var s27: Int = 0
//
//    @SerializedName("VS27")
//    @Expose
//    var vs27: Int = 0
//
//    @SerializedName("C27")
//    @Expose
//    var c27: Int = 0
//
//    @SerializedName("V28")
//    @Expose
//    var v28: Int = 0
//
//    @SerializedName("S28")
//    @Expose
//    var s28: Int = 0
//
//    @SerializedName("VS28")
//    @Expose
//    var vs28: Int = 0
//
//    @SerializedName("C28")
//    @Expose
//    var c28: Int = 0
//
//    @SerializedName("V29")
//    @Expose
//    var v29: Int = 0
//
//    @SerializedName("S29")
//    @Expose
//    var s29: Int = 0
//
//    @SerializedName("VS29")
//    @Expose
//    var vs29: Int = 0
//
//    @SerializedName("C29")
//    @Expose
//    var c29: Int = 0
//
//    @SerializedName("V30")
//    @Expose
//    var v30: Int = 0
//
//    @SerializedName("S30")
//    @Expose
//    var s30: Int = 0
//
//    @SerializedName("VS30")
//    @Expose
//    var vs30: Int = 0
//
//    @SerializedName("C30")
//    @Expose
//    var c30: Int = 0
//
//    @SerializedName("V31")
//    @Expose
//    var v31: Int = 0
//
//    @SerializedName("S31")
//    @Expose
//    var s31: Int = 0
//
//    @SerializedName("VS31")
//    @Expose
//    var vs31: Int = 0
//
//    @SerializedName("C31")
//    @Expose
//    var c31: Int = 0
//
//    @SerializedName("V32")
//    @Expose
//    var v32: Int = 0
//
//    @SerializedName("S32")
//    @Expose
//    var s32: Int = 0
//
//    @SerializedName("VS32")
//    @Expose
//    var vs32: Int = 0
//
//    @SerializedName("C32")
//    @Expose
//    var c32: Int = 0
//
//    @SerializedName("V33")
//    @Expose
//    var v33: Int = 0
//
//    @SerializedName("S33")
//    @Expose
//    var s33: Int = 0
//
//    @SerializedName("VS33")
//    @Expose
//    var vs33: Int = 0
//
//    @SerializedName("C33")
//    @Expose
//    var c33: Int = 0
//
//    @SerializedName("V34")
//    @Expose
//    var v34: Int = 0
//
//    @SerializedName("S34")
//    @Expose
//    var s34: Int = 0
//
//    @SerializedName("VS34")
//    @Expose
//    var vs34: Int = 0
//
//    @SerializedName("C34")
//    @Expose
//    var c34: Int = 0
//
//    @SerializedName("V35")
//    @Expose
//    var v35: Int = 0
//
//    @SerializedName("S35")
//    @Expose
//    var s35: Int = 0
//
//    @SerializedName("VS35")
//    @Expose
//    var vs35: Int = 0
//
//    @SerializedName("C35")
//    @Expose
//    var c35: Int = 0
//
//    @SerializedName("V36")
//    @Expose
//    var v36: Int = 0
//
//    @SerializedName("S36")
//    @Expose
//    var s36: Int = 0
//
//    @SerializedName("VS36")
//    @Expose
//    var vs36: Int = 0
//
//    @SerializedName("C36")
//    @Expose
//    var c36: Int = 0
//
//    @SerializedName("V37")
//    @Expose
//    var v37: Int = 0
//
//    @SerializedName("S37")
//    @Expose
//    var s37: Int = 0
//
//    @SerializedName("VS37")
//    @Expose
//    var vs37: Int = 0
//
//    @SerializedName("C37")
//    @Expose
//    var c37: Int = 0
//
//    @SerializedName("V38")
//    @Expose
//    var v38: Int = 0
//
//    @SerializedName("S38")
//    @Expose
//    var s38: Int = 0
//
//    @SerializedName("VS38")
//    @Expose
//    var vs38: Int = 0
//
//    @SerializedName("C38")
//    @Expose
//    var c38: Int = 0
//
//    @SerializedName("V39")
//    @Expose
//    var v39: Int = 0
//
//    @SerializedName("S39")
//    @Expose
//    var s39: Int = 0
//
//    @SerializedName("VS39")
//    @Expose
//    var vs39: Int? = null
//
//    @SerializedName("C39")
//    @Expose
//    var c39: Int = 0
//
//    @SerializedName("V40")
//    @Expose
//    var v40: Int = 0
//
//    @SerializedName("S40")
//    @Expose
//    var s40: Int = 0
//
//    @SerializedName("VS40")
//    @Expose
//    var vs40: Int = 0
//
//    @SerializedName("C40")
//    @Expose
//    var c40: Int = 0
//
//    @SerializedName("V41")
//    @Expose
//    var v41: Int = 0
//
//    @SerializedName("S41")
//    @Expose
//    var s41: Int = 0
//
//    @SerializedName("VS41")
//    @Expose
//    var vs41: Int = 0
//
//    @SerializedName("C41")
//    @Expose
//    var c41: Int = 0
//
//    @SerializedName("V42")
//    @Expose
//    var v42: Int = 0
//
//    @SerializedName("S42")
//    @Expose
//    var s42: Int = 0
//
//    @SerializedName("VS42")
//    @Expose
//    var vs42: Int = 0
//
//    @SerializedName("C42")
//    @Expose
//    var c42: Int = 0
//
//    @SerializedName("V43")
//    @Expose
//    var v43: Int = 0
//
//    @SerializedName("S43")
//    @Expose
//    var s43: Int = 0
//
//    @SerializedName("VS43")
//    @Expose
//    var vs43: Int = 0
//
//    @SerializedName("C43")
//    @Expose
//    var c43: Int = 0
//
//    @SerializedName("V44")
//    @Expose
//    var v44: Int = 0
//
//    @SerializedName("S44")
//    @Expose
//    var s44: Int = 0
//
//    @SerializedName("VS44")
//    @Expose
//    var vs44: Int = 0
//
//    @SerializedName("C44")
//    @Expose
//    var c44: Int = 0
}