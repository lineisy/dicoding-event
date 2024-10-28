package com.example.subfundamental.data.repository

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.subfundamental.preference.SettingsPreference
import com.example.subfundamental.preference.dataStore
import com.example.subfundamental.ui.favorite.FavoriteViewModel
import com.example.subfundamental.ui.main.DetailViewModel
import com.example.subfundamental.ui.settings.SettingsViewModel

class Factory(
    private val preference: SettingsPreference,
    private val repository: Repository
):ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoriteViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(preference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    companion object{
        private var INSTANCE: Factory? = null
        fun getInstance(context: Context): Factory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Factory(
                    SettingsPreference.getInstance(context.dataStore),
                    Injection.provideRepository(context)
                )
            }.also { INSTANCE = it }
    }
}