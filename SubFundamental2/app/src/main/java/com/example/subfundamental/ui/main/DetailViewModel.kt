package com.example.subfundamental.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subfundamental.data.local.FavoriteEntity
import com.example.subfundamental.data.remote.ApiConfig
import com.example.subfundamental.data.repository.Repository
import com.example.subfundamental.ui.response.DetailEventResponse
import com.example.subfundamental.ui.response.Event
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class DetailViewModel (private val repository: Repository): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _detailEvent = MutableLiveData<Event>()
    val detailEvent: MutableLiveData<Event> = _detailEvent

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun loadDetailEvents(id: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getEventDetail(id)

        client.enqueue(object : retrofit2.Callback<DetailEventResponse> {
            override fun onResponse(
                call: Call<DetailEventResponse>,
                response: Response<DetailEventResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val detailResponse = response.body()
                    if (detailResponse != null && !detailResponse.error) {
                        val eventList: Event = detailResponse.event
                        _detailEvent.value = eventList
                    } else {
                        _errorMessage.value = detailResponse?.message ?: "Unknown error"
                    }
                } else {
                    _errorMessage.value = "Failed: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<DetailEventResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = t.message ?: "Unknown error"
            }
        })
    }



    fun addFavorite(event: Event){
        val eventEntity = mapEventToEntity(event)
        viewModelScope.launch {
            repository.addFavorite(eventEntity)
        }
    }

    private fun mapEventToEntity(event: Event): FavoriteEntity {
        return FavoriteEntity(
            id = event.id,
            name = event.name,
            imageLogo = event.imageLogo,
            summary = event.summary
        )
    }

    fun isFav(id: Int): LiveData<Boolean>{
        return repository.isEventFavorite(id)
    }

    fun delFavorite(id: Int){
        viewModelScope.launch {
            repository.deleteFavorite(id)
        }
    }

}
