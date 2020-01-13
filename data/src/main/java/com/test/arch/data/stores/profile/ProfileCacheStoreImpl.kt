package com.test.arch.data.stores.profile

import com.test.arch.data.database.dao.ProfileDao
import com.test.arch.data.database.model.UserProfileDbEntity
import com.test.arch.domain.model.profile.UserProfileEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class ProfileCacheStoreImpl @Inject constructor(private val profileDao: ProfileDao) : ProfileCacheStore {

    private val profileState = BehaviorSubject.createDefault<Boolean>(false)

    override fun loadProfileCache() = profileDao.getProfile().map { it.toEntity() }

    override fun saveProfile(profile: UserProfileEntity): Completable {
        return Completable.fromRunnable {
            profileDao.insertOrUpdate(UserProfileDbEntity(profile))
            profileState.onNext(true)
        }
    }

    override fun clearProfile(): Completable {
        return Completable.fromAction {
            profileDao.clearProfile()
            profileState.onNext(false)
        }
    }

    override fun profileState(): Observable<Boolean> = profileState
}