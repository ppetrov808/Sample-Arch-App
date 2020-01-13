package com.test.arch.domain.interactor.auth

import com.test.arch.domain.repository.ProfileRepository
import io.reactivex.Completable
import javax.inject.Inject

open class CreateDeviceUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {

    fun execute(): Completable {
        return profileRepository.getAdvertisingId()
            .flatMapCompletable { token ->
                profileRepository.createDevice(token)
            }
    }
}
 
 