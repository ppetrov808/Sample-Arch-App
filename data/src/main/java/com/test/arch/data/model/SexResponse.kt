package com.test.arch.data.model

import com.google.gson.annotations.SerializedName
import com.test.arch.domain.model.SexEntity

enum class SexResponse {
    @SerializedName("MALE")
    MALE,
    @SerializedName("FEMALE")
    FEMALE,
    @SerializedName("OTHER")
    OTHER;

    fun toEntity() = when (this) {
        MALE -> SexEntity.MALE
        FEMALE -> SexEntity.FEMALE
        OTHER -> SexEntity.OTHER
    }

    companion object {

        fun fromEntity(entity: SexEntity?) = when (entity) {
            SexEntity.MALE -> MALE
            SexEntity.FEMALE -> FEMALE
            SexEntity.OTHER -> OTHER
            else -> null
        }
    }
}