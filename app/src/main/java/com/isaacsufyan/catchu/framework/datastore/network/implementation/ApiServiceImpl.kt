package com.isaacsufyan.catchu.framework.datastore.network.implementation

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.isaacsufyan.catchu.domain.model.RawMeasurementModel
import com.isaacsufyan.catchu.domain.model.ResponseModel
import com.isaacsufyan.catchu.framework.datastore.network.abstraction.ApiService
import com.isaacsufyan.catchu.framework.datastore.network.api.RestApis
import okhttp3.RequestBody
import javax.inject.Inject


class ApiServiceImpl @Inject constructor(private val restApis: RestApis) : ApiService {

    override suspend fun validateCommunity(community_id: String): ResponseModel {
        return restApis.validateCommunity(community_id = community_id)
    }

    override suspend fun activeGroupTestList(communityId: Int): ResponseModel {
        return restApis.activeGroupTestList(communityId)
    }

    override suspend fun getCreateGroupTestData(communityId: Int): ResponseModel {
        return restApis.getCreateGroupTestData(communityId)
    }


    override suspend fun getBilleList(provider_id: Int): ResponseModel {
        return restApis.getBilleeListGroupTest(providerId = provider_id)
    }

    override suspend fun createGroupTest(
        community_id: RequestBody,
        group_name: RequestBody,
        batch_code: RequestBody,
        provider_id: RequestBody,
        billee_id: RequestBody,
        expiry_date: RequestBody,
        no_of_tests: RequestBody,
        timezone: RequestBody
    ): ResponseModel {
        val response = restApis.createGroupTest(
            community_id = community_id,
            group_name = group_name,
            batch_code = batch_code,
            provider_id = provider_id,
            billee_id = billee_id,
            expiry_date = expiry_date,
            no_of_tests = no_of_tests,
            timezone = timezone,
        )
        return if (response.isSuccessful) {
            response.body()!!
        } else {
            val type = object : TypeToken<ResponseModel>() {}.type
            Gson().fromJson(response.errorBody()!!.charStream(), type)
        }
    }

    override suspend fun getGroupTestDetails(groupTestId: Int): ResponseModel {
        return restApis.getGroupTestDetails(groupTestId)
    }

    override suspend fun resumeBatchTest(groupTestId: Int): ResponseModel {
        return restApis.resumeBatchTest(groupTestId)
    }

    override suspend fun terminateGroupTest(groupTestId: Int): ResponseModel {
        return restApis.terminateGroupTest(groupTestId)
    }

    override suspend fun validatePatient(
        community_id: Int,
        firstName: String,
        lastName: String,
        dob: String
    ): ResponseModel {
        val response = restApis.validatePatient(
            community_id = community_id,
            firstName = firstName,
            lastName = lastName,
            dob = dob
        )
        return if (response.isSuccessful) {
            response.body()!!
        } else {
            val type = object : TypeToken<ResponseModel>() {}.type
            Gson().fromJson(response.errorBody()!!.charStream(), type)
        }
    }

    override suspend fun validatePatientToken(
        testToken: String,
    ): ResponseModel {
        val response = restApis.validatePatientToken(testToken)
        return if (response.isSuccessful) {
            response.body()!!
        } else {
            val type = object : TypeToken<ResponseModel>() {}.type
            Gson().fromJson(response.errorBody()!!.charStream(), type)
        }
    }


    override suspend fun checkPatientTestWithinGroup(groupId: Int, patientId: Int): ResponseModel {
        val response = restApis.createPatientTestWithInGroup(groupId, patientId)
        return if (response.isSuccessful) {
            response.body()!!
        } else {
            val type = object : TypeToken<ResponseModel>() {}.type
            Gson().fromJson(response.errorBody()!!.charStream(), type)
        }
    }

    override suspend fun termsAndConditionsStatus(
        patientId: Int,
        locale: String,
    ): ResponseModel {
        val response = restApis.termsAndConditionsStatus(
            patientId = patientId,
            locale = locale
        )
        return if (response.isSuccessful) {
            response.body()!!
        } else {
            val type = object : TypeToken<ResponseModel>() {}.type
            Gson().fromJson(response.errorBody()!!.charStream(), type)
        }
    }

    override suspend fun termsAndConditionsAccept(patientId: Int): ResponseModel {
        val response = restApis.termsAndConditionsAccept(
            patientId = patientId
        )
        return if (response.isSuccessful) {
            response.body()!!
        } else {
            val type = object : TypeToken<ResponseModel>() {}.type
            Gson().fromJson(response.errorBody()!!.charStream(), type)
        }
    }

    override suspend fun termsAndConditionsDecline(patientId: Int): ResponseModel {
        val response = restApis.termsAndConditionsDecline(
            patientId = patientId
        )
        return if (response.isSuccessful) {
            response.body()!!
        } else {
            val type = object : TypeToken<ResponseModel>() {}.type
            Gson().fromJson(response.errorBody()!!.charStream(), type)
        }
    }

    override suspend fun createRawMeasurement(rawMeasurementModel: RawMeasurementModel): ResponseModel {
        val response = restApis.createRawMeasurement(rawMeasurementModel)
        return if (response.isSuccessful) {
            response.body()!!
        } else {
            val type = object : TypeToken<ResponseModel>() {}.type
            Gson().fromJson(response.errorBody()!!.charStream(), type)
        }
    }

    override suspend fun verifyGroupTestAlgo(
        url: String,
        args: HashMap<String, Any>
    ): ResponseModel {
        val response = restApis.verifyGroupTestAlgo(url, args)
        return if (response.isSuccessful) {
            ResponseModel(code = response.code(), message = response.body(), status = true )
        } else {
            val type = object : TypeToken<ResponseModel>() {}.type
            Gson().fromJson(response.errorBody()!!.charStream(), type)
        }
    }

    override suspend fun batchTestStatus(groupTestId: Int): ResponseModel {
        val response = restApis.batchTestStatus(groupTestId)
        return if (response.isSuccessful) {
            response.body()!!
        } else {
            val type = object : TypeToken<ResponseModel>() {}.type
            Gson().fromJson(response.errorBody()!!.charStream(), type)
        }
    }

    override suspend fun sendOtpToPatient(testId: RequestBody): ResponseModel {
        val response = restApis.sendOtpToPatient(testId)
        return if (response.isSuccessful) {
            response.body()!!
        } else {
            val type = object : TypeToken<ResponseModel>() {}.type
            Gson().fromJson(response.errorBody()!!.charStream(), type)
        }
    }

    override suspend fun verifyPatientOtp(testId: RequestBody, otp: RequestBody): ResponseModel {
        val response = restApis.verifyPatientOtp(testId, otp)
        return if (response.isSuccessful) {
            response.body()!!
        } else {
            val type = object : TypeToken<ResponseModel>() {}.type
            Gson().fromJson(response.errorBody()!!.charStream(), type)
        }
    }

}