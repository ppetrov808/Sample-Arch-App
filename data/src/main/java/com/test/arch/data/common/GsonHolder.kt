package com.test.arch.data.common

import com.google.gson.GsonBuilder
import com.test.arch.domain.DomainConstants.GSON_DATE_FORMAT

class GsonHolder private constructor() {

    companion object {
        val GSON = GsonBuilder().setDateFormat(GSON_DATE_FORMAT).create()
    }
}