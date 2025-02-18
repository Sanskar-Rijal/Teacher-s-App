package com.example.teacherapp.screens.HomeScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherapp.caching.DataStoreManager
import com.example.teacherapp.data.DataorException
import com.example.teacherapp.model.getAddedData.getsubjects
import com.example.teacherapp.model.getmydetails.Data
import com.example.teacherapp.model.getmydetails.MydetailsResponse
import com.example.teacherapp.repository.getmyDetails_Repository
import com.example.teacherapp.screens.LoginScreen.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: getmyDetails_Repository,
    private val cache:DataStoreManager
):ViewModel() {

    var item: MydetailsResponse by
    mutableStateOf(MydetailsResponse(data = Data(
        id="",
        email = "",
        name="",
        password = "",
        phone = "",
        role = "",
        section = null,
    ), success = false))

    private  val _state = MutableStateFlow(LoadingState.IDLE)
    val state=_state.asStateFlow()


    init{
        loadDetails()
    }

//    fun details(){
//        getDetails()
//    }

   private fun getDetails(){
        _state.value =LoadingState.LOADING
        viewModelScope.launch {
            try{
                val response=repository.getmyDetails_Repository()

                when(response){

                    is DataorException.Success ->{
                        item= response.data!!
                        if(item.success == true){
                            _state.value = LoadingState.SUCCESS
                            cache.saveUserDetails(name=item.data.name, email = item.data.email)
                        }
                    }
                    is DataorException.Error ->{
                        _state.value = LoadingState.FAILED
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


    private fun loadDetails(){
        viewModelScope.launch {
            val cachedName =cache.userName.first()
            val cachedEmail= cache.userEmail.first()

            if(cachedName.isNullOrEmpty()&& cachedEmail.isNullOrEmpty()){
                getDetails()
            }else{
                item = MydetailsResponse(data = Data(name = cachedName!!,
                    email = cachedEmail!!,
                    password = "",
                    phone = "",
                    role = "",
                    section = null,
                    id = ""
                ), success = true)
            }

        }
    }


}