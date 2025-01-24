package com.example.teacherapp.utils

import android.content.Context
import android.net.Uri

fun getFileNameFromUri(context: Context, uri: Uri): String {
    var name = "Unnamed File"
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            val nameIndex = it.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
            if (nameIndex != -1) {
                name = it.getString(nameIndex)
            }
        }
    }
    return name
}
