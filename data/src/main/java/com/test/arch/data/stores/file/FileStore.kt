package com.test.arch.data.stores.file

import io.reactivex.Single
import java.io.File
import java.io.InputStream

interface FileStore {

    fun copyInputStreamToFile(fileDst: File, inputStream: InputStream): Single<File>

    fun getTempImageFile(extension : String): Single<File>

    fun imageFileRotationDegrees(file: File): Single<Int>

    fun rotateImageFileIfNeeded(file: File, degree: Int): Single<File>
}