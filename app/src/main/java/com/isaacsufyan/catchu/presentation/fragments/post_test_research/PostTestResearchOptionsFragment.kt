package com.isaacsufyan.catchu.presentation.fragments.post_test_research

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.isaacsufyan.catchu.R
import com.isaacsufyan.catchu.base.BaseFragment
import com.isaacsufyan.catchu.databinding.FragmentPostTestResearchOptionsBinding
import com.isaacsufyan.catchu.domain.event.StateEvent
import com.isaacsufyan.catchu.domain.state.DataState
import com.isaacsufyan.catchu.presentation.fragments.SharedViewModel

class PostTestResearchOptionsFragment : BaseFragment() {

    private var _binding: FragmentPostTestResearchOptionsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<SharedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostTestResearchOptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBackToGroupTest.visibility =
            if (viewModel.isSingleTest) View.GONE else View.VISIBLE

        binding.btnShareFeedback.setOnClickListener {
            showFeedbackDialog()
        }

        binding.btnBackToGroupTest.setOnClickListener {
            findNavController().navigate(
                PostTestResearchOptionsFragmentDirections.actionPostTestResearchToCurrentGroup()
            )
        }

        binding.btnExit.setOnClickListener {
            exitAfterTest()
        }

        viewModel.postTestFeedbackState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is DataState.Loading -> mainActivity.showProgress(true)
                is DataState.Success -> {
                    mainActivity.showProgress(false)
                    showOkDialog(
                        state.data.message
                            ?: getString(R.string.post_test_feedback_sent)
                    )
                    viewModel.resetPostTestFeedbackState()
                }
                is DataState.Failure -> {
                    mainActivity.showProgress(false)
                    showOkDialog(state.message)
                    viewModel.resetPostTestFeedbackState()
                }
                is DataState.Error -> {
                    mainActivity.showProgress(false)
                    showOkDialog(
                        state.exception.message
                            ?: getString(R.string.post_test_feedback_error)
                    )
                    viewModel.resetPostTestFeedbackState()
                }
                is DataState.Idle -> mainActivity.showProgress(false)
            }
        }
    }

    private fun showFeedbackDialog() {
        val padding = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            24f,
            resources.displayMetrics
        ).toInt()
        val editText = EditText(requireContext()).apply {
            hint = getString(R.string.post_test_feedback_hint)
            minLines = 4
            gravity = android.view.Gravity.TOP or android.view.Gravity.START
        }
        val container = FrameLayout(requireContext()).apply {
            setPadding(padding, padding / 2, padding, 0)
            addView(
                editText,
                FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                )
            )
        }

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.post_test_share_feedback)
            .setView(container)
            .setNegativeButton(R.string.decline) { d, _ -> d.dismiss() }
            .setPositiveButton(R.string.submit, null)
            .create()

        dialog.setOnShowListener {
            dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val text = editText.text?.toString()?.trim().orEmpty()
                if (text.isEmpty()) {
                    showOkDialog(getString(R.string.post_test_feedback_empty))
                    return@setOnClickListener
                }
                val testId = viewModel.testId
                if (testId == null) {
                    showOkDialog(getString(R.string.something_get_wrong_contact_support_engineer))
                    return@setOnClickListener
                }
                viewModel.resetPostTestFeedbackState()
                viewModel.setStateEvent(StateEvent.SubmitPostTestFeedback(testId, text))
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    private fun exitAfterTest() {
        viewModel.clearSessionForLanguageRestart()
        mainActivity.testInProgress.value = false
        val navController = findNavController()
        val opts = NavOptions.Builder()
            .setPopUpTo(navController.graph.id, true)
            .build()
        navController.navigate(R.id.languageFragment, null, opts)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
