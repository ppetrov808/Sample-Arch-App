package com.test.arch.data.model.profile

import com.google.gson.annotations.SerializedName
import com.test.arch.domain.model.profile.ChangePasswordEntity

data class ChangePasswordRequest(
    @SerializedName("oldPassword")
    val oldPassword: String,
    @SerializedName("newPassword")
    val newPassword: String
) {
    constructor(entity: ChangePasswordEntity) : this(
        entity.oldPassword, entity.newPassword
    )
}