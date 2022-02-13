package com.example.weatherforecast.domain.current.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_WEATHER_ID = 0

@Entity(tableName = "current_weather")
data class CurrentWeatherEntity(
    val temp: Double? = null,
    val humidity: Int? = null,
    val wind: Int? = null,
    val icon : String? = null,
    val unit: String? = null
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_WEATHER_ID
}