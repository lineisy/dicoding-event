package com.example.subfundamental.data.repository

import android.content.Context
import com.example.subfundamental.data.local.AppExcutors
import com.example.subfundamental.data.local.FavoriteDatabase


object Injection {
    fun provideRepository(context: Context) : Repository{
        val database = FavoriteDatabase.getInstance(context)
        val dao = database.eventDao()
        val appExcutors =  AppExcutors()

        return Repository.getInstance(dao,appExcutors)
    }
}