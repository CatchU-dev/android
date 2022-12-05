package com.isaacsufyan.catchu.utils

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.isaacsufyan.catchu.R
import com.isaacsufyan.catchu.databinding.DialogAlertBinding
import com.isaacsufyan.catchu.domain.model.ProviderModel
import com.isaacsufyan.catchu.interfaces.IDialogOnClickListener
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat
import java.util.*

fun Activity.showSnackBar(msg: String) {
    val snackBar = Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG)
    val sbView = snackBar.view
    sbView.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_light))
    snackBar.setTextColor(Color.WHITE)
    snackBar.show()
}

fun AppCompatActivity.showDialog(
    isOkDialog: Boolean,
    message: String,
    image: Int,
    negativeButtonText: String?,
    positiveButtonText: String?,
    onClickListener: IDialogOnClickListener
) {
    val dialog = MaterialAlertDialogBuilder(this).create()

    dialog.window?.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
    )

    val binding = DialogAlertBinding.inflate(layoutInflater)
    dialog.setView(binding.root)
    dialog.setCancelable(false)

    binding.tvDialogMessage.text = message
    binding.ivDialogHeader.setImageResource(image)

    if (isOkDialog) binding.btnNo.visibility = View.GONE
    else binding.btnNo.visibility = View.VISIBLE

    if (!positiveButtonText.isNullOrBlank()) binding.btnYes.text = positiveButtonText
    if (!negativeButtonText.isNullOrBlank()) binding.btnNo.text = negativeButtonText

    binding.btnYes.setOnClickListener { onClickListener.onOk(dialog) }
    binding.btnNo.setOnClickListener { onClickListener.onCancel(dialog) }

    dialog.show()
}


fun AppCompatActivity.isNetworkAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    } else {
        if (connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnectedOrConnecting) {
            return true
        }
    }
    return false
}

fun AppCompatActivity.writeTextFileToSDCard(fileName: String, request: String?) {
    val dir: File = getDir("DefaulterApp", MODE_PRIVATE)!!
    if (!dir.exists()) dir.mkdirs()
    val file = File(dir, "$fileName.txt")
    val fOut = FileOutputStream(file)
    val myOutWriter = OutputStreamWriter(fOut)
    myOutWriter.append(request)
    myOutWriter.close()
    fOut.flush()
    fOut.close()
}

fun parseDateToddMMyyyy(time: String): String? {
    val inputPattern = "yyyy-MM-dd"
    val outputPattern = "dd-MM-yyyy"
    val inputFormat = SimpleDateFormat(inputPattern, Locale.getDefault())
    val outputFormat = SimpleDateFormat(outputPattern, Locale.getDefault())
    val date = time.let {
        inputFormat.parse(it)
    }
    val dateString = date?.let {
        outputFormat.format(it)
    }
    return dateString

}

fun isBothListsEqualIgnoreOrder(list1: List<ProviderModel>?, list2: List<ProviderModel>?) =
    list1?.size == list2?.size && list1?.toSet() == list2?.toSet()