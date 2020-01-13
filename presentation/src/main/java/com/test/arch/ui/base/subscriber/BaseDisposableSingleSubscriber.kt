package com.test.arch.ui.base.subscriber

import androidx.lifecycle.MutableLiveData
import com.test.arch.ui.data.Resource
import com.test.arch.ui.data.ResourceState
import io.reactivex.observers.DisposableSingleObserver

open class BaseDisposableSingleSubscriber<T>(private val liveData: MutableLiveData<Resource<T>>) :
    DisposableSingleObserver<T>() {

    override fun onStart() {
        liveData.postValue(Resource(ResourceState.LOADING, null, null))
    }

    override fun onSuccess(t: T) {
        liveData.postValue(Resource(ResourceState.SUCCESS, t, null))
    }

    override fun onError(exception: Throwable) {
        liveData.postValue(Resource(ResourceState.ERROR, null, exception))
    }
}