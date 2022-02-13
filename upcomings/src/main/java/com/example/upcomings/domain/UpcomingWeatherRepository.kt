package com.example.upcomings.domain

import com.example.common.data.utils.DataState
import com.example.upcomings.domain.entity.WeatherListItem
import kotlinx.coroutines.flow.Flow

interface UpcomingWeatherRepository {

    suspend fun getUpcomingWeather(): Flow<DataState<List<WeatherListItem>>>
}