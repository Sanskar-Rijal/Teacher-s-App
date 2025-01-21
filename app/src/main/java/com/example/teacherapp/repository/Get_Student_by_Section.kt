package com.example.teacherapp.repository

import android.util.Log
import com.example.teacherapp.data.DataorException
import com.example.teacherapp.model.getstudentbysec.studentRequest
import com.example.teacherapp.model.getstudentbysec.studentResponse
import com.example.teacherapp.model.login.LoginResponse
import com.example.teacherapp.network.network
import javax.inject.Inject

class Get_Student_by_Section @Inject constructor(
    private val api:network) {

    suspend fun getStudentBySec(request: studentRequest): DataorException<studentResponse> {
        return try {

            DataorException.Loading(data = true)

            val response = api.getStudentBySec(request)

            Log.d("Smriti", "getStudentBySec: ${response} ")

            DataorException.Success(data = response)

        } catch (e: retrofit2.HttpException) {
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
            Log.d("Smriti", "repository catch block ${e.message()} | parsedError: $parsedError")

            // Return parsed error or fallback to the generic exception message
            if (parsedError != null) {
                DataorException.Error(message = parsedError.message ?: "Unknown error")
            } else {
                DataorException.Error(message = e.message ?: "HTTP ${e.code()}")
            }

        } catch (e: Exception) {
            // Handle other exceptions
            Log.d("Smriti", "repository catch block ${e.message} ")
            DataorException.Error(message = e.message.toString())
        }
    }
}
