package com.test.arch.domain.exceptions

class ServerAccessException : RuntimeException(){
    override val message: String?
        get() = "No server access"
}