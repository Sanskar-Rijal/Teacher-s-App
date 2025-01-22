package com.example.teacherapp.screens.attendance

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.util.fastCbrt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherapp.data.DataorException
import com.example.teacherapp.model.showAttendance.Subject
import com.example.teacherapp.model.showAttendance.showAttendanceRequest
import com.example.teacherapp.model.showAttendance.showAttendanceResponse
import com.example.teacherapp.repository.ShowAttendanceRepository
import com.example.teacherapp.screens.LoginScreen.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class showAttendanceViewmodel @Inject constructor(
    private val repository: ShowAttendanceRepository
):ViewModel() {

    var item:showAttendanceResponse by mutableStateOf(
        showAttendanceResponse(
            attendance = emptyList(),
            subject =Subject(
                code="",
                faculty = "",
                name = "",
                section="",
                semester = ""
            ) ,
            success = false
        )
    )

    private val _state = MutableStateFlow(LoadingState.IDLE)

    val state = _state.asStateFlow()

    fun fetchAttendance(
        section:String,
        subjectCode:String
    ){
        viewModelScope.launch {

            _state.value = LoadingState.LOADING

            try {

                val request = showAttendanceRequest(
                    section = section,
                    subjectCode = subjectCode
                )

                val response = repository.showAttendance(request)

                when(response){

                    is DataorException.Success -> {

                        item = response.data!!

                        if (item.success == true) {
                            _state.value = LoadingState.SUCCESS
                        }
                    }
                        is DataorException.Error -> {
                            _state.value = LoadingState.FAILED
                            _state.value.message = response.message
                            Log.d("april", "error: ${response.message} ")
                        }

                        else -> {
                            _state.value = LoadingState.IDLE
                        }
                    }
            }catch (e:Exception){
                _state.value = LoadingState.FAILED
            }
        }
    }


}

