package com.example.teacherapp.model.createAttendance

data class createAttendanceRequest(
    val attendanceData: List<AttendanceData>,
    val date: String,
    val subjectId: Int
)