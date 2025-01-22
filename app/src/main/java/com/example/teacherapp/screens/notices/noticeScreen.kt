package com.example.teacherapp.screens.notices

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.teacherapp.R
import com.example.teacherapp.components.AppBarbySans
import com.example.teacherapp.components.LoadingDialog
import com.example.teacherapp.components.sansButton
import com.example.teacherapp.model.getAddedData.Subject
import com.example.teacherapp.navigation.campusConnectScreen
import com.example.teacherapp.screens.LoginScreen.LoadingState


@Composable
fun NoticeScreen(navController:NavController=NavController(LocalContext.current),
                 viewmodel: NoticeViewmodel,
                 details:Subject?
) {
    val uiState = viewmodel.state.collectAsState()

    val context = LocalContext.current

    Scaffold(
        topBar = {
            AppBarbySans(
                title = "Notice",
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
                contentScale = ContentScale.FillBounds)

            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentpadding),
                color = Color.Transparent) {

                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    addNoticeField(){title,description->
                        viewmodel.sendNotice(
                            description = description,
                            faculty = details?.faculty?:"COMPUTER",
                            section = details?.section?:"CD",
                            semester = details?.semester?:"6",
                            subjectId = details?.id?:0,
                            title = title
                        ){
                            Toast.makeText(context, "Notice Sent Successfully", Toast.LENGTH_SHORT).show()
                            navController.navigate(campusConnectScreen.HomeScreen.name)
                        }
                    }
                }
                if(uiState.value== LoadingState.LOADING){
                    LoadingDialog()
                }

            }

        }
    }
}

@Composable
fun addNoticeField(
    onClick:(String,String)->Unit
){

    val context = LocalContext.current


    val keyboardController = LocalSoftwareKeyboardController.current


    val selectedTitle = rememberSaveable {
        mutableStateOf("")
    }

    val selectedDescription = rememberSaveable{
        mutableStateOf("")
    }

//    val selectedSemester = remember{
//        mutableStateOf("")
//    }
//
//    val selectedSection = remember {
//        mutableStateOf("")
//    }
//
//    val selectedFaculty = remember {
//        mutableStateOf("")
//    }


    val validNotice = rememberSaveable(selectedTitle.value,selectedDescription.value){
        selectedTitle.value.isNotEmpty() && selectedDescription.value.isNotEmpty() //if it's not empty it will be true
    }

//    val faculty = listOf("COMPUTER", "CIVIL")
//
//    val sem = listOf("1", "2", "3", "4", "5", "6", "7", "8")
//
//    //subject id pass through lamda function from card which is clicked
//
//    val sections= remember(selectedFaculty.value) {
//        sectionsMap[selectedFaculty.value]?.map {
//            it.sec
//        }?: listOf("Select faculty First")
//    }

    Column(modifier = Modifier
        .padding(10.dp)
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {

        AddNotice(
            valueState = selectedTitle,
            labelId = "Title",
            enabled = true,
            keyboardType = KeyboardType.Unspecified,
            imeaction = ImeAction.Next,
            isSingleLine = false,
            maxlines = 5
        )

        AddNotice(
            valueState = selectedDescription,
            labelId = "Description",
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

        /**

        DropdownMenuComponent(
            label = "Select Faculty",
            options = faculty,
            selectedOption =  selectedFaculty.value,
            onOptionSelected = { selectedFaculty.value = it }
        )

        DropdownMenuComponent(
            label = "Select Section",
            options = sections,
            selectedOption = selectedSection.value,
            onOptionSelected = { selectedSection.value = it }
        )

        DropdownMenuComponent(
            modifier =
            if(selectedFaculty.value.isNotEmpty())
                Modifier.height(300.dp)
            else
                Modifier,
            label = "Select Semester",
            options = sem,
            selectedOption = selectedSemester.value,
            onOptionSelected = { selectedSemester.value = it }
        )

        **/
        //subject id kasri kasri pathauna paro hai ta

        //send Button
        sansButton(text ="Send") {
            //now send datato viewmodel for backend
            if(!validNotice){
                Toast.makeText(context, "Add a Notice First", Toast.LENGTH_SHORT).show()
            }else{
                onClick(selectedTitle.value,selectedDescription.value)
            }
        }
    }

}

@Composable
fun AddNotice(modifier: Modifier=Modifier,
              valueState: MutableState<String>,
              labelId:String="Email",
              enabled:Boolean=true,
              keyboardType: KeyboardType = KeyboardType.Text,
              imeaction: ImeAction = ImeAction.Next,
              isSingleLine:Boolean=true,
              onAction: KeyboardActions = KeyboardActions.Default,
              maxlines:Int=1
){
    OutlinedTextField(
        value =valueState.value,
        onValueChange = {valueState.value=it},
        label = {
            Text(text = labelId)
        },
        singleLine = isSingleLine,
        maxLines = maxlines,
        textStyle = TextStyle(fontSize = 19.sp,
            color = MaterialTheme.colorScheme.onBackground),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled=enabled,
        shape = RoundedCornerShape(15.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType,
            imeAction = imeaction),
        keyboardActions = onAction,
    )
}
