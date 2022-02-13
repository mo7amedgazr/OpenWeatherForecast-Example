package com.example.weatherforecast.domain.current

import com.example.common.data.utils.DataState
import com.example.weatherforecast.domain.current.entity.CurrentWeatherEntity
import kotlinx.coroutines.flow.Flow

interface CurrentWeatherRepository {
    suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double,
        ignoreCache: Boolean
    ): Flow<DataState<CurrentWeatherEntity>>
}