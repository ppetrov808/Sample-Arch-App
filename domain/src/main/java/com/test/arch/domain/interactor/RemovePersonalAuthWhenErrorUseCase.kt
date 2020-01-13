package com.test.arch.domain.interactor

import com.test.arch.domain.repository.AuthRepository
import com.test.arch.domain.repository.ProfileRepository
import io.reactivex.Completable
import javax.inject.Inject

class RemovePersonalAuthWhenErrorUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val authRepository: AuthRepository
) {

    fun execute(): Completable {
        return profileRepository.clearProfile()
            .andThen(authRepository.logout())
    }

}