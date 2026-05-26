package com.isaacsufyan.catchu.domain.event

import com.isaacsufyan.catchu.domain.model.RawMeasurementModel
import okhttp3.RequestBody

sealed class StateEvent {

    class ValidateCommunity(var community_Id: String) : StateEvent()
    class GetActiveGroupTestDetails(var backend_community_Id: Int) : StateEvent()
    class GetCreateGroupTestData(var backend_community_Id: Int) : StateEvent()
    class GetBilleeList(var provider_id: Int) : StateEvent()

    class CreateGroupTest(
        var community_id: RequestBody,
        var group_name: RequestBody,
        var batch_code: RequestBody,
        var provider_id: RequestBody,
        var billee_id: RequestBody,
        var expiry_date: RequestBody,
        var no_of_tests: RequestBody,
        var timezone: RequestBody,
    ) : StateEvent()

    class GetGroupTestDetails(var groupId: Int) : StateEvent()
    class ResumeBatchTest(var groupTestId: Int) : StateEvent()
    class TerminateGroupTest(var groupTestId: Int) : StateEvent()

    class ValidatePatientToken(var testToken: String) : StateEvent()

    class ValidatePatient(
        var community_id: Int,
        var firstName: String,
        var lastName: String,
        var dob: String
    ) : StateEvent()

    class CreatePatientTestWithinGroup(
        var groupId: Int,
        var patientId: Int
    ) : StateEvent()

    class TermsAndConditionStatus(
        var patientId: Int,
        var locale: String
    ) : StateEvent()

    class TermsAndConditionAccept(var patientId: Int) : StateEvent()

    class TermsAndConditionDecline(var patientId: Int) : StateEvent()

    class CreateRawMeasurement(var rawMeasurementModel: RawMeasurementModel) : StateEvent()

    class VerifyGroupTestAlgo(var url: String, var args: HashMap<String, Any>) : StateEvent()

    class BatchTestStatus(var groupTestId: Int) : StateEvent()

    class SendOtpToPatient (var testId: RequestBody): StateEvent()

    class verifyPatientOtp (var testId: RequestBody, var otp: RequestBody): StateEvent()

    class GetPatientPrefill(
        val firstName: String,
        val lastName: String,
        val dob: String
    ) : StateEvent()

    class SubmitPostTestFeedback(
        val testId: Int,
        val feedback: String
    ) : StateEvent()

}
