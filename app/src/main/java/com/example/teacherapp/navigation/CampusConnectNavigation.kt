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
import com.example.teacherapp.screens.NotesScreen.AddNotesScreen
import com.example.teacherapp.screens.NotesScreen.NotesHomeScreen
import com.example.teacherapp.screens.attendance.AddAttendanceScreen
import com.example.teacherapp.screens.attendance.AttendanceHomeScreen
import com.example.teacherapp.screens.attendance.AttendanceViewModel
import com.example.teacherapp.screens.attendance.AttendanceViewModel_to_add_subj
import com.example.teacherapp.screens.attendance.ChooseAttendanceScreen
import com.example.teacherapp.screens.attendance.ShowAttendance
import com.example.teacherapp.screens.attendance.TakeAttendance
import com.example.teacherapp.screens.internalmarks.AddInternalMarks
import com.example.teacherapp.screens.internalmarks.InternalMarksHomeScreen
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
            val AttendanceViewModel:AttendanceViewModel= hiltViewModel<AttendanceViewModel>()

            AttendanceHomeScreen(navController,AttendanceViewModel)
        }

        composable(campusConnectScreen.AddAttendanceScreen.name){
            val AttendanceHomeScreenViewModel = hiltViewModel<AttendanceViewModel_to_add_subj>()
            AddAttendanceScreen(navController,AttendanceHomeScreenViewModel)
        }

        composable(campusConnectScreen.ChooseAttendanceScreen.name){
            ChooseAttendanceScreen(navController)
        }

        composable(campusConnectScreen.TakeAttendanceScreen.name){
            TakeAttendance(navController)
        }

        composable(campusConnectScreen.ShowAttendanceScreen.name){
            ShowAttendance(navController)
        }

        composable(campusConnectScreen.NoticeScreen.name){
            NoticeScreen(navController)
        }

        composable(campusConnectScreen.NotesHomeScreen.name){
            NotesHomeScreen(navController)
        }

        composable(campusConnectScreen.AddNotesScreen.name){
            AddNotesScreen(navController)
        }

        composable(campusConnectScreen.InternalMarksHomeScreen.name){
            InternalMarksHomeScreen(navController)
        }

        composable(campusConnectScreen.AddInternalMarksScreen.name){
            AddInternalMarks(navController)
        }

    }

}