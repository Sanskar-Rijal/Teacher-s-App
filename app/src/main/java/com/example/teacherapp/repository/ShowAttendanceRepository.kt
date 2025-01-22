package com.example.teacherapp.repository

import com.example.teacherapp.data.DataorException
import com.example.teacherapp.model.createAttendance.createAttendanceResponse
import com.example.teacherapp.model.showAttendance.showAttendanceRequest
import com.example.teacherapp.model.showAttendance.showAttendanceResponse
import com.example.teacherapp.network.network
import javax.inject.Inject

class ShowAttendanceRepository @Inject constructor(
    private val api:network
) {
    suspend fun showAttendance(showAttendanceRequest: showAttendanceRequest):DataorException<showAttendanceResponse>{
        return try {

            DataorException.Loading(data = true)

            val response = api.ShowAttendance(showAttendanceRequest)

            DataorException.Success(data = response)

        }catch (e:retrofit2.HttpException){

            val errorBody = e.response()?.errorBody()

            val errorMessage = errorBody?.string()


            val parsedError = try {
                val gson = com.google.gson.Gson()
                gson.fromJson(errorMessage, createAttendanceResponse::class.java)
            } catch (parseException: Exception) {
                null
            }

            if (parsedError != null) {
                DataorException.Error(message = parsedError.message ?: "Unknown error")
            } else {
                DataorException.Error(message = e.message ?: "HTTP ${e.code()}")
            }
        }catch (e:Exception){
            DataorException.Error(message = e.message.toString())
        }
    }
}