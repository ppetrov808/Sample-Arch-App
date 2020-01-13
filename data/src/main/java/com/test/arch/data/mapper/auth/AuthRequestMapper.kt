package com.test.arch.data.mapper.auth

import com.test.arch.data.mapper.RequestMapper
import com.test.arch.data.model.auth.AuthorizeRequest
import com.test.arch.domain.model.auth.Credentials
import javax.inject.Inject


open class AuthRequestMapper @Inject constructor() :
    RequestMapper<Credentials, AuthorizeRequest> {

    override fun mapFromData(type: Credentials): AuthorizeRequest = AuthorizeRequest(type.login, type.password)

}
 
 