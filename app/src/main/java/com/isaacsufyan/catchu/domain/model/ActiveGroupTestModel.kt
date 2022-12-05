package com.isaacsufyan.catchu.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class ActiveGroupTest(

    @SerializedName("group_id")
    @Expose
    var groupId: Int? = null,

    @SerializedName("batch_test_id")
    @Expose
    var batchTestId: String? = null,

    @SerializedName("batch_name")
    @Expose
    var batchName: String? = null,

    @SerializedName("batch_code")
    @Expose
    var batchCode: String? = null,

    @SerializedName("no_of_tests")
    @Expose
    var noOfTests: Int? = null,

    @SerializedName("status")
    @Expose
    var status: String? = null,

    @SerializedName("batch_test_location")
    @Expose
    var batchTestLocation: String? = null,

    @SerializedName("timezone")
    @Expose
    var timezone: String? = null,

    @SerializedName("expiration_date")
    @Expose
    var expirationDate: String? = null,

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null,

    @SerializedName("community")
    @Expose
    var community: Int? = null,

    @SerializedName("provider")
    @Expose
    var provider: ProviderModel? = null,

    @SerializedName("billee")
    @Expose
    var billee: BilleeModel? = null,

    @SerializedName("total_no_of_tests")
    @Expose
    var totalNoOfTests: String? = null,

    @SerializedName("expiration_date_am_pm")
    @Expose
    var expirationDateAmPm: String? = null,

    @SerializedName("billee_list")
    @Expose
    var billeeList: List<BilleeModel>? = null,

    @SerializedName("provider_list")
    @Expose
    var providerList: List<ProviderModel>? = null
){
    override fun toString(): String {
        return "$batchCode - $batchName"
    }
}