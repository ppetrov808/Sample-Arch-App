package com.test.arch.ui.screens.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.arch.domain.interactor.RemovePersonalAuthWhenErrorUseCase
import com.test.arch.domain.interactor.auth.AuthUseCase
import com.test.arch.domain.interactor.auth.CreateDeviceUseCase
import com.test.arch.domain.interactor.profile.DownloadProfileUseCase
import javax.inject.Inject

class LoginViewModelFactory @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val createDeviceUseCase: CreateDeviceUseCase,
    private val downloadProfileUseCase: DownloadProfileUseCase,
    private val removePersonalAuthWhenErrorUseCase: RemovePersonalAuthWhenErrorUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            LoginViewModel(
                authUseCase, createDeviceUseCase,
                downloadProfileUseCase,
                removePersonalAuthWhenErrorUseCase
            ) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}