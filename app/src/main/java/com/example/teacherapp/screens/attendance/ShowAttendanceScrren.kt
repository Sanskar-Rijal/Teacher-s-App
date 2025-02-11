package com.example.teacherapp.screens.attendance

import android.content.Context
import android.content.Intent
import android.media.MediaScannerConnection
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import com.example.teacherapp.R
import com.example.teacherapp.components.AppBarbySans
import com.example.teacherapp.components.LoadingDialog
import com.example.teacherapp.model.getAddedData.Subject
import com.example.teacherapp.model.getstudentbysec.studentResponse
import com.example.teacherapp.model.showAttendance.Attendance
import com.example.teacherapp.model.showAttendance.showAttendanceRequest
import com.example.teacherapp.model.showAttendance.showAttendanceResponse
import com.example.teacherapp.screens.LoginScreen.LoadingState
import com.example.teacherapp.utils.OpenExcelFile
import com.example.teacherapp.utils.getRollno
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream


@Composable
fun ShowAttendance(navController: NavController= NavController(LocalContext.current),
                   showAttendanceViewmodel: showAttendanceViewmodel,
                   subject: Subject?) {

    val data: showAttendanceResponse = showAttendanceViewmodel.item

    val uiState = showAttendanceViewmodel.state.collectAsState()

    val context= LocalContext.current


    LaunchedEffect(key1 = null) {
        if(subject != null) {
             showAttendanceViewmodel.fetchAttendance(
                section = subject.section,
                subjectCode = subject.subjectCode
            )
        }
    }

    Scaffold(
        topBar = {
            AppBarbySans(
                title = "Show Attendance",
                downloadExcel = true,
                ondownloadExcelClicked = {
                    MakeandSaveExcelFile(frontendData=subject,
                        context = context,
                        backenddata =data)
                },
                icon = Icons.AutoMirrored.Filled.ArrowBack
            ) {
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

                Column(modifier = Modifier.padding(10.dp)) {

                    if (uiState.value == LoadingState.LOADING) {
                        LoadingDialog()
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(10.dp)
                        ) {
                            items(data.attendance){ list->
                                Showatt(data=list)
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun Showatt(
    data:Attendance,
    size:Int=50,
    title:String="sans",
){
    Card(modifier = Modifier
        .height(79.dp)
        .fillMaxWidth()
        .padding(5.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ){
        Row(modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween){
            Column {
                Text(
                    text = getRollno(data.email)?:"no data",
                    modifier = Modifier.padding(bottom = 5.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 23.sp,
                    letterSpacing = 0.5.sp
                )

                Text(
                    text=data.name ?: "no data",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.LightGray,
                    textAlign = TextAlign.Center,
                    lineHeight = 23.sp
                )
            }
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFF1490CF), // Red color for total classes attended
                            fontWeight = FontWeight.Bold,
                            fontSize = 23.sp
                        )
                    ) {
                        append(data.totalClassesAttended?.toString() ?: "no data")
                    }
                    append("/") // Separator
                    withStyle(
                        style = SpanStyle(
                            color = Color.Gray, // Gray color for total classes conducted
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        )
                    ) {
                        append(data.totalClassesConducted?.toString() ?: "no data")
                    }
                },
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                lineHeight = 23.sp
            )


        }
    }
}

fun MakeandSaveExcelFile(frontendData:Subject?,
    context: Context,backenddata:showAttendanceResponse){

    try {

        //saving the file in directory
        val filename = "${frontendData?.name?:"null"} attendance " +
                "${frontendData?.faculty?:"null"}${frontendData?.section?:"null"}.xlsx"
        //saving at downloads folder
        val downloadsPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

        val file = File(downloadsPath, filename)

        // Notify MediaStore to remove stale references before file operations
        MediaScannerConnection.scanFile(context, arrayOf(file.absolutePath), null) { path, uri ->
            Log.d("kamala", "MediaScanner finished scanning: $path")
        }


        // Check if the file already exists, and delete it
        if (file.exists()) {
            Log.d("kamala", "File deletion failed, retrying...")
            val deleted = file.delete()
            if (deleted) {
                //notifying the media store
                MediaScannerConnection.scanFile(context, arrayOf(file.absolutePath), null, null)
            }
        }
         //if there is no files we create a new one or override it after deleting
        val workBook = XSSFWorkbook() //creating excel workbook

        //creating first sheet inside workbook
        val sheet: Sheet = workBook.createSheet("Attendance${frontendData?.faculty?:"null"}${frontendData?.section?:"null"}")

        //creating header row i.e first row
        val headerRow = sheet.createRow(0)

        headerRow.createCell(0).setCellValue("Student Name")
        headerRow.createCell(1).setCellValue("Roll No")
        headerRow.createCell(2).setCellValue("Class Attended")

        //now giving the row data of attendance
        backenddata.attendance.forEachIndexed { index, attendance ->
            val row = sheet.createRow(index + 1)
            row.createCell(0).setCellValue(attendance.name?:"no data")
            row.createCell(1).setCellValue(getRollno(attendance.email)?:"no data")
            row.createCell(2).setCellValue("${attendance.totalClassesAttended?:"0"}/${attendance.totalClassesConducted?:"0"}")
        }

        FileOutputStream(file).use { outputStream ->
            Log.d("kamala", "inside fileoutput stream")
            workBook.write(outputStream)
            workBook.close()
            Log.d("kamala", "finished work in fileoutput stream")
        }

        // Notify MediaStore of the new file
        MediaScannerConnection.scanFile(context, arrayOf(file.absolutePath), null, null)

        Toast.makeText(context, "File Saved  Successfully", Toast.LENGTH_SHORT).show()

        //open the file after it is saved
        OpenExcelFile(context,file)
    }
    catch (ex:Exception){
        Log.d("kamala", "${ex.localizedMessage}")
        Toast.makeText(context, "Failed To Save File :${ex.localizedMessage}", Toast.LENGTH_SHORT).show()
    }

}