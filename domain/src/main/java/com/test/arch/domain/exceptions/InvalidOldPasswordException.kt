package com.test.arch.domain.exceptions

class InvalidOldPasswordException(message: String) : Exception(message) {

    companion object {
        const val STATUS_CODE = 1010
    }
}
