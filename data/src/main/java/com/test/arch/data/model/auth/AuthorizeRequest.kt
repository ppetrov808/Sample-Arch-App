package com.test.arch.data.model.auth

import com.google.gson.annotations.SerializedName

class AuthorizeRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)

 