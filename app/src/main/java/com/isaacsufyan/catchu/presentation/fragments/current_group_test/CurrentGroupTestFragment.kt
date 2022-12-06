package com.isaacsufyan.catchu.presentation.fragments.current_group_test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.isaacsufyan.catchu.R
import com.isaacsufyan.catchu.adapter.CurrentGroupTestAdapter
import com.isaacsufyan.catchu.base.BaseFragment
import com.isaacsufyan.catchu.databinding.FragmentCurrentGroupTestBinding
import com.isaacsufyan.catchu.domain.event.StateEvent
import com.isaacsufyan.catchu.domain.model.TestHistoryModel
import com.isaacsufyan.catchu.domain.state.DataState
import com.isaacsufyan.catchu.framework.interfaces.IDialogOnClickListener
import com.isaacsufyan.catchu.presentation.fragments.SharedViewModel
import com.isaacsufyan.catchu.utils.showDialog

class CurrentGroupTestFragment : BaseFragment() {

    private var _binding: FragmentCurrentGroupTestBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CurrentGroupTestAdapter
    private val viewModel by activityViewModels<SharedViewModel>()
    private lateinit var stateEvent: StateEvent

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrentGroupTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.resetResponseModel()

        viewModel.groupId?.let { id ->
            stateEvent = StateEvent.GetGroupTestDetails(groupId = id)
            viewModel.setStateEvent(stateEvent)
        } ?: run {
            showOkDialog(resources.getString(R.string.something_get_wrong_contact_support_engineer))
        }


        binding.btnResumeGroupTest.setOnClickListener {
            viewModel.groupId?.let {
                stateEvent = StateEvent.ResumeBatchTest(it)
                viewModel.setStateEvent(stateEvent)
            } ?: run {
                showOkDialog("This group is not exist - Try Again Later or Contact with Support Engineer")
            }
        }

        binding.btnTerminateGroupTest.setOnClickListener {
            mainActivity.showDialog(
                isOkDialog = false,
                message = resources.getString(R.string.terminate_test_msg),
                R.drawable.terminate_icon,
                null,
                null,
                object : IDialogOnClickListener {
                    override fun onOk(dialog: AlertDialog) {
                        dialog.dismiss()
                        viewModel.groupId?.let {
                            stateEvent = StateEvent.TerminateGroupTest(it)
                            viewModel.setStateEvent(stateEvent)
                        }
                    }

                    override fun onCancel(dialog: AlertDialog) {
                        dialog.dismiss()
                    }
                })
        }

        subscriber()
    }

    private fun subscriber() {
        viewModel.responseModel.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> {
                    mainActivity.showProgress(true)
                }
                is DataState.Success -> {
                    mainActivity.showProgress(false)

                    if (stateEvent is StateEvent.GetGroupTestDetails) {
                        populateTable(it.data.data?.testHistory)
                    } else if (stateEvent is StateEvent.ResumeBatchTest) {
                        if (it.data.data?.resumeBatchTest == true) {
                            val action =
                                CurrentGroupTestFragmentDirections.actionCurrentGroupToPatientProfile()
                            findNavController().navigate(action)
                        }
                    } else if (stateEvent is StateEvent.TerminateGroupTest) {
                        if (it.data.status) {
                            val action =
                                CurrentGroupTestFragmentDirections.actionCurrentGroupToCommunityInd()
                            findNavController().navigate(action)
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

    private fun populateTable(testHistory: List<TestHistoryModel>?) {
        if (!testHistory.isNullOrEmpty()) {
            binding.rvCurrentGroupTestTable.visibility = View.VISIBLE
            binding.tvNoPatientRecordFound.visibility = View.GONE
            adapter = CurrentGroupTestAdapter()
            adapter.setList(testHistory)
            binding.rvCurrentGroupTestTable.adapter = adapter
        } else {
            binding.rvCurrentGroupTestTable.visibility = View.GONE
            binding.tvNoPatientRecordFound.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}