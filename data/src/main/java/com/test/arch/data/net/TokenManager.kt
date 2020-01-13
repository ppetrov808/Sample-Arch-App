package com.test.arch.data.net

interface TokenManager {

    fun getTypeToken(): String?

    fun getToken(): String?

    fun setTypeToken(type: String)

    fun setToken(token: String)

    fun hasToken(): Boolean

    fun clearToken()
}