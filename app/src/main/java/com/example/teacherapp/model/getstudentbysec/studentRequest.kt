package com.example.teacherapp.model.getstudentbysec

data class studentRequest(
    val faculty: String,
    val section: String,
    val semester: String
)