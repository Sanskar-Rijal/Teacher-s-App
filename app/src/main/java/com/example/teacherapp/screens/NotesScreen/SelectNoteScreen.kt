package com.example.teacherapp.screens.NotesScreen

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.teacherapp.R
import com.example.teacherapp.components.AddNotice
import com.example.teacherapp.components.AppBarbySans
import com.example.teacherapp.components.LoadingDialog
import com.example.teacherapp.components.sansButton
import com.example.teacherapp.model.getAddedData.Subject
import com.example.teacherapp.model.showAttendance.Attendance
import com.example.teacherapp.navigation.campusConnectScreen
import com.example.teacherapp.screens.LoginScreen.LoadingState
import com.example.teacherapp.utils.getFileNameFromUri


@Composable
fun SelectNoteScreen(
    notesViewModel: NotesViewmodel,
    navController: NavController = NavController(LocalContext.current),
    details: Subject?
) {
    val context = LocalContext.current

    val loadingState by notesViewModel.state.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    // State for title and selected file URI
    val selectedTitle = rememberSaveable { mutableStateOf("") }
    val selectedFileUri = rememberSaveable { mutableStateOf<Uri?>(null) }

    var pdfName by remember {
        mutableStateOf("")
    }

    // File picker launcher
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            val fileName = getFileNameFromUri(context, it)
            pdfName=fileName
            selectedFileUri.value = it
            Toast.makeText(context, "PDF selected: ${fileName}", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            AppBarbySans(
                title = "NOTES",
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                onBackArrowClicked = {
                    navController.popBackStack()
                }
            )
        }
    ) { contentPadding ->
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
                    .padding(contentPadding),
                color = Color.Transparent
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Show loading state
                    when (loadingState) {
                        LoadingState.LOADING -> LoadingDialog()
                        LoadingState.SUCCESS -> {
                            Toast.makeText(context, "Notes Added Successfully", Toast.LENGTH_SHORT).show()
                        }
                        LoadingState.FAILED -> Toast.makeText(context, "Upload Failed. Please try again", Toast.LENGTH_SHORT).show()
                        else -> Unit
                    }

                    // Title input field
                    AddNotice(
                        valueState = selectedTitle,
                        labelId = "Title",
                        enabled = true,
                        keyboardType = KeyboardType.Text,
                        imeaction = ImeAction.Done,
                        isSingleLine = true,
                        maxlines = 1
                    )

                    // File picker button
                    sansButton(text = "Select Note") {
                        if (selectedTitle.value.isEmpty()) {
                            Toast.makeText(context, "Please enter a title first", Toast.LENGTH_SHORT).show()
                        } else {
                            filePickerLauncher.launch(arrayOf("application/pdf"))
                        }
                    }

                    // Show selected file in a card-like structure
                    selectedFileUri.value?.let { uri ->
                        ShowPdf(title = pdfName?: "No name")
                        keyboardController?.hide()
                        sansButton(text ="Send Note") {
                                val title = selectedTitle.value
                                val fileUri = selectedFileUri.value

                                if (title.isEmpty()) {
                                    Toast.makeText(context, "Please enter a title", Toast.LENGTH_SHORT).show()
                                } else if (fileUri == null) {
                                    Toast.makeText(context, "Please select a PDF", Toast.LENGTH_SHORT).show()
                                } else {
                                    if (details != null) {
                                        notesViewModel.sendNote(
                                            title = title,
                                            faculty = details.faculty,
                                            semester = details.semester,
                                            section = details.section,
                                            subjectId = details.id,
                                            fileUri = fileUri
                                        ) {
                                            Toast.makeText(context, "Note uploaded successfully!", Toast.LENGTH_SHORT).show()
                                            navController.navigate(campusConnectScreen.HomeScreen.name)
                                        }
                                    }
                                }

                        }

                    }
                }
            }
        }
    }
}




@Preview
@Composable
fun ShowPdf(
     title:String="sans"
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
            .padding(20.dp)
            .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = title?:"No name",
                    modifier = Modifier.padding(bottom = 5.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 23.sp,
                    letterSpacing = 0.5.sp
                )
            Icon(imageVector = Icons.Default.PictureAsPdf,
                contentDescription = "pdf", tint = Color.Black.copy(alpha = 0.4f))
        }
    }
}