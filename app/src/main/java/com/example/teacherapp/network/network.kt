package com.example.teacherapp.network

import com.example.teacherapp.model.addSubject.AddSubjRequest
import com.example.teacherapp.model.addSubject.AddsubjResponse
import com.example.teacherapp.model.createAttendance.createAttendanceRequest
import com.example.teacherapp.model.createAttendance.createAttendanceResponse
import com.example.teacherapp.model.getAddedData.getsubjects
import com.example.teacherapp.model.getstudentbysec.studentRequest
import com.example.teacherapp.model.getstudentbysec.studentResponse
import com.example.teacherapp.model.login.LoginRequest
import com.example.teacherapp.model.login.LoginResponse
import com.example.teacherapp.model.showAttendance.showAttendanceRequest
import com.example.teacherapp.model.showAttendance.showAttendanceResponse
import com.example.teacherapp.utils.Constants
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import javax.inject.Singleton

//for Login
@Singleton
interface network {

    //for login
    @POST(value = Constants.LOGIN_ENDPOINT)
    suspend fun LoginTeacher(
        @Body loginRequest: LoginRequest):LoginResponse

    //to get the subject
    @GET(value = Constants.get_added_data)
    suspend fun getAddedData():getsubjects

    //to add subject by teacher
    @POST(value = Constants.add_subject)
    suspend fun addSubject(
        @Body addSubjRequest: AddSubjRequest):AddsubjResponse

    //getting all the students by section
    @POST(value = Constants.get_student_by_sec)
    suspend fun getStudentBySec(
        @Body studentRequest: studentRequest):studentResponse

    //creating attendance i mean taking attendance
    @POST(value=Constants.create_attendance)
    suspend fun createAttendance(
        @Body createAttendanceRequest: createAttendanceRequest):createAttendanceResponse


    //for showing attendance
    @POST(value=Constants.show_attendance)
    suspend fun ShowAttendance(
        @Body showAttendanceRequest: showAttendanceRequest):showAttendanceResponse



}