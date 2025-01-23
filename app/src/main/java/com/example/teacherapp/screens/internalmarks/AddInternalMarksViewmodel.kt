package com.example.teacherapp.screens.internalmarks

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherapp.data.DataorException
import com.example.teacherapp.model.AddInternalMarks.InternalMarksRequest
import com.example.teacherapp.model.AddInternalMarks.InternalMarksResponse
import com.example.teacherapp.model.AddInternalMarks.MarksData
import com.example.teacherapp.repository.InternalMarksRepository
import com.example.teacherapp.screens.LoginScreen.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddInternalMarksViewmodel @Inject constructor(
    private val repository: InternalMarksRepository):ViewModel() {

    var item: InternalMarksResponse by mutableStateOf(
        InternalMarksResponse("",false)
    )

    private val _state = MutableStateFlow(LoadingState.IDLE)

    val state = _state.asStateFlow()


    fun addInternalMarks(
         marksData: List<MarksData>,
         subjectId: String,
         onSuccess:()->Unit
    ){
        Log.d("bibek", "inside viewmodel: ")
        viewModelScope.launch {
            _state.value = LoadingState.LOADING

            try {

                val request = InternalMarksRequest(
                    marksData= marksData,
                    subjectId = subjectId
                )

                val response = repository.addInternalMarks(request)

                when (response) {

                    is DataorException.Success -> {

                        item = response.data!!

                        if (item.success == true) {
                            _state.value = LoadingState.SUCCESS
                            onSuccess.invoke()
                        }

                    }

                    is DataorException.Error -> {
                        _state.value = LoadingState.FAILED
                        _state.value.message = response.message.toString()
                        Log.d("bibek", "error from viewmodel Error: ${response.message} ")
                    }

                    else -> {
                        _state.value = LoadingState.IDLE
                    }
                }
            } catch (e: Exception) {
                _state.value = LoadingState.FAILED
                _state.value.message = e.message.toString()
                Log.d("bibek", "error from viewmodel catch block: ${e.message} ")
            }
        }
    }
}
