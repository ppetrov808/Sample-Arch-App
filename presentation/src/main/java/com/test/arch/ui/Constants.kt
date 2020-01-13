package com.test.arch.ui

object Constants {

    const val DEF_OBJECT_ANCHOR_V = 0.5F

    const val DEF_SELECTED_OBJECT_ANCHOR_V1 = 0.75F
    const val DEF_SELECTED_OBJECT_ANCHOR_V2 = 1F

    const val ACTIVITY_ENTER_CODE = 3001
    const val FAVOURITES_ENTER_CODE = 3002
    const val MY_CARD_ENTER_CODE = 3003
    const val GIFT_CARD_ENTER_CODE = 3004
    const val PROFILE_ENTER_CODE = 3005
    const val RESTAURANT_DETAILS_WRITE_REVIEW_CODE = 3006
    const val AVATAR_IMAGE_FROM_GALLERY_REQUEST_CODE = 3007
    const val CUISINE_TYPE_CODE = 3008
    const val FOOD_TYPE_CODE = 3009
    const val GOOGLE_PAY_REQUEST_CODE = 3010
    const val MY_CARD_RETURN_CODE = 3011
    const val BUY_CARD_RETURN_CODE = 4001
    const val ADD_GIFT_CARD_ENTER_CODE = 4002

    const val DEFAULT_CAMERA_SCALE = 15F
    const val DEFAULT_RADIUS = 150.0
    const val METERS_IN_KM = 1000.0
    const val STROKE_WIDTH = 1F
    const val BASE_ANCHOR = 45F
    const val MIN_AGE = 16
    const val MAX_AGE = 100

    const val MEDIA_TYPE_IMAGE = "image/*"

    const val TIME_FORMAT = "HH:mm"
    const val DEFAULT_START_TIME = "14:00"
    const val DEFAULT_END_TIME = "22:00"

    const val RESTAURANT_VIEW_TYPE_ACTIVITY = "RESTAURANT_VIEW"
    const val FILTER_TYPE_ACTIVITY = "FILTER"

    const val EXTRA_IS_USER_PRESENT = "EXTRA_IS_USER_PRESENT"
    const val EXTRA_IS_GIFT_CARD = "EXTRA_IS_GIFT_CARD"
    const val EXTRA_PARENT_ID = "EXTRA_PARENT_ID"

    const val EXTRA_CARD_ORDER_BUNDLE = "EXTRA_CARD_ORDER_BUNDLE"
    const val EXTRA_CHECKOUT_INFO = "EXTRA_CHECKOUT_INFO"
    const val EXTRA_TARIFF_INFO = "EXTRA_TARIFF_INFO"

    const val EXTRA_LOAD_PRELAUNCH = "EXTRA_LOAD_PRELAUNCH"
    const val EXTRA_DAOPAY_DATA = "EXTRA_DAOPAY_DATA"
    const val EXTRA_DAOPAY_TRANSACTION_ID = "EXTRA_DAOPAY_TRANSACTION_ID"
    const val EXTRA_ADD_GIFT_CARD = "EXTRA_ADD_GIFT_CARD"

    const val MAX_RADIUS = 20.0
    const val M_TO_KM = 1000.0

    const val DISCOUNT_DATE_FMT = "dd.MM.yyyy HH:mm"
    // "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}\$"
    const val PASSWORD_VALIDATION = "^(?=.*[A-Za-z])[A-Za-z\\d\\@\\?\\$*!#%^&+=_]{8,}\$"
    const val PASSWORD_VALIDATION_SIGN_UP = "^(?=.*\\d)(?=.*[a-zA-Z]).{8,}\$"
    const val SIGN_UP_FLAG = "SIGN_UP_FLAG"

    const val VISA = "Visa"
    const val MASTERCARD = "MasterCard"

    const val DAOPAY = "DAOPAY"
    const val STRIPE = "STRIPE"

    const val EN = "en"
    const val GER = "ger"

    const val CUISINE = "CUISINE"
    const val FOOD = "FOOD"

    const val STAGE_SERVER = "stage"

    const val ANNUAL_EUR_PLAN = "ANNUAL_EUR"

    const val DAOPAY_TRANSACTION_PAR = "wbtransactionid"
}