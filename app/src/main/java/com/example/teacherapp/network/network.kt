package com.example.teacherapp.network

import com.example.teacherapp.model.AddInternalMarks.InternalMarksRequest
import com.example.teacherapp.model.AddInternalMarks.InternalMarksResponse
import com.example.teacherapp.model.Notice.NoticeRequest
import com.example.teacherapp.model.Notice.NoticeResponse
import com.example.teacherapp.model.ShowInternalMarks.ShowMarksRequest
import com.example.teacherapp.model.ShowInternalMarks.ShowMarksResponse
import com.example.teacherapp.model.addSubject.AddSubjRequest
import com.example.teacherapp.model.addSubject.AddsubjResponse
import com.example.teacherapp.model.createAttendance.createAttendanceRequest
import com.example.teacherapp.model.createAttendance.createAttendanceResponse
import com.example.teacherapp.model.deleteSubject.DeleteSubjectRequest
import com.example.teacherapp.model.deleteSubject.DeleteSubjectResponse
import com.example.teacherapp.model.getAddedData.getsubjects
import com.example.teacherapp.model.getmydetails.MydetailsResponse
import com.example.teacherapp.model.getstudentbysec.studentRequest
import com.example.teacherapp.model.getstudentbysec.studentResponse
import com.example.teacherapp.model.login.LoginRequest
import com.example.teacherapp.model.login.LoginResponse
import com.example.teacherapp.model.notes.NoteRequest
import com.example.teacherapp.model.notes.NoteResponse
import com.example.teacherapp.model.showAttendance.showAttendanceRequest
import com.example.teacherapp.model.showAttendance.showAttendanceResponse
import com.example.teacherapp.utils.Constants
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import javax.inject.Singleton


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


    //for sending Notice
    @POST(value = Constants.send_notice)
    suspend fun sendNotice(
        @Body noticeRequest: NoticeRequest):NoticeResponse

    // Function to upload a note
    @Multipart
    @POST(value = Constants.add_notes)
    suspend fun SendNote(
        @Part("title") title: RequestBody,
        @Part("faculty") faculty: RequestBody,
        @Part("semester") semester: RequestBody,
        @Part("section") section: RequestBody,
        @Part("subjectId") subjectId: RequestBody,
        @Part file: MultipartBody.Part
    ): NoteResponse


    //function to add internal Marks
    @POST(value = Constants.add_internal_marks)
    suspend fun addInternalMarks(
        @Body addInternalMarksRequest: InternalMarksRequest
    ): InternalMarksResponse


    //function to get my details
    @GET(value=Constants.my_details)
    suspend fun getmyDetails():MydetailsResponse


    //function to show internal marks
    @POST(value = Constants.show_marks)
    suspend fun showInternalMarks(
        @Body ShowInternalMarksRequest: ShowMarksRequest):ShowMarksResponse

    //delete subject added by teacher
    @HTTP(method = "DELETE", path = Constants.delete_subject, hasBody = true)
    suspend fun DeleteSubject(
        @Body DeleteSubjectRequest:DeleteSubjectRequest):DeleteSubjectResponse

}

