package com.example.teacherapp.screens.NotesScreen

import android.app.Application
import android.net.Uri
import android.provider.ContactsContract.Data
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherapp.data.DataorException
import com.example.teacherapp.model.notes.NoteResponse
import com.example.teacherapp.repository.NoteRepository
import com.example.teacherapp.screens.LoginScreen.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewmodel @Inject constructor(
    application: Application,
    private val repository:NoteRepository):AndroidViewModel(application) {

        var item:NoteResponse by mutableStateOf(
            NoteResponse(
                message = "",
                success = false
            )
        )

    private val _state = MutableStateFlow(LoadingState.IDLE)

    val state = _state.asStateFlow()

    fun sendNote(
        title: String,
        faculty: String,
        semester: String,
        section: String,
        subjectId: String,
        fileUri: Uri,
        onSuccess:()->Unit
    ){
        Log.d("pukar", "title from viewmodel: $title")
        viewModelScope.launch {
            _state.value=LoadingState.LOADING
            try {

                val response= repository.sendNotes(
                    title = title,
                    faculty = faculty,
                    semester = semester,
                    section = section,
                    subjectId = subjectId,
                    fileUri = fileUri,
                    context = getApplication()
                )

                when(response){
                    is DataorException.Success ->{
                        item = response.data!!

                        if (item.success == true) {
                            _state.value = LoadingState.SUCCESS
                            onSuccess.invoke()
                        }
                    }

                    is DataorException.Error ->{
                        _state.value=LoadingState.FAILED
                    }
                    else ->{
                        _state.value=LoadingState.FAILED
                    }
                }
            }catch (ex:Exception){
                _state.value=LoadingState.FAILED
                _state.value.message=ex.message
                Log.d("jeevan", "${ex.message} ")
            }
        }
    }

}



