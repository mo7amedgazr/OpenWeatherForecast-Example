package com.example.weatherforecast.data.current.remote.dto.response

import com.example.common.data.utils.BaseResponse
import com.example.weatherforecast.domain.current.entity.*
import com.google.gson.annotations.SerializedName


data class CurrentWeatherResponse(

    @field:SerializedName("visibility")
    val visibility: Int? = null,

    @field:SerializedName("timezone")
    val timezone: Int? = null,

    @field:SerializedName("main")
    val main: Main? = null,

    @field:SerializedName("clouds")
    val clouds: Clouds? = null,

    @field:SerializedName("sys")
    val sys: Sys? = null,

    @field:SerializedName("dt")
    val dt: Int? = null,

    @field:SerializedName("coord")
    val coord: Coord? = null,

    @field:SerializedName("weather")
    val weather: List<WeatherItem?>? = null,

    @field:SerializedName("name")
    val name: String = "",

    @field:SerializedName("base")
    val base: String? = null,

    @field:SerializedName("wind")
    val wind: Wind? = null
) : BaseResponse() {

    fun getCurrentEntity(unit: String) = CurrentWeatherEntity(
        temp = main?.temp,
        humidity = main?.humidity,
        wind = wind?.deg,
        unit = unit,
        icon = if (weather?.isNotEmpty() == true) weather[0]?.icon else null
    )
}