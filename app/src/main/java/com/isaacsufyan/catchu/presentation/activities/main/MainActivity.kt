package com.isaacsufyan.catchu.presentation.activities.main

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.isaacsufyan.catchu.base.BaseActivity
import com.isaacsufyan.catchu.databinding.ActivityMainBinding
import com.isaacsufyan.catchu.domain.enum.ELanguage
import com.isaacsufyan.catchu.presentation.fragments.SharedViewModel


class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<SharedViewModel>()
    var selectedLanguage: ELanguage = ELanguage.ENGLISH

    override fun onCreate(savedInstanceState: Bundle?) {
        hideSystemBars()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel.getLanguage.observe(this) {
            selectedLanguage = if (it == ELanguage.SPANISH.language) {
                ELanguage.SPANISH
            } else {
                ELanguage.ENGLISH
            }
        }
        setContentView(binding.root)
        getDynamicLink()
    }

    private fun getDynamicLink() {
        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData ->
                var deepLink: Uri? = null

                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.link
                }

                deepLink?.let { uri ->
                    viewModel.token = uri.toString().substring(uri.toString().lastIndexOf("=") + 1)
                    Log.d("TAG", "getDynamicLink - Uri: $uri")
                    Log.d("TAG", "getDynamicLink - Token: ${viewModel.token}")
                }
            }.addOnFailureListener {
                Log.d("TAG", "handleIncomingDeepLinks: ${it.message}")
            }
    }
}