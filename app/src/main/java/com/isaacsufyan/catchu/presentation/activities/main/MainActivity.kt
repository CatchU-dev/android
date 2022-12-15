package com.isaacsufyan.catchu.presentation.activities.main

import android.annotation.TargetApi
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.isaacsufyan.catchu.base.BaseActivity
import com.isaacsufyan.catchu.databinding.ActivityMainBinding
import com.isaacsufyan.catchu.domain.enum.ELanguage
import com.isaacsufyan.catchu.presentation.fragments.SharedViewModel
import java.util.*

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<SharedViewModel>()
    var selectedLanguage: ELanguage = ELanguage.ENGLISH

    override fun onCreate(savedInstanceState: Bundle?) {
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

    fun restartApp() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    fun setLocale(language: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(language)
        }
        updateResourcesLegacy(language)
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration: Configuration = resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        createConfigurationContext(configuration)
        baseContext.resources.updateConfiguration(
            configuration,
            baseContext.resources.displayMetrics
        )
    }

    private fun updateResourcesLegacy(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources: Resources = resources
        val configuration: Configuration = resources.configuration
        configuration.locale = locale
        configuration.setLayoutDirection(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
        baseContext.resources.updateConfiguration(
            configuration,
            baseContext.resources.displayMetrics
        )
    }
}