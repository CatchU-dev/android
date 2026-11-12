package com.isaacsufyan.catchu.framework.datastore.network.api

import com.isaacsufyan.catchu.domain.model.LabSessionRequest
import com.isaacsufyan.catchu.domain.model.PatientPrefillResponse
import com.isaacsufyan.catchu.domain.model.RawMeasurementModel
import com.isaacsufyan.catchu.domain.model.ResponseModel
import com.isaacsufyan.catchu.domain.model.TestFeedbackRequest
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface RestApis {

    @FormUrlEncoded
    @POST("api/community/validate_community/")
    suspend fun validateCommunity(
        @Field("community_id") community_id: String
    ): ResponseModel

    @GET("api/community/active_group_test_list/{Id}/")
    suspend fun activeGroupTestList(
        @Path("Id") communityId: Int
    ): ResponseModel

    @GET("api/test/billee_list_group_test/{Id}/")
    suspend fun getBilleeListGroupTest(
        @Path("Id") providerId: Int
    ): ResponseModel

    @GET("api/community/create_group_test/{Id}/")
    suspend fun getCreateGroupTestData(
        @Path("Id") communityId: Int
    ): ResponseModel

    @Multipart
    @POST("api/community/create_group_test/")
    suspend fun createGroupTest(
        @Part("community_id") community_id: RequestBody,
        @Part("group_name") group_name: RequestBody,
        @Part("batch_code") batch_code: RequestBody,
        @Part("provider_id") provider_id: RequestBody,
        @Part("billee_id") billee_id: RequestBody,
        @Part("expiry_date") expiry_date: RequestBody,
        @Part("no_of_tests") no_of_tests: RequestBody,
        @Part("timezone") timezone: RequestBody,
    ): Response<ResponseModel>

    @GET("api/community/group_test_details/{Id}/")
    suspend fun getGroupTestDetails(
        @Path("Id") groupId: Int
    ): ResponseModel

    @GET("api/community/resume_batch_test/{Id}")
    suspend fun resumeBatchTest(
        @Path("Id") groupTestId: Int
    ): ResponseModel

    @POST("api/community/terminate_group_test/{Id}")
    suspend fun terminateGroupTest(
        @Path("Id") groupTestId: Int
    ): ResponseModel

    @GET("api/community/patient_prefill/")
    suspend fun getPatientPrefill(
        @Query("first_name") firstName: String,
        @Query("last_name") lastName: String,
        @Query("dob") dob: String
    ): Response<PatientPrefillResponse>

    @FormUrlEncoded
    @POST("api/community/validate_patient/")
    suspend fun validatePatient(
        @Field("community_id") community_id: Int,
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String,
        @Field("dob") dob: String
    ): Response<ResponseModel>

    @GET("api/test/test_and_patient_details/")
    suspend fun validatePatientToken(
        @Query("test_token") testToken: String
    ): Response<ResponseModel>

    @FormUrlEncoded
    @POST("api/test/create_patient_test_within_group/")
    suspend fun createPatientTestWithInGroup(
        @Field("batch_test_id") groupId: Int,
        @Field("patient_id") patientIn: Int
    ): Response<ResponseModel>

    @FormUrlEncoded
    @POST("api/user/terms_conditions_status/")
    suspend fun termsAndConditionsStatus(
        @Field("patient_id") patientId: Int,
        @Field("locale") locale: String
    ): Response<ResponseModel>

    @FormUrlEncoded
    @POST("api/user/accept_terms_conditions/")
    suspend fun termsAndConditionsAccept(
        @Field("patient_id") patientId: Int
    ): Response<ResponseModel>

    @FormUrlEncoded
    @POST("api/user/reject_terms_conditions/")
    suspend fun termsAndConditionsDecline(
        @Field("patient_id") patientId: Int
    ): Response<ResponseModel>

    @POST("api/test/create_raw_measurements/")
    suspend fun createRawMeasurement(
        @Body rawMeasurementModel: RawMeasurementModel
    ): Response<ResponseModel>

    @POST
    suspend fun verifyGroupTestAlgo(
        @Url url: String,
        @Body hashMap: HashMap<String, Any>
    ): Response<String>

    @GET("api/community/batch_test_status/{Id}")
    suspend fun batchTestStatus(
        @Path("Id") groupTestId: Int
    ): Response<ResponseModel>

    @Multipart
    @POST("/api/test/send_otp_to_patient/")
    suspend fun sendOtpToPatient(
        @Part("test_id") test_id: RequestBody
    ): Response<ResponseModel>

    @Multipart
    @POST("/api/test/verify_patient_otp/")
    suspend fun verifyPatientOtp(
        @Part("test_id") test_id: RequestBody,
        @Part("otp") otp: RequestBody,
    ): Response<ResponseModel>

    @POST("api/community/create_lab_test/")
    suspend fun createLabSession(
        @Body request: LabSessionRequest
    ): Response<ResponseModel>

    @FormUrlEncoded
    @POST("api/test/lab_test_report/")
    suspend fun labTestReport(
        @Field("test_id") testId: Int
    ): Response<ResponseModel>

    @POST("api/test/create_test_feedback/")
    suspend fun createTestFeedback(
        @Body body: TestFeedbackRequest
    ): Response<ResponseModel>

}