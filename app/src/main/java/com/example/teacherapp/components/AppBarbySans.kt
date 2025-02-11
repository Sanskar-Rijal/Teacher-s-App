package com.example.teacherapp.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarbySans(
    title: String="Attendance",
    icon:ImageVector?=null,
    downloadExcel:Boolean=false,
    ondownloadExcelClicked:()->Unit={},
    onBackArrowClicked:()->Unit={}
) {

    CenterAlignedTopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = title,
                    color = Color.Black,
                    style = TextStyle(fontWeight = FontWeight.Bold,
                        fontSize = 23.sp)
                )
            }
        },
        navigationIcon = {
            //navigation icon is shown at the front of the Screen

           if(icon!=null){
                IconButton(onClick = {
                    onBackArrowClicked.invoke()
                }){
                    Icon(imageVector = icon,
                        contentDescription = "back arrow",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer)
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(Color(0xFFF5F4F4)),
        actions = {
            //show only on ShowAttendanceScreen
            if(downloadExcel){
              IconButton(onClick = {
                  ondownloadExcelClicked.invoke() }){

                  Icon(imageVector = Icons.Default.Download,
                      contentDescription = "Download Excel File",
                      tint = MaterialTheme.colorScheme.onPrimaryContainer)
              }
            }

        }
    )
}