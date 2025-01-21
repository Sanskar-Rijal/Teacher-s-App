package com.example.teacherapp.repository

import android.util.Log
import com.example.teacherapp.data.DataorException
import com.example.teacherapp.model.getAddedData.getsubjects
import com.example.teacherapp.network.network
import javax.inject.Inject


class Get_Teacher_Subjects @Inject constructor(
    private val getTeacher:network) {
    //getting availabe data from teachers that have added like subjects etc
    suspend fun getSubjects(): DataorException<getsubjects> {
        return try {

            DataorException.Loading(data = true)

            val response = getTeacher.getAddedData()

            Log.d("sangyog", "getSubjects: $response")

            DataorException.Success(data = response)

        }catch (ex:Exception){
            Log.d("sangyog", "getSubjects: ${ex.message}")
            DataorException.Error(message = ex.message.toString())
        }
    }
}