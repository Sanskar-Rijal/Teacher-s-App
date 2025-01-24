package com.example.teacherapp.repository

import com.example.teacherapp.data.DataorException
import com.example.teacherapp.model.ShowInternalMarks.ShowMarksRequest
import com.example.teacherapp.model.ShowInternalMarks.ShowMarksResponse
import com.example.teacherapp.network.network
import javax.inject.Inject

class ShowInternalMarksRepository @Inject constructor(
    private val api:network){

    suspend fun showInternalMarks(request:ShowMarksRequest):DataorException<ShowMarksResponse>{
        return try{
            DataorException.Loading(data = true)

            val response = api.showInternalMarks(request)

            DataorException.Success(data = response)
        }catch (ex:Exception){
            DataorException.Error(message = ex.message.toString())
        }
    }

}