package com.example.teacherapp.model.notes

data class NoteRequest(
  val  title: String,
  val faculty: String,
  val semester: String,
  val section: String,
  val subjectId: String,
)