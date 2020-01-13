package com.test.arch.ui.screens.profile

import android.content.Context
import android.net.Uri
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.test.arch.domain.interactor.profile.*
import com.test.arch.domain.model.profile.UpdateProfileEntity
import com.test.arch.domain.model.profile.UploadProfileImageEntity
import com.test.arch.domain.model.profile.UserProfileEntity
import com.test.arch.ui.R
import com.test.arch.ui.base.BaseViewModel2
import com.test.arch.ui.base.mvvm.SingleLiveEvent
import com.test.arch.ui.base.subscriber.BaseDisposableCompletableSubscriber
import com.test.arch.ui.base.subscriber.BaseDisposableMaybeSubscriber
import com.test.arch.ui.base.subscriber.BaseDisposableSingleSubscriber
import com.test.arch.ui.data.Resource
import com.test.arch.ui.extensions.getFileExtension
import com.test.arch.ui.extensions.getInputStream
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class ProfileViewModel(
    private val context: Context,
    private val loadProfileCacheUseCase: LoadProfileCacheUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val uploadFileUseCase: UploadFileUseCase,
    private val profileStateUseCase: ProfileStateUseCase,
    private val checkProfileUseCase: CheckProfileUseCase,
    private val deleteProfileUseCase: DeleteProfileUseCase
) : BaseViewModel2() {

    private var profileStateDisposable: Disposable? = null

    val userProfileLiveData = MutableLiveData<Resource<UserProfileEntity>>()
    val updateProfileLiveData = MutableLiveData<Resource<Nothing>>()
    val uploadFileLiveData = MutableLiveData<Resource<String>>()
    val errorFieldLiveData = SingleLiveEvent<ErrorField>()
    val deleteProfileLiveData = MutableLiveData<Resource<Nothing>>()

    override fun onStart() {
        super.onStart()
        compositeDisposable.add(
            checkProfileUseCase.execute()
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(object : DisposableCompletableObserver() {
                    override fun onComplete() {}
                    override fun onError(e: Throwable) {}
                })
        )
        if (profileStateDisposable == null) {
            profileStateDisposable = profileStateUseCase.execute()
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : DisposableObserver<Boolean>() {
                    override fun onComplete() {}
                    override fun onNext(t: Boolean) {
                        loadProfileCache()
                    }

                    override fun onError(e: Throwable) {}

                })
        }
    }

    override fun onCleared() {
        super.onCleared()
        profileStateDisposable?.dispose()
    }

    private fun loadProfileCache() {
        val disposable = loadProfileCacheUseCase.execute(BaseDisposableMaybeSubscriber(userProfileLiveData), null)
        compositeDisposable.add(disposable)
    }

    fun updateProfile(updateProfile: UpdateProfileEntity) {
        if (updateProfile.firstName.isEmpty()) {
            errorFieldLiveData.value = ErrorField.FirstName(R.string.empty_first_name)
            return
        }
        if (updateProfile.lastName.isEmpty()) {
            errorFieldLiveData.value = ErrorField.LastName(R.string.empty_last_name)
            return
        }
        if (updateProfile.email.isEmpty()) {
            errorFieldLiveData.value = ErrorField.Email(R.string.empty_email)
            return
        }
        if (!isValidEmail(updateProfile.email)) {
            errorFieldLiveData.value = ErrorField.Email(R.string.invalid_email)
            return
        }
        if (updateProfile.birthday.isNullOrEmpty()) {
            errorFieldLiveData.value = ErrorField.Birthday(R.string.empty_date_of_birth)
            return
        }
        val disposable = updateProfileUseCase.execute(updateProfile)
            .subscribeWith(BaseDisposableCompletableSubscriber(updateProfileLiveData))
        compositeDisposable.add(disposable)
    }

    private fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    fun uploadProfileImage(uri: Uri) {
        val params = UploadProfileImageEntity(uri.getInputStream(context), uri.getFileExtension(context))
        val disposable = uploadFileUseCase.execute(BaseDisposableSingleSubscriber(uploadFileLiveData), params)
        compositeDisposable.add(disposable)
    }

    fun deleteProfile() {
        val disposable = deleteProfileUseCase.execute(Any())
            .subscribeWith(BaseDisposableCompletableSubscriber(deleteProfileLiveData))
        compositeDisposable.add(disposable)
    }

    sealed class ErrorField {

        class FirstName(val resource: Int) : ErrorField()

        class LastName(val resource: Int) : ErrorField()

        class Email(val resource: Int) : ErrorField()

        class Birthday(val resource: Int) : ErrorField()

    }

}