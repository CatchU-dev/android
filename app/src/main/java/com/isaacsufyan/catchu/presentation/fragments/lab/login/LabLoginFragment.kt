package com.isaacsufyan.catchu.presentation.fragments.lab.login

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.isaacsufyan.catchu.R
import com.isaacsufyan.catchu.base.BaseFragment
import com.isaacsufyan.catchu.databinding.FragmentLabLoginBinding
import com.isaacsufyan.catchu.domain.event.StateEvent
import com.isaacsufyan.catchu.domain.state.DataState
import com.isaacsufyan.catchu.presentation.fragments.SharedViewModel
import com.isaacsufyan.catchu.utils.LabSession
import com.isaacsufyan.catchu.utils.hideSoftKeyboard
import com.isaacsufyan.catchu.utils.isNullOrEmpty
import com.isaacsufyan.catchu.utils.textToString
import java.util.Calendar

/**
 * Lab-only entry: collects study site, study ID, wave, age, gender.
 * POSTs these to api/lab/create_lab_test/ to get a testId, then navigates to TestFragment.
 */
class LabLoginFragment : BaseFragment() {

    private var _binding: FragmentLabLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<SharedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLabLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val genderOptions = resources.getStringArray(R.array.lab_gender_options)
        binding.actvGender.setAdapter(
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, genderOptions)
        )

        val dobClickListener = View.OnClickListener { showDatePicker() }
        binding.edtDateOfBirth.setOnClickListener(dobClickListener)
        binding.tilDateOfBirth.setEndIconOnClickListener(dobClickListener)

        binding.btnContinue.setOnClickListener {
            it.hideSoftKeyboard()
            if (!validateForm()) return@setOnClickListener
            submitLabSession()
        }

        observeResponse()
    }

    private fun showDatePicker() {
        val today = Calendar.getInstance()
        val picker = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                binding.edtDateOfBirth.setText(
                    "%04d-%02d-%02d".format(year, month + 1, dayOfMonth)
                )
                binding.tilDateOfBirth.error = null
            },
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        )
        picker.datePicker.maxDate = today.timeInMillis
        picker.show()
    }

    private fun validateForm(): Boolean {
        binding.tilStudySite.error   = null
        binding.tilStudyId.error     = null
        binding.tilWave.error        = null
        binding.tilFirstName.error   = null
        binding.tilLastName.error    = null
        binding.tilDateOfBirth.error = null
        binding.tilGender.error      = null
        return when {
            binding.edtStudySite.isNullOrEmpty() -> {
                binding.tilStudySite.error = getString(R.string.required); false
            }
            binding.edtStudyId.isNullOrEmpty() -> {
                binding.tilStudyId.error = getString(R.string.required); false
            }
            binding.edtWave.isNullOrEmpty() -> {
                binding.tilWave.error = getString(R.string.required); false
            }
            binding.edtFirstName.isNullOrEmpty() -> {
                binding.tilFirstName.error = getString(R.string.required); false
            }
            binding.edtLastName.isNullOrEmpty() -> {
                binding.tilLastName.error = getString(R.string.required); false
            }
            binding.edtDateOfBirth.isNullOrEmpty() -> {
                binding.tilDateOfBirth.error = getString(R.string.required); false
            }
            binding.actvGender.text.isNullOrBlank() -> {
                binding.tilGender.error = getString(R.string.required); false
            }
            else -> true
        }
    }

    private fun submitLabSession() {
        viewModel.resetResponseModel()
        viewModel.setStateEvent(
            StateEvent.CreateLabSession(
                studySite   = binding.edtStudySite.textToString(),
                studyId     = binding.edtStudyId.textToString(),
                wave        = binding.edtWave.textToString(),
                firstName   = binding.edtFirstName.textToString(),
                lastName    = binding.edtLastName.textToString(),
                dateOfBirth = binding.edtDateOfBirth.textToString(),
                gender      = binding.actvGender.text.toString()
            )
        )
    }

    private fun observeResponse() {
        viewModel.responseModel.observe(viewLifecycleOwner) { state ->
            when (state) {
                is DataState.Loading -> mainActivity.showProgress(true)
                is DataState.Success -> {
                    mainActivity.showProgress(false)
                    val testId = state.data.data?.testId
                    if (state.data.status && testId != null) {
                        viewModel.testId = testId
                        rawMeasurements.studySite      = binding.edtStudySite.textToString()
                        rawMeasurements.studyId        = binding.edtStudyId.textToString()
                        rawMeasurements.labWave        = binding.edtWave.textToString()
                        rawMeasurements.labFirstName   = binding.edtFirstName.textToString()
                        rawMeasurements.labLastName    = binding.edtLastName.textToString()
                        rawMeasurements.labDateOfBirth = binding.edtDateOfBirth.textToString()
                        rawMeasurements.selectedGender = binding.actvGender.text.toString()
                        navigateToTest()
                    } else {
                        showOkDialog(state.data.message
                            ?: getString(R.string.something_get_wrong_contact_support_engineer))
                    }
                }
                is DataState.Failure -> {
                    mainActivity.showProgress(false)
                    showOkDialog("Code: ${state.code} Message: ${state.message}")
                }
                is DataState.Error -> {
                    mainActivity.showProgress(false)
                    showOkDialog(state.exception.message
                        ?: getString(R.string.something_get_wrong_contact_support_engineer))
                }
                is DataState.Idle -> mainActivity.showProgress(false)
            }
        }
    }

    private fun navigateToTest() {
        LabSession.prepareRawMeasurementsForTest(rawMeasurements, null)
        mainActivity.testInProgress.value = true
        val options = NavOptions.Builder()
            .setPopUpTo(R.id.sensorSetupFragment, true)
            .build()
        findNavController().navigate(R.id.testFragment, null, options)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
