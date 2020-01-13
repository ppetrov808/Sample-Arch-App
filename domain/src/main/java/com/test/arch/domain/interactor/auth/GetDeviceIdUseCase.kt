package com.test.arch.domain.interactor.auth

import com.test.arch.domain.repository.ProfileRepository
import io.reactivex.Single
import javax.inject.Inject

open class GetDeviceIdUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {

    fun execute(): Single<String> {
        return profileRepository.getAdvertisingId()
    }
}
 
 