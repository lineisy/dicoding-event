package com.example.subfundamental.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "eventFavorite")
data class FavoriteEntity (
    @PrimaryKey
    val id: Int,

    @field:ColumnInfo(name = "name")
    val name : String,

    @field:ColumnInfo(name = "imageLogo")
    val imageLogo : String,

    @field:ColumnInfo(name = "summary")
    val summary : String
)