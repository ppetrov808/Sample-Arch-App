package com.test.arch.domain.interactor.profile

import com.test.arch.domain.executor.PostExecutionThread
import com.test.arch.domain.executor.ThreadExecutor
import com.test.arch.domain.interactor.CompletableUseCase
import com.test.arch.domain.model.profile.UpdateProfileEntity
import com.test.arch.domain.repository.ProfileRepository
import io.reactivex.Completable
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<UpdateProfileEntity>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: UpdateProfileEntity): Completable {
        return profileRepository.updateProfile(params).flatMapCompletable { profileRepository.saveProfile(it) }
    }
}