package com.test.arch.data.net

import com.test.arch.data.model.HEADER_AUTHORIZATION
import io.reactivex.subjects.PublishSubject
import okhttp3.Interceptor
import okhttp3.Response


class TokenInterceptor constructor(
    private val tokenManager: TokenManager,
    private val unauthorizedSubject: PublishSubject<Boolean>
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response? {
        val initialRequest = chain.request()
        var modifiedRequest = initialRequest
        if (tokenManager.hasToken()) {
            val typeToken = tokenManager.getTypeToken()
            val token = tokenManager.getToken()
            modifiedRequest = initialRequest.newBuilder()
                .addHeader(
                    HEADER_AUTHORIZATION,
                    "$typeToken $token"
                )
                .build()
        }
        val response = chain.proceed(modifiedRequest)
        val unauthorized = response.code() == 401
        if (unauthorized) {
            unauthorizedSubject.onNext(true)
        }
        return response
    }
}