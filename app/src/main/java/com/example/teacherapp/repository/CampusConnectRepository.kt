package com.example.teacherapp.repository

import android.util.Log
import com.example.teacherapp.cookiesmanage.AppCookieJar
import com.example.teacherapp.data.DataorException
import com.example.teacherapp.model.login.LoginRequest
import com.example.teacherapp.model.login.LoginResponse
import com.example.teacherapp.network.loginApi
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val booksApi: loginApi,
    private val cookieJar: AppCookieJar
) {

    suspend fun loginTeacher(email: String, password: String): DataorException<LoginResponse> {
        return try {
            DataorException.Loading(data = true)

            val response = booksApi.LoginTeacher(LoginRequest(email, password))
            Log.d("april", "repository $response ")

            DataorException.Success(data = response)

        }
        catch (e: retrofit2.HttpException) {
            // Handle HTTP error responses and parse the error body
            val errorBody = e.response()?.errorBody()
            val errorMessage = errorBody?.string() // Get raw error JSON

            // Try parsing the error JSON into LoginResponse
            val parsedError = try {
                val gson = com.google.gson.Gson()
                gson.fromJson(errorMessage, LoginResponse::class.java)
            }
            catch (parseException: Exception) {
                null
            }
            Log.d("april", "repository catch block ${e.message()} | parsedError: $parsedError")

            // Return parsed error or fallback to the generic exception message
            if (parsedError != null) {
                DataorException.Error(message = parsedError.message ?: "Unknown error")
            } else {
                DataorException.Error(message = e.message ?: "HTTP ${e.code()}")
            }

        } catch (e: Exception) {
            // Handle other exceptions
            Log.d("april", "repository catch block ${e.message} ")
            DataorException.Error(message = e.message.toString())
        }
    }

    fun logoutTeacher() {
        cookieJar.clearCookies()
    }
}



