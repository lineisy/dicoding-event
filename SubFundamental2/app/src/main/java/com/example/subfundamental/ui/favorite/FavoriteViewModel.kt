package com.example.subfundamental.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.subfundamental.data.local.FavoriteEntity
import com.example.subfundamental.data.repository.Repository

class FavoriteViewModel(private val repository: Repository) : ViewModel() {
    // TODO: Implement the ViewModel

    fun getFavEvent():LiveData<List<FavoriteEntity>>{
        return repository.getAllData()
    }
}