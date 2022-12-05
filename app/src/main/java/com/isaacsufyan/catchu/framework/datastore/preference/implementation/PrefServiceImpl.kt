package com.isaacsufyan.catchu.framework.datastore.preference.implementation

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.isaacsufyan.catchu.domain.enum.ELanguage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import com.isaacsufyan.catchu.framework.datastore.preference.abstraction.PrefService
import com.isaacsufyan.catchu.utils.Constants
import com.isaacsufyan.catchu.utils.Constants.PREFS_NAME
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PrefServiceImpl @Inject constructor(
    @ApplicationContext var context: Context
) : PrefService {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFS_NAME)

    override fun getLanguage(): Flow<String> {
        return context.dataStore.data.map {
            it[Constants.PreferencesKeys.SELECTED_LANGUAGE] ?: ELanguage.ENGLISH.name
        }
    }

    override suspend fun setLanguage(language: String) {
        context.dataStore.edit {
            it[Constants.PreferencesKeys.SELECTED_LANGUAGE] = language
        }
    }
}