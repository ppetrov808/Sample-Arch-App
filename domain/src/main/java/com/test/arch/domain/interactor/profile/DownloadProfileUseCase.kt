package com.test.arch.domain.interactor.profile

import com.test.arch.domain.repository.ProfileRepository
import io.reactivex.Completable
import javax.inject.Inject

@Suppress("LABEL_NAME_CLASH")
class DownloadProfileUseCase @Inject constructor(private val profileRepository: ProfileRepository) {

    fun execute(): Completable {
        return profileRepository.loadProfileCloud()
            .flatMapCompletable { cloudProfile ->
                profileRepository.saveProfile(cloudProfile)
            }
    }
}