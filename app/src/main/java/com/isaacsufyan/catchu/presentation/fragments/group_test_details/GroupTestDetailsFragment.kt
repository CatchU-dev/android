package com.isaacsufyan.catchu.presentation.fragments.group_test_details

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TimePicker
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.isaacsufyan.catchu.R
import com.isaacsufyan.catchu.adapter.MaterialArrayAdapter
import com.isaacsufyan.catchu.base.BaseFragment
import com.isaacsufyan.catchu.databinding.FragmentGroupTestDetailsBinding
import com.isaacsufyan.catchu.domain.event.StateEvent
import com.isaacsufyan.catchu.domain.model.ActiveGroupTest
import com.isaacsufyan.catchu.domain.model.BilleeModel
import com.isaacsufyan.catchu.domain.model.ProviderModel
import com.isaacsufyan.catchu.domain.state.DataState
import com.isaacsufyan.catchu.presentation.fragments.SharedViewModel
import com.isaacsufyan.catchu.utils.hideSoftKeyboard
import com.isaacsufyan.catchu.utils.isNullOrEmpty
import com.isaacsufyan.catchu.utils.parseDateFormat
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.text.SimpleDateFormat
import java.util.*


class GroupTestDetailsFragment : BaseFragment() {

    private var _binding: FragmentGroupTestDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<SharedViewModel>()
    private var stateEvent: StateEvent? = null

    private var batchCode: Int? = null
    private var providerId: Int? = null
    private var billeeId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGroupTestDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.resetResponseModel()

        viewModel.communityId?.let {
            viewModel.setStateEvent(StateEvent.GetActiveGroupTestDetails(backend_community_Id = it))

            stateEvent = StateEvent.GetCreateGroupTestData(backend_community_Id = it)
            viewModel.setStateEvent(stateEvent as StateEvent.GetCreateGroupTestData)
        } ?: run {
            showOkDialog(resources.getString(R.string.something_get_wrong_contact_support_engineer))
        }

        subscriber()

