package com.example.common.data.utils

import com.google.gson.annotations.SerializedName

open class BaseResponse(
    @SerializedName("cod") val code: Int? = null
)