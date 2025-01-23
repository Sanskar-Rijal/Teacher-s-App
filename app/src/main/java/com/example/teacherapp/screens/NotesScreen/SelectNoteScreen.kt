package com.example.teacherapp.screens.NotesScreen

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.teacherapp.R
import com.example.teacherapp.components.AddNotice
import com.example.teacherapp.components.AppBarbySans
import com.example.teacherapp.components.LoadingDialog
import com.example.teacherapp.components.sansButton
import com.example.teacherapp.model.getAddedData.Subject
import com.example.teacherapp.navigation.campusConnectScreen
import com.example.teacherapp.screens.LoginScreen.LoadingState
import com.example.teacherapp.screens.notices.addNoticeField


@Composable
fun SelectNoteScreen(
    notesViewModel: NotesViewmodel,
    navController: NavController=NavController(LocalContext.current),
    details: Subject?
) {
    val context = LocalContext.current

    val loadingState by notesViewModel.state.collectAsState()

    val keyboardController = LocalSoftwareKeyboardController.current


    //state for title
    val selectedTitle = rememberSaveable {
        mutableStateOf("")
    }

    // File picker launcher
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            // Trigger sending note once the file is selected
            if (details != null) {
                notesViewModel.sendNote(
                    title = selectedTitle.value,
                    faculty = details.faculty,
                    semester = details.semester,
                    section = details.section,
                    subjectId = details.id,
                    fileUri = it
                ) {
                    Toast.makeText(context, "Note uploaded successfully!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
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
                        LoadingState.SUCCESS -> Toast.makeText(context, "Notes Added Successfully", Toast.LENGTH_SHORT).show()
                        LoadingState.FAILED -> Toast.makeText(context, "Upload Failed. Please try again", Toast.LENGTH_SHORT).show()
                        else -> Unit
                    }

                    // File picker button
                    addTitle {
                        selectedTitle.value = it
                        keyboardController?.hide()
                        filePickerLauncher.launch(arrayOf("application/pdf"))

                    }
                }
            }
        }
    }
}


@Composable
fun addTitle(
    onClick:(String)->Unit
){

    val context = LocalContext.current


    val keyboardController = LocalSoftwareKeyboardController.current


    val selectedTitle = rememberSaveable {
        mutableStateOf("")
    }


    val validNotice = rememberSaveable(selectedTitle.value){
        selectedTitle.value.isNotEmpty() //if it's not empty it will be true
    }

    Column(modifier = Modifier
        .padding(10.dp)
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {

        AddNotice(
            valueState = selectedTitle,
            labelId = "Title",
            enabled = true,
            keyboardType = KeyboardType.Unspecified,
            imeaction = ImeAction.Done,
            isSingleLine = false,
            maxlines = 5,
            onAction = KeyboardActions{
                if(!validNotice){
                    return@KeyboardActions
                }
                keyboardController?.hide()
            }
        )
        //send Button
        sansButton(text ="Select Note") {
            //now send datato viewmodel for backend
            if(!validNotice){
                Toast.makeText(context, "Add title of the name first", Toast.LENGTH_SHORT).show()
            }else{
                onClick(selectedTitle.value)
            }
        }
    }
}