package com.test.arch.domain.interactor.profile

import com.test.arch.domain.repository.ProfileRepository
import io.reactivex.Observable
import javax.inject.Inject

class ProfileStateUseCase @Inject constructor(private val profileRepository: ProfileRepository) {

    fun execute(): Observable<Boolean> = profileRepository.profileState()

}