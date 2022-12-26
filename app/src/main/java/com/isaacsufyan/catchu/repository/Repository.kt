package com.isaacsufyan.catchu.repository

import com.isaacsufyan.catchu.domain.model.RawMeasurementModel
import com.isaacsufyan.catchu.domain.model.ResponseModel
import com.isaacsufyan.catchu.domain.state.DataState
import com.isaacsufyan.catchu.framework.datastore.network.abstraction.ApiService
import kotlinx.coroutines.flow.flow
import okhttp3.RequestBody
import retrofit2.HttpException


class Repository(
    private val apiService: ApiService
) {
    private val TAG = "Repository"

    fun validateCommunity(community_id: String) = flow {
        emit(DataState.Loading)
        try {
            val response: ResponseModel = apiService.validateCommunity(community_id)
            if (response.status) {
                emit(DataState.Success(response))
            } else {
                emit(
                    DataState.Failure(
                        response.code ?: -1,
                        response.message ?: "No Message from Server"
                    )
                )
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun getActiveGroupTestList(community_id: Int) = flow {
        emit(DataState.Loading)
        try {
            val responseActiveGroup = apiService.activeGroupTestList(community_id)
            if (responseActiveGroup.status) {
                emit(DataState.Success(responseActiveGroup))
            } else {
                emit(
                    DataState.Failure(
                        responseActiveGroup.code ?: -1,
                        responseActiveGroup.message ?: "No Message from Server"
                    )
                )
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun getCreateGroupTestData(community_id: Int) = flow {
        emit(DataState.Loading)
        try {
            val response = apiService.getCreateGroupTestData(community_id)
            if (response.status) {
                emit(DataState.Success(response))
            } else {
                emit(
                    DataState.Failure(
                        response.code ?: -1,
                        response.message ?: "No Message from Server"
                    )
                )
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun getBilleList(provider_id: Int) = flow {
        emit(DataState.Loading)
        try {
            val responseActiveGroup = apiService.getBilleList(provider_id)
            if (responseActiveGroup.status) {
                emit(DataState.Success(responseActiveGroup))
            } else {
                emit(
                    DataState.Failure(
                        responseActiveGroup.code ?: -1,
                        responseActiveGroup.message ?: "No Message from Server"
                    )
                )
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun createGroupTest(
        community_id: RequestBody,
        group_name: RequestBody,
        batch_code: RequestBody,
        provider_id: RequestBody,
        billee_id: RequestBody,
        expiry_date: RequestBody,
        no_of_tests: RequestBody,
        timezone: RequestBody,
    ) = flow {

        emit(DataState.Loading)

        try {
            val response = apiService.createGroupTest(
                community_id = community_id,
                group_name = group_name,
                batch_code = batch_code,
                provider_id = provider_id,
                billee_id = billee_id,
                expiry_date = expiry_date,
                no_of_tests = no_of_tests,
                timezone = timezone,
            )
            if (response.status) {
                emit(DataState.Success(response))
            } else {
                emit(
                    DataState.Failure(
                        response.code ?: -1,
                        ("\n" + response.message)
                    )
                )
            }
        } catch (e: HttpException) {
            emit(DataState.Error(e))
        }
    }

    fun getGroupTestDetails(groupTestId: Int) = flow {
        emit(DataState.Loading)
        try {
            val response = apiService.getGroupTestDetails(groupTestId)
            if (response.status) {
                emit(DataState.Success(response))
            } else {
                emit(
                    DataState.Failure(
                        response.code ?: -1,
                        response.message ?: "No Message from Server"
                    )
                )
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun resumeBatchTest(groupTestId: Int) = flow {
        emit(DataState.Loading)
        try {
            val response = apiService.resumeBatchTest(groupTestId)
            if (response.status) {
                emit(DataState.Success(response))
            } else {
                emit(
                    DataState.Failure(
                        response.code ?: -1,
                        response.message ?: "No Message from Server"
                    )
                )
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun terminateGroupTest(groupTestId: Int) = flow {
        emit(DataState.Loading)
        try {
            val response = apiService.terminateGroupTest(groupTestId)
            if (response.status) {
                emit(DataState.Success(response))
            } else {
                emit(
                    DataState.Failure(
                        response.code ?: -1,
                        response.message ?: "No Message from Server"
                    )
                )
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun validatePatientToken(
        testToken: String,
    ) = flow {
        emit(DataState.Loading)
        try {
            val response = apiService.validatePatientToken(testToken)
            if (response.status) {
                emit(DataState.Success(response))
            } else {
                emit(
                    DataState.Failure(
                        response.code ?: -1,
                        response.message ?: "No Message from Server"
                    )
                )
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun validatePatient(
        community_id: Int,
        firstName: String,
        lastName: String,
        dob: String
    ) = flow {
        emit(DataState.Loading)
        try {
            val response = apiService.validatePatient(
                community_id = community_id,
                firstName = firstName,
                lastName = lastName,
                dob = dob
            )
            if (response.status) {
                emit(DataState.Success(response))
            } else {
                emit(
                    DataState.Failure(
                        response.code ?: -1,
                        response.message ?: "No Message from Server"
                    )
                )
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }


    fun createPatientTestWithinGroup(
        groupId: Int,
        patientId: Int
    ) = flow {
        emit(DataState.Loading)
        try {
            val response = apiService.checkPatientTestWithinGroup(groupId, patientId)
            if (response.status) {
                emit(DataState.Success(response))
            } else {
                emit(
                    DataState.Failure(
                        response.code ?: -1,
                        response.message ?: "No Message from Server"
                    )
                )
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun termsAndConditionsStatus(
        patientId: Int,
        locale: String
    ) = flow {
        emit(DataState.Loading)
        try {
            val response = apiService.termsAndConditionsStatus(
                patientId = patientId,
                locale = locale
            )
            if (response.status) {
                emit(DataState.Success(response))
            } else {
                emit(
                    DataState.Failure(
                        response.code ?: -1,
                        response.message ?: "No Message from Server"
                    )
                )
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun termsAndConditionsAccept(
        patientId: Int
    ) = flow {
        emit(DataState.Loading)
        try {
            val response = apiService.termsAndConditionsAccept(
                patientId = patientId
            )
            if (response.status) {
                emit(DataState.Success(response))
            } else {
                emit(
                    DataState.Failure(
                        response.code ?: -1,
                        response.message ?: "No Message from Server"
                    )
                )
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun termsAndConditionsDecline(
        patientId: Int
    ) = flow {
        emit(DataState.Loading)
        try {
            val response = apiService.termsAndConditionsDecline(
                patientId = patientId
            )
            if (response.status) {
                emit(DataState.Success(response))
            } else {
                emit(
                    DataState.Failure(
                        response.code ?: -1,
                        response.message ?: "No Message from Server"
                    )
                )
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }


    fun createRawMeasurement(
        rawMeasurementModel: RawMeasurementModel
    ) = flow {
        emit(DataState.Loading)
        try {
            val response = apiService.createRawMeasurement(rawMeasurementModel)
            if (response.status) {
                emit(DataState.Success(response))
            } else {
                emit(
                    DataState.Failure(
                        response.code ?: -1,
                        response.message ?: "No Message from Server"
                    )
                )
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun verifyGroupTestAlgo(
        url: String,
        args: HashMap<String, Any>
    ) = flow {
        emit(DataState.Loading)
        try {
            val response = apiService.verifyGroupTestAlgo(url, args)
            if (response.status) {
                emit(DataState.Success(response))
            } else {
                emit(
                    DataState.Failure(
                        response.code ?: -1,
                        response.message ?: "No Message from Server"
                    )
                )
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun batchTestStatus(
        groupTestId: Int
    ) = flow {
        emit(DataState.Loading)
        try {
            val response = apiService.batchTestStatus(groupTestId)
            if (response.status) {
                emit(DataState.Success(response))
            } else {
                emit(
                    DataState.Failure(
                        response.code ?: -1,
                        response.message ?: "No Message from Server"
                    )
                )
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}