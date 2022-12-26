package com.isaacsufyan.catchu.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class ResponseModel(

    @SerializedName("status")
    @Expose
    var status: Boolean = false,

    @SerializedName("code")
    @Expose
    var code: Int? = null,

    @SerializedName("message")
    @Expose
    var message: String? = null,

    @SerializedName("data")
    @Expose
    var data: Data? = null

)

data class Data(

    //  Validate Community API
    @SerializedName("community_id")
    @Expose
    var community_id: Int? = null,

    @SerializedName("community")
    @Expose
    var community: String? = null,

    // Active Group Test API
    @SerializedName("active_group_tests")
    @Expose
    var activeGroupTests: List<ActiveGroupTest>? = null,

    //  Get Data For Create Group Test API
    @SerializedName("batch_code")
    @Expose
    var batchCode: Int? = null,

    @SerializedName("providers_list")
    @Expose
    var providersList: List<ProviderModel>? = null,

    @SerializedName("billee_list")
    @Expose
    var billeeList: List<BilleeModel>? = null,

    //  Billee List Group Test API
    @SerializedName("billees_list")
    @Expose
    var billeesList: List<BilleeModel>? = null,

    //  Create Group Test API
    @SerializedName("group_test_id")
    @Expose
    var groupTestId: Int? = null,

    // Resume Batch Test API
    @SerializedName("test_history")
    @Expose
    var testHistory: List<TestHistoryModel>? = null,

    //  Create Group Test API
    @SerializedName("resume_batch_status")
    @Expose
    var resumeBatchTest: Boolean = false,

    @SerializedName("patient_details")
    @Expose
    var patientDetails: PatientDetails? = null,

    //  Create Patient Test within Group API
    @SerializedName("test_id")
    @Expose
    var test_id: Int? = null,

    //  TermsAndCondition API
    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @SerializedName("accepted")
    @Expose
    var acceptedTermAndCondition: Boolean = false,

    @SerializedName("iframe_url")
    @Expose
    var termAndConditionURL: String? = null,

    //Batch Test Status Api

    @SerializedName("batch_completed")
    @Expose
    var batchCompleted: Boolean = false
)