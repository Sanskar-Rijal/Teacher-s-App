package com.example.teacherapp.screens.attendance

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.teacherapp.model.getAddedData.Subject
import com.example.teacherapp.navigation.campusConnectScreen

@Preview
@Composable
fun ChooseAttendanceScreen(navController: NavController = NavController(LocalContext.current)) {
    Scaffold(
        topBar = {
            AppBarbySans(
                title = "Choose for Attendance",
                icon = Icons.AutoMirrored.Filled.ArrowBack
            ) {
                //when the back icon is pressed then
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
                color = Color.Transparent) {
                Column(modifier = Modifier.padding(10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {

                    CardForChoose(title = "Take Attendance", onClick = {
                        navController.navigate(campusConnectScreen.TakeAttendanceScreen.name)
                    })

                    CardForChoose(title = "Show Attendance ", onClick = {
                        navController.navigate(campusConnectScreen.ShowAttendanceScreen.name)
                    })

                }

            }
        }
    }
}

@Composable
fun CardForChoose(
    size:Int=50,
    title:String="hehehe",
    onClick: () -> Unit={}
){
    Card(modifier = Modifier
        .height(130.dp)
        .fillMaxWidth()
        .padding(5.dp)
        .clickable {
            onClick.invoke()
        },
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ){
        Column(modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center){

            Text(
                text = title,
                modifier = Modifier.padding(bottom = 5.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                lineHeight = 23.sp,
                letterSpacing = 0.5.sp
            )
        }
    }
}