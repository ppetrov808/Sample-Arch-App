package com.test.arch.ui.extensions

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import java.io.FileInputStream
import java.io.InputStream

const val FILE_OPEN_MODE_READ = "r"
const val DEFAULT_EXTENSION = "jpg"

fun Uri.getInputStream(context: Context): InputStream {
    val contentResolver = context.contentResolver
    val parcelDescriptor = contentResolver?.openFileDescriptor(this, FILE_OPEN_MODE_READ)
    val fileDescriptor = parcelDescriptor?.fileDescriptor
    return FileInputStream(fileDescriptor)
}

fun Uri.getFileExtension(context: Context): String {
    val contentResolver = context.contentResolver
    return MimeTypeMap.getSingleton().getExtensionFromMimeType(contentResolver.getType(this)) ?: DEFAULT_EXTENSION
}
