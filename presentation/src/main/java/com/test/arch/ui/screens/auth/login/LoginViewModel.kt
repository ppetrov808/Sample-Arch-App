package com.test.arch.ui.screens.auth.login

import android.text.TextUtils
import com.test.arch.domain.exceptions.IncorrectCredentialsException
import com.test.arch.domain.interactor.auth.AuthUseCase
import com.test.arch.domain.interactor.profile.DownloadProfileUseCase
import com.test.arch.domain.model.auth.Credentials
import com.test.arch.ui.Constants
import com.test.arch.ui.R
import com.test.arch.ui.base.BaseViewModel2
import com.test.arch.ui.base.mvvm.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val downloadProfileUseCase: DownloadProfileUseCase
) : BaseViewModel2() {

    companion object {
        const val TAG = "LoginViewModel"
    }

    //region livedata

    val routeLiveData = SingleLiveEvent<RouterNode>()
    val errorField = SingleLiveEvent<ErrorField>()
    val errorMessage = SingleLiveEvent<String>()
    val errorMessageResource = SingleLiveEvent<Int>()
    val loadingStateLiveData = SingleLiveEvent<Boolean>()

    //endregion

    //region own methods

    fun onLoginClicked(credentials: Credentials) {
        if (credentials.login.isEmpty()) {
            errorField.value = ErrorField.Login(R.string.empty_email)
            return
        }
        if (!isValidEmail(credentials.login)) {
            errorField.value = ErrorField.Login(R.string.invalid_email)
            return
        }
        if (credentials.password.isEmpty()) {
            errorField.value = ErrorField.Password(R.string.empty_password)
            return
        }
        if (!Constants.PASSWORD_VALIDATION.toRegex().matches(credentials.password)) {
            errorField.value = ErrorField.Password(R.string.invalid_password_message)
            errorMessageResource.value = R.string.invalid_password_message
            return
        }
        compositeDisposable.add(
            authUseCase.execute(credentials)
                .doOnSubscribe { loadingStateLiveData.postValue(true) }
                .flatMapCompletable { downloadProfileUseCase.execute() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableCompletableObserver() {
                    override fun onComplete() {
                        loadingStateLiveData.value = false
                        routeLiveData.value = RouterNode.Main
                    }

                    override fun onError(e: Throwable) {
                        loadingStateLiveData.value = false
                        if (e is IncorrectCredentialsException) {
                            if (e.type == IncorrectCredentialsException.Type.LOGIN_INCORRECT) {
                                errorField.value = ErrorField.Login(R.string.invalid_email)
                            } else {
                                errorField.value = ErrorField.Password(R.string.wrong_password)
                            }
                        } else {
                            errorMessage.value = e.message
                        }
                    }
                })
        )
    }

    fun onSignUpClicked() {
        routeLiveData.value = RouterNode.SignUp
    }

    fun onRecoveryPasswordClicked() {
        routeLiveData.value = RouterNode.RecoveryPassword
    }

    private fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    //endregion

    //region route

    sealed class RouterNode {

        object Main : RouterNode()

        object SignUp : RouterNode()

        object RecoveryPassword : RouterNode()

    }

    //endregion

    //region error type

    sealed class ErrorField {

        class Login(val resource: Int) : ErrorField()

        class Password(val resource: Int) : ErrorField()

    }

    //endregion

}
 
 