package com.isaacsufyan.catchu.domain.model

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class TestFeedbackRequest(
    @SerializedName("test_id")
    @Expose
    val testId: Int,

    @SerializedName("feedback")
    @Expose
    val feedback: String,
)
