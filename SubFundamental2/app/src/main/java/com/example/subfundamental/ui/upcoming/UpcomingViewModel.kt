package com.example.subfundamental.ui.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.subfundamental.data.remote.ApiConfig
import com.example.subfundamental.ui.response.EventResponse
import com.example.subfundamental.ui.response.ListEventsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcomingViewModel : ViewModel() {
    private val _isloading = MutableLiveData<Boolean>()
    private val _upcomingEvent = MutableLiveData<List<ListEventsItem>>()
    private val _errorMessage = MutableLiveData<String>()

    val isloading: LiveData<Boolean> = _isloading
    val upcomingEvent: LiveData<List<ListEventsItem>> = _upcomingEvent
    val errorMessage: LiveData<String> = _errorMessage

    fun findUpcomingEvent() {
        _isloading.value = true
        val client = ApiConfig.getApiService().getEvents(1)
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
                        _upcomingEvent.value = eventList
                    }
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _errorMessage.value = t.message
            }
        })
    }
}
