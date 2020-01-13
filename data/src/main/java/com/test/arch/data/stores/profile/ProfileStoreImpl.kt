package com.test.arch.data.stores.profile

import android.content.Context
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.test.arch.data.model.auth.CreateDeviceRequest
import com.test.arch.data.model.profile.ChangePasswordRequest
import com.test.arch.data.model.profile.ResetPasswordRequest
import com.test.arch.data.model.profile.UpdateProfileRequest
import com.test.arch.data.service.profile.ProfileRestService
import com.test.arch.data.utils.MockDataTools
import com.test.arch.domain.model.AddressEntity
import com.test.arch.domain.model.SexEntity
import com.test.arch.domain.model.StatusEntity
import com.test.arch.domain.model.profile.ChangePasswordEntity
import com.test.arch.domain.model.profile.UpdateProfileEntity
import com.test.arch.domain.model.profile.UserProfileEntity
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class ProfileStoreImpl @Inject constructor(private val service: ProfileRestService, private val context: Context) : ProfileStore {

    companion object {
        const val UPLOAD_FILE_NAME = "file"
        const val MULTIPART_FORM_DATA = "multipart/form-data"
    }

    override fun updateProfile(request: UpdateProfileEntity): Single<UserProfileEntity> {
        return service.updateProfile(UpdateProfileRequest(request)).map { it.toEntity() }
    }

    override fun uploadFile(file: File) = Single.just(file)
        .map { RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file) }
        .map { MultipartBody.Part.createFormData(UPLOAD_FILE_NAME, file.name, it) }
        .flatMap { service.uploadFile(it) }
        .map { it.string() }

    override fun changePassword(request: ChangePasswordEntity): Completable {
        return Single.just(request)
            .map { ChangePasswordRequest(request.oldPassword, request.newPassword) }
            .flatMapCompletable { service.changePassword(it) }

    }

    override fun resetPassword(email: String) = service.resetPassword(ResetPasswordRequest(email))

    override fun loadProfile(): Single<UserProfileEntity> = MockDataTools.mockProfile() //service.loadProfile().map { it.toEntity() }

    override fun sendCodeForDeletingProfile(): Completable = service.sendCodeForDeletingProfile()

    override fun createDevice(token: String) = service.createDevice(CreateDeviceRequest(token))

    override fun getAdvertisingId(): Single<String> {
        return Single.fromCallable{ return@fromCallable getAdvId()}
    }

    private fun getAdvId(): String {
        var idInfo: AdvertisingIdClient.Info? = null
        try {
            idInfo = AdvertisingIdClient.getAdvertisingIdInfo(context)
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace()
        } catch (e: GooglePlayServicesRepairableException) {
            e.printStackTrace()
        }
        var advertId: String? = null
        try {
            advertId = idInfo!!.id
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

        return advertId ?: "unknown"
    }

}