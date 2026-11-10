package com.isaacsufyan.catchu.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LabSessionRequest(
    @SerializedName("study_site")    @Expose val studySite:   String,
    @SerializedName("study_id")      @Expose val studyId:     String,
    @SerializedName("wave")          @Expose val wave:        String,
    @SerializedName("first_name")    @Expose val firstName:   String,
    @SerializedName("last_name")     @Expose val lastName:    String,
    @SerializedName("dob")           @Expose val dateOfBirth: String,
    @SerializedName("gender")        @Expose val gender:      String,
    @SerializedName("num_blocks")    @Expose val numBlocks:   Int
)
