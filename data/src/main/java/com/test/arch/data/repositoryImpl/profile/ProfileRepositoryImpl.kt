package com.test.arch.data.repositoryImpl.profile

import com.test.arch.data.common.GsonHolder
import com.test.arch.data.model.ErrorBody
import com.test.arch.data.stores.file.FileStore
import com.test.arch.data.stores.profile.ProfileCacheStore
import com.test.arch.data.stores.profile.ProfileStore
import com.test.arch.domain.DomainConstants
import com.test.arch.domain.DomainConstants.ERROR_CODE_400
import com.test.arch.domain.exceptions.InvalidOldPasswordException
import com.test.arch.domain.exceptions.InvalidPasswordException
import com.test.arch.domain.exceptions.NoAccountException
import com.test.arch.domain.model.profile.ChangePasswordEntity
import com.test.arch.domain.model.profile.UpdateProfileEntity
import com.test.arch.domain.model.profile.UploadProfileImageEntity
import com.test.arch.domain.model.profile.UserProfileEntity
import com.test.arch.domain.repository.ProfileRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.HttpException
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileStore: ProfileStore,
    private val profileCacheStore: ProfileCacheStore,
    private val fileStore: FileStore
) : ProfileRepository {

    override fun loadProfileCache() = profileCacheStore.loadProfileCache()

    override fun loadProfileCloud() = profileStore.loadProfile()

    override fun saveProfile(profile: UserProfileEntity) = profileCacheStore.saveProfile(profile)

    override fun updateProfile(request: UpdateProfileEntity) = profileStore.updateProfile(request)

    override fun uploadFile(request: UploadProfileImageEntity): Single<String> {
        return fileStore.getTempImageFile(request.fileExtension)
            .flatMap { fileStore.copyInputStreamToFile(it, request.inputStream) }
            .flatMap { profileStore.uploadFile(it) }
    }

    override fun changePassword(request: ChangePasswordEntity) = profileStore.changePassword(request)
        .onErrorResumeNext {
            if (it is HttpException) {
                if (it.code() == ERROR_CODE_400) {
                    val errorMessage = it.response().errorBody()?.string() ?: ""
                    val errorBody = GsonHolder.GSON.fromJson(errorMessage, ErrorBody::class.java)
                    if (InvalidOldPasswordException.STATUS_CODE == errorBody.statusCode) {
                        return@onErrorResumeNext Completable.error(InvalidOldPasswordException(it.message()))
                    } else if (errorMessage.contains(DomainConstants.INVALID_PASSWORD_ERROR_MATCHER)) {
                        return@onErrorResumeNext Completable.error(InvalidPasswordException(it.message()))
                    }
                }
            }
            return@onErrorResumeNext Completable.error(it)
        }

    override fun resetPassword(email: String): Completable {
        return profileStore.resetPassword(email)
            .onErrorResumeNext {
                if (it is HttpException) {
                    if (it.code() == ERROR_CODE_400) {
                        val errorMessage = it.response().errorBody()?.string()
                        val errorBody = GsonHolder.GSON.fromJson(errorMessage, ErrorBody::class.java)
                        if (NoAccountException.STATUS_CODE == errorBody.statusCode) {
                            return@onErrorResumeNext Completable.error(NoAccountException(it.message()))
                        }
                    }
                }
                return@onErrorResumeNext Completable.error(it)
            }
    }

    override fun profileState(): Observable<Boolean> = profileCacheStore.profileState()

    override fun clearProfile(): Completable = profileCacheStore.clearProfile()

    override fun sendCodeForDeletingProfile(): Completable = profileStore.sendCodeForDeletingProfile()

    override fun createDevice(token: String) = profileStore.createDevice(token)

    override fun getAdvertisingId() = profileStore.getAdvertisingId()
}