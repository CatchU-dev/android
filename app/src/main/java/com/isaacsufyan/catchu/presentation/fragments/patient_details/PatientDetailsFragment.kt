package com.isaacsufyan.catchu.presentation.fragments.patient_details

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.isaacsufyan.catchu.R
import com.isaacsufyan.catchu.base.BaseFragment
import com.isaacsufyan.catchu.databinding.FragmentPatientDetailsBinding
import com.isaacsufyan.catchu.domain.event.StateEvent
import com.isaacsufyan.catchu.domain.model.ResponseModel
import com.isaacsufyan.catchu.domain.state.DataState
import com.isaacsufyan.catchu.framework.interfaces.IDialogOnClickListener
import com.isaacsufyan.catchu.presentation.fragments.SharedViewModel
import com.isaacsufyan.catchu.utils.hideSoftKeyboard
import com.isaacsufyan.catchu.utils.isNullOrEmpty
import com.isaacsufyan.catchu.utils.parseDateFormat
import com.isaacsufyan.catchu.utils.textToString
import java.text.SimpleDateFormat
import java.util.*

class PatientDetailsFragment : BaseFragment() {

    private var _binding: FragmentPatientDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<SharedViewModel>()
    private var stateEvent: StateEvent? = null
    private var firstName: String? = null
    private var lastName: String? = null
    private var dob: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPatientDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.resetResponseModel()
        viewModel.token?.let {
            stateEvent = StateEvent.ValidatePatientToken(testToken = it)
            viewModel.setStateEvent(stateEvent as StateEvent.ValidatePatientToken)
        }

        binding.edtDOB.setOnClickListener {
            it.hideSoftKeyboard()
            selectDate()
        }

