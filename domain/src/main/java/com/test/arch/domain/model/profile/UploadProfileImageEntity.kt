package com.test.arch.domain.model.profile

import java.io.InputStream

data class UploadProfileImageEntity(
    val inputStream: InputStream,
    val fileExtension: String
)