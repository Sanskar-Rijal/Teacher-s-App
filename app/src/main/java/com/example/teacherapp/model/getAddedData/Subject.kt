package com.example.teacherapp.model.getAddedData

import kotlinx.serialization.Serializable

@Serializable
data class Subject(
    val faculty: String,
    val id: String,
    val name: String,
    val semester: String,
    val subjectCode: String,
    val teacherId: Int,
    val section:String
)