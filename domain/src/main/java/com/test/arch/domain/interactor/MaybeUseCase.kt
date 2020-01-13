package com.test.arch.domain.interactor

import com.test.arch.domain.executor.PostExecutionThread
import com.test.arch.domain.executor.ThreadExecutor
import io.reactivex.Maybe
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.schedulers.Schedulers

abstract class MaybeUseCase<T, in Params> constructor(
    private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread
) {

    protected abstract fun buildUseCaseObservable(params: Params): Maybe<T>

    open fun execute(observer: DisposableMaybeObserver<T>, params: Params): DisposableMaybeObserver<T> {
        return this.buildUseCaseObservable(params)
            .subscribeOn(Schedulers.from(threadExecutor))
            .observeOn(postExecutionThread.scheduler)
            .subscribeWith(observer)
    }
}