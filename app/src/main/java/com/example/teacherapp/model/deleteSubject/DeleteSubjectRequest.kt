package com.example.teacherapp.model.deleteSubject

data class DeleteSubjectRequest(
    val faculty: String,
    val section: String,
    val semester: String,
    val subjectCode: String
)