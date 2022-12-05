package com.isaacsufyan.catchu.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RawMeasurementModel(

    @SerializedName("test")
    @Expose
    var test: Int? = null,

    @SerializedName("raw_measurements")
    @Expose
    var rawMeasurements: RawMeasurements? = null
)

object RawMeasurements {

    @SerializedName("subjectid")
    @Expose
    var subjectid: Int? = null

    @SerializedName("testdate")
    @Expose
    var testdate: String? = null

    @SerializedName("selectedYear")
    @Expose
    var selectedYear: Int = 0

    @SerializedName("selectedMonth")
    @Expose
    var selectedMonth: Int = 0

    @SerializedName("selectedDate")
    @Expose
    var selectedDate: Int = 0

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
    var selectedGender: Int? = null

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
    var regularlyScheduledMedicines: String? = null

    @SerializedName("ResPurp")
    @Expose
    var researchPurpose: String = "NO"

//    @SerializedName("StudySite")
//    @Expose
//    var studySite: String? = null
//
//    @SerializedName("StudyID")
//    @Expose
//    var studyID: String? = null

    @SerializedName("Consent")
    @Expose
    var consent: Int = 1

    @SerializedName("V0")
    @Expose
    var v0: Int? = null

    @SerializedName("S0")
    @Expose
    var s0: Int? = null

    @SerializedName("VS0")
    @Expose
    var vs0: Int? = null

    @SerializedName("C0")
    @Expose
    var c0: Int? = null

    @SerializedName("V1")
    @Expose
    var v1: Int? = null

    @SerializedName("S1")
    @Expose
    var s1: Int? = null

    @SerializedName("VS1")
    @Expose
    var vs1: Int? = null

    @SerializedName("C1")
    @Expose
    var c1: Int? = null

    @SerializedName("V2")
    @Expose
    var v2: Int? = null

    @SerializedName("S2")
    @Expose
    var s2: Int? = null

    @SerializedName("VS2")
    @Expose
    var vs2: Int? = null

    @SerializedName("C2")
    @Expose
    var c2: Int? = null

    @SerializedName("V3")
    @Expose
    var v3: Int? = null

    @SerializedName("S3")
    @Expose
    var s3: Int? = null

    @SerializedName("VS3")
    @Expose
    var vs3: Int? = null

    @SerializedName("C3")
    @Expose
    var c3: Int? = null

    @SerializedName("V4")
    @Expose
    var v4: Int? = null

    @SerializedName("S4")
    @Expose
    var s4: Int? = null

    @SerializedName("VS4")
    @Expose
    var vs4: Int? = null

    @SerializedName("C4")
    @Expose
    var c4: Int? = null

    @SerializedName("V5")
    @Expose
    var v5: Int? = null

    @SerializedName("S5")
    @Expose
    var s5: Int? = null

    @SerializedName("VS5")
    @Expose
    var vs5: Int? = null

    @SerializedName("C5")
    @Expose
    var c5: Int? = null

    @SerializedName("V6")
    @Expose
    var v6: Int? = null

    @SerializedName("S6")
    @Expose
    var s6: Int? = null

    @SerializedName("VS6")
    @Expose
    var vs6: Int? = null

    @SerializedName("C6")
    @Expose
    var c6: Int? = null

    @SerializedName("V7")
    @Expose
    var v7: Int? = null

    @SerializedName("S7")
    @Expose
    var s7: Int? = null

    @SerializedName("VS7")
    @Expose
    var vs7: Int? = null

    @SerializedName("C7")
    @Expose
    var c7: Int? = null

    @SerializedName("V8")
    @Expose
    var v8: Int? = null

    @SerializedName("S8")
    @Expose
    var s8: Int? = null

    @SerializedName("VS8")
    @Expose
    var vs8: Int? = null

    @SerializedName("C8")
    @Expose
    var c8: Int? = null

    @SerializedName("V9")
    @Expose
    var v9: Int? = null

    @SerializedName("S9")
    @Expose
    var s9: Int? = null

    @SerializedName("VS9")
    @Expose
    var vs9: Int? = null

    @SerializedName("C9")
    @Expose
    var c9: Int? = null

    @SerializedName("V10")
    @Expose
    var v10: Int? = null

    @SerializedName("S10")
    @Expose
    var s10: Int? = null

    @SerializedName("VS10")
    @Expose
    var vs10: Int? = null

    @SerializedName("C10")
    @Expose
    var c10: Int? = null

    @SerializedName("V11")
    @Expose
    var v11: Int? = null

    @SerializedName("S11")
    @Expose
    var s11: Int? = null

    @SerializedName("VS11")
    @Expose
    var vs11: Int? = null

    @SerializedName("C11")
    @Expose
    var c11: Int? = null

    @SerializedName("V12")
    @Expose
    var v12: Int? = null

    @SerializedName("S12")
    @Expose
    var s12: Int? = null

