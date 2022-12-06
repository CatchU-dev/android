package com.isaacsufyan.catchu.framework.interfaces

import androidx.appcompat.app.AlertDialog

interface IDialogOnClickListener {
    fun onOk(dialog: AlertDialog)

    fun onCancel(dialog: AlertDialog){

    }
}