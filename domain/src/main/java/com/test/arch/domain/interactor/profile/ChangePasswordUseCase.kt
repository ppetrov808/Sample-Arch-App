package com.test.arch.domain.interactor.profile

import com.test.arch.domain.model.profile.ChangePasswordEntity
import com.test.arch.domain.repository.ProfileRepository
import io.reactivex.Completable
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {

    fun execute(params: ChangePasswordEntity): Completable {
        return profileRepository.changePassword(params)
    }
}