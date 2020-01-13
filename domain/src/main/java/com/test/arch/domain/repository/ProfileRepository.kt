package com.test.arch.domain.repository

import com.test.arch.domain.model.profile.ChangePasswordEntity
import com.test.arch.domain.model.profile.UpdateProfileEntity
import com.test.arch.domain.model.profile.UploadProfileImageEntity
import com.test.arch.domain.model.profile.UserProfileEntity
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

interface ProfileRepository {

    fun loadProfileCache(): Maybe<UserProfileEntity>

    fun saveProfile(profile: UserProfileEntity): Completable

    fun updateProfile(request: UpdateProfileEntity): Single<UserProfileEntity>

    fun uploadFile(request: UploadProfileImageEntity): Single<String>

    fun changePassword(request: ChangePasswordEntity): Completable

    fun resetPassword(email: String): Completable

    fun profileState(): Observable<Boolean>

    fun clearProfile(): Completable

    fun loadProfileCloud(): Single<UserProfileEntity>

    fun sendCodeForDeletingProfile(): Completable

    fun createDevice(token: String): Completable

    fun getAdvertisingId(): Single<String>

}