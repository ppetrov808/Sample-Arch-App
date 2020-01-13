package com.test.arch.domain.interactor.profile

import com.test.arch.domain.executor.PostExecutionThread
import com.test.arch.domain.executor.ThreadExecutor
import com.test.arch.domain.interactor.CompletableUseCase
import com.test.arch.domain.model.profile.UserProfileEntity
import com.test.arch.domain.repository.ProfileRepository
import javax.inject.Inject

class SaveProfileCacheUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<UserProfileEntity>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: UserProfileEntity) = profileRepository.saveProfile(params)
}