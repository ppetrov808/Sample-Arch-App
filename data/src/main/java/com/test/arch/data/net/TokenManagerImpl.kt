package com.test.arch.data.net

import android.content.SharedPreferences
import javax.inject.Inject

class TokenManagerImpl @Inject constructor(private val prefs: SharedPreferences) : TokenManager {

    companion object {

        private const val TOKEN = "TOKEN"
        private const val TOKEN_TYPE = "TOKEN_TYPE"

    }

    override fun getTypeToken(): String? {
        return prefs.getString(TOKEN_TYPE, null)
    }

    override fun getToken(): String? {
        return prefs.getString(TOKEN, null)
    }

    override fun setTypeToken(type: String) {
        prefs.edit().putString(TOKEN_TYPE, type).apply()
    }

    override fun setToken(token: String) {
        prefs.edit().putString(TOKEN, token).apply()
    }

    override fun hasToken(): Boolean {
        if (prefs.getString(TOKEN, null).isNullOrEmpty()) {
            return false
        }
        return true
    }

    override fun clearToken() {
        prefs.edit()
            .remove(TOKEN)
            .remove(TOKEN_TYPE)
            .apply()
    }
}