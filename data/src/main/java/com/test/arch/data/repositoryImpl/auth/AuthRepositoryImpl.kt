package com.test.arch.data.repositoryImpl.auth

import com.test.arch.data.common.GsonHolder
import com.test.arch.data.model.ErrorBody
import com.test.arch.data.stores.auth.AuthDataStore
import com.test.arch.domain.DomainConstants
import com.test.arch.domain.DomainConstants.ERROR_CODE_401
import com.test.arch.domain.DomainConstants.LOGIN_ERROR_MATCHER
import com.test.arch.domain.DomainConstants.PASSWORD_ERROR_MATCHER
import com.test.arch.domain.DomainConstants.INVALID_PASSWORD_ERROR_MATCHER
import com.test.arch.domain.exceptions.IncorrectCredentialsException
import com.test.arch.domain.exceptions.InvalidPasswordException
import com.test.arch.domain.exceptions.UserAlreadyExistsException
import com.test.arch.domain.model.auth.Credentials
import com.test.arch.domain.model.auth.SignUpCredentials
import com.test.arch.domain.model.profile.UserProfileEntity
import com.test.arch.domain.repository.AuthRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val store: AuthDataStore
) : AuthRepository {

    override fun isUserAuthorize(): Single<Boolean> = store.isUserAuthorize()

    override fun authorize(credentials: Credentials): Single<UserProfileEntity> {
        return store.authorize(credentials)
            .onErrorResumeNext {
                if (it is HttpException) {
                    if (it.code() == ERROR_CODE_401) {
                        val errorMessage = it.response().errorBody()?.string() ?: ""
                        when {
                            errorMessage.contains(LOGIN_ERROR_MATCHER) -> return@onErrorResumeNext Single.error(
                                IncorrectCredentialsException(
                                    IncorrectCredentialsException.Type.LOGIN_INCORRECT
                                )
                            )
                            errorMessage.contains(PASSWORD_ERROR_MATCHER) -> return@onErrorResumeNext Single.error(
                                IncorrectCredentialsException(
                                    IncorrectCredentialsException.Type.PASSWORD_INCORRECT
                                )
                            )
                        }
                    }
                }
                return@onErrorResumeNext Single.error(it)
            }
    }

    override fun logout() = store.logout()

    override fun userAuthState(): Observable<Boolean> = store.userAuthState()

    override fun signUp(signUpCredentials: SignUpCredentials) = store.signUp(signUpCredentials)
        .onErrorResumeNext {
            if (it is HttpException) {
                if (it.code() == DomainConstants.ERROR_CODE_400) {
                    val errorMessage = it.response().errorBody()?.string() ?: ""
                    val errorBody = GsonHolder.GSON.fromJson(errorMessage, ErrorBody::class.java)
                    if (UserAlreadyExistsException.STATUS_CODE == errorBody.statusCode) {
                        return@onErrorResumeNext Completable.error(UserAlreadyExistsException(it.message()))
                    } else if (errorMessage.contains(INVALID_PASSWORD_ERROR_MATCHER)) {
                        return@onErrorResumeNext Completable.error(InvalidPasswordException(it.message()))
                    }
                }
            }
            return@onErrorResumeNext Completable.error(it)
        }

}
 
 