package com.test.arch.ui.screens.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.arch.domain.interactor.profile.*
import javax.inject.Inject

class ProfileViewModelFactory @Inject constructor(
    private val context: Context,
    private val loadProfileCacheUseCase: LoadProfileCacheUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val uploadFileUseCase: UploadFileUseCase,
    private val profileStateUseCase: ProfileStateUseCase,
    private val checkProfileUseCase: CheckProfileUseCase,
    private val deleteProfileUseCase: DeleteProfileUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            ProfileViewModel(
                context, loadProfileCacheUseCase, updateProfileUseCase, uploadFileUseCase,
                profileStateUseCase, checkProfileUseCase, deleteProfileUseCase
            ) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}