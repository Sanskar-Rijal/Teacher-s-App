package com.example.teacherapp.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File

fun OpenExcelFile(context: Context, file: File){
    try{
        val uri = FileProvider.getUriForFile(context,"${context.packageName}.provider",file)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(uri,"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }catch (ex:Exception){
        Toast.makeText(context, "No app available to open this file.", Toast.LENGTH_LONG).show()
    }
}