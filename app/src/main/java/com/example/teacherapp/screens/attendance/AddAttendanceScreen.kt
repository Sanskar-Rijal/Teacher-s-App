package com.example.teacherapp.screens.attendance

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.teacherapp.R
import com.example.teacherapp.components.AppBarbySans
import com.example.teacherapp.components.sansButton
import com.example.teacherapp.model.login.LoginResponse

@Preview
@Composable
fun AddAttendanceScreen(navController: NavController=NavController(LocalContext.current)) {
    Scaffold(
        topBar = {
            AppBarbySans(
                title = "Add Attendance",
                icon = Icons.AutoMirrored.Filled.ArrowBack
            ) {
                navController.popBackStack()
            }
        }
    ) { contentpadding ->
        Box {
            Image(
                painter = painterResource(R.drawable.home_page_bg),
                modifier = Modifier.fillMaxSize(),
                contentDescription = "background",
                contentScale = ContentScale.FillBounds)

            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentpadding),
                color = Color.Transparent) {

                Column(modifier = Modifier.padding(10.dp)) {
                    AddCourseScreen()
                }

            }

        }

    }
}

@Composable
fun AddCourseScreen() {

    var selectedfaculty by rememberSaveable {
        mutableStateOf("")
    }
    var selectedbatch by rememberSaveable  {
        mutableStateOf("")
    }
    var selectedsection by rememberSaveable {
        mutableStateOf("")
    }

    var selectedsubject by rememberSaveable {
        mutableStateOf("")
    }

    val faculty = listOf("BCT","BEI")
    val batch = listOf("081", "080", "079","078","077")
    val subjects = listOf("Math", "Physics", "Computer Science")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // College Dropdown
        DropdownMenuComponent(
            label = "Select your faculty",
            options = faculty,
            selectedOption = selectedfaculty,
            onOptionSelected = { selectedfaculty = it }
        )

        // Section Dropdown
        DropdownMenuComponent(
            label = "Select your sem",
            options = batch,
            selectedOption = selectedbatch,
            onOptionSelected = { selectedbatch = it }
        )

        // Subject Dropdown
        DropdownMenuComponent(
            label = "Select your Section",
            options = subjects,
            selectedOption = selectedsection,
            onOptionSelected = { selectedsection = it }
        )

        DropdownMenuComponent(
            label = "Select your Subject",
            options = subjects,
            selectedOption = selectedsubject,
            onOptionSelected = { selectedsubject = it }
        )

    //create Button
        sansButton(text = "Create",
            modifier = Modifier.fillMaxWidth()) {
            //to do something when button is clicked
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuComponent(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by rememberSaveable{
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded } //first it was false when we click down arrow we want it to expand
        //so we wrote !expand
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = { },
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}
