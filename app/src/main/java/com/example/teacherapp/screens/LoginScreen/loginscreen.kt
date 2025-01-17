package com.example.teacherapp.screens.LoginScreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.HideSource
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.navigation.NavController
import com.example.teacherapp.R
import com.example.teacherapp.components.LoadingDialog
import com.example.teacherapp.navigation.campusConnectScreen
import org.w3c.dom.Text
import kotlin.math.log
import kotlin.math.sin


@Composable
fun LoginScreen(navController: NavController,loginViewmodel: LoginViewmodel){
    val uiState =loginViewmodel.state.collectAsState()  //to be modified with api for loading animation

    val context= LocalContext.current

    Box {

        Image(
            painter = painterResource(R.drawable.background),
            modifier = Modifier.fillMaxSize(),
            contentDescription = "background",
            contentScale = ContentScale.FillBounds
        )

        Surface(modifier = Modifier
            .padding()
            .fillMaxSize(),
            color = Color.Transparent ) {

            Column( modifier = Modifier.padding( 12.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start) {

                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement =Arrangement.End) {

                    Text(text = "Welcome !",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold)
                }

                Column(modifier = Modifier.padding(10.dp)) {

                    Icon(painter = painterResource(R.drawable.hat),
                        contentDescription = "icon",
                        modifier = Modifier.size(80.dp),
                        tint = Color.Black)

                    Text(text = "Campus\nConnect",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White)
                }

                Spacer(modifier = Modifier.height(120.dp))

                Column(modifier = Modifier.padding(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 10.dp)) {

                    Text(text = "Login",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.ExtraBold)
                    Text(text = "please sign in to continue",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray
                    )
                }

                UserForm(navController = navController){email,pass->
                    loginViewmodel.loginTeacher(email,pass){
                        navController.navigate(campusConnectScreen.HomeScreen.name)
                    }
                    Log.d("sangyog", "LoginScreen: ${uiState.value} ")
                }
                if(uiState.value== LoadingState.LOADING){
                    LoadingDialog()
                }
                if(uiState.value== LoadingState.FAILED){
                    Toast.makeText(context, uiState.value.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}

@Composable

fun UserForm(
    navController: NavController,
    onDone:(String,String) ->Unit ={email,pass ->}
){

    val email = rememberSaveable {
        mutableStateOf("")
    }

    val password = rememberSaveable{
        mutableStateOf("")
    }

    //to show password
    val passwordVisibility = rememberSaveable {
        mutableStateOf(false)
    }

    //making sure the email is in the pattern ioepc.edu.np
    val emailPattern = "^[a-zA-Z0-9._%+-]+@ioepc\\.edu\\.np$".toRegex()

    val keyboardController = LocalSoftwareKeyboardController.current //we can hide keyboard

    val valid = remember(email.value,password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty() //if its empty it will written false
    }

    //making enable or disbale for button
    val enable = remember(email.value,password.value){
        emailPattern.matches(email.value) && password.value.trim().isNotEmpty()
    }


    val modifier = Modifier
        .height(270.dp)
        .padding(start = 20.dp)
        //.background(MaterialTheme.colorScheme.background)
        .verticalScroll(rememberScrollState()) //allows us to scroll when the screen size is too small


    Column (modifier=modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top){

        EmailField(
            email = email,
            labelid = "Email",
            imeAction = ImeAction.Next,
            keyboardtype = KeyboardType.Email,
            isSingleLine = true
        )

        Spacer(modifier=Modifier.height(10.dp))

        PasswordField(
            passwordState = password,
            labelid = "Password",
            imeAction = ImeAction.Done,
            onAction = KeyboardActions {
                if(!valid) { //checking whether the inputs are not empty
                    return@KeyboardActions
                }
                //use lamda to send data
                keyboardController?.hide()
                                       },
            passwordvisibility = passwordVisibility,
            maxlines = 1,
            keyboardtype = KeyboardType.Password
        )

        Text(text = "Forgot Password?",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF5329C5),
            modifier = Modifier
                .padding(10.dp)
                .clickable {

                }
        )
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End){
            LoginButton(
                navController=navController,
                Textid = "Login",
                validinput =enable){
                onDone(email.value.trim(),password.value.trim())
            }
        }
    }

}




@Composable
fun EmailField(
    email:MutableState<String>,
    labelid:String,
    imeAction: ImeAction= ImeAction.Default,
    onAction:KeyboardActions=KeyboardActions.Default,
    maxlines:Int=1,
    keyboardtype: KeyboardType = KeyboardType.Unspecified,
    isSingleLine:Boolean
){
    TextField(
        value = email.value,
        onValueChange = { email.value = it },
        label = { Text(text = labelid) },
        modifier = Modifier
            .padding(10.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFFF5F4F4),
            focusedContainerColor = Color(0xFFF5F4F4)
        ),
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.username_icon),
                contentDescription = "email",
                modifier = Modifier.size(30.dp)
            )
        },
        singleLine = isSingleLine,
        keyboardActions =onAction,
        maxLines = maxlines,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardtype, imeAction = imeAction)
    )
}



@Composable
fun PasswordField(
    passwordState:MutableState<String>,
    labelid:String,
    imeAction: ImeAction= ImeAction.Done,
    onAction:KeyboardActions=KeyboardActions.Default,
    passwordvisibility:MutableState<Boolean>,
    maxlines:Int=1,
    keyboardtype: KeyboardType = KeyboardType.Unspecified

){

    val visualTransformation = if(passwordvisibility.value) VisualTransformation.None
    else
        PasswordVisualTransformation()

    TextField(
        value = passwordState.value,
        onValueChange = { passwordState.value = it },
        label = { Text(text = labelid) },
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .background(Color.White), // Set the background color to white
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFFF5F4F4),
            focusedContainerColor = Color(0xFFF5F4F4)
        ),
        trailingIcon = {

            if(passwordvisibility.value){
                IconButton(onClick ={passwordvisibility.value= !passwordvisibility.value} ) {
                    Icon(imageVector = Icons.Default.HideSource, contentDescription = "icon")
                }
            }
            else{
                IconButton(onClick = {passwordvisibility.value =!passwordvisibility.value}) {
                    Icon(imageVector = Icons.Default.RemoveRedEye, contentDescription = "icon")
                }
            }
        },
        maxLines = maxlines,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardtype, imeAction = imeAction),
        visualTransformation = visualTransformation,
        keyboardActions = onAction,
        singleLine = true
    )
}




@Composable
fun LoginButton(Textid:String,
                navController: NavController,
                validinput:Boolean,
                onClick:()->Unit){

    val context = LocalContext.current

    Button(onClick = {
        if(validinput){
            onClick.invoke()
        }
        else{
            Toast.makeText(context, "Make sure email is at format xxx@ioepc.edu.np", Toast.LENGTH_SHORT).show()
        }
    },
        //shape = ButtonDefaults.shape,
        enabled = true,
        modifier = Modifier.padding(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1490CF)),
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 10.dp)
        ) {

        Text(text = Textid,
            modifier = Modifier.padding(end = 10.dp),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White)
        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "button")
    }
}