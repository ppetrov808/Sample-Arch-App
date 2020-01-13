package com.test.arch.domain.exceptions

class NoAccountException(message: String) : Exception(message) {

    companion object {
        const val STATUS_CODE = 1001
    }
}