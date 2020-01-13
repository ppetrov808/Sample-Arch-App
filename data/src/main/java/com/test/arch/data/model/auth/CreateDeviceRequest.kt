package com.test.arch.data.model.auth

import com.google.gson.annotations.SerializedName
import com.test.arch.data.model.DEVICE_TYPE

data class CreateDeviceRequest(
    @SerializedName("token")
    val token: String,
    @SerializedName("type")
    val type: String = DEVICE_TYPE
)