package com.test.arch.ui.base.subscriber

import androidx.lifecycle.MutableLiveData
import com.test.arch.ui.data.Resource
import com.test.arch.ui.data.ResourceState
import io.reactivex.subscribers.DisposableSubscriber

open class BaseDisposableSubscriber<T>(private val liveData: MutableLiveData<Resource<T>>)
    : DisposableSubscriber<T>() {

    override fun onComplete() { }

    override fun onNext(t: T) {
        liveData.postValue(Resource(ResourceState.SUCCESS, t, null))
    }

    override fun onError(exception: Throwable) {
        liveData.postValue(Resource(ResourceState.ERROR, null, exception))
    }
}
 
 