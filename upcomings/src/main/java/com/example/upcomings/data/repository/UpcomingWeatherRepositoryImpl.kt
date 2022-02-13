package com.example.upcomings.data.repository

import com.example.common.data.preferences.SharedPrefs
import com.example.common.data.utils.DataState
import com.example.common.data.utils.ErrorResponse
import com.example.upcomings.data.remote.UpcomingWeatherApi
import com.example.upcomings.domain.UpcomingWeatherRepository
import com.example.upcomings.domain.entity.WeatherListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpcomingWeatherRepositoryImpl @Inject constructor(
    private val currentWeatherApi: UpcomingWeatherApi,
    private val sharedPrefs: SharedPrefs
) : UpcomingWeatherRepository {

    override suspend fun getUpcomingWeather(
    ): Flow<DataState<List<WeatherListItem>>> = flow {
        if (sharedPrefs.isLastKnownLocationAvailable()) {
            emit(
                DataState.Success(
                    currentWeatherApi.getCurrentWeather(
                        sharedPrefs.getLastLatitude(),
                        sharedPrefs.getLastLongitude(),
                        sharedPrefs.getUnit()
                    ).list
                )
            )
        } else {
            emit(
                DataState.GenericError(400, ErrorResponse(400, "No Location Available"))
            )
        }

    }
}