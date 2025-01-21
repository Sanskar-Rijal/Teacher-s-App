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
import com.example.teacherapp.screens.NotesScreen.NotesHomeScreen
import com.example.teacherapp.screens.attendance.AddAttendanceScreen
import com.example.teacherapp.screens.attendance.AttendanceHomeScreen
import com.example.teacherapp.screens.attendance.AttendanceViewModel
import com.example.teacherapp.screens.attendance.AttendanceViewModel_to_add_subj
import com.example.teacherapp.screens.attendance.ChooseAttendanceScreen
import com.example.teacherapp.screens.attendance.ShowAttendance
import com.example.teacherapp.screens.attendance.TakeAttendance
import com.example.teacherapp.screens.internalmarks.InternalMarksHomeScreen
import com.example.teacherapp.screens.notices.NoticeHomeScreen
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


    //viewmodel to add new subject by teacher
    val addNewsubj = hiltViewModel<AttendanceViewModel_to_add_subj>()

    NavHost(navController=navController,
        startDestination = campusConnectScreen.LoginScreen.name){

        composable(campusConnectScreen.LoginScreen.name){

            LoginScreen(navController,loginviewmodel)
        }

        composable(campusConnectScreen.HomeScreen.name){
            HomeScreen(navController,loginviewmodel)
        }

        composable(campusConnectScreen.AttendanceHomeScreen.name){
            //viewmodel to show all subject of teacher
            val AttendanceViewModel:AttendanceViewModel= hiltViewModel<AttendanceViewModel>()
            AttendanceHomeScreen(navController,AttendanceViewModel)
        }

        composable(campusConnectScreen.AddAttendanceScreen.name){

            AddAttendanceScreen(navController,addNewsubj)
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

        composable(campusConnectScreen.NoticeHomeScreen.name){
            //viewmodel to show all subject of teacher
            val AttendanceViewModel:AttendanceViewModel= hiltViewModel<AttendanceViewModel>()
            NoticeHomeScreen(navController,AttendanceViewModel)
        }

        composable(campusConnectScreen.NoticeScreen.name){
            NoticeScreen(navController)
        }

        composable(campusConnectScreen.NotesHomeScreen.name){
            //viewmodel to show all subject of teacher
            val AttendanceViewModel:AttendanceViewModel= hiltViewModel<AttendanceViewModel>()
            NotesHomeScreen(navController,AttendanceViewModel)
        }

//        composable(campusConnectScreen.AddNotesScreen.name){
//            AddNotesScreen(navController,addNewsubj)
//        }

        composable(campusConnectScreen.InternalMarksHomeScreen.name){
            //viewmodel to show all subject of teacher
            val AttendanceViewModel:AttendanceViewModel= hiltViewModel<AttendanceViewModel>()
            InternalMarksHomeScreen(navController,AttendanceViewModel)
        }

//        composable(campusConnectScreen.AddInternalMarksScreen.name){
//            AddInternalMarks(navController,addNewsubj)
//        }


    }

}