        binding.edtExpiryDate.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                date,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).apply {
                datePicker.minDate = System.currentTimeMillis() - 1000
                show()
            }
        }

        binding.btnNext.setOnClickListener {
            it.hideSoftKeyboard()
            if (viewModel.groupId != null) {
                navigateToNextScreen()
            }
        }

        binding.btnSubmit.setOnClickListener {
            it.hideSoftKeyboard()
            if (validationBeforeCreateGroupTest()) {
                val groupName = binding.edtGroupName.text.toString()
                val noOfTest = binding.edtNoOfTest.text.toString()
                val expireDate = parseDateFormat(binding.edtExpiryDate.text.toString(), "MM-dd-yyyy hh:mm:ss","yyyy-MM-dd hh:mm:ss")
                val timeZoneId = TimeZone.getDefault().id

                val communityIdParam = viewModel.communityId.toString()
                    .toRequestBody("multipart/form-data".toMediaTypeOrNull())
                val groupNameParam =
                    groupName.toRequestBody("multipart/form-data".toMediaTypeOrNull())
                val batchCodeParam =
                    batchCode.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
                val providerIdParam =
                    providerId.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
                val billeeIdParam =
                    billeeId.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
                val noOfTestParam =
                    noOfTest.toRequestBody("multipart/form-data".toMediaTypeOrNull())
                val timeZoneIdParam =
                    timeZoneId.toRequestBody("multipart/form-data".toMediaTypeOrNull())
                val expireDateParam =
                    expireDate.toRequestBody("multipart/form-data".toMediaTypeOrNull())

                stateEvent = StateEvent.CreateGroupTest(
                    community_id = communityIdParam,
                    group_name = groupNameParam,
                    batch_code = batchCodeParam,
                    provider_id = providerIdParam,
                    billee_id = billeeIdParam,
                    expiry_date = expireDateParam,
                    no_of_tests = noOfTestParam,
                    timezone = timeZoneIdParam
                )
                viewModel.setStateEvent(stateEvent as StateEvent.CreateGroupTest)
            }
        }
    }

    private val date = DatePickerDialog.OnDateSetListener { _, year, month, day ->
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        timePicker()
    }

    private fun validationBeforeCreateGroupTest(): Boolean {
        binding.edtGroupNameLayout.error = null
        binding.edtExpiryDateLayout.error = null

        if (binding.edtGroupName.isNullOrEmpty()) {
            binding.edtGroupNameLayout.error = "Required"
            return false
        } else if (batchCode == null) {
            showOkDialog("Batch Code Is Empty - Contact with Support Engineer")
            return false
        } else if (viewModel.communityId == null) {
            showOkDialog("Community Id is not exist - Contact with Support Engineer")
            return false
        } else if (providerId == null) {
            showOkDialog("Select Provider or Contact with Support Engineer")
            return false
        } else if (billeeId == null) {
            showOkDialog("Select Billee Id or Contact with Support Engineer")
            return false
        } else if (binding.edtExpiryDate.isNullOrEmpty()) {
            binding.edtExpiryDateLayout.error = "Required"
            return false
        }

        return true
    }

    private fun subscriber() {
        viewModel.responseModelGetActiveGroupList.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> {
                    mainActivity.showProgress(true)
                }
                is DataState.Success -> {
                    mainActivity.showProgress(false)
                    populateActiveGroupDropdown(it.data.data?.activeGroupTests)
                }
                is DataState.Failure -> {
                    mainActivity.showProgress(false)
                    showOkDialog("Code: ${it.code} Message: ${it.message}")
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

        viewModel.responseModel.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> {
                    mainActivity.showProgress(true)
                }
                is DataState.Success -> {
                    mainActivity.showProgress(false)

                    if (stateEvent != null && stateEvent is StateEvent.GetCreateGroupTestData) {
                        batchCode = it.data.data?.batchCode
                        populateGroupCode()
                        val providerListOfGroupTest = it.data.data?.providersList
                        providerListOfGroupTest?.let { itt ->
                            populateProviderDropdown(itt)
                        }
                    } else if (stateEvent != null && stateEvent is StateEvent.GetBilleeList) {
                        it.data.data?.billeesList?.let { billeeList ->
                            populateBilleDropDown(billeeList)
                        }
                    } else if (stateEvent != null && stateEvent is StateEvent.CreateGroupTest) {
                        if (it.data.status) {
                            viewModel.groupId = it.data.data?.groupTestId
                            navigateToNextScreen()
                        }
                    }

                }
                is DataState.Failure -> {
                    mainActivity.showProgress(false)
                    showOkDialog("Code: ${it.code} Message: ${it.message}")
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

    private fun navigateToNextScreen() {
        val action =
            GroupTestDetailsFragmentDirections.actionGroupDetailsToCurrentGroup()
        findNavController().navigate(action)
    }

    private fun populateActiveGroupDropdown(activeGroupTestList: List<ActiveGroupTest>?) {
        if (!activeGroupTestList.isNullOrEmpty()) {
            viewModel.groupId = activeGroupTestList[0].groupId
            val materialAdapter = MaterialArrayAdapter(
                requireContext(),
                R.layout.simple_list_item,
                activeGroupTestList,
                binding.edtGroupTestDetails.simpleItemSelectedColor,
                binding.edtGroupTestDetails.simpleItemSelectedRippleColor
            )

            binding.edtGroupTestDetails.setAdapter(materialAdapter)
            var selectedText = binding.edtGroupTestDetails.adapter.getItem(0).toString()
            binding.edtGroupTestDetails.setText(selectedText)
            materialAdapter.updateColor(selectedText)

            binding.edtGroupTestDetails.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, position, _ ->
                    selectedText = binding.edtGroupTestDetails.adapter.getItem(position).toString()
                    viewModel.groupId = activeGroupTestList[position].groupId
                    materialAdapter.updateColor(selectedText)
                }
        } else {
            viewModel.groupId = null
            binding.edtGroupTestDetailsLayout.isEnabled = false
            binding.edtGroupTestDetails.hint = "List Empty - Create New Group Test"
        }
    }

    private fun populateGroupCode() {
        batchCode?.let {
            binding.edtGroupCode.setText(it.toString())
            binding.edtGroupCodeLayout.isEnabled = false
            val color = ResourcesCompat.getColor(resources, R.color.blue_light, null)
            binding.edtGroupCodeLayout.boxBackgroundColor = color
        }
    }

    private fun populateProviderDropdown(providerList: List<ProviderModel>) {
        val providerDropDownAdapter = MaterialArrayAdapter(
            requireContext(),
            R.layout.simple_list_item,
            providerList,
            binding.edtProvider.simpleItemSelectedColor,
            binding.edtProvider.simpleItemSelectedRippleColor
        )
        binding.edtProvider.setAdapter(providerDropDownAdapter)


        var selectedText = binding.edtProvider.adapter.getItem(0).toString()
        providerId = providerList[0].id ?: providerList[0].providerId

        binding.edtProvider.setText(selectedText)
        providerDropDownAdapter.updateColor(selectedText)

        binding.edtProvider.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                selectedText = binding.edtProvider.adapter.getItem(position).toString()
                providerId = providerList[position].id ?: providerList[position].providerId

                providerDropDownAdapter.updateColor(selectedText)
                getBilleeOfProvider(providerId)
            }

        getBilleeOfProvider(providerId)
    }

    private fun getBilleeOfProvider(providerId: Int?) {
        providerId?.let {
            stateEvent = StateEvent.GetBilleeList(provider_id = it)
            viewModel.setStateEvent(stateEvent as StateEvent.GetBilleeList)
        }
    }

    private fun populateBilleDropDown(billeeList: List<BilleeModel>) {
        val materialAdapter = MaterialArrayAdapter(
            requireContext(),
            R.layout.simple_list_item,
            billeeList,
            binding.edtBillee.simpleItemSelectedColor,
            binding.edtBillee.simpleItemSelectedRippleColor
        )

        binding.edtBillee.setAdapter(materialAdapter)
        var selectedText = binding.edtBillee.adapter.getItem(0).toString()
        billeeId = billeeList[0].Id ?: billeeList[0].billeeId

        binding.edtBillee.setText(selectedText)
        materialAdapter.updateColor(selectedText)

        binding.edtBillee.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                selectedText = binding.edtBillee.adapter.getItem(position).toString()
                billeeId = billeeList[position].Id ?: billeeList[position].billeeId
                materialAdapter.updateColor(selectedText)
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun timePicker() {
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(requireContext(), object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                setExpiryTimeHandler()
            }
        }, hour, minute, false)
        timePickerDialog.show();
    }
    
    private fun setExpiryTimeHandler() { 
        val dateFormat = SimpleDateFormat("MM-dd-yyyy hh:mm:ss", Locale.getDefault())
        val expireDate = dateFormat.format(calendar.time)
        binding.edtExpiryDate.setText(expireDate)
    }

}