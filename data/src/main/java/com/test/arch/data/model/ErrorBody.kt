package com.test.arch.data.model

import com.google.gson.annotations.SerializedName

data class ErrorBody(
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("message")
    val message: String
)