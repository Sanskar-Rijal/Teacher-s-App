package com.example.teacherapp.screens.internalmarks

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.teacherapp.R
import com.example.teacherapp.components.AddCourseScreen
import com.example.teacherapp.components.AppBarbySans
import com.example.teacherapp.components.LoadingDialog
import com.example.teacherapp.navigation.campusConnectScreen
import com.example.teacherapp.screens.LoginScreen.LoadingState
import com.example.teacherapp.screens.attendance.AttendanceViewModel_to_add_subj

@Composable
fun AddInternalMarks(navController: NavController = NavController(LocalContext.current),
                     viewmodel: AttendanceViewModel_to_add_subj
) {

    val uiState=viewmodel.state.collectAsState()

    val context = LocalContext.current

    Scaffold(
        topBar = {
            AppBarbySans(
                title = "Add Internal Marks",
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
                            navController.navigate(campusConnectScreen.AddAttendanceScreen.name) //navigate to attendance Screen Instead
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
