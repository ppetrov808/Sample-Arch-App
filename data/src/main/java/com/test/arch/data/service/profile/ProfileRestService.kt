package com.test.arch.data.service.profile

import com.test.arch.data.model.*
import com.test.arch.data.model.auth.CreateDeviceRequest
import com.test.arch.data.model.profile.ChangePasswordRequest
import com.test.arch.data.model.profile.ResetPasswordRequest
import com.test.arch.data.model.profile.UpdateProfileRequest
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface ProfileRestService {

    @PUT(PROFILE_UPDATE)
    fun updateProfile(@Body request: UpdateProfileRequest): Single<AccountResponse>

    @GET(PROFILE_UPDATE)
    fun loadProfile(): Single<AccountResponse>

    @Multipart
    @POST(PROFILE_MEDIA)
    fun uploadFile(@Part multipart: MultipartBody.Part): Single<ResponseBody>

    @PUT(PROFILE_PASSWORD)
    fun changePassword(@Body request: ChangePasswordRequest): Completable

    @POST(PROFILE_PASSWORD_RESET)
    fun resetPassword(@Body request: ResetPasswordRequest): Completable

    @POST(PROFILE_CODE)
    fun sendCodeForDeletingProfile(): Completable

    @POST(DEVICES)
    fun createDevice(@Body signUpCredentials: CreateDeviceRequest): Completable
}