package com.example.subfundamental.ui.finished

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.subfundamental.data.remote.ApiConfig
import com.example.subfundamental.ui.response.EventResponse
import com.example.subfundamental.ui.response.ListEventsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FinishedViewModel : ViewModel() {

    private val _isloading = MutableLiveData<Boolean>()
    private val _finishedEvent = MutableLiveData<List<ListEventsItem>>()
    private val _errorMessage = MutableLiveData<String>()

    val isloading: LiveData<Boolean> = _isloading
    val finishedEvent: LiveData<List<ListEventsItem>> = _finishedEvent
    val errorMessage: LiveData<String> = _errorMessage

    fun findFinishedEvent() {
        _isloading.value = true
        val client = ApiConfig.getApiService().getEvents(0)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(
                call: Call<EventResponse>,
                response: Response<EventResponse>
            ) {
                if (response.isSuccessful) {
                    _isloading.value = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val eventList: List<ListEventsItem> = responseBody.listEvents
                        _finishedEvent.value = eventList
                    }
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _errorMessage.value = t.message
            }
        })
    }
}