package com.example.teacherapp.model.ShowInternalMarks

data class ShowMarksResponse(
    val data: List<Data>,
    val message: String,
    val success: Boolean
)