package com.isaacsufyan.catchu.presentation.fragments

import androidx.lifecycle.*
import com.isaacsufyan.catchu.domain.event.StateEvent
import com.isaacsufyan.catchu.domain.model.PatientPrefillResponse
import com.isaacsufyan.catchu.domain.model.RawMeasurements
import com.isaacsufyan.catchu.domain.model.ResponseModel
import com.isaacsufyan.catchu.domain.state.DataState
import com.isaacsufyan.catchu.framework.datastore.preference.abstraction.PrefService
import com.isaacsufyan.catchu.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: Repository,
    private val prefService: PrefService,
    private val rawMeasurements: RawMeasurements,
) : ViewModel() {

    var token: String? = null
    var groupId: Int? = null
    var patientId: Int? = null

    var testId: Int? = null
    var communityId: Int? = null
    var isSingleTest = false

    /** When false, the research-purpose dropdown is hidden (GONE) and post-test research UI is skipped. */
    var isResearchPurposeDropdownVisible: Boolean = true

    var termsAndConditionUrl: String? = null

    var prefillData: RawMeasurements? = null

    val getLanguage: LiveData<String> = prefService.getLanguage().asLiveData()
    val searchPurposeList: LiveData<List<String>> = MutableLiveData(listOf("No", "Yes"))
    val languageList: LiveData<List<String>> = MutableLiveData(listOf("English", "Spanish"))
    private val _keyEvent:MutableLiveData<Int> = MutableLiveData(-1)
    val keyEvent:LiveData<Int> get() = _keyEvent

    fun setEvent(code:Int){
        _keyEvent.value = code
    }

    fun setLanguage(language: String) {
        viewModelScope.launch {
            prefService.setLanguage(language)
        }
    }

    private val _responseModel = MutableLiveData<DataState<ResponseModel>>()
    val responseModel: LiveData<DataState<ResponseModel>>
        get() = _responseModel

    private val _responseModelGetActiveGroupList = MutableLiveData<DataState<ResponseModel>>()
    val responseModelGetActiveGroupList: LiveData<DataState<ResponseModel>>
        get() = _responseModelGetActiveGroupList

    private val _prefillResponseModel = MutableLiveData<DataState<PatientPrefillResponse>>()
    val prefillResponseModel: LiveData<DataState<PatientPrefillResponse>>
        get() = _prefillResponseModel

    private val _postTestFeedbackState = MutableLiveData<DataState<ResponseModel>>()
    val postTestFeedbackState: LiveData<DataState<ResponseModel>>
        get() = _postTestFeedbackState

    fun resetPostTestFeedbackState() {
        _postTestFeedbackState.value = DataState.Idle
    }

    /**
     * Clears session-scoped IDs and form state when the user exits the post-test flow back to
     * [com.isaacsufyan.catchu.presentation.fragments.language.LanguageFragment].
     */
    fun clearSessionForLanguageRestart() {
        token = null
        groupId = null
        patientId = null
        testId = null
        communityId = null
        isSingleTest = false
        termsAndConditionUrl = null
        prefillData = null
        isResearchPurposeDropdownVisible = true
        rawMeasurements.resetForNewSessionAfterExit()
        resetResponseModel()
        resetPostTestFeedbackState()
        _prefillResponseModel.value = DataState.Idle
    }

    fun setStateEvent(stateEvent: StateEvent) {
        when (stateEvent) {
            is StateEvent.ValidateCommunity -> {
                repository.validateCommunity(community_id = stateEvent.community_Id)
                    .onEach {
                        _responseModel.value = it
                    }
                    .catch { DataState.Error(it as Exception) }
                    .launchIn(viewModelScope)
            }

            is StateEvent.GetActiveGroupTestDetails -> {
                repository.getActiveGroupTestList(community_id = stateEvent.backend_community_Id)
                    .onEach { _responseModelGetActiveGroupList.value = it }
                    .catch { DataState.Error(it as Exception) }
                    .launchIn(viewModelScope)
            }

            is StateEvent.GetCreateGroupTestData -> {
                repository.getCreateGroupTestData(community_id = stateEvent.backend_community_Id)
                    .onEach { _responseModel.value = it }
                    .catch { DataState.Error(it as Exception) }
                    .launchIn(viewModelScope)
            }

            is StateEvent.GetBilleeList -> {
                repository.getBilleList(provider_id = stateEvent.provider_id)
                    .onEach { _responseModel.value = it }
                    .catch { DataState.Error(it as Exception) }
                    .launchIn(viewModelScope)
            }

            is StateEvent.CreateGroupTest -> {
                repository.createGroupTest(
                    community_id = stateEvent.community_id,
                    group_name = stateEvent.group_name,
                    batch_code = stateEvent.batch_code,
                    provider_id = stateEvent.provider_id,
                    billee_id = stateEvent.billee_id,
                    expiry_date = stateEvent.expiry_date,
                    no_of_tests = stateEvent.no_of_tests,
                    timezone = stateEvent.timezone
                )
                    .onEach { _responseModel.value = it }
                    .catch { DataState.Error(it as Exception) }
                    .launchIn(viewModelScope)
            }

            is StateEvent.GetGroupTestDetails -> {
                repository.getGroupTestDetails(groupTestId = stateEvent.groupId)
                    .onEach { _responseModel.value = it }
                    .catch { DataState.Error(it as Exception) }
                    .launchIn(viewModelScope)
            }

            is StateEvent.ResumeBatchTest -> {
                repository.resumeBatchTest(groupTestId = stateEvent.groupTestId)
                    .onEach { _responseModel.value = it }
                    .catch { DataState.Error(it as Exception) }
                    .launchIn(viewModelScope)
            }

            is StateEvent.TerminateGroupTest -> {
                repository.terminateGroupTest(groupTestId = stateEvent.groupTestId)
                    .onEach { _responseModel.value = it }
                    .catch { DataState.Error(it as Exception) }
                    .launchIn(viewModelScope)
            }

            is StateEvent.ValidatePatientToken -> {
                repository.validatePatientToken(testToken = stateEvent.testToken)
                    .onEach { _responseModel.value = it }
                    .catch { DataState.Error(it as Exception) }
                    .launchIn(viewModelScope)
            }

            is StateEvent.ValidatePatient -> {
                repository.validatePatient(
                    community_id = stateEvent.community_id,
                    firstName = stateEvent.firstName,
                    lastName = stateEvent.lastName,
                    dob = stateEvent.dob,
                )
                    .onEach { _responseModel.value = it }
                    .catch { DataState.Error(it as Exception) }
                    .launchIn(viewModelScope)
            }

            is StateEvent.CreatePatientTestWithinGroup -> {
                repository.createPatientTestWithinGroup(
                    groupId = stateEvent.groupId,
                    patientId = stateEvent.patientId
                )
                    .onEach { _responseModel.value = it }
                    .catch { DataState.Error(it as Exception) }
                    .launchIn(viewModelScope)
            }

            is StateEvent.TermsAndConditionStatus -> {
                repository.termsAndConditionsStatus(
                    patientId = stateEvent.patientId,
                    locale = stateEvent.locale
                )
                    .onEach { _responseModel.value = it }
                    .catch { DataState.Error(it as Exception) }
                    .launchIn(viewModelScope)
            }

            is StateEvent.TermsAndConditionAccept -> {
                repository.termsAndConditionsAccept(
                    patientId = stateEvent.patientId
                )
                    .onEach { _responseModel.value = it }
                    .catch { DataState.Error(it as Exception) }
                    .launchIn(viewModelScope)
            }

            is StateEvent.TermsAndConditionDecline -> {
                repository.termsAndConditionsDecline(
                    patientId = stateEvent.patientId
                )
                    .onEach { _responseModel.value = it }
                    .catch { DataState.Error(it as Exception) }
                    .launchIn(viewModelScope)
            }

            is StateEvent.CreateRawMeasurement -> {
                repository.createRawMeasurement(rawMeasurementModel = stateEvent.rawMeasurementModel)
                    .onEach { _responseModel.value = it }
                    .catch { DataState.Error(it as Exception) }
                    .launchIn(viewModelScope)
            }

            is StateEvent.VerifyGroupTestAlgo -> {
                repository.verifyGroupTestAlgo(stateEvent.url, stateEvent.args)
                    .onEach { _responseModel.value = it }
                    .catch { DataState.Error(it as Exception) }
                    .launchIn(viewModelScope)
            }

            is StateEvent.BatchTestStatus -> {
                repository.batchTestStatus(stateEvent.groupTestId)
                    .onEach { _responseModel.value = it }
                    .catch { DataState.Error(it as Exception) }
                    .launchIn(viewModelScope)
            }
            is StateEvent.SendOtpToPatient -> {
                repository.sendOtpToPatient(testId = stateEvent.testId)
                    .onEach { _responseModel.value = it }
                    .catch { DataState.Error(it as Exception) }
                    .launchIn(viewModelScope)
            }
            is StateEvent.verifyPatientOtp -> {
                repository.verifyPatientOtp(testId = stateEvent.testId, otp = stateEvent.otp)
                    .onEach { _responseModel.value = it }
                    .catch { DataState.Error(it as Exception) }
                    .launchIn(viewModelScope)
            }

            is StateEvent.GetPatientPrefill -> {
                repository.getPatientPrefill(
                    firstName = stateEvent.firstName,
                    lastName = stateEvent.lastName,
                    dob = stateEvent.dob
                )
                    .onEach { state ->
                        // Store prefillData directly in the ViewModel scope so it survives
                        // fragment view destruction during navigation (T&C → Instructions → Demographics)
                        if (state is DataState.Success) {
                            prefillData = state.data.data
                        }
                        _prefillResponseModel.value = state
                    }
                    .catch { DataState.Error(it as Exception) }
                    .launchIn(viewModelScope)
            }

            is StateEvent.SubmitPostTestFeedback -> {
                repository.submitPostTestFeedback(
                    testId = stateEvent.testId,
                    feedback = stateEvent.feedback
                )
                    .onEach { _postTestFeedbackState.value = it }
                    .catch { _postTestFeedbackState.value = DataState.Error(it as Exception) }
                    .launchIn(viewModelScope)
            }
        }
    }

    fun resetResponseModel() {
        _responseModel.value = DataState.Idle
        _responseModelGetActiveGroupList.value = DataState.Idle
    }
}