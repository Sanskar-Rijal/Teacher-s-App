package com.example.teacherapp.screens.LoginScreen

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import com.example.teacherapp.data.DataorException
import com.example.teacherapp.model.login.LoginResponse
import com.example.teacherapp.repository.AuthRepository
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewmodel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

   var item:LoginResponse by mutableStateOf(LoginResponse(success =false  , message = ""))
    var isLoading:Boolean by mutableStateOf(false)

    //for making state for loading idle etc
    //private val _loading = MutableLiveData(false)

    private val _state= MutableStateFlow(LoadingState.IDLE)

    val state= _state.asStateFlow()

    /**
     * handling cookes checking every thing etc
     */
    var isLoggedIn by mutableStateOf(false)

    fun checkStatusLoginStatus(domain:String){
        val cookies = repository.getStoredCookies(domain)
        isLoggedIn = cookies.any{it.contains("token")}
    }

    fun loginTeacher(email: String, password: String,furtherAction:()->Unit) {
        viewModelScope.launch(Dispatchers.Main) {

            _state.value= LoadingState.LOADING
            //loginResponse.value = repository.loginTeacher(email, password)
            if(email.isEmpty() || password.isEmpty()){
                return@launch
            }
            try {
                val response = repository.loginTeacher(email,password)
                when(response){

                    is DataorException.Success->{

                        item=response.data!!
                      //  Log.d("april", "success: ${item} ")
                        if(item.success == true){

                            _state.value= LoadingState.SUCCESS

                            furtherAction()
                           // Log.d("samsth", "inside successblock ")
                            isLoading=false
                        }
                    }
                    is DataorException.Error->{
                        _state.value= LoadingState.FAILED
                        _state.value.message=response.message
                      //  Log.d("april", "error: ${response.message} ")
                        isLoading=false
                    }
                    else ->{
                        _state.value= LoadingState.IDLE
                        isLoading=false
                    }
                }
            } catch (e: Exception) {
                isLoading=false
            }
        }
    }

    fun logoutTeacher() {
        repository.logoutTeacher()
    }
}
