package com.example.teacherapp.model.AddInternalMarks

data class InternalMarksRequest(
    val marksData: List<MarksData>,
    val subjectId: String
)