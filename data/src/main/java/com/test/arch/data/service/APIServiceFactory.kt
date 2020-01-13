package com.test.arch.data.service

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.test.arch.data.BuildConfig
import com.test.arch.data.net.NetworkConnectionInterceptor
import com.test.arch.data.net.TokenInterceptor
import com.test.arch.data.net.TokenManager
import com.test.arch.domain.DomainConstants.GSON_DATE_FORMAT
import io.reactivex.subjects.PublishSubject
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import javax.security.cert.CertificateException

object APIServiceFactory {

    private const val CONNECT_TIMEOUT = 45L
    private const val READ_TIMEOUT = 30L

    inline fun <reified T> makeRestService(okHttpClient: OkHttpClient, gson: Gson): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(T::class.java)
    }

    fun makeOkHttpClient(interceptors: Array<Interceptor>): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        interceptors.forEach { okHttpClientBuilder.addInterceptor(it) }
        return okHttpClientBuilder.build()
    }

    fun makeUnsafeOkHttpClientWith(interceptors: Array<Interceptor>): OkHttpClient {
        try {

            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }

                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                }
            })

            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            val sslSocketFactory = sslContext.socketFactory

            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { _, _ -> true }
            builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            interceptors.forEach { builder.addInterceptor(it) }
            return builder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    fun makeGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .setDateFormat(GSON_DATE_FORMAT)
            .create()
    }

    fun makeLoggingInterceptor(isDebug: Boolean): Interceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }

    fun makeTokenInterceptor(tokenManager: TokenManager, unauthorizedSubject: PublishSubject<Boolean>): Interceptor {
        return TokenInterceptor(tokenManager, unauthorizedSubject)
    }

    fun makeNetworkConnectionInterceptor(): Interceptor {
        return NetworkConnectionInterceptor()
    }
}