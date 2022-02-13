package com.example.weatherforecast.data.current.remote.api

import com.example.weatherforecast.data.current.remote.dto.response.CurrentWeatherResponse
import retrofit2.http.*

interface CurrentWeatherApi {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") unit : String
    ): CurrentWeatherResponse
}