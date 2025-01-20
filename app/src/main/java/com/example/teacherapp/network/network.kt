package com.example.teacherapp.network

import com.example.teacherapp.model.addSubject.AddSubjRequest
import com.example.teacherapp.model.addSubject.AddsubjResponse
import com.example.teacherapp.model.getAddedData.getsubjects
import com.example.teacherapp.model.login.LoginRequest
import com.example.teacherapp.model.login.LoginResponse
import com.example.teacherapp.utils.Constants
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import javax.inject.Singleton

//for Login
@Singleton
interface network {

    @POST(value = Constants.LOGIN_ENDPOINT)
    suspend fun LoginTeacher(
        @Body loginRequest: LoginRequest):LoginResponse

    @GET(value = Constants.get_added_data)
    suspend fun getAddedData():getsubjects

    @POST(value = Constants.add_subject)
    suspend fun addSubject(
        @Body addSubjRequest: AddSubjRequest):AddsubjResponse
}