package com.forevertea.travelstories.utils

import android.os.Environment
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class FileUtils {
    @Throws(IOException::class)
    fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(Date())
        val imageFileName = "travelstories_" + timeStamp + "_"
        val storageDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(imageFileName, ".jpg", storageDir)
    }

    fun copyInputStreamToFile(`in`: InputStream, file: File) {
        var out: OutputStream? = null
        try {
            out = FileOutputStream(file)
            val buf = ByteArray(1024)
            var len: Int = 0
            while (`in`.read(buf).apply { len = this } > 0) {
                out.write(buf, 0, len)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                out?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            try {
                `in`.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}