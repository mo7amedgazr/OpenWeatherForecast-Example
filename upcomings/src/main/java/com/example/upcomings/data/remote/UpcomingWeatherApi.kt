package com.example.upcomings.data.remote

import com.example.upcomings.data.remote.dto.UpcomingWeatherResponse
import retrofit2.http.*

interface UpcomingWeatherApi {

    @GET("forecast")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") unit : String
    ): UpcomingWeatherResponse
}