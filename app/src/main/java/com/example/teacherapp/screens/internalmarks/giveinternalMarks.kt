package com.example.teacherapp.screens.internalmarks

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.teacherapp.R
import com.example.teacherapp.components.AddNotice
import com.example.teacherapp.components.AppBarbySans
import com.example.teacherapp.components.LoadingDialog
import com.example.teacherapp.components.sansButton
import com.example.teacherapp.model.AddInternalMarks.MarksData
import com.example.teacherapp.model.getAddedData.Subject
import com.example.teacherapp.model.getstudentbysec.StudentX
import com.example.teacherapp.model.getstudentbysec.studentResponse
import com.example.teacherapp.navigation.campusConnectScreen
import com.example.teacherapp.screens.LoginScreen.LoadingState
import com.example.teacherapp.screens.attendance.GetStudentBySection_Viewmodel
import com.example.teacherapp.utils.getRollno
import kotlin.math.log


@Composable
fun giveinternalmarks(navController: NavController = NavController(LocalContext.current),
                   getstudent: GetStudentBySection_Viewmodel = hiltViewModel(),
                   subject: Subject?,
                      addInternalMarksViewmodel: AddInternalMarksViewmodel
) {


    // State to store marks for each student
    val studentMarks = remember {
        mutableStateListOf<MarksData>()
    }

    /**
     * mutableMapOf<String, String>():
     *
     *     This creates an empty mutable map where both the keys and values are of type String.
     *     Keys (String): Represent the unique identifier of the student, typically the studentId.
     *     Values (String): Represent the marks associated with each student.
     */
    // State for the selected student
    var selectedStudent by remember {
        mutableStateOf(mutableMapOf<String,String>())
    }


    val AddinternalMarksState= addInternalMarksViewmodel.state.collectAsState()


    val context= LocalContext.current

    // Fetch the student data from the ViewModel
    val data: studentResponse = getstudent.item

    val uiState = getstudent.state.collectAsState()


    LaunchedEffect(key1 = null) {
        if (subject != null) {
            getstudent.getStudentBySection(
                faculty = subject.faculty,
                semester = subject.semester,
                section = subject.section
            )
        }
    }


    Scaffold(
        topBar = {
            AppBarbySans(
                title = "Add Internal Marks",
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
                if (uiState.value == LoadingState.LOADING) {
                    LoadingDialog()
                } else {
                    Column(modifier = Modifier.padding(5.dp)) {

                        LazyColumn(
                            modifier = Modifier.padding(10.dp),
                            contentPadding = PaddingValues(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            items(data.students) { student ->
                                showStudent(student = student,
                                    marks = selectedStudent[student.id]?:"",
                                ){studentid,marks->

                                    val existing = studentMarks.find{
                                        it.studentId == studentid
                                    }
                                    if (existing != null) {
                                        studentMarks.remove(existing)
                                    }
                                    studentMarks.add(
                                        MarksData(
                                            marks=marks,
                                            studentId = studentid
                                        )
                                    )
                                    selectedStudent= selectedStudent.toMutableMap().apply {
                                        put(studentid,marks)
                                    }

                                }
                            }
                            item{
                                sansButton(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "Save"
                                ) {
                                    //when button is pressed then data is to be sent to backend

                                    Log.d("jagriti", "${studentMarks} ")

                                    addInternalMarksViewmodel.addInternalMarks(
                                        marksData = studentMarks,
                                        subjectId = subject!!.id){
                                        Toast.makeText(context, "Marks Added Successfully", Toast.LENGTH_SHORT).show()
                                        navController.popBackStack()
                                    }
                                }

                            }
                        }
                        when (AddinternalMarksState.value) {
                            LoadingState.LOADING -> LoadingDialog()
                            LoadingState.SUCCESS -> {
                                //Toast.makeText(context, "Notes Added Successfully", Toast.LENGTH_SHORT).show()
                            }
                            LoadingState.FAILED -> {
                                Log.d("bibek", "${AddinternalMarksState.value.message} ")
                                Toast.makeText(context, "Marks Already Added", Toast.LENGTH_SHORT).show()

                            }
                            else -> Unit
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun showStudent(
    student: StudentX,
    size:Int=50,
    marks:String?=null,
    onClick:(String,String)->Unit
) {
    //to show the dialog box
    var showDialouge by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable {
                showDialouge = true
            }
          ,
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                   // text = student.name?:"no data",
                    text = getRollno(student.email) ?:"no data",
                    modifier = Modifier.padding(bottom = 5.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 23.sp,
                    letterSpacing = 0.5.sp
                )
                Text(
                    //text = student.email?:"no data",
                    text = student.name?:"no data",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray.copy(0.7f),
                    textAlign = TextAlign.Center,
                    lineHeight = 23.sp
                )

            }
            marks?.let {
                Text(
                    text = it,
                    color = Color(0xFF1490CF),
                    modifier = Modifier.padding(10.dp),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    lineHeight = 23.sp,
                    fontSize = 20.sp
                )
            }

        }
    }

    if(showDialouge){
        giveinternalMarks(
            onSave = {marks->
                showDialouge=false

                onClick(student.id, marks)
            },
            onTapOutside = {
                showDialouge=false
            }
        )
    }
}


@Composable
@Preview
fun giveinternalMarks(
    subjectName: String = "SANSKAR RIJAL",
    onSave: (String) -> Unit = {},
    onTapOutside: () -> Unit = {}
){
    val context = LocalContext.current

    val keyboardController = LocalSoftwareKeyboardController.current

    val marks = rememberSaveable {
        mutableStateOf("")
    }

    val valid = rememberSaveable(marks.value) {
        marks.value.isNotEmpty()
    }

    AlertDialog(
        modifier = Modifier.fillMaxWidth(),
        title = {
            Text(
                text = subjectName,
                //modifier = Modifier.fillMaxWidth(),
               // textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        text = {
            Text(
                text = "PUR078BCT079",
               // modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium,
               // textAlign = TextAlign.Center
            )
        },
        onDismissRequest = {
            onTapOutside()
        },
        confirmButton = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                AddNotice(
                    modifier = Modifier,
                    valueState = marks,
                    labelId = "Description",
                    enabled = true,
                    keyboardType = KeyboardType.Number,
                    imeaction = ImeAction.Done,
                    isSingleLine = false
                )


                sansButton(text = "Save",
                    modifier = Modifier.fillMaxWidth()) {

                    if(!valid){
                        Toast.makeText(context, "Enter Marks First", Toast.LENGTH_SHORT).show()
                    }else{
                        keyboardController?.hide()
                        onSave(marks.value)
                    }

                }
            }
        },
        containerColor = Color.White
    )
}