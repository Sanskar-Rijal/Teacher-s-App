package com.example.teacherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.example.teacherapp.navigation.CampusConnectNavigation
import com.example.teacherapp.ui.theme.TeacherAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            myapp {
                CampusConnectNavigation()
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
