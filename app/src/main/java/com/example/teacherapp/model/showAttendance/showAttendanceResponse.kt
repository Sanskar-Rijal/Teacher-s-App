package com.example.teacherapp.model.showAttendance

data class showAttendanceResponse(
    val attendance: List<Attendance>,
    val subject: Subject,
    val success: Boolean
)