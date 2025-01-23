package com.example.teacherapp.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.teacherapp.data.DataorException
import com.example.teacherapp.model.login.LoginResponse
import com.example.teacherapp.model.notes.NoteRequest
import com.example.teacherapp.model.notes.NoteResponse
import com.example.teacherapp.network.network
import com.example.teacherapp.utils.FileUtils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val api:network
) {
    suspend fun sendNotes(
        title: String,
        faculty: String,
        semester: String,
        section: String,
        subjectId: String,
        fileUri: Uri,
        context: Context
    ):DataorException<NoteResponse>{
        return try {
            DataorException.Loading(data = true)

            val file = FileUtils.getFileFromUri(context, fileUri)

            val requestFile = file.asRequestBody("application/pdf".toMediaTypeOrNull())

            val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

            val titlePart = title.toRequestBody("text/plain".toMediaTypeOrNull())
            val facultyPart = faculty.toRequestBody("text/plain".toMediaTypeOrNull())
            val semesterPart = semester.toRequestBody("text/plain".toMediaTypeOrNull())
            val sectionPart = section.toRequestBody("text/plain".toMediaTypeOrNull())
            val subjectIdPart = subjectId.toRequestBody("text/plain".toMediaTypeOrNull())

            val response = api.SendNote(
                title = titlePart,
                faculty = facultyPart,
                semester = semesterPart,
                section = sectionPart,
                subjectId = subjectIdPart,
                file = body,
            )

            Log.d("jeevan", "repository try block ${body}")
            DataorException.Success(data = response)

        }catch (e: retrofit2.HttpException) {
            // Handle HTTP error responses and parse the error body
            val errorBody = e.response()?.errorBody()
            val errorMessage = errorBody?.string() // Get raw error JSON

            // Try parsing the error JSON into LoginResponse
            val parsedError = try {
                val gson = com.google.gson.Gson()
                gson.fromJson(errorMessage, LoginResponse::class.java)
            } catch (parseException: Exception) {
                null
            }
            Log.d("jeevan", "repository catch block ${e.message()} | parsedError: $parsedError")

            // Return parsed error or fallback to the generic exception message
            if (parsedError != null) {
                DataorException.Error(message = parsedError.message ?: "Unknown error")
            } else {
                DataorException.Error(message = e.message ?: "HTTP ${e.code()}")
            }

        } catch (e: Exception) {
            // Handle other exceptions
            Log.d("jeevan", "repository catch block ${e.message} ")
            DataorException.Error(message = e.message.toString())
        }
    }
}