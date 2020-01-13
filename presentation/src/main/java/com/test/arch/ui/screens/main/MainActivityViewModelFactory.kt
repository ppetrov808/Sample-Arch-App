package com.test.arch.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.arch.domain.interactor.auth.AuthUserState
import com.test.arch.domain.interactor.auth.LogoutUseCase
import com.test.arch.domain.interactor.profile.LoadProfileCacheUseCase
import com.test.arch.domain.interactor.profile.ProfileStateUseCase
import javax.inject.Inject

class MainActivityViewModelFactory @Inject constructor(
    private val authUserState: AuthUserState,
    private val logoutUseCase: LogoutUseCase,
    private val loadProfileCacheUseCase: LoadProfileCacheUseCase,
    private val profileStateUseCase: ProfileStateUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            MainActivityViewModel(
                authUserState,
                logoutUseCase,
                loadProfileCacheUseCase,
                profileStateUseCase
            ) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}