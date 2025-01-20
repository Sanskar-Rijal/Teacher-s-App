package com.example.teacherapp.model.addSubject

data class AddSubjRequest(
    val faculty: String,
    val semester: String,
    val section: String,
    val subjectCode:String
)
