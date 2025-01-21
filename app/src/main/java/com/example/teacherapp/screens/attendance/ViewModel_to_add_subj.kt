package com.example.teacherapp.screens.attendance

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherapp.data.DataorException
import com.example.teacherapp.model.addSubject.AddSubjRequest
import com.example.teacherapp.model.addSubject.AddsubjResponse
import com.example.teacherapp.repository.Add_Teacher_Subjects
import com.example.teacherapp.screens.LoginScreen.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel_to_add_subj @Inject constructor(
    private val repository:Add_Teacher_Subjects
): ViewModel() {
    var item :AddsubjResponse by mutableStateOf(AddsubjResponse(success = false, message = ""))

    private val _state = MutableStateFlow(LoadingState.IDLE)

    val state = _state.asStateFlow()

    fun addSubject(
        faculty: String,
        semester: String,
        section: String,
        subjectCode: String,
        onSuccess:()->Unit){
        viewModelScope.launch{
            val request  = AddSubjRequest(faculty,semester,section,subjectCode)

            _state.value = LoadingState.LOADING
            try {
                val response = repository.addSubjects(request)
                Log.d("pravin", "outside whenblock: $item")
                when(response){

                    is DataorException.Success ->{
                        item = response.data!!
                        Log.d("pravin", "insidewhenblock: $item")
                        if(item.success == true){
                            Log.d("pravin", "successblock: $item")
                            _state.value = LoadingState.SUCCESS
                            _state.value.message=response.message
                            onSuccess()
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