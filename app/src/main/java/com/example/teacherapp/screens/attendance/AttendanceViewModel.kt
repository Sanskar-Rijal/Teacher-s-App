package com.example.teacherapp.screens.attendance

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherapp.data.DataorException
import com.example.teacherapp.model.getAddedData.getsubjects
import com.example.teacherapp.model.login.LoginResponse
import com.example.teacherapp.repository.AuthRepository
import com.example.teacherapp.repository.Get_Teacher_Subjects
import com.example.teacherapp.screens.LoginScreen.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AttendanceViewModel @Inject constructor(private val repository: Get_Teacher_Subjects):ViewModel() {

    var item: getsubjects by mutableStateOf(getsubjects(subjects = emptyList(), success = false))

    var isLoading: Boolean by mutableStateOf(false)

    //for making state for loading idle etc
    //private val _loading = MutableLiveData(false)

    private val _state = MutableStateFlow(LoadingState.IDLE)

    val state = _state.asStateFlow()

    init {
        loadsubj()
    }

    private fun loadsubj(){
        fetchSubjects()
    }


    fun fetchSubjects() {
        viewModelScope.launch(Dispatchers.Main) {
            _state.value = LoadingState.LOADING
            //loginResponse.value = repository.loginTeacher(email, password)
            try {
                val response = repository.getSubjects()
                when (response) {

                    is DataorException.Success -> {

                        item = response.data!!
                        Log.d("april", "success: ${item} ")
                        if (item.success == true) {
                            _state.value = LoadingState.SUCCESS
                            Log.d("samsth", "inside successblock ")
                            isLoading = false
                        }
                    }

                    is DataorException.Error -> {
                        _state.value = LoadingState.FAILED
                        _state.value.message = response.message
                        Log.d("april", "error: ${response.message} ")
                        isLoading = false
                    }

                    else -> {
                        _state.value = LoadingState.IDLE
                        isLoading = false
                    }
                }
            } catch (e: Exception) {
                isLoading = false
            }
        }
    }
}