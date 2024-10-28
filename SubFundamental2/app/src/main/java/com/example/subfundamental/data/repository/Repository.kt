package com.example.subfundamental.data.repository

import androidx.lifecycle.LiveData
import com.example.subfundamental.data.local.AppExcutors
import com.example.subfundamental.data.local.EventDao
import com.example.subfundamental.data.local.FavoriteEntity
import com.example.subfundamental.data.remote.ApiService
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.withContext

class Repository private constructor(
    private val eventDao: EventDao,
    private val appExecutors: AppExcutors
){
    suspend fun addFavorite(event: FavoriteEntity){
        return eventDao.insertEvent(event)
    }

    fun isEventFavorite(eventId: Int): LiveData<Boolean> {
        return eventDao.isFavorite(eventId) // Assumes you have an EventDao method to check favorite status
    }

    suspend fun deleteFavorite(id: Int){
        withContext(appExecutors.diskIO.asCoroutineDispatcher()){
            eventDao.deleteById(id)
        }
    }



    fun getAllData():LiveData<List<FavoriteEntity>>{
        return eventDao.getAllData()
    }

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(
            eventDao: EventDao,
            appExecutors: AppExcutors
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository( eventDao, appExecutors)
            }.also { instance = it }
    }
}