    @SerializedName("VS12")
    @Expose
    var vs12: Int? = null

    @SerializedName("C12")
    @Expose
    var c12: Int? = null

    @SerializedName("V13")
    @Expose
    var v13: Int? = null

    @SerializedName("S13")
    @Expose
    var s13: Int? = null

    @SerializedName("VS13")
    @Expose
    var vs13: Int? = null

    @SerializedName("C13")
    @Expose
    var c13: Int? = null

    @SerializedName("V14")
    @Expose
    var v14: Int? = null

    @SerializedName("S14")
    @Expose
    var s14: Int? = null

    @SerializedName("VS14")
    @Expose
    var vs14: Int? = null

    @SerializedName("C14")
    @Expose
    var c14: Int? = null

    @SerializedName("V15")
    @Expose
    var v15: Int? = null

    @SerializedName("S15")
    @Expose
    var s15: Int? = null

    @SerializedName("VS15")
    @Expose
    var vs15: Int? = null

    @SerializedName("C15")
    @Expose
    var c15: Int? = null

    @SerializedName("V16")
    @Expose
    var v16: Int? = null

    @SerializedName("S16")
    @Expose
    var s16: Int? = null

    @SerializedName("VS16")
    @Expose
    var vs16: Int? = null

    @SerializedName("C16")
    @Expose
    var c16: Int? = null

    @SerializedName("V17")
    @Expose
    var v17: Int? = null

    @SerializedName("S17")
    @Expose
    var s17: Int? = null

    @SerializedName("VS17")
    @Expose
    var vs17: Int? = null

    @SerializedName("C17")
    @Expose
    var c17: Int? = null

    @SerializedName("V18")
    @Expose
    var v18: Int? = null

    @SerializedName("S18")
    @Expose
    var s18: Int? = null

    @SerializedName("VS18")
    @Expose
    var vs18: Int? = null

    @SerializedName("C18")
    @Expose
    var c18: Int? = null

    @SerializedName("V19")
    @Expose
    var v19: Int? = null

    @SerializedName("S19")
    @Expose
    var s19: Int? = null

    @SerializedName("VS19")
    @Expose
    var vs19: Int? = null

    @SerializedName("C19")
    @Expose
    var c19: Int? = null

    @SerializedName("V20")
    @Expose
    var v20: Int? = null

    @SerializedName("S20")
    @Expose
    var s20: Int? = null

    @SerializedName("VS20")
    @Expose
    var vs20: Int? = null

    @SerializedName("C20")
    @Expose
    var c20: Int? = null

    @SerializedName("V21")
    @Expose
    var v21: Int? = null

    @SerializedName("S21")
    @Expose
    var s21: Int? = null

    @SerializedName("VS21")
    @Expose
    var vs21: Int? = null

    @SerializedName("C21")
    @Expose
    var c21: Int? = null

    @SerializedName("V22")
    @Expose
    var v22: Int? = null

    @SerializedName("S22")
    @Expose
    var s22: Int? = null

    @SerializedName("VS22")
    @Expose
    var vs22: Int? = null

    @SerializedName("C22")
    @Expose
    var c22: Int? = null

    @SerializedName("V23")
    @Expose
    var v23: Int? = null

    @SerializedName("S23")
    @Expose
    var s23: Int? = null

    @SerializedName("VS23")
    @Expose
    var vs23: Int? = null

    @SerializedName("C23")
    @Expose
    var c23: Int? = null

    @SerializedName("V24")
    @Expose
    var v24: Int? = null

    @SerializedName("S24")
    @Expose
    var s24: Int? = null

    @SerializedName("VS24")
    @Expose
    var vs24: Int? = null

    @SerializedName("C24")
    @Expose
    var c24: Int? = null

    @SerializedName("V25")
    @Expose
    var v25: Int? = null

    @SerializedName("S25")
    @Expose
    var s25: Int? = null

    @SerializedName("VS25")
    @Expose
    var vs25: Int? = null

    @SerializedName("C25")
    @Expose
    var c25: Int? = null

    @SerializedName("V26")
    @Expose
    var v26: Int? = null

    @SerializedName("S26")
    @Expose
    var s26: Int? = null

    @SerializedName("VS26")
    @Expose
    var vs26: Int? = null

    @SerializedName("C26")
    @Expose
    var c26: Int? = null

    @SerializedName("V27")
    @Expose
    var v27: Int? = null

    @SerializedName("S27")
    @Expose
    var s27: Int? = null

    @SerializedName("VS27")
    @Expose
    var vs27: Int? = null

    @SerializedName("C27")
    @Expose
    var c27: Int? = null

    @SerializedName("V28")
    @Expose
    var v28: Int? = null

    @SerializedName("S28")
    @Expose
    var s28: Int? = null

    @SerializedName("VS28")
    @Expose
    var vs28: Int? = null

