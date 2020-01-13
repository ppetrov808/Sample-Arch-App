package com.test.arch.ui.screens.main

import com.test.arch.domain.interactor.auth.AuthUserState
import com.test.arch.domain.interactor.auth.GetDeviceIdUseCase
import com.test.arch.domain.interactor.auth.LogoutUseCase
import com.test.arch.domain.interactor.profile.LoadProfileCacheUseCase
import com.test.arch.domain.interactor.profile.ProfileStateUseCase
import com.test.arch.domain.model.profile.UserProfileEntity
import com.test.arch.ui.Constants.PROFILE_ENTER_CODE
import com.test.arch.ui.base.BaseViewModel2
import com.test.arch.ui.base.mvvm.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel constructor(
    private val authUserState: AuthUserState,
    private val logoutUseCase: LogoutUseCase,
    private val loadProfileCacheUseCase: LoadProfileCacheUseCase,
    private val profileStateUseCase: ProfileStateUseCase
) : BaseViewModel2() {

    companion object {
        const val TAG = "MainActivityViewModel"
    }

    private var authUserStateDisposable = Disposables.empty()
    private var profileStateDisposable = Disposables.empty()

    private var isAuth: Boolean? = null

    //region livedata
    var routerLiveData = SingleLiveEvent<RouterNode>()
    var profileLiveData = SingleLiveEvent<UserProfileEntity>()

    override fun onCleared() {
        super.onCleared()
        if (!authUserStateDisposable.isDisposed) {
            authUserStateDisposable.dispose()
        }
        if (!profileStateDisposable.isDisposed) {
            profileStateDisposable.dispose()
        }
    }

    override fun onStart() {
        super.onStart()
        if (!authUserStateDisposable.isDisposed) {
            authUserStateDisposable.dispose()
        }
        authUserStateDisposable = authUserState.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<Boolean>() {
                override fun onNext(auth: Boolean) {
                    isAuth = auth
                    if (!auth) {
                        routerLiveData.value = RouterNode.Login(1)
                    }
                }

                override fun onError(e: Throwable) {
                    isAuth = false
                    routerLiveData.value = RouterNode.Login(0)
                }

                override fun onComplete() {}
            })
        if (!profileStateDisposable.isDisposed) {
            profileStateDisposable.dispose()
        }
        profileStateDisposable = profileStateUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<Boolean>() {
                override fun onNext(auth: Boolean) {
                    loadProfile()
                }

                override fun onError(e: Throwable) {}

                override fun onComplete() {}
            })
    }

    private fun loadProfile() {
        val disposable = loadProfileCacheUseCase.execute(object : DisposableMaybeObserver<UserProfileEntity>() {
            override fun onSuccess(userProfile: UserProfileEntity) {
                profileLiveData.postValue(userProfile)
            }

            override fun onError(e: Throwable) {
                profileLiveData.postValue(null)
            }

            override fun onComplete() {
                profileLiveData.postValue(null)
            }

        }, null)
        compositeDisposable.add(disposable)
    }


    fun onLogOutClicked() {
        compositeDisposable.add(
            logoutUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableCompletableObserver() {
                    override fun onComplete() {
                        routerLiveData.value = RouterNode.Login(1)
                    }

                    override fun onError(e: Throwable) {
                        routerLiveData.value = RouterNode.Login(0)
                    }
                })
        )
    }

    fun onProfileClicked() {
        if (routerLiveData.value == RouterNode.Profile) return
        isAuth?.let {
            routerLiveData.value = if (it) {
                RouterNode.Profile
            } else {
                RouterNode.Login(PROFILE_ENTER_CODE)
            }
        } ?: run {
            RouterNode.Login(PROFILE_ENTER_CODE)
        }
    }

    //region route
    sealed class RouterNode {

        object Profile : RouterNode()

        data class Login(val code: Int) : RouterNode()
    }

    //endregion
}