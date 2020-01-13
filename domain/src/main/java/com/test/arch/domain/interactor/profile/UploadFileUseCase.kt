package com.test.arch.domain.interactor.profile

import com.test.arch.domain.executor.PostExecutionThread
import com.test.arch.domain.executor.ThreadExecutor
import com.test.arch.domain.interactor.SingleUseCase
import com.test.arch.domain.model.profile.UploadProfileImageEntity
import com.test.arch.domain.repository.ProfileRepository
import io.reactivex.Single
import javax.inject.Inject

class UploadFileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<String, UploadProfileImageEntity>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: UploadProfileImageEntity): Single<String> {
        return profileRepository.uploadFile(params)
    }
}