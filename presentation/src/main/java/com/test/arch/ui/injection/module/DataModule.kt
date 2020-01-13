package com.test.arch.ui.injection.module

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.test.arch.data.BuildConfig
import com.test.arch.data.database.TestDataBase
import com.test.arch.data.database.dao.ProfileDao
import com.test.arch.data.net.TokenManager
import com.test.arch.data.net.TokenManagerImpl
import com.test.arch.data.repositoryImpl.profile.ProfileRepositoryImpl
import com.test.arch.data.service.APIServiceFactory
import com.test.arch.data.service.auth.AuthRestService
import com.test.arch.data.service.profile.ProfileRestService
import com.test.arch.data.stores.auth.AuthDataStore
import com.test.arch.data.stores.auth.AuthDataStoreImpl
import com.test.arch.data.stores.profile.ProfileCacheStore
import com.test.arch.data.stores.profile.ProfileCacheStoreImpl
import com.test.arch.data.stores.profile.ProfileStore
import com.test.arch.data.stores.profile.ProfileStoreImpl
import com.test.arch.domain.DomainConstants
import com.test.arch.domain.DomainConstants.AUTHORIZED_HTTP_CLIENT
import com.test.arch.domain.DomainConstants.AUTHORIZED_INTERCEPTORS
import com.test.arch.domain.DomainConstants.UNAUTHORIZED_HTTP_CLIENT
import com.test.arch.domain.DomainConstants.UNAUTHORIZED_INTERCEPTORS
import com.test.arch.domain.repository.*
import com.test.arch.data.repositoryImpl.auth.AuthRepositoryImpl
import com.test.arch.data.stores.file.FileStore
import com.test.arch.data.stores.file.FileStoreImpl

import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.subjects.PublishSubject
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import javax.inject.Named
import javax.inject.Singleton

@Module
abstract class DataModule {

    @Module
    companion object {

        @Provides
        @JvmStatic
        @Singleton
        fun provideAuthRestService(@Named(UNAUTHORIZED_HTTP_CLIENT) httpClient: OkHttpClient, gson: Gson): AuthRestService {
            return APIServiceFactory.makeRestService(httpClient, gson)
        }

        @Provides
        @JvmStatic
        @Singleton
        fun provideProfileRestService(@Named(AUTHORIZED_HTTP_CLIENT) httpClient: OkHttpClient, gson: Gson): ProfileRestService {
            return APIServiceFactory.makeRestService(httpClient, gson)
        }

        @Provides
        @Singleton
        @JvmStatic
        fun provideGson(): Gson = APIServiceFactory.makeGson()

        @Provides
        @Singleton
        @JvmStatic
        @Named(UNAUTHORIZED_HTTP_CLIENT)
        fun provideUnauthorizedOkHttpClient(@Named(UNAUTHORIZED_INTERCEPTORS) interceptors: Array<Interceptor>): OkHttpClient {
            return APIServiceFactory.makeUnsafeOkHttpClientWith(interceptors)
        }

        @Provides
        @Singleton
        @JvmStatic
        @Named(AUTHORIZED_HTTP_CLIENT)
        fun provideAuthorizedOkHttpClient(@Named(AUTHORIZED_INTERCEPTORS) interceptors: Array<Interceptor>): OkHttpClient {
            return APIServiceFactory.makeUnsafeOkHttpClientWith(interceptors)
        }

        @Provides
        @Singleton
        @JvmStatic
        @Named(UNAUTHORIZED_INTERCEPTORS)
        fun provideUnauthorizedInterceptors(): Array<Interceptor> {
            val loggingInterceptor = APIServiceFactory.makeLoggingInterceptor(BuildConfig.DEBUG)
            val makeConnectionNetworkInterceptor = APIServiceFactory.makeNetworkConnectionInterceptor()
            return arrayOf(loggingInterceptor, makeConnectionNetworkInterceptor)
        }

        @Provides
        @Singleton
        @JvmStatic
        @Named(AUTHORIZED_INTERCEPTORS)
        fun provideAuthorizedInterceptors(
            tokenManager: TokenManager, @Named(DomainConstants.UNAUTHORIZED) unauthorizedSubject: PublishSubject<Boolean>
        ): Array<Interceptor> {
            val loggingInterceptor = APIServiceFactory.makeLoggingInterceptor(BuildConfig.DEBUG)
            val makeTokenInterceptor = APIServiceFactory.makeTokenInterceptor(tokenManager, unauthorizedSubject)
            val makeConnectionNetworkInterceptor = APIServiceFactory.makeNetworkConnectionInterceptor()
            return arrayOf(loggingInterceptor, makeConnectionNetworkInterceptor, makeTokenInterceptor)
        }

        @Provides
        @Singleton
        @JvmStatic
        @Named(DomainConstants.UNAUTHORIZED)
        fun provideUnauthorizedSubject(): PublishSubject<Boolean> {
            return PublishSubject.create()
        }

        @Provides
        @JvmStatic
        @Singleton
        fun provideDataBase(context: Context): TestDataBase = TestDataBase.getInstance(context)

        @Provides
        @JvmStatic
        @Singleton
        fun provideProfileDao(dataBase: TestDataBase): ProfileDao = dataBase.profileDao()

        @Provides
        @JvmStatic
        @Singleton
        fun provideSharedPrefs(context: Context): SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindProfileRepository(repository: ProfileRepositoryImpl): ProfileRepository

    @Binds
    @Singleton
    abstract fun bindAuthDataStore(authDataStoreImpl: AuthDataStoreImpl): AuthDataStore

    @Binds
    @Singleton
    abstract fun bindFileStore(store: FileStoreImpl): FileStore

    @Binds
    @Singleton
    abstract fun bindTokenManager(tokenManager: TokenManagerImpl): TokenManager

    @Binds
    @Singleton
    abstract fun bindProfileStore(store: ProfileStoreImpl): ProfileStore

    @Binds
    @Singleton
    abstract fun bindProfileCacheStore(store: ProfileCacheStoreImpl): ProfileCacheStore
}