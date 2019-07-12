package com.oky2abbas.sms2html.helper

import android.content.Context
import android.os.Environment
import java.io.File


class FileHelper(val context: Context) {

    private val INTERNAL_STORAGE = "INTERNAL"
    private val EXTERNAL_STORAGE = "EXTERNAL"

    fun delete(filename: String): Boolean {
        val file = getFilePath(filename)
        return if (file.exists())
            file.delete()
        else false
    }

    fun getFiles(): Array<out File>? {
        return File(getPath()).listFiles()
    }

    private fun getFilePath(filename: String): File {
        return File(getPath() + filename)
    }

    fun getPath(): String {
        val separator = "/"

        return if (getLocation() == INTERNAL_STORAGE)
            context.filesDir?.path + separator
        else
            context.getExternalFilesDir(null)?.path + separator
    }

    private fun getLocation(): String {
        return if (!isExternalStorageAvailable()) INTERNAL_STORAGE
        else EXTERNAL_STORAGE
    }

    private fun isExternalStorageAvailable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }
}