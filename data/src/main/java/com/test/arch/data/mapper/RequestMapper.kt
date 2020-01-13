package com.test.arch.data.mapper

interface RequestMapper<in M, out E> {

    fun mapFromData(type: M): E

}
 
 