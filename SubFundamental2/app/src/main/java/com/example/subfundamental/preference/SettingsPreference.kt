package com.example.subfundamental.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

class SettingsPreference private constructor(private val dataStore: DataStore<Preferences>){
    private val keyTheme = booleanPreferencesKey("settings_theme")

    fun getTheme() : Flow<Boolean> {
        return dataStore.data.map {
            it[keyTheme] ?: false
        }
    }

    suspend fun setThemeSetting(isDarkMode : Boolean){
        dataStore.edit {
            it[keyTheme] = isDarkMode
        }
    }

    companion object{
        @Volatile
        private var instance: SettingsPreference?= null

        fun getInstance(dataStore: DataStore<Preferences>) : SettingsPreference =
            instance ?: synchronized(this){
                instance ?: SettingsPreference(dataStore)
            }.also { instance = it }
    }
}