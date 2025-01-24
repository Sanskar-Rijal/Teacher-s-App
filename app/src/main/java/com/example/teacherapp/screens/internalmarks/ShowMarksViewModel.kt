package com.example.teacherapp.screens.internalmarks

import androidx.compose.animation.fadeOut
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherapp.data.DataorException
import com.example.teacherapp.model.ShowInternalMarks.ShowMarksRequest
import com.example.teacherapp.model.ShowInternalMarks.ShowMarksResponse
import com.example.teacherapp.repository.ShowInternalMarksRepository
import com.example.teacherapp.screens.LoginScreen.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowMarksViewModel @Inject constructor(
    val repository:ShowInternalMarksRepository):ViewModel() {

        var item :ShowMarksResponse by mutableStateOf(
            ShowMarksResponse(
                message = "",
                data = emptyList(),
                success = false
            )
        )

     private val _state = MutableStateFlow(LoadingState.IDLE)

    val state= _state.asStateFlow()

    fun showMarks(
         section: String,
         subjectCode: String
    ){
        viewModelScope.launch {
            _state.value=LoadingState.LOADING
            try {

                val request = ShowMarksRequest(
                    section=section,
                    subjectCode=subjectCode
                )

                val response= repository.showInternalMarks(request)

                when(response){

                    is DataorException.Success ->{

                        item = response.data!!
                        _state.value=LoadingState.SUCCESS
                    }
                    is DataorException.Error ->{

                        _state.value=LoadingState.FAILED
                    }

                    else -> {
                        _state.value = LoadingState.FAILED
                    }
                }
            }catch (ex:Exception){
                _state.value=LoadingState.FAILED
            }

        }
    }

}