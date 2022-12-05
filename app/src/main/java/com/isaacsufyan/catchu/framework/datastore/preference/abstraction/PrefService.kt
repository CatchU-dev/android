package com.isaacsufyan.catchu.framework.datastore.preference.abstraction

import kotlinx.coroutines.flow.Flow

interface PrefService {

    fun getLanguage(): Flow<String>

    suspend fun setLanguage(language: String)
}