package com.example.teacherapp.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import java.io.File

object FileUtils {
    fun getFileFromUri(context: Context, uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri)
        val tempFile = File.createTempFile("upload_", ".pdf", context.cacheDir)
        inputStream.use { input ->
            tempFile.outputStream().use { output ->
                input?.copyTo(output)
            }
        }
        Log.d("jeevan", "File utils: ${tempFile.absolutePath}")
        return tempFile
    }
}
