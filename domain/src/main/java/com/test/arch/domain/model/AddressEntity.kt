package com.test.arch.domain.model

import java.io.Serializable

data class AddressEntity(
    val city: String,
    val country: String,
    val street: String,
    val to: String,
    val zip: String
) : Serializable {

    companion object {
        @JvmStatic
        val emptyAddress: AddressEntity
            get () {
                return AddressEntity("", "", "", "", "")
            }

    }
}
