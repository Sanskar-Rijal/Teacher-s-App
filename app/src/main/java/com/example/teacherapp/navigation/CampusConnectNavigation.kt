package com.example.teacherapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.teacherapp.screens.HomeScreen.HomeScreen
import com.example.teacherapp.screens.LoginScreen.LoginScreen
import com.example.teacherapp.screens.LoginScreen.LoginViewmodel
import com.example.teacherapp.screens.attendance.AddAttendanceScreen
import com.example.teacherapp.screens.attendance.AttendanceHomeScreen
import com.example.teacherapp.screens.notices.NoticeScreen
import kotlin.math.log

@Composable
fun CampusConnectNavigation() {

    val navController = rememberNavController()

    val loginviewmodel:LoginViewmodel= hiltViewModel()

    val domain = "sangyog-cc.vercel.app"

    LaunchedEffect(Unit) {
        loginviewmodel.checkStatusLoginStatus(domain)
        if (loginviewmodel.isLoggedIn) {
            navController.navigate(campusConnectScreen.HomeScreen.name)
        }
    }

    NavHost(navController=navController,
        startDestination = campusConnectScreen.LoginScreen.name){

        composable(campusConnectScreen.LoginScreen.name){

            LoginScreen(navController,loginviewmodel)
        }

        composable(campusConnectScreen.HomeScreen.name){
            HomeScreen(navController,loginviewmodel)
        }

        composable(campusConnectScreen.AttendanceHomeScreen.name){
            AttendanceHomeScreen(navController)
        }

        composable(campusConnectScreen.AddAttendanceScreen.name){
            AddAttendanceScreen(navController)
        }

        composable(campusConnectScreen.NoticeScreen.name){
            NoticeScreen(navController)
        }

    }

}