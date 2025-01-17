package com.example.teacherapp.network

import com.example.teacherapp.model.login.LoginRequest
import com.example.teacherapp.model.login.LoginResponse
import com.example.teacherapp.utils.Constants
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Singleton


@Singleton
interface loginApi {

    @POST(value = Constants.LOGIN_ENDPOINT)
    suspend fun LoginTeacher(
        @Body loginRequest: LoginRequest):LoginResponse

}