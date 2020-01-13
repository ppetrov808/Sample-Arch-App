package com.test.arch.data.stores.profile

import com.test.arch.domain.model.profile.ChangePasswordEntity
import com.test.arch.domain.model.profile.UpdateProfileEntity
import com.test.arch.domain.model.profile.UserProfileEntity
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File

interface ProfileStore {

    fun updateProfile(request: UpdateProfileEntity): Single<UserProfileEntity>

    fun uploadFile(file: File): Single<String>

    fun changePassword(request: ChangePasswordEntity): Completable

    fun resetPassword(email: String): Completable

    fun loadProfile(): Single<UserProfileEntity>

    fun sendCodeForDeletingProfile(): Completable

    fun createDevice(token: String): Completable

    fun getAdvertisingId(): Single<String>
}