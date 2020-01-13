package com.test.arch.data.model.auth

import com.google.gson.annotations.SerializedName

class AuthTokenModel(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("tokenType")
    val tokenType: String
)
 
 