package com.test.arch.domain.exceptions

import java.lang.Exception

class NoSuchTokenException(message: String) : Exception(message) {

    companion object {
        const val STATUS_CODE = 1003
    }
}
