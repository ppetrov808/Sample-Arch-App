package com.test.arch.data.model.auth

class AuthSession {
    var token: AuthTokenModel? = null

    fun isEmpty(): Boolean = token == null
}