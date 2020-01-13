package com.test.arch.domain.interactor.auth

import com.test.arch.domain.repository.AuthRepository
import com.test.arch.domain.repository.ProfileRepository
import io.reactivex.Completable
import javax.inject.Inject

open class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository) {

    fun execute(): Completable {
        return authRepository.logout()
            .andThen(profileRepository.clearProfile())
    }
}