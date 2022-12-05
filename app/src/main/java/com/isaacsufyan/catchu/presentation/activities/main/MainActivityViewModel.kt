package com.isaacsufyan.catchu.presentation.activities.main

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.isaacsufyan.catchu.framework.datastore.preference.abstraction.PrefService
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel
@Inject
constructor(
    private val prefService: PrefService,
) : ViewModel() {

    val getLanguage: LiveData<String> = prefService.getLanguage().asLiveData()

    fun setLanguage(language: String){
        viewModelScope.launch {
            prefService.setLanguage(language)
        }
    }
}