package com.test.arch.domain.interactor.auth

import com.test.arch.domain.repository.AuthRepository
import io.reactivex.Observable
import javax.inject.Inject

class AuthUserState @Inject constructor(private val authRepository: AuthRepository) {

    fun execute(): Observable<Boolean> = authRepository.userAuthState()

}