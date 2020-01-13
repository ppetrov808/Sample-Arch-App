package com.test.arch.domain.exceptions

class UserAlreadyExistsException(message: String) : Exception(message) {

    companion object {
        const val STATUS_CODE = 1002
    }

    override val message: String?
        get() = "Deine angegebene E-Mail existiert bereits in unserem System. Bitte melde dich an."
}