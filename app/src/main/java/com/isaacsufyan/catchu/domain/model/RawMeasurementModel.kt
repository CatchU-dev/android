package com.isaacsufyan.catchu.domain.model

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class RawMeasurementModel(

    @SerializedName("test")
    @Expose
    var test: Int? = null,

    @SerializedName("raw_measurements")
    @Expose
    var rawMeasurements: RawMeasurements? = null,

    @SerializedName("unsorted_raw_measurements")
    @Expose
    var unsortedRawMeasurements: HashMap<String, Int>? = null,

    // Lab build only (isLabTest); null for live so these keys are not sent.
    @SerializedName("lab_config")
    @Expose
    var labConfig: LabConfig? = null,

    @SerializedName("practice_trials")
    @Expose
    var practiceTrials: List<LabPracticeTrial>? = null,

    @SerializedName("trials")
    @Expose
    var trials: List<LabTrial>? = null
)
