package com.example.teacherapp.model.Notice

data class NoticeRequest(
    val description: String,
    val faculty: String,
    val section: String,
    val semester: String,
    val subjectId: Int,
    val title: String
)