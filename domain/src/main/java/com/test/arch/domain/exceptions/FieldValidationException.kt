package com.test.arch.domain.exceptions

class FieldValidationException(val failureSet: Set<Enum<*>>) : Exception() {

    override val message: String?
        get() = toString()

    override fun toString(): String {
        return "FieldValidationException(failureSet=$failureSet)"
    }
}