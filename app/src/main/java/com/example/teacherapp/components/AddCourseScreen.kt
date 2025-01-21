package com.example.teacherapp.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.teacherapp.utils.sectionsMap
import com.example.teacherapp.utils.subjectsMap

@Composable
fun AddCourseScreen(
    onDone:(String,String,String,String)->Unit={faculty,semester,section,subjectcode->}
) {

    val context = LocalContext.current

    var selectedfaculty by remember {
        mutableStateOf("")
    }
    var selectedSemester by remember {
        mutableStateOf("")
    }
    var selectedsection by remember {
        mutableStateOf("")
    }

    var selectedsubject by remember {
        mutableStateOf("")
    }

    var selectedsubjCode by remember {
        mutableStateOf("")
    }

    //checking if selectedfaculty and selectedSemester is not empty then only show subjects
    var valid = remember(selectedfaculty, selectedSemester, selectedsubject, selectedsection) {
        selectedfaculty.trim().isNotEmpty() && selectedSemester.trim().isNotEmpty()
                && selectedsubject.trim().isNotEmpty() && selectedsection.trim()
            .isNotEmpty() //empty xaina vane true falxa
    }

    val faculty = listOf("COMPUTER", "CIVIL")
    val sem = listOf("1", "2", "3", "4", "5", "6", "7", "8")

    val filterSubjects = remember(selectedfaculty, selectedSemester) {
        subjectsMap[selectedfaculty]?.get(selectedSemester)?.map {
            "${it.name} [${it.code}]" //to show subject name and code
        } ?: listOf("Select faculty and semester first")
    }


    val sections = remember(selectedfaculty) {
        sectionsMap[selectedfaculty]?.map {
            it.sec
        } ?: listOf("Select faculty and semester first")
    }

    Log.d("karuna", "AddCourseScreen:$selectedsubjCode ")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // College Dropdown
        DropdownMenuComponent (
            label = "Select faculty",
            options = faculty,
            selectedOption = selectedfaculty,
            onOptionSelected = { selectedfaculty = it }
        )

        // Section Dropdown
        DropdownMenuComponent(
            label = "Select Semester",
            options = sem,
            selectedOption = selectedSemester,
            onOptionSelected = { selectedSemester = it }
        )

        // Subject Dropdown
        DropdownMenuComponent(
            label = "Select your Section",
            options = sections,
            selectedOption = selectedsection,
            onOptionSelected = { selectedsection = it }
        )

        DropdownMenuComponent(
            modifier = if (selectedfaculty.isNotEmpty() && selectedSemester.isNotEmpty()) Modifier.height(
                300.dp
            ) else Modifier,
            label = "Select your Subject",
            options = filterSubjects,
            selectedOption = selectedsubject,
            onOptionSelected = {
                selectedsubject = it
                val code = it.substringAfter('[').substringBefore(']')
                selectedsubjCode = code
            }
        )

        //create Button
        sansButton(
            text = "Create",
            modifier = Modifier.fillMaxWidth()
        ) {
            if (!valid) {
                Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            } else {
                //to do something when button is clicked
                onDone(selectedfaculty, selectedSemester, selectedsection, selectedsubjCode)
                Log.d(
                    "pravin",
                    "$selectedfaculty,$selectedSemester,$selectedsection,$selectedsubjCode"
                )
            }
        }
    }
}