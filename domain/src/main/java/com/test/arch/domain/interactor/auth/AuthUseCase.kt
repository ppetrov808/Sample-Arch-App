package com.test.arch.domain.interactor.auth

import com.test.arch.domain.model.auth.Credentials
import com.test.arch.domain.model.profile.UserProfileEntity
import com.test.arch.domain.repository.AuthRepository
import io.reactivex.Single
import javax.inject.Inject

open class AuthUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    fun execute(params: Credentials?): Single<UserProfileEntity> {
        val credentials = params ?: Credentials("", "")
        return authRepository.authorize(credentials)
    }
}
 
 