package com.test.arch.data.model.profile

import com.google.gson.annotations.SerializedName
import com.test.arch.data.model.AddressResponse
import com.test.arch.data.model.SexResponse
import com.test.arch.domain.model.AddressEntity
import com.test.arch.domain.model.profile.UpdateProfileEntity

data class UpdateProfileRequest(
    @SerializedName("address")
    val address: AddressResponse?,
    @SerializedName("birthday")
    val birthday: String?,
    @SerializedName("email")
    val email: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("imgUrl")
    val imgUrl: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("language")
    val language: String?,
    @SerializedName("sex")
    val sex: SexResponse?
) {
    constructor(entity: UpdateProfileEntity) : this(
        getAddressResponse(entity.address),
        entity.birthday,
        entity.email,
        entity.firstName,
        entity.imgUrl,
        entity.lastName,
        entity.language,
        SexResponse.fromEntity(entity.sex)
    )

    companion object {

        private fun getAddressResponse(address: AddressEntity): AddressResponse? {
            return if (address.city.isEmpty() && address.country.isEmpty() && address.street.isEmpty() && address.to.isEmpty() && address.zip.isEmpty()) {
                null
            } else {
                val city = if (address.city.isEmpty()) null else address.city
                val country = if (address.country.isEmpty()) null else address.country
                val street = if (address.street.isEmpty()) null else address.street
                val to = if (address.to.isEmpty()) null else address.to
                val zip = if (address.zip.isEmpty()) null else address.zip
                AddressResponse(city, country, street, to, zip)
            }
        }
    }
}