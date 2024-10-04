package com.isaacsufyan.catchu.presentation.activities.main

import android.annotation.TargetApi
import android.app.Dialog
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.gson.Gson
import com.isaacsufyan.catchu.R
import com.isaacsufyan.catchu.base.BaseActivity
import com.isaacsufyan.catchu.databinding.ActivityMainBinding
import com.isaacsufyan.catchu.domain.enum.ELanguage
import com.isaacsufyan.catchu.domain.event.StateEvent
import com.isaacsufyan.catchu.framework.interfaces.IDialogOnClickListener
import com.isaacsufyan.catchu.presentation.fragments.SharedViewModel
import com.isaacsufyan.catchu.utils.DialogNoInternet
import com.isaacsufyan.catchu.utils.NetworkConnection
import com.isaacsufyan.catchu.utils.getDialogRef
import java.util.*

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<SharedViewModel>()
    var selectedLanguage: ELanguage = ELanguage.ENGLISH
    lateinit var dialog: Dialog
    val isInternetConnected: MutableLiveData<Boolean> = MutableLiveData()
    //    var testInProgress: Boolean = false
    var testInProgress: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        value = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel.getLanguage.observe(this) {
            selectedLanguage = if (it == ELanguage.SPANISH.language) {
                setLocale(ELanguage.SPANISH.language)
                ELanguage.SPANISH
            } else {
                setLocale(ELanguage.ENGLISH.language)
                ELanguage.ENGLISH
            }
        }
        setContentView(binding.root)
        dialog = this.DialogNoInternet(
            message = resources.getString(R.string.no_internet_msg),
            R.drawable.terminate_icon,
            heading = resources.getString(R.string.no_internet_title)
        )
        getDynamicLink()
        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this) { isConnected ->
            if (isConnected) {
                isInternetConnected.value = true
                testInProgress.observe(this) {
                    if (!it) {
                        dialog.dismiss()
                    }
                }
            } else {
                isInternetConnected.value = false
                testInProgress.observe(this) {
                    if (!it) {
                        this.getDialogRef().observe(this) { diag->
                            this.freeBackgroundTouchesHandler()
                            diag?.dismiss()
                        }
                        dialog.show()
                    }
                }
                this.getDialogRef().observe(this) { diag->
                    this.freeBackgroundTouchesHandler()
                    diag?.dismiss()
                }
            }
        }
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
                }
            }.addOnFailureListener {
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

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_DEL) {
            viewModel.setEvent(keyCode)
            true
        } else super.onKeyUp(keyCode, event)
    }
}