    @SerializedName("C28")
    @Expose
    var c28: Int? = null

    @SerializedName("V29")
    @Expose
    var v29: Int? = null

    @SerializedName("S29")
    @Expose
    var s29: Int? = null

    @SerializedName("VS29")
    @Expose
    var vs29: Int? = null

    @SerializedName("C29")
    @Expose
    var c29: Int? = null

    @SerializedName("V30")
    @Expose
    var v30: Int? = null

    @SerializedName("S30")
    @Expose
    var s30: Int? = null

    @SerializedName("VS30")
    @Expose
    var vs30: Int? = null

    @SerializedName("C30")
    @Expose
    var c30: Int? = null

    @SerializedName("V31")
    @Expose
    var v31: Int? = null

    @SerializedName("S31")
    @Expose
    var s31: Int? = null

    @SerializedName("VS31")
    @Expose
    var vs31: Int? = null

    @SerializedName("C31")
    @Expose
    var c31: Int? = null

    @SerializedName("V32")
    @Expose
    var v32: Int? = null

    @SerializedName("S32")
    @Expose
    var s32: Int? = null

    @SerializedName("VS32")
    @Expose
    var vs32: Int? = null

    @SerializedName("C32")
    @Expose
    var c32: Int? = null

    @SerializedName("V33")
    @Expose
    var v33: Int? = null

    @SerializedName("S33")
    @Expose
    var s33: Int? = null

    @SerializedName("VS33")
    @Expose
    var vs33: Int? = null

    @SerializedName("C33")
    @Expose
    var c33: Int? = null

    @SerializedName("V34")
    @Expose
    var v34: Int? = null

    @SerializedName("S34")
    @Expose
    var s34: Int? = null

    @SerializedName("VS34")
    @Expose
    var vs34: Int? = null

    @SerializedName("C34")
    @Expose
    var c34: Int? = null

    @SerializedName("V35")
    @Expose
    var v35: Int? = null

    @SerializedName("S35")
    @Expose
    var s35: Int? = null

    @SerializedName("VS35")
    @Expose
    var vs35: Int? = null

    @SerializedName("C35")
    @Expose
    var c35: Int? = null

    @SerializedName("V36")
    @Expose
    var v36: Int? = null

    @SerializedName("S36")
    @Expose
    var s36: Int? = null

    @SerializedName("VS36")
    @Expose
    var vs36: Int? = null

    @SerializedName("C36")
    @Expose
    var c36: Int? = null

    @SerializedName("V37")
    @Expose
    var v37: Int? = null

    @SerializedName("S37")
    @Expose
    var s37: Int? = null

    @SerializedName("VS37")
    @Expose
    var vs37: Int? = null

    @SerializedName("C37")
    @Expose
    var c37: Int? = null

    @SerializedName("V38")
    @Expose
    var v38: Int? = null

    @SerializedName("S38")
    @Expose
    var s38: Int? = null

    @SerializedName("VS38")
    @Expose
    var vs38: Int? = null

    @SerializedName("C38")
    @Expose
    var c38: Int? = null

    @SerializedName("V39")
    @Expose
    var v39: Int? = null

    @SerializedName("S39")
    @Expose
    var s39: Int? = null

    @SerializedName("VS39")
    @Expose
    var vs39: Int? = null

    @SerializedName("C39")
    @Expose
    var c39: Int? = null

    @SerializedName("V40")
    @Expose
    var v40: Int? = null

    @SerializedName("S40")
    @Expose
    var s40: Int? = null

    @SerializedName("VS40")
    @Expose
    var vs40: Int? = null

    @SerializedName("C40")
    @Expose
    var c40: Int? = null

    @SerializedName("V41")
    @Expose
    var v41: Int? = null

    @SerializedName("S41")
    @Expose
    var s41: Int? = null

    @SerializedName("VS41")
    @Expose
    var vs41: Int? = null

    @SerializedName("C41")
    @Expose
    var c41: Int? = null

    @SerializedName("V42")
    @Expose
    var v42: Int? = null

    @SerializedName("S42")
    @Expose
    var s42: Int? = null

    @SerializedName("VS42")
    @Expose
    var vs42: Int? = null

    @SerializedName("C42")
    @Expose
    var c42: Int? = null

    @SerializedName("V43")
    @Expose
    var v43: Int? = null

    @SerializedName("S43")
    @Expose
    var s43: Int? = null

    @SerializedName("VS43")
    @Expose
    var vs43: Int? = null

    @SerializedName("C43")
    @Expose
    var c43: Int? = null

    @SerializedName("V44")
    @Expose
    var v44: Int? = null

    @SerializedName("S44")
    @Expose
    var s44: Int? = null

    @SerializedName("VS44")
    @Expose
    var vs44: Int? = null

    @SerializedName("C44")
    @Expose
    var c44: Int? = null
}