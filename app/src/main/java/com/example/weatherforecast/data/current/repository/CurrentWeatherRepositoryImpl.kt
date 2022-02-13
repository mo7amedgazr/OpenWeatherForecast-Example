package com.example.weatherforecast.data.current.repository

import com.example.common.data.preferences.SharedPrefs
import com.example.common.data.utils.DataState
import com.example.weatherforecast.data.current.db.CurrentWeatherDao
import com.example.weatherforecast.data.current.remote.api.CurrentWeatherApi
import com.example.weatherforecast.domain.current.CurrentWeatherRepository
import com.example.weatherforecast.domain.current.entity.CurrentWeatherEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.sql.Timestamp
import javax.inject.Inject

class CurrentWeatherRepositoryImpl @Inject constructor(
    private val currentWeatherApi: CurrentWeatherApi,
    private val sharedPrefs: SharedPrefs,
    private val currentWeatherDao: CurrentWeatherDao
) :
    CurrentWeatherRepository {

    override suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double,
        ignoreCache: Boolean
    ): Flow<DataState<CurrentWeatherEntity>> =
        flow {
            sharedPrefs.saveLastKnowLocation(latitude, longitude)
            val currentTimeStamp = System.currentTimeMillis()
            val lastCheckTime = sharedPrefs.getLastFetch()
            val unit = sharedPrefs.getUnit()
            if (isFetchNeeded(lastCheckTime, currentTimeStamp) || ignoreCache) {
                val entity =
                    currentWeatherApi.getCurrentWeather(latitude, longitude, unit)
                        .getCurrentEntity(unit)
                GlobalScope.launch(Dispatchers.IO) {
                    currentWeatherDao.insert(entity)
                }
                sharedPrefs.saveLastFetch(currentTimeStamp)
                emit(DataState.Success(entity))
            } else {
                // fetch from db.
                emit(DataState.Success(currentWeatherDao.getCurrentWeather().first()))
            }
        }


    // this logic for fetching the weather only after 30 minutes.
    private fun isFetchNeeded(lastCheckTime: Long, currentTimeStamp: Long): Boolean {
        val timestampAfter30Mins = lastCheckTime + (30 * 60 * 1000)
        return Timestamp(timestampAfter30Mins).before(Timestamp(currentTimeStamp))
    }
}