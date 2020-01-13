package com.test.arch.domain.interactor.profile

import com.test.arch.domain.repository.ProfileRepository
import io.reactivex.Completable
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {

    fun execute(email: String): Completable = profileRepository.resetPassword(email)
}