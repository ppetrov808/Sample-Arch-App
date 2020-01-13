package com.test.arch.domain.interactor.auth

import com.test.arch.domain.repository.AuthRepository
import io.reactivex.Single
import javax.inject.Inject

class IsUserAuthUseCase @Inject constructor(private val repository: AuthRepository) {

    fun execute(): Single<Boolean> = repository.isUserAuthorize()

}