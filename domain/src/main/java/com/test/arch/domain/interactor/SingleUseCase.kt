package com.test.arch.domain.interactor

import com.test.arch.domain.executor.PostExecutionThread
import com.test.arch.domain.executor.ThreadExecutor
import io.reactivex.Single
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

abstract class SingleUseCase<T, in Params> constructor(
    protected val threadExecutor: ThreadExecutor,
    protected val postExecutionThread: PostExecutionThread
) {

    protected abstract fun buildUseCaseObservable(params: Params): Single<T>

    open fun execute(observer: DisposableSingleObserver<T>, params: Params): DisposableSingleObserver<T> {
        return buildUseCaseObservable(params)
            .subscribeOn(Schedulers.from(threadExecutor))
            .observeOn(postExecutionThread.scheduler)
            .subscribeWith(observer)
    }
}