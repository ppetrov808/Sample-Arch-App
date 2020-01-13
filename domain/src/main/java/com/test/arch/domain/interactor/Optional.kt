package com.test.arch.domain.interactor

class Optional<M>(private val optional: M?) {

    val empty: Boolean
        get() = this.optional == null

    fun get(): M = optional ?: throw NoSuchElementException("No value present")
}
 
 