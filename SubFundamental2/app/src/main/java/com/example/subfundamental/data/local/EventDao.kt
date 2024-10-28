package com.example.subfundamental.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EventDao {
    @Query("SELECT * FROM eventFavorite WHERE id = :id")
    fun getFavoriteEventById(id: Int): LiveData<FavoriteEntity>

    @Query("DELETE FROM eventFavorite ")
    fun deleteAll()

    @Query("SELECT * FROM eventFavorite")
    fun getAllData():LiveData<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: FavoriteEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM eventFavorite WHERE id = :id)")
    fun isFavorite(id: Int): LiveData<Boolean>

    @Query("DELETE FROM eventFavorite WHERE id = :id")
    suspend fun deleteById(id: Int)
}