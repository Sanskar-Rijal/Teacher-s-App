package com.example.teacherapp.repository

import android.util.Log
import com.example.teacherapp.data.DataorException
import com.example.teacherapp.model.AddInternalMarks.InternalMarksRequest
import com.example.teacherapp.model.AddInternalMarks.InternalMarksResponse
import com.example.teacherapp.model.Notice.NoticeResponse
import com.example.teacherapp.model.createAttendance.createAttendanceResponse
import com.example.teacherapp.network.network
import javax.inject.Inject


class InternalMarksRepository @Inject constructor(
    private val api:network) {

    suspend fun addInternalMarks(request:InternalMarksRequest):DataorException<InternalMarksResponse>{
        return try {

            DataorException.Loading(data = true)

            val response = api.addInternalMarks(request)

            DataorException.Success(data = response)

        } catch (e: retrofit2.HttpException) {
            // Handle HTTP error responses and parse the error body
            val errorBody = e.response()?.errorBody()
            val errorMessage = errorBody?.string() // Get raw error JSON


            // Try parsing the error JSON into LoginResponse
            val parsedError = try {
                val gson = com.google.gson.Gson()
                gson.fromJson(errorMessage, createAttendanceResponse::class.java)
            } catch (parseException: Exception) {
                null
            }
            Log.d("bibek", "repository catch block ${e.message()} | parsedError: $parsedError")

            // Return parsed error or fallback to the generic exception message
            if (parsedError != null) {
                DataorException.Error(message = parsedError.message ?: "Unknown error")
            } else {
                DataorException.Error(message = e.message ?: "HTTP ${e.code()}")
            }

        } catch (e: Exception) {
            // Handle other exceptions
             Log.d("bibek", "repository catch block ${e.message} ")
            DataorException.Error(message = e.message.toString())
        }
    }
}
