package com.example.annect.data

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ConnectViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(ConnectData())
    //値を公開する
    val uiState: StateFlow<ConnectData> = _uiState.asStateFlow()

    fun ChangeIntaractionState(interaction:Boolean){
        _uiState.value=_uiState.value.copy(interaction =interaction)
    }

    fun ChangeDisplayFace(displayFace:Boolean){
        _uiState.value=_uiState.value.copy(displayFace = displayFace)
    }

    fun ChangeAnimalName(animalName:String){
        _uiState.value=_uiState.value.copy(animal = animalName)
    }

    fun setConnect(str:String){
        val n=_uiState.value.serialData+1
        _uiState.value=_uiState.value.copy(serialData = n )
    }
}