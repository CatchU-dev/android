package com.isaacsufyan.catchu.base

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.isaacsufyan.catchu.R
import com.isaacsufyan.catchu.utils.NetworkConnection
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {

    private var progressDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemBars()
        WebView(this).destroy()

        if (progressDialog == null) {
            progressDialog = Dialog(this)
            progressDialog?.window?.setBackgroundDrawable(ColorDrawable(0))
            progressDialog?.window?.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            )
            progressDialog?.setContentView(R.layout.progress_dialog)
        }
//        val networkConnection= NetworkConnection(applicationContext)
//        networkConnection.observe(this) { isConnected ->
//            if (isConnected) {
//                Toast.makeText(this, "Internet Connected", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(this, "Internet Disconnected", Toast.LENGTH_SHORT).show()
//            }
//        }
    }

    fun showProgress(show: Boolean) {
        when {
            show -> {
                if (!isFinishing && !progressDialog!!.isShowing) {
                    disableBackgroundTouchesHandler()
                    progressDialog?.setCanceledOnTouchOutside(false)
                    progressDialog?.show()
                }
            }
            else -> {
                if (!isFinishing && progressDialog!!.isShowing) {
                    freeBackgroundTouchesHandler()
                    progressDialog?.dismiss()
                }
            }
        }
    }

    private fun hideSystemBars() {
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView) ?: return
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }

    fun disableBackgroundTouchesHandler() {
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    fun freeBackgroundTouchesHandler() {
        this.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}