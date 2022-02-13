package com.example.common.data.utils

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("cod")
    val code: Int? = null,
    @SerializedName("message")
    val message: String? = null,
)

