package com.test.arch.domain.exceptions

class ServerErrorException : RuntimeException() {
    override val message: String?
        get() = "Server error. Try again after a while"
}