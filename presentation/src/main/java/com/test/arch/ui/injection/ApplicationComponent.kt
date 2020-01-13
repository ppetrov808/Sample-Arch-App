package com.test.arch.ui.injection

import android.app.Application
import com.test.arch.domain.DomainConstants.UNAUTHORIZED
import com.test.arch.domain.interactor.auth.LogoutUseCase
import com.test.arch.ui.App
import com.test.arch.ui.injection.module.ApplicationModule
import com.test.arch.ui.injection.module.DataModule
import com.test.arch.ui.injection.module.DomainModule
import com.test.arch.ui.injection.module.PresentationModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import io.reactivex.subjects.PublishSubject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        AndroidSupportInjectionModule::class,
        DataModule::class,
        DomainModule::class,
        PresentationModule::class]
)
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(app: App)

    @Named(UNAUTHORIZED)
    fun getUnauthorizedTokenError(): PublishSubject<Boolean>

    fun getLogOutUseCase(): LogoutUseCase

}
 
 