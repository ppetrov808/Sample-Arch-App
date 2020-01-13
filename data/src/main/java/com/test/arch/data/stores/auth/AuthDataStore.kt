package com.test.arch.data.stores.auth

import com.test.arch.domain.model.auth.Credentials
import com.test.arch.domain.model.auth.SignUpCredentials
import com.test.arch.domain.model.profile.UserProfileEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface AuthDataStore {

    fun authorize(credentials: Credentials): Single<UserProfileEntity>

    fun logout(): Completable

    fun isUserAuthorize(): Single<Boolean>

    fun userAuthState(): Observable<Boolean>

    fun signUp(signUpCredentials: SignUpCredentials): Completable


}
 
 