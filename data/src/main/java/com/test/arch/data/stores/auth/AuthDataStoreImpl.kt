package com.test.arch.data.stores.auth

import com.test.arch.data.mapper.auth.AuthRequestMapper
import com.test.arch.data.model.auth.SignUpRequest
import com.test.arch.data.net.TokenManager
import com.test.arch.data.service.auth.AuthRestService
import com.test.arch.data.utils.MockDataTools
import com.test.arch.domain.model.AddressEntity
import com.test.arch.domain.model.SexEntity
import com.test.arch.domain.model.StatusEntity
import com.test.arch.domain.model.auth.Credentials
import com.test.arch.domain.model.auth.SignUpCredentials
import com.test.arch.domain.model.profile.UserProfileEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import java.io.Serializable
import javax.inject.Inject

class AuthDataStoreImpl @Inject constructor(
    private val tokenManager: TokenManager,
    private val authRestService: AuthRestService,
    private val requestMapper: AuthRequestMapper
) : AuthDataStore {

    private val userAuthSubject = BehaviorSubject.createDefault<Boolean>(tokenManager.hasToken())

    override fun authorize(credentials: Credentials): Single<UserProfileEntity> {
        //return authRestService.authorize(requestMapper.mapFromData(credentials))
        //    .doOnSuccess {
        //        tokenManager.setTypeToken(it.token.tokenType)
        //        tokenManager.setToken(it.token.accessToken)
        //    }
        //    .map { it.profile.toEntity() }
        //    .doOnSuccess { userAuthSubject.onNext(true) }
        //    .doOnError { tokenManager.clearToken() }
        return MockDataTools.mockProfile()
            .doOnSuccess {
                tokenManager.setTypeToken("Bearer")
                tokenManager.setToken("valid token")
                userAuthSubject.onNext(true) }
            .doOnError { tokenManager.clearToken() }
    }

    override fun logout(): Completable {
        return Completable.fromAction {
            tokenManager.clearToken()
            userAuthSubject.onNext(false)
        }
    }

    override fun isUserAuthorize(): Single<Boolean> {
        return Single.fromCallable { return@fromCallable tokenManager.hasToken() }
    }

    override fun userAuthState(): Observable<Boolean> = userAuthSubject

    override fun signUp(signUpCredentials: SignUpCredentials) = authRestService.signUp(SignUpRequest(signUpCredentials))
}