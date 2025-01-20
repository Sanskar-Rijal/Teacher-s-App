package com.example.teacherapp.screens.attendance

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.teacherapp.R
import com.example.teacherapp.components.AppBarbySans
import com.example.teacherapp.components.LoadingDialog
import com.example.teacherapp.components.sansButton
import com.example.teacherapp.navigation.campusConnectScreen
import com.example.teacherapp.screens.LoginScreen.LoadingState
import com.example.teacherapp.utils.sectionsMap
import com.example.teacherapp.utils.subjectsMap
import kotlin.math.log


@Composable
fun AddAttendanceScreen(navController: NavController=NavController(LocalContext.current),
                        viewmodel:AttendanceViewModel_to_add_subj) {

    val uiState=viewmodel.state.collectAsState()

    val context = LocalContext.current

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

                Column(modifier = Modifier
                    .padding(10.dp)) {

                    AddCourseScreen{faculty,semester,section,subjectcode->
                        viewmodel.addSubject(faculty,semester,section,subjectcode){
                            Log.d("pravin", "inside navcontroller: ")
                            navController.navigate(campusConnectScreen.AttendanceHomeScreen.name) //navigate to attendance Screen Instead
                        }
                    }
                    if(uiState.value== LoadingState.LOADING){
                        LoadingDialog()
                    }

                }

            }

        }

    }
}

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
        DropdownMenuComponent(
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuComponent(
    modifier: Modifier=Modifier,
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
            modifier = modifier
                .background(color = Color.White)
                .padding(2.dp),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option,
                            color = Color.Black,
                            style = MaterialTheme.typography.bodyLarge,
                            letterSpacing = 0.5.sp,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                    ,
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
