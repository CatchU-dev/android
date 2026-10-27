package com.isaacsufyan.catchu.presentation.fragments.patient_details

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
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
import com.isaacsufyan.catchu.utils.*
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

        binding.edtDOBLayout.setEndIconOnClickListener {
            it.hideSoftKeyboard()
            selectDate()
        }

        binding.edtDOB.addTextChangedListener(object : TextWatcher {
            private var isFormatting = false
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (isFormatting || s == null) return
                isFormatting = true
                val digits = s.filter { it.isDigit() }
                val sb = StringBuilder()
                for (i in digits.indices) {
                    if ((i == 2 || i == 4) && sb.length < 10) sb.append('-')
                    if (sb.length < 10) sb.append(digits[i])
                }
                s.replace(0, s.length, sb)
                isFormatting = false
            }
        })

        binding.btnContinue.setOnClickListener {
            it.hideSoftKeyboard()
            if (validation()) {
                val dobText = binding.edtDOB.text.toString()
                val sdf = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault())
                sdf.isLenient = false
                sdf.parse(dobText)?.let { parsed ->
                    val cal = Calendar.getInstance().apply { time = parsed }
                    rawMeasurements.selectedYear  = cal.get(Calendar.YEAR).toString()
                    rawMeasurements.selectedMonth = (cal.get(Calendar.MONTH) + 1).toString()
                    rawMeasurements.selectedDate  = cal.get(Calendar.DAY_OF_MONTH).toString()
                }
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
//        mainActivity.isInternetConnected.observe(viewLifecycleOwner) {
//            if (!it) {
//                mainActivity.getDialogRef().observe(viewLifecycleOwner) {diag ->
//                    diag?.dismiss()
//                }
//            }
//        }
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
                        if(mainActivity.isInternetConnected.value == true) {
                            showOkDialog("Code: ${it.code} Message: ${it.message}",
                                object : IDialogOnClickListener {
                                    override fun onOk(dialog: AlertDialog) {
                                        mainActivity.finish()
                                    }
                                })
                        }
                    } else {
                        mainActivity.showProgress(false)
                        if(mainActivity.isInternetConnected.value == true) {
                            showOkDialog("Code: ${it.code} Message: ${it.message}")
                        }
                    }
                }
                is DataState.Error -> {
                    mainActivity.showProgress(false)
                    if(mainActivity.isInternetConnected.value == true) {
                        showOkDialog(it.exception.message.toString())
                    }
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
                val dobApi = parseDateFormat(
                    binding.edtDOB.text.toString(),
                    "MM-dd-yyyy",
                    "yyyy-MM-dd"
                )
                viewModel.setStateEvent(
                    StateEvent.GetPatientPrefill(
                        firstName = binding.edtFirstName.textToString(),
                        lastName = binding.edtLastName.textToString(),
                        dob = dobApi
                    )
                )
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
        } else if (!isValidDob(binding.edtDOB.text.toString())) {
            binding.edtDOBLayout.error = "Invalid date or date is in the future (MM-DD-YYYY)"
            false
        } else {
            true
        }
    }

    private fun isValidDob(dob: String): Boolean {
        if (dob.length != 10) return false
        return try {
            val sdf = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault())
            sdf.isLenient = false
            val parsed = sdf.parse(dob) ?: return false
            !parsed.after(Date())
        } catch (e: Exception) { false }
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
        rawMeasurements.selectedYear = year.toString()
        rawMeasurements.selectedMonth = (month+1).toString()
        rawMeasurements.selectedDate = day.toString()
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