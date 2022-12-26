package com.isaacsufyan.catchu.utils

import android.widget.EditText


fun EditText.textToString(): String = this.text.toString().trim()

fun EditText.isNullOrEmpty(): Boolean =
    text == null || "" == textToString() || text.toString().equals("null", ignoreCase = true)

