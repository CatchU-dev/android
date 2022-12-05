package com.isaacsufyan.catchu.utils

import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern


fun EditText.textToString(): String = this.text.toString().trim()

fun EditText.isNullOrEmpty(): Boolean =
    text == null || "" == textToString() || text.toString().equals("null", ignoreCase = true)

fun EditText.isValidEmail(): Boolean {
    val pattern = Pattern.compile(
        "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
        Pattern.CASE_INSENSITIVE
    )
    return pattern.matcher(text).matches()
}

fun EditText.isValidPhoneNumber(): Boolean {
    return text.length==11 && text.startsWith("03")
}

fun EditText.isValidCNIC(): Boolean{
    return text.length == 13 && !text.startsWith("0")
}

fun TextInputLayout.showError(error: String) {
    this.error = error
    this.showSoftKeyboard()
}
