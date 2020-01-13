@file:Suppress("UNUSED_PARAMETER")

package com.test.arch.ui.data

open class Resource<out T> constructor(val status: ResourceState, val data: T? = null, val error: Throwable? = null) {

    fun <T> success(data: T): Resource<T> {
        return Resource(ResourceState.SUCCESS, data)
    }

    fun <T> error(error: Throwable, data: T?): Resource<T> {
        return Resource(ResourceState.ERROR, error = error)
    }

    fun <T> loading(): Resource<T> {
        return Resource(ResourceState.LOADING)
    }

    override fun toString(): String {
        return "Resource(status=$status, data=${data.toString()}, error=${error.toString()})"
    }
}
 