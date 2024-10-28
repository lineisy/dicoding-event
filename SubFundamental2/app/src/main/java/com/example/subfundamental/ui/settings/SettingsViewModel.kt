package com.example.subfundamental.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.subfundamental.preference.SettingsPreference
import kotlinx.coroutines.launch

class SettingsViewModel(private val preference: SettingsPreference) : ViewModel() {
    // TODO: Implement the ViewModel
    fun getThemeSetting(): LiveData<Boolean> {
        return preference.getTheme().asLiveData()
    }

    fun setThemeSetting(isDarkMode: Boolean){
        viewModelScope.launch {
            preference.setThemeSetting(isDarkMode)
        }
    }
}