package com.isaacsufyan.catchu.presentation.fragments.community_indentification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.isaacsufyan.catchu.R
import com.isaacsufyan.catchu.base.BaseFragment
import com.isaacsufyan.catchu.databinding.FragmentCommunityIdentificationBinding
import com.isaacsufyan.catchu.domain.event.StateEvent
import com.isaacsufyan.catchu.domain.state.DataState
import com.isaacsufyan.catchu.presentation.fragments.SharedViewModel
import com.isaacsufyan.catchu.utils.hideSoftKeyboard
import com.isaacsufyan.catchu.utils.isNullOrEmpty
import com.isaacsufyan.catchu.utils.textToString


class CommunityIdentificationFragment : BaseFragment() {

    private var _binding: FragmentCommunityIdentificationBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<SharedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommunityIdentificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.resetResponseModel()
        binding.btnSubmit.setOnClickListener {
            if (binding.edtCommunityId.isNullOrEmpty()) {
                return@setOnClickListener
            }
            viewModel.setStateEvent(StateEvent.ValidateCommunity(binding.edtCommunityId.textToString()))
            it.hideSoftKeyboard()
            binding.edtCommunityId.clearFocus()
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
                    if (it.data.data?.community_id != null) {
                        val text = String.format(
                            resources.getString(R.string.you_have_entered_msg),
                            it.data.data?.community.toString()
                        )
                        viewModel.communityId = it.data.data?.community_id
                        val action =
                            CommunityIdentificationFragmentDirections.actionCommunityIndToGroupDetails()
                        showDialogAndNavigateScreen(text, action)
                    } else {
                        showOkDialog("Try Another Community: ${it.data.message}")
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
                else -> {}
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}