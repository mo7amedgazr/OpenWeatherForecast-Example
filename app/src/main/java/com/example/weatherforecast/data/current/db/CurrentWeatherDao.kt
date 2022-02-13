package com.example.weatherforecast.data.current.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherforecast.domain.current.entity.CURRENT_WEATHER_ID
import com.example.weatherforecast.domain.current.entity.CurrentWeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weatherEntry: CurrentWeatherEntity)

    @Query("select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getCurrentWeather(): Flow<CurrentWeatherEntity>
}