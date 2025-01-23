package com.example.teacherapp.model.showAttendance

data class Attendance(
    val email: String,
    val name: String,
    val studentId: String,
    val totalClassesAttended: Int,
    val totalClassesConducted: Int
)