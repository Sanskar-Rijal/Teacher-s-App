package com.example.teacherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.example.teacherapp.screens.LoginScreen.LoginScreen

import com.example.teacherapp.ui.theme.TeacherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            myapp {
                LoginScreen()
            }
        }
    }
}


@Composable
fun myapp(content:@Composable ()->Unit){
    TeacherAppTheme {
        content()
    }
}
