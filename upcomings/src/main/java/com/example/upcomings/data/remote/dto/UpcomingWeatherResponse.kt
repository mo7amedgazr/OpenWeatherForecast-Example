package com.example.upcomings.data.remote.dto

import com.example.common.data.utils.BaseResponse
import com.example.upcomings.domain.entity.City
import com.example.upcomings.domain.entity.WeatherListItem
import com.google.gson.annotations.SerializedName

data class UpcomingWeatherResponse(

	@field:SerializedName("city")
	val city: City? = null,

	@field:SerializedName("cnt")
	val cnt: Int? = null,

	@field:SerializedName("message")
	val message: Double? = null,

	@field:SerializedName("list")
	val list: List<WeatherListItem>? = null
):BaseResponse()