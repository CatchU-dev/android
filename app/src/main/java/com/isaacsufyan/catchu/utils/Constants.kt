package com.isaacsufyan.catchu.utils

import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.annotations.SerializedName
import com.isaacsufyan.catchu.domain.enum.EAnswer
import com.isaacsufyan.catchu.domain.enum.EGender

object Constants {

    const val PREFS_NAME = "MY_PREF"

    object PreferencesKeys {
        val SELECTED_LANGUAGE = stringPreferencesKey("SELECTED_LANGUAGE")
    }
}