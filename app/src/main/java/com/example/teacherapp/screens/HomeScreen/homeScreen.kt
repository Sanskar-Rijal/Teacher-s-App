package com.example.teacherapp.screens.HomeScreen

import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Usb
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.util.TableInfo
import com.example.teacherapp.R
import com.example.teacherapp.model.getAddedData.getsubjects
import com.example.teacherapp.model.getmydetails.Data
import com.example.teacherapp.model.getmydetails.MydetailsResponse
import com.example.teacherapp.navigation.campusConnectScreen
import com.example.teacherapp.screens.LoginScreen.LoginViewmodel

@Composable
fun HomeScreen(navController: NavController= NavController(LocalContext.current),
               loginViewmodel: LoginViewmodel,
               homeScreenViewModel: HomeScreenViewModel
               ) {

    val details: MydetailsResponse = homeScreenViewModel.item

    val uiState = homeScreenViewModel.state.collectAsState()

    Scaffold { contentpadding ->

        Surface(
            modifier = Modifier
                .padding(contentpadding)
                .fillMaxSize(),
            color = Color(0xFF1490CF)
        ) {

            Column {
                Spacer(modifier = Modifier.height(30.dp))

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween) {

                    Icon(
                        imageVector = Icons.Rounded.AccountCircle,
                        modifier = Modifier.size(80.dp),
                        contentDescription = "User Icons",
                        tint = Color.Black.copy(0.5f)
                    )

                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "Welcome !",
                            style = MaterialTheme.typography.labelMedium,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black.copy(0.8f)
                        )
                        Text(
                            text = details.data.name?:"No Name",
                            modifier = Modifier.padding(top = 10.dp),
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black.copy(0.8f)
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))

                    IconButton(modifier = Modifier.padding(start = 30.dp, top = 15.dp),
                            onClick = {
                                loginViewmodel.logoutTeacher()
                                navController.navigate(campusConnectScreen.LoginScreen.name)

                    },) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "LogOut",
                            tint = Color.Black.copy(alpha = 0.4f))
                        }


                }
                Spacer(modifier = Modifier.height(50.dp))
                BackgroundCardView(navController)
            }
        }
    }
}



@Composable
fun CardView(
    size:Int=50,
    icon:Painter,
    title:String,
    onClick: () -> Unit
){
    Card(modifier = Modifier
        .height(160.dp)
        .width(170.dp)
        .padding(10.dp)
        .clickable {
            onClick.invoke()
        },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ){
        Column(modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center){

            Icon(painter = icon,
                contentDescription = "Icon",
                modifier = Modifier.size(size.dp))

            Text(
                modifier = Modifier.padding(10.dp),
                text = title,
                style = MaterialTheme.typography.bodySmall,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                textAlign = TextAlign.Center,
                lineHeight = 23.sp
            )

        }
    }
}




@Composable
fun BackgroundCardView(navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxSize(),
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box {
            Image(
                painter = painterResource(R.drawable.home_page_bg),
                modifier = Modifier.fillMaxSize(),
                contentDescription = "background",
                contentScale = ContentScale.FillBounds)

            Column(modifier = Modifier
                .padding(10.dp)) {

                Spacer(modifier = Modifier.height(80.dp))

                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement =Arrangement.Absolute.SpaceEvenly) {
                    CardView(
                        title = "Attendance",
                        icon = painterResource(id = R.drawable.attendance_icon)
                    ){
                        navController.navigate(campusConnectScreen.AttendanceHomeScreen.name)
                    }

                    CardView( title = "Notes",
                        icon = painterResource(id = R.drawable.notes_icon)){
                        navController.navigate(campusConnectScreen.NotesHomeScreen.name)
                    }

                }

                Spacer(modifier = Modifier.height(50.dp))

                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement =Arrangement.Absolute.SpaceEvenly) {

                    CardView( title = "Notice",
                        icon = painterResource(id = R.drawable.notice_icon)){
                        navController.navigate(campusConnectScreen.NoticeHomeScreen.name)
                    }

                    CardView(
                        title = "Internal Marks",
                        size = 40,
                        icon = painterResource(id = R.drawable.internal_marks_icon)
                    ){
                        navController.navigate(campusConnectScreen.InternalMarksHomeScreen.name)
                    }
                }


            }
        }
    }
}