package com.isaacsufyan.catchu.presentation.fragments.otp_verification

import android.content.Context
import android.content.res.Resources
import android.opengl.Visibility
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.isaacsufyan.catchu.R
import com.isaacsufyan.catchu.base.BaseFragment
import com.isaacsufyan.catchu.databinding.FragmentOtpVerificationBinding
import com.isaacsufyan.catchu.domain.event.StateEvent
import com.isaacsufyan.catchu.domain.model.ResponseModel
import com.isaacsufyan.catchu.domain.state.DataState
import com.isaacsufyan.catchu.framework.interfaces.IDialogOnClickListener
import com.isaacsufyan.catchu.presentation.fragments.SharedViewModel
import com.isaacsufyan.catchu.presentation.fragments.patient_details.PatientDetailsFragmentDirections
import com.isaacsufyan.catchu.utils.Constants
import com.isaacsufyan.catchu.utils.hideSoftKeyboard
import com.isaacsufyan.catchu.utils.showSnackBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.log


class OtpVerificationFragment : BaseFragment(), TextWatcher {

    private var _binding: FragmentOtpVerificationBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<SharedViewModel>()
    private var selectedETPosition: Int = 1
    private var resendTime: Long = 120
    private var resendEnabled: Boolean = false
    private var stateEvent: StateEvent? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOtpVerificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val savedTime: Long? = savedInstanceState?.getLong(Constants.RESEND_TIME) ?: null
        if (savedTime != null) {
            resendTime = savedTime
        }
        init()
        viewModel.resetResponseModel()
        viewModel.token?.let {
            stateEvent = StateEvent.ValidatePatientToken(testToken = it)
            viewModel.setStateEvent(stateEvent as StateEvent.ValidatePatientToken)
        }
        subscriber()
    }

    private fun init() {
        observeKeyEventChanges()
        binding.edtOtpId1.addTextChangedListener(this)
        binding.edtOtpId1.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                selectedETPosition = 1
            }
        }

        binding.edtOtpId2.addTextChangedListener(this)
        binding.edtOtpId2.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                selectedETPosition = 2
            }
        }
        binding.edtOtpId3.addTextChangedListener(this)
        binding.edtOtpId3.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                selectedETPosition = 3
            }
        }
        binding.edtOtpId4.addTextChangedListener(this)
        binding.edtOtpId4.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                selectedETPosition = 4
            }
        }
        binding.edtOtpId5.addTextChangedListener(this)
        binding.edtOtpId5.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                selectedETPosition = 5
            }
        }
        binding.edtOtpId6.addTextChangedListener(this)
        binding.edtOtpId6.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                selectedETPosition = 6
            }
        }
        binding.resendCode.setOnClickListener() {
            if (resendEnabled) {
                resendOtp()
            }
        }
        binding.btnVerify.setOnClickListener() {
            it.hideSoftKeyboard()
            if (OtpValidation()) {
                validateOTP()
            } else {
                mainActivity.showSnackBar("Some OTP fields missing")
            }
        }
    }

    private fun showKeyboard(otpEdt: EditText) {
        otpEdt.requestFocus()
        val inputMethodManager: InputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(otpEdt, InputMethodManager.SHOW_IMPLICIT)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        if (s != null) {
            if (s.isNotEmpty()) {
                Log.d("AFTER CHANGE", selectedETPosition.toString())
                when (selectedETPosition) {
                    1 -> {
                        selectedETPosition = 2
                        showKeyboard(binding.edtOtpId2)
                    }
                    2 -> {
                        selectedETPosition = 3
                        showKeyboard(binding.edtOtpId3)
                    }
                    3 -> {
                        selectedETPosition = 4
                        showKeyboard(binding.edtOtpId4)
                    }
                    4 -> {
                        selectedETPosition = 5
                        showKeyboard(binding.edtOtpId5)
                    }
                    5 -> {
                        selectedETPosition = 6
                        showKeyboard(binding.edtOtpId6)
                    }
                }
            }
        }
    }

    private fun observeKeyEventChanges() {
        viewModel.keyEvent.observe(viewLifecycleOwner) {
            when (selectedETPosition) {
                6 -> {
                    selectedETPosition = 5
                    showKeyboard(binding.edtOtpId5)
                }
                5 -> {
                    selectedETPosition = 4
                    showKeyboard(binding.edtOtpId4)
                }
                4 -> {
                    selectedETPosition = 3
                    showKeyboard(binding.edtOtpId3)
                }
                3 -> {
                    selectedETPosition = 2
                    showKeyboard(binding.edtOtpId2)
                }
                2 -> {
                    selectedETPosition = 1
                    showKeyboard(binding.edtOtpId1)
                }
            }
        }
    }

    private fun startCountDownTimer() {
        Log.d("NETWORK_REQ", "startCountDownTimer: ")
        resendEnabled = false
        binding.resendCode.visibility = View.GONE
        binding.time.visibility = View.VISIBLE
        val timer = object : CountDownTimer(resendTime * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
//                binding.time.setText(
//                    (context?.resources?.getString(R.string.resend_code_in)
//                        ?: "") + "(" + String.format(
//                        "%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
//                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
//                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
//                        )
//                    ) + ")"
//                )
                binding.time.setText(
                    (context?.resources?.getString(R.string.resend_code_in)
                        ?: "") + String.format(
                        Locale.getDefault(), " %02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60)
                )
                resendTime = millisUntilFinished / 1000
            }

            override fun onFinish() {
                resendEnabled = true
                binding.resendCode.visibility = View.VISIBLE
                binding.time.visibility = View.GONE
            }
        }
        timer.start()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(Constants.RESEND_TIME, resendTime)
    }

    private fun OtpValidation(): Boolean {
        if (binding.edtOtpId1.text.isNullOrEmpty() || binding.edtOtpId2.text.isNullOrEmpty() ||
            binding.edtOtpId3.text.isNullOrEmpty() || binding.edtOtpId4.text.isNullOrEmpty() ||
            binding.edtOtpId5.text.isNullOrEmpty() || binding.edtOtpId6.text.isNullOrEmpty()
        ) {
            return false
        } else {
            return true
        }
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
                            Log.d("NETWORK_REQ", Gson().toJson(it.data))
                            mainActivity.showProgress(false)
                            performActionAfterPatientValidateToken(it.data)
                        }
                        is StateEvent.SendOtpToPatient -> {
                            Log.d("NETWORK_REQ", Gson().toJson(it.data))
                            mainActivity.showProgress(false)
                            afterSendOTP(it.data)
                        }
                        is StateEvent.verifyPatientOtp -> {
                            Log.d("NETWORK_REQ", Gson().toJson(it.data))
                            mainActivity.showProgress(false)
                            afterOTPValidation(it.data)
                        }
                        is StateEvent.TermsAndConditionStatus -> {
                            Log.d("NETWORK_REQ", Gson().toJson(it.data))
                            mainActivity.showProgress(false)
                            performActionAfterTermsAndConditionStatus(it.data)
                        }
                        else -> {}
                    }

                }
                is DataState.Failure -> {
                    if (stateEvent is StateEvent.ValidatePatientToken) {
                        Log.d("NETWORK_REQ", Gson().toJson(it))
                        mainActivity.showProgress(false)
                        showOkDialog("Code: ${it.code} Message: ${it.message}",
                            object : IDialogOnClickListener {
                                override fun onOk(dialog: AlertDialog) {
                                    mainActivity.finish()
                                }
                            })
                    } else {
                        Log.d("NETWORK_REQ", Gson().toJson(it))
                        mainActivity.showProgress(false)
                        showOkDialog("Code: ${it.code} Message: ${it.message}")
                    }
                }
                is DataState.Error -> {
                    Log.d("NETWORK_REQ", Gson().toJson(it))
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
            viewModel.patientId = data.data?.patientDetails?.patient_Id
            viewModel.testId = data.data?.patientDetails?.test_id
            Log.d("NETWORK_REQ", Gson().toJson(data.data?.patientDetails?.patient_Id))
            Log.d("NETWORK_REQ", Gson().toJson(data.data?.patientDetails?.test_id))
            val dob = data.data?.patientDetails?.dob?.split("-")
            Log.d("NETWORK_REQ", Gson().toJson(dob))
            CoroutineScope(Dispatchers.IO).launch {
                rawMeasurements.selectedYear = Integer.parseInt (((dob?.get(2) ?: 0) as String)).toString()
                rawMeasurements.selectedMonth = Integer.parseInt(((dob?.get(0) ?: 0) as String)).toString()
                rawMeasurements.selectedDate = Integer.parseInt (((dob?.get(1) ?: 0) as String)).toString()
            }
            startCountDownTimer()
        }
    }

    private fun resendOtp() {
        val testId = viewModel.testId.toString()
            .toRequestBody("multipart/form-data".toMediaTypeOrNull())
        stateEvent = StateEvent.SendOtpToPatient(testId = testId)
        viewModel.resetResponseModel()
        viewModel.setStateEvent(stateEvent as StateEvent.SendOtpToPatient)
    }

    private fun validateOTP() {
        val otp = binding.edtOtpId1.text.toString().plus(binding.edtOtpId2.text.toString())
            .plus(binding.edtOtpId3.text.toString()).plus(binding.edtOtpId4.text.toString())
            .plus(binding.edtOtpId5.text.toString()).plus(binding.edtOtpId6.text.toString())
            .toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val testId = viewModel.testId.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
        stateEvent = StateEvent.verifyPatientOtp(testId, otp)
        viewModel.resetResponseModel()
        viewModel.setStateEvent(stateEvent as StateEvent.verifyPatientOtp)
    }

    private fun afterSendOTP(data: ResponseModel) {
        if (data.status) {
            resendTime = 120
            startCountDownTimer()
        }
    }

    private fun afterOTPValidation(data: ResponseModel) {
        if (data.status) {
            viewModel.isSingleTest = true
            viewModel.patientId?.let { it1 ->
                checkTermsAndConditionAgreeAPI(
                    patientID = it1
                )
            }
        }
    }

    private fun checkTermsAndConditionAgreeAPI(patientID: Int) {
        stateEvent = StateEvent.TermsAndConditionStatus(
            patientId = patientID,
            locale = mainActivity.selectedLanguage.language
        )
        viewModel.resetResponseModel()
        viewModel.setStateEvent(stateEvent as StateEvent.TermsAndConditionStatus)
    }

    private fun performActionAfterTermsAndConditionStatus(data: ResponseModel) {
        Log.d("STATUS", Gson().toJson(data.status))
        if (data.status) {
            if (data.data?.acceptedTermAndCondition == true) {
                navigateToInstructionScreenScreen()
            } else {
                navigateToTermsAndConditionScreen(data.data?.termAndConditionURL)
            }
        }
    }

    private fun navigateToInstructionScreenScreen() {
        val action =
            OtpVerificationFragmentDirections.actionOtpVerificationFragmentToInstructionBeforeTesting()
        findNavController().navigate(action)
    }

    private fun navigateToTermsAndConditionScreen(termAndConditionURL: String?) {
        termAndConditionURL?.let {
            viewModel.termsAndConditionUrl = it
            val action = OtpVerificationFragmentDirections.actionOtpVerificationFragmentToTermsAndConditionFragment()
            findNavController().navigate(action)
        } ?: run {
            navigateToInstructionScreenScreen()
        }
    }
}