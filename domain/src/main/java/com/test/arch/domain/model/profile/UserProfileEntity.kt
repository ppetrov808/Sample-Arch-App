package com.test.arch.domain.model.profile

import com.test.arch.domain.model.AddressEntity
import com.test.arch.domain.model.SexEntity
import com.test.arch.domain.model.StatusEntity
import java.io.Serializable

data class UserProfileEntity(
    val address: AddressEntity?,
    val birthday: String?,
    val email: String,
    val firstName: String,
    val id: Long,
    val imgUrl: String,
    val lastName: String,
    val language: String?,
    val roles: List<String>,
    val sex: SexEntity?,
    val status: StatusEntity?
) : Serializable