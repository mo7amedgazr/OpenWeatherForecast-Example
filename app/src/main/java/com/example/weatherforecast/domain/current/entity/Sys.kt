package com.example.weatherforecast.domain.current.entity

import com.google.gson.annotations.SerializedName

data class Sys(

	@field:SerializedName("country")
	val country: String = "",

	@field:SerializedName("sunrise")
	val sunrise: Int? = null,

	@field:SerializedName("sunset")
	val sunset: Int? = null
)