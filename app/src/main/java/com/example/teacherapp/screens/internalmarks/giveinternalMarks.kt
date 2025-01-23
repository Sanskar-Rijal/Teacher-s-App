package com.example.teacherapp.screens.internalmarks

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.teacherapp.R
import com.example.teacherapp.components.AppBarbySans
import com.example.teacherapp.components.LoadingDialog
import com.example.teacherapp.components.sansButton
import com.example.teacherapp.model.createAttendance.AttendanceData
import com.example.teacherapp.model.getAddedData.Subject
import com.example.teacherapp.model.getstudentbysec.StudentX
import com.example.teacherapp.model.getstudentbysec.studentResponse
import com.example.teacherapp.navigation.campusConnectScreen
import com.example.teacherapp.screens.LoginScreen.LoadingState
import com.example.teacherapp.screens.attendance.GetStudentBySection_Viewmodel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun giveinternalmarks(navController: NavController = NavController(LocalContext.current),
                   getstudent: GetStudentBySection_Viewmodel = hiltViewModel(),
                   subject: Subject?
) {
    val context= LocalContext.current

    val data: studentResponse = getstudent.item

    val uiState = getstudent.state.collectAsState()


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
                            items(data.students) { student ->
                                giveintmarks(student){

                                }
                            }
                            item{
                                sansButton(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "Save"
                                ) {
                                    //when button is pressed then data is to be sent to backend


                                }

                            }
                        }

                    }
                }
            }
        }
    }
}


@Composable
fun giveintmarks(
    student: StudentX,
    size:Int=50,
    title:String="Sanskar",
    onClick:()->Unit={}
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable {
                onClick.invoke()
            }
          ,
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
                   // text = student.name?:"no data",
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
                    //text = student.email?:"no data",
                    text = student.email?:"no data",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.LightGray,
                    textAlign = TextAlign.Center,
                    lineHeight = 23.sp
                )

            }
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFF1490CF), // Red color for total classes attended
                            fontWeight = FontWeight.Bold,
                            fontSize = 23.sp
                        )
                    ) {
                        append("0")
                    }
                    append("/") // Separator
                    withStyle(
                        style = SpanStyle(
                            color = Color.Gray, // Gray color for total classes conducted
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        )
                    ) {
                        append("20")
                    }
                },
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                lineHeight = 23.sp
            )

        }
    }
}