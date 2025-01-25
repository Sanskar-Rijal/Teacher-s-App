package com.example.teacherapp.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.fragment.app.FragmentManager.BackStackEntry
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.teacherapp.model.getAddedData.Subject
import com.example.teacherapp.screens.HomeScreen.HomeScreen
import com.example.teacherapp.screens.HomeScreen.HomeScreenViewModel
import com.example.teacherapp.screens.LoginScreen.LoginScreen
import com.example.teacherapp.screens.LoginScreen.LoginViewmodel
import com.example.teacherapp.screens.NotesScreen.AddNotesScreen
import com.example.teacherapp.screens.NotesScreen.NotesHomeScreen
import com.example.teacherapp.screens.NotesScreen.NotesViewmodel
import com.example.teacherapp.screens.NotesScreen.SelectNoteScreen
import com.example.teacherapp.screens.attendance.AddAttendanceScreen
import com.example.teacherapp.screens.attendance.AttendanceHomeScreen
import com.example.teacherapp.screens.attendance.CreateAttendance_viewModel
import com.example.teacherapp.screens.attendance.GetAllTeacherSubj_Viewmodel
import com.example.teacherapp.screens.attendance.GetStudentBySection_Viewmodel
import com.example.teacherapp.screens.attendance.ViewModel_to_add_subj
import com.example.teacherapp.screens.attendance.ShowAttendance
import com.example.teacherapp.screens.attendance.TakeAttendance
import com.example.teacherapp.screens.attendance.showAttendanceViewmodel
import com.example.teacherapp.screens.internalmarks.AddInternalMarks
import com.example.teacherapp.screens.internalmarks.AddInternalMarksViewmodel
import com.example.teacherapp.screens.internalmarks.AddInternalMarksViewmodel_Factory
import com.example.teacherapp.screens.internalmarks.InternalMarksHomeScreen
import com.example.teacherapp.screens.internalmarks.ShowInternalMarks
import com.example.teacherapp.screens.internalmarks.ShowMarksViewModel
import com.example.teacherapp.screens.internalmarks.giveinternalmarks
import com.example.teacherapp.screens.notices.NoticeHomeScreen
import com.example.teacherapp.screens.notices.NoticeScreen
import com.example.teacherapp.screens.notices.NoticeViewmodel
import kotlinx.serialization.json.Json

