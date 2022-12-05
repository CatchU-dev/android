package com.isaacsufyan.catchu.framework.datastore.network.abstraction

import com.isaacsufyan.catchu.domain.model.ResponseModel
import okhttp3.RequestBody


interface ApiService {

    suspend fun validateCommunity(community_id: String): ResponseModel

    suspend fun activeGroupTestList(communityId: Int): ResponseModel

    suspend fun getCreateGroupTestData(communityId: Int): ResponseModel

    suspend fun getGroupTestDetails(groupTestId: Int): ResponseModel

    suspend fun getBilleList(provider_id: Int): ResponseModel

    suspend fun createGroupTest(
        community_id: RequestBody,
        group_name: RequestBody,
        batch_code: RequestBody,
        provider_id: RequestBody,
        billee_id: RequestBody,
        expiry_date: RequestBody,
        no_of_tests: RequestBody,
        timezone: RequestBody,
    ): ResponseModel

    suspend fun resumeBatchTest(groupTestId: Int): ResponseModel
    suspend fun terminateGroupTest(groupTestId: Int): ResponseModel

    suspend fun validatePatient(
        community_id: Int,
        firstName: String,
        lastName: String,
        dob: String
    ): ResponseModel

    suspend fun validatePatientToken(
        testToken: String
    ): ResponseModel

    suspend fun checkPatientTestWithinGroup(
        groupId: Int,
        patientId: Int
    ): ResponseModel

    suspend fun termsAndConditionsStatus(
        patientId: Int,
        locale: String
    ): ResponseModel

    suspend fun termsAndConditionsAccept(
        patientId: Int
    ): ResponseModel

    suspend fun termsAndConditionsDecline(
        patientId: Int
    ): ResponseModel
}