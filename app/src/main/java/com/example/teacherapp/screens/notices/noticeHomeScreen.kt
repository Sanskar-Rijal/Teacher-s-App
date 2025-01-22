package com.example.teacherapp.screens.notices

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.teacherapp.R
import com.example.teacherapp.components.AppBarbySans
import com.example.teacherapp.components.LoadingDialog
import com.example.teacherapp.navigation.campusConnectScreen
import com.example.teacherapp.screens.LoginScreen.LoadingState
import com.example.teacherapp.screens.attendance.GetAllTeacherSubj_Viewmodel

@Composable
fun NoticeHomeScreen(navController: NavController = NavController(LocalContext.current),
                    viewModel: GetAllTeacherSubj_Viewmodel
) {
    //getting data from viewmodel
    val data = viewModel.item

    val uiState = viewModel.state.collectAsState()


    Scaffold(
        topBar = {
            AppBarbySans(
                title = "Notice",
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
                color = Color.Transparent
            ) {

                //checking whether data is loading or not
                if (uiState.value == LoadingState.LOADING) {
                    LoadingDialog()
                } else {
                    Column(modifier = Modifier.padding(10.dp)) {

                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top
                        ) {
                            items(data.subjects){eachSubj->
                                com.example.teacherapp.components.CardView(eachSubj) {
                                    //to be made
                                    navController.navigate(campusConnectScreen.NoticeScreen.name)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}