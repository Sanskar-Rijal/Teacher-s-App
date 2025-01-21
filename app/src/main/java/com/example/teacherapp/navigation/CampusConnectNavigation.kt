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
import com.example.teacherapp.screens.attendance.GetAllTeacherSubj_Viewmodel
import com.example.teacherapp.screens.attendance.GetStudentBySection_Viewmodel
import com.example.teacherapp.screens.attendance.ViewModel_to_add_subj
import com.example.teacherapp.screens.attendance.ShowAttendance
import com.example.teacherapp.screens.attendance.TakeAttendance
import com.example.teacherapp.screens.internalmarks.AddInternalMarks
import com.example.teacherapp.screens.internalmarks.InternalMarksHomeScreen
import com.example.teacherapp.screens.notices.NoticeHomeScreen
import com.example.teacherapp.screens.notices.NoticeScreen

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
            //viewmodel to show all subject of teacher
            val AttendanceViewModel:GetAllTeacherSubj_Viewmodel= hiltViewModel<GetAllTeacherSubj_Viewmodel>()
            AttendanceHomeScreen(navController,AttendanceViewModel)
        }

        composable(campusConnectScreen.AddAttendanceScreen.name){
            //viewmodel to add new subject by teacher
            val addNewsubj = hiltViewModel<ViewModel_to_add_subj>()

            AddAttendanceScreen(navController,addNewsubj)
        }


        composable(campusConnectScreen.TakeAttendanceScreen.name){
            val viewmodel:GetStudentBySection_Viewmodel= hiltViewModel<GetStudentBySection_Viewmodel>()
            TakeAttendance(navController,viewmodel)
        }

        composable(campusConnectScreen.ShowAttendanceScreen.name){
            ShowAttendance(navController)
        }

        composable(campusConnectScreen.NoticeHomeScreen.name){
            //viewmodel to show all subject of teacher
            val AttendanceViewModel:GetAllTeacherSubj_Viewmodel= hiltViewModel<GetAllTeacherSubj_Viewmodel>()
            NoticeHomeScreen(navController,AttendanceViewModel)
        }

        composable(campusConnectScreen.NoticeScreen.name){
            NoticeScreen(navController)
        }

        composable(campusConnectScreen.NotesHomeScreen.name){
            //viewmodel to show all subject of teacher
            val AttendanceViewModel:GetAllTeacherSubj_Viewmodel= hiltViewModel<GetAllTeacherSubj_Viewmodel>()
            NotesHomeScreen(navController,AttendanceViewModel)
        }

        composable(campusConnectScreen.AddNotesScreen.name){
            //viewmodel to add new subject by teacher
            val addNewsubj = hiltViewModel<ViewModel_to_add_subj>()
            AddNotesScreen(navController,addNewsubj)
        }

        composable(campusConnectScreen.InternalMarksHomeScreen.name){
            //viewmodel to show all subject of teacher
            val AttendanceViewModel:GetAllTeacherSubj_Viewmodel= hiltViewModel<GetAllTeacherSubj_Viewmodel>()
            InternalMarksHomeScreen(navController,AttendanceViewModel)
        }

        composable(campusConnectScreen.AddInternalMarksScreen.name){
            //viewmodel to add new subject by teacher
            val addNewsubj = hiltViewModel<ViewModel_to_add_subj>()
            AddInternalMarks(navController,addNewsubj)
        }


    }

}