package com.test.arch.data.net

import com.test.arch.domain.exceptions.ServerAccessException
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


class NetworkConnectionInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            val request = chain.request()
            val response = chain.proceed(request)
            //TODO uncomment closer to release
//            if (response.code() == 500) {
//                throw ServerErrorException()
//            }
            return response
        } catch (exception: SocketTimeoutException) {
            exception.printStackTrace()
            throw ServerAccessException()
        } catch (exception: UnknownHostException) {
            exception.printStackTrace()
            throw ServerAccessException()
        }
    }

}