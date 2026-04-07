package com.isaacsufyan.catchu.domain.model

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class PatientPrefillResponse(
    @SerializedName("status")
    @Expose
    val status: Boolean,
    @SerializedName("code")
    @Expose
    val code: Int?,
    @SerializedName("message")
    @Expose
    val message: String?,
    @SerializedName("data")
    @Expose
    val data: RawMeasurements?
)
