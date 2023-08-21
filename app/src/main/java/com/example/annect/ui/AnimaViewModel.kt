package com.example.annect.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AnimaViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AnimaData())
    val uiState:StateFlow<AnimaData> = _uiState.asStateFlow()

    fun ChangeAnimaParts(parts: Int){
        Log.d("TAG","$parts")

        bodyData.forEach{
            if(it==parts){
                _uiState.value=_uiState.value.copy(body = parts)
            }
        }
        eyeData.forEach{
            if(it==parts){
                _uiState.value=_uiState.value.copy(eye = parts)
            }
        }
        mouthData.forEach{
            if(it==parts){
                _uiState.value=_uiState.value.copy(mouth = parts)
            }
        }
        accessoryData.forEach{
            if(it==parts){
                _uiState.value=_uiState.value.copy(accessory = parts)
            }
        }


    }


}

