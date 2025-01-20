package com.example.teacherapp.repository

import android.util.Log
import com.example.teacherapp.data.DataorException
import com.example.teacherapp.model.addSubject.AddSubjRequest
import com.example.teacherapp.model.addSubject.AddsubjResponse
import com.example.teacherapp.model.getAddedData.getsubjects
import com.example.teacherapp.network.network
import javax.inject.Inject

class Add_Teacher_Subjects @Inject constructor(
    private val addTeacher_Subj:network
) {
        //pushing the data to backend that teacher used to make a subject

        suspend fun addSubjects(request:AddSubjRequest): DataorException<AddsubjResponse> {
            return try {

                DataorException.Loading(data = true)

                val response = addTeacher_Subj.addSubject(request)

                Log.d("pravin", "getSubjects: $response")
                DataorException.Success(data = response)
            }catch (ex:Exception){
                Log.d("pravin", "INSIDE CATCH BLOCK: ${ex.localizedMessage}")
                DataorException.Error(message = ex.message.toString())
            }
        }
    }