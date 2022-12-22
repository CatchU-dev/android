package com.isaacsufyan.catchu.presentation.fragments.terms_and_condition

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.*
import android.webkit.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.isaacsufyan.catchu.R
import com.isaacsufyan.catchu.base.BaseFragment
import com.isaacsufyan.catchu.databinding.FragmentTermsAndConditionBinding
import com.isaacsufyan.catchu.domain.event.StateEvent
import com.isaacsufyan.catchu.domain.state.DataState
import com.isaacsufyan.catchu.framework.interfaces.IDialogOnClickListener
import com.isaacsufyan.catchu.presentation.fragments.SharedViewModel
import com.isaacsufyan.catchu.utils.showDialog


class TermsAndConditionFragment : BaseFragment() {

    private var _binding: FragmentTermsAndConditionBinding? = null
    private val binding get() = _binding!!
    private var isTerminateDialogDisplay = false
    private val viewModel by activityViewModels<SharedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTermsAndConditionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.resetResponseModel()
        viewModel.termsAndConditionUrl?.let {
            loadTermAndCondition(it)
        } ?: run {
            showOkDialog(resources.getString(R.string.something_get_wrong_contact_support_engineer))
        }

        binding.btnDecline.setOnClickListener {
            if (isTerminateDialogDisplay) {
                viewModel.patientId?.let {
                    viewModel.setStateEvent(
                        StateEvent.TermsAndConditionDecline(
                            patientId = it
                        )
                    )
                } ?: run {
                    showOkDialog(resources.getString(R.string.something_get_wrong_contact_support_engineer))
                }
            } else showTerminateDialog()
        }

        binding.btnAccept.setOnClickListener {
            viewModel.patientId?.let {
                viewModel.setStateEvent(
                    StateEvent.TermsAndConditionAccept(
                        patientId = it
                    )
                )
            } ?: run {
                showOkDialog(resources.getString(R.string.something_get_wrong_contact_support_engineer))
            }
        }

        subscriber()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadTermAndCondition(url: String) {
        Log.d("TAG", "loadTermAndCondition: $url")
        val webSettings = binding.webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true
        webSettings.builtInZoomControls = true

        binding.webView.loadUrl(url)
        binding.webView.webChromeClient = WebChromeClient()
        binding.webView.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                mainActivity.showProgress(true)
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                mainActivity.showProgress(false)
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                mainActivity.showProgress(false)
            }
        }

        binding.webView.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == MotionEvent.ACTION_UP && binding.webView.canGoBack()) {
                binding.webView.goBack()
                return@setOnKeyListener true
            }
            false
        }
    }

    private fun showTerminateDialog() {
        mainActivity.disableBackgroundTouchesHandler()
        mainActivity.showDialog(
            true,
            resources.getString(R.string.terminate_test_msg),
            R.mipmap.ic_launcher,
            null,
            resources.getString(R.string.ok),
            object : IDialogOnClickListener {
                override fun onOk(dialog: AlertDialog) {
                    mainActivity.freeBackgroundTouchesHandler()
                    dialog.dismiss()
                    isTerminateDialogDisplay = true
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscriber() {
        viewModel.responseModel.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> {
                    mainActivity.showProgress(true)
                }
                is DataState.Success -> {
                    mainActivity.showProgress(false)
                    if (it.data.status) {
                        if (it.data.data?.acceptedTermAndCondition == true) navigateToNextScreen()
                        else navigateToPreviousScreen()
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
        val action = TermsAndConditionFragmentDirections.actionTermAndConditionToInstruction()
        findNavController().navigate(action)
    }

    private fun navigateToPreviousScreen() {
        val action =
            TermsAndConditionFragmentDirections.actionTermAndConditionToPatientDetail()
        findNavController().navigate(action)
    }

}