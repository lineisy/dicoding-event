package com.example.subfundamental.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteEntity::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase(){
    abstract fun eventDao(): EventDao

    companion object{

        private val INSTANCE: FavoriteDatabase?= null
        fun getInstance(context: Context): FavoriteDatabase =
            INSTANCE?: synchronized(this){
                INSTANCE?: Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteDatabase::class.java, "Fav.db"
                ).build()
            }
    }
}