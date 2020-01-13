package com.test.arch.domain.interactor.profile

import com.test.arch.domain.executor.PostExecutionThread
import com.test.arch.domain.executor.ThreadExecutor
import com.test.arch.domain.interactor.MaybeUseCase
import com.test.arch.domain.model.profile.UserProfileEntity
import com.test.arch.domain.repository.ProfileRepository
import javax.inject.Inject

class LoadProfileCacheUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : MaybeUseCase<UserProfileEntity, Nothing?>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Nothing?) = profileRepository.loadProfileCache()
}