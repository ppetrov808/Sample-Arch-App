package com.test.arch.domain.model.profile

import com.test.arch.domain.model.AddressEntity
import com.test.arch.domain.model.SexEntity

data class UpdateProfileEntity(
    val address: AddressEntity,
    val birthday: String?,
    val email: String,
    val firstName: String,
    val imgUrl: String,
    val lastName: String,
    val sex: SexEntity?,
    val language: String?

)