@Composable
fun CampusConnectNavigation() {

    val navController = rememberNavController()

    val loginviewmodel:LoginViewmodel= hiltViewModel()

//    val domain = "sangyog-cc.vercel.app"
    val domain = "campus-connect-dag0d0dzfphceser.centralindia-01.azurewebsites.net"


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

            val mydetailsViewmodel:HomeScreenViewModel= hiltViewModel<HomeScreenViewModel>()

            HomeScreen(navController = navController,
                loginViewmodel = loginviewmodel,
                homeScreenViewModel = mydetailsViewmodel)
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


        //for take attendance we have to pass 3 things to backend i.e faculty,semester and section
        //so we pass these 3 things from attendance homeScreen
        val route1=campusConnectScreen.TakeAttendanceScreen.name
        composable("$route1/{details}",
            arguments = listOf(
                navArgument(name ="details"){
                    type=NavType.StringType
                })){ BackStackEntry->

            val viewmodel:GetStudentBySection_Viewmodel= hiltViewModel<GetStudentBySection_Viewmodel>()

            val viewmodel2:CreateAttendance_viewModel= hiltViewModel<CreateAttendance_viewModel>()

            BackStackEntry.arguments?.getString("details").let {details->

                val subjectDecode = details?.let {
                    Json.decodeFromString<Subject>(it)
                }
                Log.d("akriti", "from navigation : $subjectDecode ")
                if(details != null){
                    TakeAttendance(navController = navController,
                        getstudent = viewmodel,
                        subject = subjectDecode,
                        createAttendace = viewmodel2)
                }
            }

        }


        val route2=campusConnectScreen.ShowAttendanceScreen.name
        composable("$route2/{details}",
            arguments = listOf(
                navArgument(name ="details"){
                    type=NavType.StringType
                })) { BackStackEntry ->

            val showAttendanceViewModel = hiltViewModel<showAttendanceViewmodel>()

            BackStackEntry.arguments?.getString("details").let { details ->

                val subjectDecode = details?.let {
                    Json.decodeFromString<Subject>(it)
                }

                    if(details != null){
                        ShowAttendance(navController = navController,
                           showAttendanceViewmodel =  showAttendanceViewModel,
                            subject= subjectDecode
                            )
                    }

                }
        }

        composable(campusConnectScreen.NoticeHomeScreen.name){
            //viewmodel to show all subject of teacher
            val AttendanceViewModel:GetAllTeacherSubj_Viewmodel= hiltViewModel<GetAllTeacherSubj_Viewmodel>()
            NoticeHomeScreen(navController,AttendanceViewModel)
        }

        val route3=campusConnectScreen.NoticeScreen.name
        composable("$route3/{details}",
            arguments = listOf(
                navArgument(name ="details"){
                    type=NavType.StringType })) { BackStackEntry ->

            val noticeViewmodel: NoticeViewmodel = hiltViewModel<NoticeViewmodel>()

            BackStackEntry.arguments?.getString("details").let { details ->

                val subjectDecode = details?.let {
                    Json.decodeFromString<Subject>(it)
                }
                if(details != null) {
                    NoticeScreen(navController=navController,
                        viewmodel=noticeViewmodel,
                        details=subjectDecode)
                }
            }
        }

        composable(campusConnectScreen.NotesHomeScreen.name){
            //viewmodel to show all subject of teacher
            val AttendanceViewModel:GetAllTeacherSubj_Viewmodel= hiltViewModel<GetAllTeacherSubj_Viewmodel>()
            NotesHomeScreen(navController,AttendanceViewModel)
        }

        val route4=campusConnectScreen.SelectNoteScreen.name
        composable("$route4/{details}",
            arguments = listOf(
                navArgument(name="details"){
                    type=NavType.StringType
                })){BackStackEntry->

            val sendNotesViewmodel:NotesViewmodel= hiltViewModel<NotesViewmodel>()

            BackStackEntry.arguments?.getString("details").let {details->

                val subjectDecode= details?.let {
                    Json.decodeFromString<Subject>(it)
                }

                if(details !=null){
                    SelectNoteScreen(navController = navController,
                        notesViewModel = sendNotesViewmodel,
                        details = subjectDecode)
                }

            }
        }

        composable(campusConnectScreen.AddNotesScreen.name){
            //viewmodel to add new subject by teacher
            val addNewsubj = hiltViewModel<ViewModel_to_add_subj>()
            AddNotesScreen(navController,addNewsubj)
        }



        composable(campusConnectScreen.InternalMarksHomeScreen.name){
            //viewmodel to show all subject of teacher
            val AttendanceViewModel:GetAllTeacherSubj_Viewmodel= hiltViewModel<GetAllTeacherSubj_Viewmodel>()

            InternalMarksHomeScreen(
                navController=navController,
                viewModel = AttendanceViewModel)
        }

        composable(campusConnectScreen.AddInternalMarksScreen.name){
            //viewmodel to add new subject by teacher
            val addNewsubj = hiltViewModel<ViewModel_to_add_subj>()
            AddInternalMarks(navController,addNewsubj)
        }

        val route5=campusConnectScreen.GiveInternalMarksScreen.name
        composable("$route5/{details}",
            arguments = listOf(
                navArgument(name="details"){
                    type=NavType.StringType
                })){BackStackEntry->

            val viewmodel:GetStudentBySection_Viewmodel= hiltViewModel<GetStudentBySection_Viewmodel>()

            val addInternalMarksViewmodel:AddInternalMarksViewmodel = hiltViewModel<AddInternalMarksViewmodel>()

            BackStackEntry.arguments?.getString("details").let {details->

                val subjectDecode= details?.let {
                    Json.decodeFromString<Subject>(it)
                }

                if(details !=null){
                    giveinternalmarks(navController = navController,
                        getstudent =viewmodel ,
                        subject = subjectDecode,
                        addInternalMarksViewmodel = addInternalMarksViewmodel)
                }

            }
        }

        //for show attendance Screen
        val route6=campusConnectScreen.ShowInternalMarksScreen.name
        composable("$route6/{details}",
            arguments = listOf(
                navArgument(name = "details"){
                    type= NavType.StringType
                })){BackStackEntry->

            val showMarksViewmodel:ShowMarksViewModel= hiltViewModel<ShowMarksViewModel>()

            BackStackEntry.arguments?.getString("details").let {details->

                val subjectDecode= details?.let {
                    Json.decodeFromString<Subject>(it)
                }
                if(details !=null){
                    ShowInternalMarks(navController=navController,
                        showmarks=showMarksViewmodel,
                        subject = subjectDecode)
                }
            }
        }
    }
}