package com.test.arch.domain.interactor.profile

import com.test.arch.domain.model.profile.UserProfileEntity
import com.test.arch.domain.repository.AuthRepository
import com.test.arch.domain.repository.ProfileRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

@Suppress("LABEL_NAME_CLASH")
class CheckProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val authRepository: AuthRepository
) {


    fun execute(): Completable {
        return authRepository.isUserAuthorize()
            .flatMap {
                if (it) {
                    return@flatMap profileRepository.loadProfileCloud()
                } else {
                    return@flatMap Single.error<UserProfileEntity>(IllegalAccessException())
                }
            }
            .flatMapCompletable { cloudProfile ->
                profileRepository.loadProfileCache()
                    .flatMapCompletable { cacheProfile ->
                        if (cacheProfile == cloudProfile) {
                            return@flatMapCompletable Completable.complete()
                        }
                        profileRepository.saveProfile(cloudProfile)
                    }
            }
    }
}