package com.test.arch.data.model.auth

import com.google.gson.annotations.SerializedName
import com.test.arch.data.model.AccountResponse

data class AuthorizeResponse(
    @SerializedName("token")
    val token: AuthTokenModel,
    @SerializedName("profile")
    val profile: AccountResponse
)