package com.test.arch.data.model

import com.google.gson.annotations.SerializedName
import com.test.arch.domain.model.StatusEntity

enum class StatusResponse {

    @SerializedName("ACTIVE")
    ACTIVE,
    @SerializedName("PAYMENT_PENDING")
    PAYMENT_PENDING,
    @SerializedName("PAYMENT_FAILED")
    PAYMENT_FAILED,
    @SerializedName("EXPIRED_SOON_GIFT")
    EXPIRED_SOON_GIFT,
    @SerializedName("EXPIRED_SOON")
    EXPIRED_SOON,
    @SerializedName("EXPIRED")
    EXPIRED,
    @SerializedName("LOST")
    LOST,
    @SerializedName("NEW_ORDER_PAYMENT")
    NEW_ORDER_PAYMENT;


    fun toEntity() = when (this) {
        ACTIVE -> StatusEntity.ACTIVE
        PAYMENT_PENDING -> StatusEntity.PAYMENT_PENDING
        PAYMENT_FAILED -> StatusEntity.PAYMENT_FAILED
        EXPIRED_SOON_GIFT -> StatusEntity.EXPIRED_SOON_GIFT
        EXPIRED_SOON -> StatusEntity.EXPIRED_SOON
        EXPIRED -> StatusEntity.EXPIRED
        LOST -> StatusEntity.LOST
        //TODO check after
        NEW_ORDER_PAYMENT -> StatusEntity.ACTIVE
    }
}
