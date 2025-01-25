package com.example.teacherapp.repository

import android.util.Log
import com.example.teacherapp.data.DataorException
import com.example.teacherapp.model.AddInternalMarks.InternalMarksRequest
import com.example.teacherapp.model.AddInternalMarks.InternalMarksResponse
import com.example.teacherapp.model.createAttendance.createAttendanceResponse
import com.example.teacherapp.model.deleteSubject.DeleteSubjectRequest
import com.example.teacherapp.model.deleteSubject.DeleteSubjectResponse
import com.example.teacherapp.network.network
import javax.inject.Inject

class DeleteSubjectRepository @Inject constructor(
    private val api: network
) {

    suspend fun deleteSubj(request: DeleteSubjectRequest): DataorException<DeleteSubjectResponse> {
        return try {

            DataorException.Loading(data = true)

            val response = api.DeleteSubject(request)

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
            Log.d("pikachu", "repository catch block ${e.message()} | parsedError: $parsedError")

            // Return parsed error or fallback to the generic exception message
            if (parsedError != null) {
                DataorException.Error(message = parsedError.message ?: "Unknown error")
            } else {
                DataorException.Error(message = e.message ?: "HTTP ${e.code()}")
            }

        } catch (e: Exception) {
            // Handle other exceptions
            Log.d("pikachu", "repository catch block ${e.message} ")
            DataorException.Error(message = e.message.toString())
        }
    }
}