package com.test.arch.ui.base.subscriber

import androidx.lifecycle.MutableLiveData
import com.test.arch.ui.data.Resource
import com.test.arch.ui.data.ResourceState
import io.reactivex.observers.DisposableMaybeObserver

open class BaseDisposableMaybeSubscriber<T>(private val liveData: MutableLiveData<Resource<T>>) :
    DisposableMaybeObserver<T>() {

    override fun onStart() {
        liveData.postValue(Resource(ResourceState.LOADING, null, null))
    }

    override fun onSuccess(t: T) {
        liveData.postValue(Resource(ResourceState.SUCCESS, t, null))
    }

    override fun onComplete() {
        liveData.postValue(Resource(ResourceState.SUCCESS, null, null))
    }

    override fun onError(exception: Throwable) {
        liveData.postValue(Resource(ResourceState.ERROR, null, exception))
    }
}