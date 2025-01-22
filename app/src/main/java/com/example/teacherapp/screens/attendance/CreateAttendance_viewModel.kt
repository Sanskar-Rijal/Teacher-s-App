package com.example.teacherapp.screens.attendance

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherapp.data.DataorException
import com.example.teacherapp.model.createAttendance.createAttendanceRequest
import com.example.teacherapp.model.createAttendance.createAttendanceResponse
import com.example.teacherapp.model.getstudentbysec.studentResponse
import com.example.teacherapp.repository.createAttendance_Repository
import com.example.teacherapp.screens.LoginScreen.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateAttendance_viewModel @Inject constructor(
    private val repository: createAttendance_Repository):ViewModel() {

        var item:createAttendanceResponse by mutableStateOf(
            createAttendanceResponse("",false)
        )

    private val _state = MutableStateFlow(LoadingState.IDLE)

    val state = _state.asStateFlow()

    fun createAttendance(attendanceRequest: createAttendanceRequest,onSuccess:()->Unit){

        viewModelScope.launch {

                _state.value = LoadingState.LOADING
           // Log.d("saurav", "before try${state.value} ")

                try {
                    val response = repository.createAttendance(attendanceRequest)

                    when(response){
                        is DataorException.Success ->{
                            item = response.data!!

                            if(item.success == true){
                                onSuccess.invoke()
                               // Log.d("saurav", "inside if ${state.value} ")
                                _state.value = LoadingState.SUCCESS
                            }
                            //_state.value = LoadingState.SUCCESS
                        }
                        else ->{
                            _state.value = LoadingState.FAILED
                        }
                    }
                }catch (ex:Exception){
                    _state.value = LoadingState.FAILED
                }
        }

    }

}