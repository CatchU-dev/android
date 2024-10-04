package com.isaacsufyan.catchu.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class PatientDetails(
    @SerializedName("id")
    @Expose
    var patient_Id: Int? = null,

    @SerializedName("test_id")
    @Expose
    var test_id: Int? = null,

    @SerializedName("patient_id")
    @Expose
    var patientId: Int? = null,

    @SerializedName("first_name")
    @Expose
    var firstName: String? = null,

    @SerializedName("last_name")
    @Expose
    var lastName: String? = null,

    @SerializedName("dob")
    @Expose
    var dob: String? = null
)