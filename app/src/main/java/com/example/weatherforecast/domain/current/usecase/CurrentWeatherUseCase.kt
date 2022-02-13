package com.example.weatherforecast.domain.current.usecase

import com.example.common.data.utils.DataState
import com.example.common.data.utils.NetworkHelper
import com.example.weatherforecast.domain.current.CurrentWeatherRepository
import com.example.weatherforecast.domain.current.entity.CurrentWeatherEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CurrentWeatherUseCase @Inject constructor(private val currentWeatherRepository: CurrentWeatherRepository) :
    NetworkHelper<CurrentWeatherUseCase.Params, CurrentWeatherEntity>() {

    data class Params constructor(
        val latitude: Double,
        val longitude: Double,
        val ignoreCache: Boolean
    )

    override suspend fun buildUseCase(parameter: Params): Flow<DataState<CurrentWeatherEntity>> {
        return currentWeatherRepository.getCurrentWeather(
            parameter.latitude,
            parameter.longitude,
            parameter.ignoreCache
        )
    }
}