package com.example.teacherapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.teacherapp.screens.HomeScreen.HomeScreen
import com.example.teacherapp.screens.LoginScreen.LoginScreen
import com.example.teacherapp.screens.attendance.AttendanceHomeScreen

@Composable
fun CampusConnectNavigation() {

    val navController = rememberNavController()

    NavHost(navController=navController,
        startDestination = campusConnectScreen.LoginScreen.name){


        composable(campusConnectScreen.LoginScreen.name){
            LoginScreen(navController)
        }

        composable(campusConnectScreen.HomeScreen.name){
            HomeScreen(navController)
        }

        composable(campusConnectScreen.AttendanceHomeScreen.name){
            AttendanceHomeScreen(navController)
        }


    }

}