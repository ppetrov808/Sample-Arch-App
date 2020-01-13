package com.test.arch.data.model

import com.google.gson.annotations.SerializedName
import com.test.arch.domain.model.profile.UserProfileEntity

data class AccountResponse(
    @SerializedName("address")
    val address: AddressResponse?,
    @SerializedName("birthday")
    val birthday: String?,
    @SerializedName("email")
    val email: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("id")
    val id: Long,
    @SerializedName("imgUrl")
    val imgUrl: String?,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("language")
    val language: String?,
    @SerializedName("roles")
    val roles: List<String>,
    @SerializedName("sex")
    val sex: SexResponse?,
    @SerializedName("status")
    val status: StatusResponse?
) {
    fun toEntity() = UserProfileEntity(
        address?.toEntity(),
        birthday,
        email,
        firstName,
        id,
        imgUrl ?: "",
        lastName,
        language,
        roles,
        sex?.toEntity(),
        status?.toEntity()
    )
}