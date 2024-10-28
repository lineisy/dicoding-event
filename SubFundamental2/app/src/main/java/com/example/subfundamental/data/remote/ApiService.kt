package com.example.subfundamental.data.remote
import com.example.subfundamental.ui.response.EventResponse
import com.example.subfundamental.ui.response.DetailEventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    fun getEvents(
        @Query("active")
        active: Int
    ) : Call<EventResponse>
    @GET("events/{id}")
    fun getEventDetail(
        @Path("id")
        id: Int
    ) : Call<DetailEventResponse>
}