        binding.btnContinue.setOnClickListener {
            it.hideSoftKeyboard()
            if (validation()) {
                val firstName = binding.edtFirstName.textToString()
                val lastName = binding.edtLastName.textToString()
                val dob = binding.edtDOB.text.toString()

                if (stateEvent != null && stateEvent is StateEvent.ValidatePatientToken) {

                    val newDob = parseDateFormat(dob, "MM-dd-yyyy", "dd-MM-yyyy")


                    if (this.firstName?.trim().equals(firstName.trim(), ignoreCase = true) &&
                        this.lastName?.trim().equals(lastName.trim(), ignoreCase = true) &&
                        this.dob == newDob
                    ) {
                        viewModel.isSingleTest = true
                        viewModel.patientId?.let { it1 ->
                            checkTermsAndConditionAgreeAPI(
                                patientID = it1
                            )
                        }
                    } else {
                        showOkDialog("Patient Details InValid")
                    }
                } else {
                    val newDob = parseDateFormat(dob, "MM-dd-yyyy", "yyyy-MM-dd")
                    callValidatePersonApi(firstName, lastName, newDob)
                }
            }
        }
        binding.edtFirstName.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        binding.edtLastName.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        binding.edtDOB.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        subscriber()

    }

    private fun subscriber() {
        viewModel.responseModel.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> {
                    mainActivity.showProgress(true)
                }
                is DataState.Success -> {
                    when (stateEvent) {
                        is StateEvent.ValidatePatientToken -> {
                            mainActivity.showProgress(false)
                            performActionAfterPatientValidateToken(it.data)
                        }
                        is StateEvent.ValidatePatient -> {
                            performActionAfterPatientValidate(it.data)
                        }
                        is StateEvent.CreatePatientTestWithinGroup -> {
                            performActionAfterCreatePatientWithinGroup(it.data)
                        }
                        is StateEvent.TermsAndConditionStatus -> {
                            mainActivity.showProgress(false)
                            performActionAfterTermsAndConditionStatus(it.data)
                        }
                        else -> {}
                    }

                }
                is DataState.Failure -> {
                    if (stateEvent is StateEvent.ValidatePatientToken) {
                        mainActivity.showProgress(false)
                        showOkDialog("Code: ${it.code} Message: ${it.message}",
                            object : IDialogOnClickListener {
                                override fun onOk(dialog: AlertDialog) {
                                    mainActivity.finish()
                                }
                            })
                    } else {
                        mainActivity.showProgress(false)
                        showOkDialog("Code: ${it.code} Message: ${it.message}")
                    }
                }
                is DataState.Error -> {
                    mainActivity.showProgress(false)
                    showOkDialog(it.exception.message.toString())
                }

                is DataState.Idle -> {
                    mainActivity.showProgress(false)
                }
            }
        }
    }

    private fun performActionAfterPatientValidateToken(data: ResponseModel) {
        if (data.status) {
            firstName = data.data?.patientDetails?.firstName
            lastName = data.data?.patientDetails?.lastName
            dob = data.data?.patientDetails?.dob
            viewModel.patientId = data.data?.patientDetails?.patient_Id
            viewModel.testId = data.data?.id
        }
    }

    private fun performActionAfterPatientValidate(data: ResponseModel) {
        if (data.status) {
            val patientId = data.data?.patientDetails?.patientId
            patientId?.let { id ->
                viewModel.patientId = id
                createPatientTestWithinGroup(id)
            } ?: run {
                showOkDialog("Invalidate Patient")
            }
        }
    }

    private fun performActionAfterCreatePatientWithinGroup(data: ResponseModel) {
        if (data.status) {
            val testId = data.data?.id
            testId?.let { id ->
                viewModel.testId = id
                viewModel.patientId?.let { it1 ->
                    checkTermsAndConditionAgreeAPI(
                        patientID = it1
                    )
                }
            }
        }
    }

    private fun performActionAfterTermsAndConditionStatus(data: ResponseModel) {
        if (data.status) {
            if (data.data?.acceptedTermAndCondition == true) {
                navigateToInstructionScreenScreen()
            } else {
                navigateToTermsAndConditionScreen(data.data?.termAndConditionURL)
            }
        }
    }

    private fun callValidatePersonApi(firstName: String, lastName: String, dob: String) {
        viewModel.communityId?.let {
            stateEvent = StateEvent.ValidatePatient(
                community_id = it,
                firstName = firstName,
                lastName = lastName,
                dob = dob
            )
            viewModel.setStateEvent(stateEvent as StateEvent.ValidatePatient)
        } ?: run {
            showOkDialog(resources.getString(R.string.something_get_wrong_contact_support_engineer))
        }
    }

    private fun createPatientTestWithinGroup(patientID: Int) {
        viewModel.groupId?.let {
            stateEvent = StateEvent.CreatePatientTestWithinGroup(
                groupId = it,
                patientId = patientID
            )
            viewModel.setStateEvent(stateEvent as StateEvent.CreatePatientTestWithinGroup)
        } ?: run {
            showOkDialog(resources.getString(R.string.something_get_wrong_contact_support_engineer))
        }

    }

    private fun checkTermsAndConditionAgreeAPI(patientID: Int) {
        stateEvent = StateEvent.TermsAndConditionStatus(
            patientId = patientID,
            locale = mainActivity.selectedLanguage.language
        )
        viewModel.setStateEvent(stateEvent as StateEvent.TermsAndConditionStatus)
    }

    private fun validation(): Boolean {
        binding.edtFirstNameLayout.error = null
        binding.edtLastNameLayout.error = null
        binding.edtDOBLayout.error = null
        return if (binding.edtFirstName.isNullOrEmpty()) {
            binding.edtFirstNameLayout.error = "Required"
            false
        } else if (binding.edtLastName.isNullOrEmpty()) {
            binding.edtLastNameLayout.error = "Required"
            false
        } else if (binding.edtDOB.isNullOrEmpty()) {
            binding.edtDOBLayout.error = "Required"
            false
        } else {
            true
        }
    }

    private fun selectDate() {

        DatePickerDialog(
            requireContext(),
            date,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            datePicker.maxDate = System.currentTimeMillis()
            show()
        }
    }

    private val date = DatePickerDialog.OnDateSetListener { _, year, month, day ->
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        val dateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault())
        val dob = dateFormat.format(calendar.time)
        binding.edtDOB.setText(dob)
        rawMeasurements.selectedYear = year
        rawMeasurements.selectedMonth = month+1
        rawMeasurements.selectedDate = day
    }

    private fun navigateToTermsAndConditionScreen(termAndConditionURL: String?) {
        termAndConditionURL?.let {
            viewModel.termsAndConditionUrl = it
            val action = PatientDetailsFragmentDirections.actionPatientDetailToTermAndCondition()
            findNavController().navigate(action)
        } ?: run {
            navigateToInstructionScreenScreen()
        }
    }

    private fun navigateToInstructionScreenScreen() {
        val action =
            PatientDetailsFragmentDirections.actionBypassTermAndConditionAndMoveToInstruction()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}