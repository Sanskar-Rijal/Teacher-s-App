package com.example.teacherapp.repository

import com.example.teacherapp.data.DataorException
import com.example.teacherapp.model.Notice.NoticeRequest
import com.example.teacherapp.model.Notice.NoticeResponse
import com.example.teacherapp.network.network
import javax.inject.Inject

class SendNoticeRepository @Inject constructor(
    private val api:network) {

    suspend fun sendNotice(noticeRequest: NoticeRequest):DataorException<NoticeResponse>{
        return try {

            DataorException.Loading(data = true)

            val response = api.sendNotice(noticeRequest)

            DataorException.Success(data = response)

        }catch (e:retrofit2.HttpException){

            val errorBody = e.response()?.errorBody()

            val errorMessage = errorBody?.string()

            val parsedError = try {

                val gson = com.google.gson.Gson()

                gson.fromJson(errorMessage, NoticeResponse::class.java)

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