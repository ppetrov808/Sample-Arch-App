package com.test.arch.data.stores.profile

import com.test.arch.domain.model.profile.UserProfileEntity
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable

interface ProfileCacheStore {

    fun loadProfileCache(): Maybe<UserProfileEntity>

    fun saveProfile(profile: UserProfileEntity): Completable

    fun clearProfile(): Completable

    fun profileState(): Observable<Boolean>
}