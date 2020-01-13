package com.test.arch.ui.base.subscriber

import androidx.lifecycle.MutableLiveData
import com.test.arch.ui.data.Resource
import com.test.arch.ui.data.ResourceState
import io.reactivex.observers.DisposableCompletableObserver

open class BaseDisposableCompletableSubscriber<T>(private val liveData: MutableLiveData<Resource<T>>) :
    DisposableCompletableObserver() {

    override fun onStart() {
        liveData.postValue(Resource(ResourceState.LOADING, null, null))
    }

    override fun onComplete() {
        liveData.postValue(Resource(ResourceState.SUCCESS, null, null))
    }

    override fun onError(exception: Throwable) {
        liveData.postValue(Resource(ResourceState.ERROR, null, exception))
    }
}