package com.test.arch.data.model.auth

import com.google.gson.annotations.SerializedName
import com.test.arch.domain.model.auth.SignUpCredentials

class SignUpRequest(
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("birthday")
    val birthday: String,
    @SerializedName("password")
    val password: String
) {
    constructor(entity: SignUpCredentials) : this(
        entity.firstName,
        entity.lastName,
        entity.email,
        entity.birthday,
        entity.password
    )
}