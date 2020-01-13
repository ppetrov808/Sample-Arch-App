package com.test.arch.data.stores.file

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import io.reactivex.Single
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class FileStoreImpl @Inject constructor(val context: Context) : FileStore {

    companion object {

        private const val ZERO_VALUE = 0
        private const val FULL_QUALITY = 100

        private const val PHOTOS_DIR = "photos_dir"
        private const val TIME_STAMP_PATTERN = "yyyyMMdd_HHmmss"

        private const val ROTATION_90 = 90
        private const val ROTATION_180 = 180
        private const val ROTATION_270 = 270

        private val tempFileDateFormat = SimpleDateFormat(TIME_STAMP_PATTERN, Locale.US)
    }

    override fun copyInputStreamToFile(fileDst: File, inputStream: InputStream): Single<File> {
        return Single.fromCallable {
            inputStream.use { input -> fileDst.outputStream().use { fileOut -> input.copyTo(fileOut) } }
            return@fromCallable fileDst
        }
    }

    override fun getTempImageFile(extension : String): Single<File> {
        return Single.just(context.cacheDir.absoluteFile)
            .map { File(it, PHOTOS_DIR).apply { mkdirs() } }
            .map {
                File.createTempFile(
                    "JPEG_${tempFileDateFormat.format(Date())}_",
                    ".$extension",
                    it.absoluteFile
                )
            }
    }

    override fun imageFileRotationDegrees(file: File): Single<Int> {
        return Single.just(file.absolutePath)
            .map { ExifInterface(it) }
            .map { it.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL) }
            .map { getRotationDegrees(it) }
    }

    override fun rotateImageFileIfNeeded(file: File, degree: Int): Single<File> {
        return Single.fromCallable {
            if (degree == ZERO_VALUE) {
                file
            } else {
                BitmapFactory.decodeFile(file.absolutePath)
                    .rotateImage(degree.toFloat())
                    .writeBitmapToFile(file.absolutePath)
            }
        }
    }

    private fun getRotationDegrees(it: Int) = when (it) {
        ExifInterface.ORIENTATION_NORMAL -> ZERO_VALUE
        ExifInterface.ORIENTATION_ROTATE_90 -> ROTATION_90
        ExifInterface.ORIENTATION_ROTATE_180 -> ROTATION_180
        ExifInterface.ORIENTATION_ROTATE_270 -> ROTATION_270
        else -> ZERO_VALUE
    }

    private fun Bitmap.rotateImage(degree: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree)
        val rotatedImg = Bitmap.createBitmap(this, ZERO_VALUE, ZERO_VALUE, width, height, matrix, true)
        recycle()
        return rotatedImg
    }

    private fun Bitmap.writeBitmapToFile(filePath: String): File {
        val os = BufferedOutputStream(FileOutputStream(filePath))
        compress(Bitmap.CompressFormat.JPEG, FULL_QUALITY, os)
        os.close()
        return File(filePath)
    }
}