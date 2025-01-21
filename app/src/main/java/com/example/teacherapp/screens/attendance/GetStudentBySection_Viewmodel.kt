package com.example.teacherapp.screens.attendance

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherapp.data.DataorException
import com.example.teacherapp.model.getstudentbysec.studentRequest
import com.example.teacherapp.model.getstudentbysec.studentResponse
import com.example.teacherapp.repository.Get_Student_by_Section
import com.example.teacherapp.screens.LoginScreen.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetStudentBySection_Viewmodel @Inject constructor(
    private val repository:Get_Student_by_Section):ViewModel() {

        var item:studentResponse by mutableStateOf(
            studentResponse(emptyList())
        )
    private val _state = MutableStateFlow(LoadingState.IDLE)

    val state = _state.asStateFlow()

    fun getStudentBySection(
        faculty: String,
        semester: String,
        section: String){
        viewModelScope.launch{

            _state.value = LoadingState.LOADING

            try {
                val request = studentRequest(faculty,section,semester)

                val response = repository.getStudentBySec(request)

                when(response){
                    is DataorException.Success ->{
                        item = response.data!!

                        if(item.students.isNotEmpty()){

                            _state.value = LoadingState.SUCCESS

                        }
                        _state.value = LoadingState.SUCCESS
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