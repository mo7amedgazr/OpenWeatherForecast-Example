package com.example.upcomings.domain.usecase

import com.example.common.data.utils.DataState
import com.example.common.data.utils.NetworkHelper
import com.example.upcomings.domain.UpcomingWeatherRepository
import com.example.upcomings.domain.entity.WeatherListItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpcomingWeatherUseCase @Inject constructor(private val upcomingWeatherRepository: UpcomingWeatherRepository) :
    NetworkHelper<UpcomingWeatherUseCase.Params, List<WeatherListItem>>() {

    class Params

    override suspend fun buildUseCase(parameter: Params): Flow<DataState<List<WeatherListItem>>> {
        return upcomingWeatherRepository.getUpcomingWeather()
    }
}