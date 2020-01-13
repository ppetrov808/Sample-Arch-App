package com.test.arch.ui.injection.module

import com.test.arch.domain.executor.JobExecutor
import com.test.arch.domain.executor.ThreadExecutor
import dagger.Binds
import dagger.Module

@Module
abstract class DomainModule {

    @Binds
    abstract fun bindThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor

}