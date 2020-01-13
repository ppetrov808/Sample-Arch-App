package com.test.arch.data.model

import com.google.gson.annotations.SerializedName
import com.test.arch.domain.model.AddressEntity

data class AddressResponse(
    @SerializedName("city")
    val city: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("street")
    val street: String?,
    @SerializedName("to")
    val to: String?,
    @SerializedName("zip")
    val zip: String?
) {

    constructor(entity: AddressEntity) : this(entity.city, entity.country, entity.street, entity.to, entity.zip)

    fun toEntity() = AddressEntity(city ?: "", country ?: "", street ?: "", to ?: "", zip ?: "")
}