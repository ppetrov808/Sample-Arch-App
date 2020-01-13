package com.test.arch.domain

object DomainConstants {

    const val UNAUTHORIZED = "UNAUTHORIZED"
    const val DEVICE_TYPE = "ANDROID"
    const val DEVICE_TYPE_IOS = "IOS"

    const val UNAUTHORIZED_HTTP_CLIENT = "UNAUTHORIZED_HTTP_CLIENT"
    const val AUTHORIZED_HTTP_CLIENT = "AUTHORIZED_HTTP_CLIENT"

    const val UNAUTHORIZED_INTERCEPTORS = "UNAUTHORIZED_INTERCEPTORS"
    const val AUTHORIZED_INTERCEPTORS = "AUTHORIZED_INTERCEPTORS"

    const val DATE_FORMAT = "dd.MM.yyyy"
    const val TIME_FORMAT = "HH:mm"
    const val LOGIN_ERROR_MATCHER = "username is incorrect"
    const val PASSWORD_ERROR_MATCHER = "password is incorrect"
    const val INVALID_PASSWORD_ERROR_MATCHER = "ValidPassword.password"

    const val GSON_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    const val ERROR_CODE_400 = 400
    const val ERROR_CODE_401 = 401
}