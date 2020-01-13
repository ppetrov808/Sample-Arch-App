package com.test.arch.data.mapper

interface Mapper<in M, out E> {

    fun mapFromRemote(type: M): E
}
 
 