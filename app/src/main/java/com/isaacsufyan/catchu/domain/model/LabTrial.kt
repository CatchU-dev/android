package com.isaacsufyan.catchu.domain.model

import androidx.annotation.Keep
import com.google.gson.TypeAdapter
import com.google.gson.annotations.Expose
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

/** Lab build only: parameters of the run, so the backend knows how many trials to expect. */
@Keep
data class LabConfig(
    @SerializedName("num_blocks") @Expose val numBlocks: Int,
    @SerializedName("trials_per_block") @Expose val trialsPerBlock: Int,
    @SerializedName("per_condition_per_block") @Expose val perConditionPerBlock: Int,
    @SerializedName("stimulus_ms") @Expose val stimulusMs: Int,
    @SerializedName("response_window_ms") @Expose val responseWindowMs: Int,
    @SerializedName("iti_min_ms") @Expose val itiMinMs: Int,
    @SerializedName("iti_max_ms") @Expose val itiMaxMs: Int
)

/**
 * One main-test trial, in presentation order. [rtMs] is null when there was no response and must be
 * sent as an explicit JSON null (the app-wide Gson omits nulls), hence the dedicated adapter.
 */
@Keep
@JsonAdapter(LabTrialAdapter::class)
data class LabTrial(
    val trialNumber: Int,
    val block: Int,
    val type: String,
    val rtMs: Int?,
    val responded: Boolean,
    val outcome: String
)

@Keep
@JsonAdapter(LabPracticeTrialAdapter::class)
data class LabPracticeTrial(
    val attempt: Int,
    val trialNumber: Int,
    val type: String,
    val rtMs: Int?,
    val responded: Boolean
)

@Keep
class LabTrialAdapter : TypeAdapter<LabTrial>() {
    override fun write(out: JsonWriter, value: LabTrial?) {
        if (value == null) {
            out.nullValue()
            return
        }
        val previous = out.serializeNulls
        out.serializeNulls = true
        out.beginObject()
        out.name("trial_number").value(value.trialNumber.toLong())
        out.name("block").value(value.block.toLong())
        out.name("type").value(value.type)
        out.name("rt_ms")
        if (value.rtMs == null) out.nullValue() else out.value(value.rtMs.toLong())
        out.name("responded").value(value.responded)
        out.name("outcome").value(value.outcome)
        out.endObject()
        out.serializeNulls = previous
    }

    override fun read(reader: JsonReader): LabTrial =
        throw UnsupportedOperationException("LabTrial is upload-only")
}

@Keep
class LabPracticeTrialAdapter : TypeAdapter<LabPracticeTrial>() {
    override fun write(out: JsonWriter, value: LabPracticeTrial?) {
        if (value == null) {
            out.nullValue()
            return
        }
        val previous = out.serializeNulls
        out.serializeNulls = true
        out.beginObject()
        out.name("attempt").value(value.attempt.toLong())
        out.name("trial_number").value(value.trialNumber.toLong())
        out.name("type").value(value.type)
        out.name("rt_ms")
        if (value.rtMs == null) out.nullValue() else out.value(value.rtMs.toLong())
        out.name("responded").value(value.responded)
        out.endObject()
        out.serializeNulls = previous
    }

    override fun read(reader: JsonReader): LabPracticeTrial =
        throw UnsupportedOperationException("LabPracticeTrial is upload-only")
}
