package com.test.arch.domain.exceptions

class IncorrectCredentialsException(var type: Type) : RuntimeException() {
    override val message: String?
        get() = "Incorrect login or password"


    enum class Type {

        LOGIN_INCORRECT, PASSWORD_INCORRECT

    }

}