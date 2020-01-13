package com.test.arch.data.database.model

import androidx.room.ColumnInfo
import com.test.arch.domain.model.AddressEntity

data class AddressDbEntity(
    @ColumnInfo(name = "city")
    val city: String,
    @ColumnInfo(name = "country")
    val country: String,
    @ColumnInfo(name = "street")
    val street: String,
    @ColumnInfo(name = "to")
    val to: String?,
    @ColumnInfo(name = "zip")
    val zip: String
) {
    constructor(address: AddressEntity) : this(address.city, address.country, address.street, address.to, address.zip)
}