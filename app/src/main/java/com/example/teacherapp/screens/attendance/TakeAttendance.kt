package com.example.teacherapp.screens.attendance

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.teacherapp.R
import com.example.teacherapp.components.AppBarbySans
import com.example.teacherapp.components.LoadingDialog
import com.example.teacherapp.components.sansButton
import com.example.teacherapp.model.createAttendance.AttendanceData
import com.example.teacherapp.model.createAttendance.createAttendanceRequest
import com.example.teacherapp.model.getAddedData.Subject
import com.example.teacherapp.model.getstudentbysec.StudentX
import com.example.teacherapp.model.getstudentbysec.studentResponse
import com.example.teacherapp.navigation.campusConnectScreen
import com.example.teacherapp.screens.LoginScreen.LoadingState
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date


@Composable
fun TakeAttendance(navController: NavController=NavController(LocalContext.current),
                   getstudent: GetStudentBySection_Viewmodel= hiltViewModel(),
                   subject: Subject?, createAttendace:CreateAttendance_viewModel
) {
    val context= LocalContext.current

    val data: studentResponse = getstudent.item

    val uiState = getstudent.state.collectAsState()

    val createState= createAttendace.state.collectAsState()

    //for attendance to mark student present or absent
    val attendanceData = remember {
        mutableStateListOf<AttendanceData>()
    }


    //to pass current date
    val currentDate = remember {
        LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }

    LaunchedEffect(key1 = null) {
        if (subject != null) {
            getstudent.getStudentBySection(
                faculty = subject.faculty,
                semester = subject.semester,
                section = subject.section
            )
        }
    }


    Scaffold(
        topBar = {
            AppBarbySans(
                title = "Take Attendance",
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
                contentScale = ContentScale.FillBounds
            )

            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentpadding),
                color = Color.Transparent
            ) {
                if (uiState.value == LoadingState.LOADING) {
                    LoadingDialog()
                } else {
                    Column(modifier = Modifier.padding(5.dp)) {

                        LazyColumn(
                            modifier = Modifier.padding(10.dp),
                            contentPadding = PaddingValues(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            items(data.students) { student->
                                takeAtt(student){id,present->

                                    val existing = attendanceData.find {
                                        it.studentId == id
                                    }
                                    if (existing != null) {
                                        attendanceData.remove(existing)
                                    }

                                    attendanceData.add(AttendanceData(present = present, studentId = id))
                                }
                            }
                            item {
                                sansButton(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "Save"
                                ) {
                                    //when button is pressed then data is to be sent to backend

                                    val attendanceRequest = createAttendanceRequest(
                                        attendanceData = attendanceData,
                                        date = currentDate,
                                        subjectId = subject!!.id
                                    )
                                    Log.d("saurav", "${uiState.value} ")

                                    createAttendace.createAttendance(
                                        attendanceRequest =  attendanceRequest
                                    ){
                                        Toast.makeText(context,"Attendance Taken Successfully",Toast.LENGTH_SHORT).show()
//                                        navController.navigate(campusConnectScreen.AttendanceHomeScreen.name)
                                        navController.popBackStack()
                                    }
                                }

                            }
                        }
                        if(createState.value == LoadingState.LOADING)
                            LoadingDialog()
                    }
                }
            }
        }
    }
}

@Composable
fun takeAtt(
    student:StudentX,
    size:Int=50,
    title:String="Sanskar",
    onClick: (String,Boolean) -> Unit
) {

    //mutable state for checking whether the card has been clicked or not
    var isChecked by rememberSaveable {
        mutableStateOf(false)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable {

                isChecked =
                    !isChecked //if the card is clicked then also the checkbox should be clicked
                onClick(student.id, isChecked)
            },
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = student.name?:"no data",
                    modifier = Modifier.padding(bottom = 5.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 23.sp,
                    letterSpacing = 0.5.sp
                )
                Text(
                    text = student.email?:"no data",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.LightGray,
                    textAlign = TextAlign.Center,
                    lineHeight = 23.sp
                )

            }
            //creating checkbox
            Checkbox(
                modifier = Modifier
                    .padding(8.dp),
                checked = isChecked,
                onCheckedChange = {tick->
                    isChecked=tick
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFF588157),
                )
            )

        }
    }
}