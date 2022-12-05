package com.isaacsufyan.catchu.interfaces

import androidx.appcompat.app.AlertDialog

interface IDialogOnClickListener {
    fun onOk(dialog: AlertDialog)

    fun onCancel(dialog: AlertDialog)
}