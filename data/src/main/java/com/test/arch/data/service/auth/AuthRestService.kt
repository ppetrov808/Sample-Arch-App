package com.test.arch.data.service.auth

import com.test.arch.data.model.URL_AUTH
import com.test.arch.data.model.URL_SIGN_UP
import com.test.arch.data.model.auth.AuthorizeRequest
import com.test.arch.data.model.auth.AuthorizeResponse
import com.test.arch.data.model.auth.SignUpRequest
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRestService {

    @POST(URL_AUTH)
    fun authorize(@Body credentials: AuthorizeRequest): Single<AuthorizeResponse>

    @POST(URL_SIGN_UP)
    fun signUp(@Body signUpCredentials: SignUpRequest): Completable


}