package com.example.teacherapp.screens.notices


import android.util.Log
import android.view.View
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.util.fastCbrt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.teacherapp.data.DataorException
import com.example.teacherapp.model.Notice.NoticeRequest
import com.example.teacherapp.model.Notice.NoticeResponse
import com.example.teacherapp.repository.SendNoticeRepository
import com.example.teacherapp.screens.LoginScreen.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoticeViewmodel @Inject constructor(
    private val repository:SendNoticeRepository): ViewModel() {

    var item: NoticeResponse by mutableStateOf(
        NoticeResponse(
            message = "",
            success = false
        )
    )

    private val _state = MutableStateFlow(LoadingState.IDLE)

    val state = _state.asStateFlow()


    fun sendNotice(
        description: String,
        faculty: String,
        section: String,
        semester: String,
        subjectId: Int,
        title: String,
        onSuccess:()->Unit
    ) {
        viewModelScope.launch {
            _state.value = LoadingState.LOADING

            try {

                val request = NoticeRequest(
                    description = description,
                    faculty = faculty,
                    section = section,
                    semester = semester,
                    subjectId = subjectId,
                    title = title
                )
                val response = repository.sendNotice(request)
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
                        _state.value.message = response.message
                        Log.d("april", "error: ${response.message} ")
                    }

                    else -> {
                        _state.value = LoadingState.IDLE
                    }
                }
            } catch (e: Exception) {
                _state.value = LoadingState.FAILED
                _state.value.message = e.message
                Log.d("april", "error: ${e.message} ")
            }
        }
    }
}