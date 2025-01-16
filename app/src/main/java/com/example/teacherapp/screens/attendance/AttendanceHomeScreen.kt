package com.example.teacherapp.screens.attendance

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.teacherapp.R
import com.example.teacherapp.components.AppBarbySans

@Composable
@Preview
fun AttendanceHomeScreen(navController: NavController= NavController(LocalContext.current)) {
    Scaffold(
        topBar = {
            AppBarbySans(
                title = "Attendance",
                icon = Icons.AutoMirrored.Filled.ArrowBack
            ) {
                //when the back icon is pressed then
                navController.popBackStack()
            }
        },
        floatingActionButton = {
            FloatingContent {
                //navigate to the add icon
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
                color = Color.Transparent
            ) {


            }
        }
    }
}

//floating bottom below
@Composable
fun FloatingContent(onClick:(String)->Unit ){
    FloatingActionButton(onClick={
        onClick("")
    },
        shape = RoundedCornerShape(45.dp),
        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
    ) {
        Icon(Icons.Default.Add, contentDescription = "Add Icon", tint = MaterialTheme.colorScheme.onTertiaryContainer)
    }
}