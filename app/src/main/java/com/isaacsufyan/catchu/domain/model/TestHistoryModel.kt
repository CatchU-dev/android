package com.isaacsufyan.catchu.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class TestHistoryModel(

    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @SerializedName("patient_first_name")
    @Expose
    var patientFirstName: String? = null,

    @SerializedName("patient_last_name")
    @Expose
    var patientLastName: String? = null,

    @SerializedName("dob")
    @Expose
    var dob: String? = null,

    @SerializedName("test_ordered_date")
    @Expose
    var testOrderedDate: String? = null,

    @SerializedName("patient_email")
    @Expose
    var patientEmail: String? = null,

    @SerializedName("patient_telephone")
    @Expose
    var patientTelephone: String? = null,

    @SerializedName("test_token")
    @Expose
    var testToken: String? = null,

    @SerializedName("date_scheduled")
    @Expose
    var dateScheduled: String? = null,

    @SerializedName("link_sent_datetime")
    @Expose
    var linkSentDatetime: String? = null,

    @SerializedName("expiration_date")
    @Expose
    var expirationDate: String? = null,

    @SerializedName("timezone")
    @Expose
    var timezone: String? = null,

    @SerializedName("status")
    @Expose
    var status: String? = null,

    @SerializedName("test_location")
    @Expose
    var testLocation: String? = null,

    @SerializedName("test_id")
    @Expose
    var testId: String? = null,

    @SerializedName("test_line_item_id")
    @Expose
    var testLineItemId: String? = null,

    @SerializedName("is_remote")
    @Expose
    var isRemote: Boolean? = null,

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null,

    @SerializedName("patient")
    @Expose
    var patient: Int? = null,

    @SerializedName("billee")
    @Expose
    var billee: Int? = null,

    @SerializedName("iPhone")
    @Expose
    var iPhone: String? = null,

    @SerializedName("Provider")
    @Expose
    var provider: Int? = null,

    @SerializedName("batch_test")
    @Expose
    var batchTest: Int? = null,

    @SerializedName("invoice")
    @Expose
    var invoice: String? = null,

    @SerializedName("invoice_credit")
    @Expose
    var invoiceCredit: String? = null,

    @SerializedName("credit_amount")
    @Expose
    var creditAmount: String? = null,

    @SerializedName("amount_reason")
    @Expose
    var amountReason: String? = null
)