package com.test.arch.domain.exceptions

import java.lang.Exception

class HttpException(val statusCode: Int, message: String) : Exception(message)