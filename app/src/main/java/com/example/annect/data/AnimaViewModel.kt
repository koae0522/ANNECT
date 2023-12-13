package com.example.annect.data

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AnimaViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AnimaData())
    val uiState:StateFlow<AnimaData> = _uiState.asStateFlow()

    fun ChangeAnimalName(animalName:String){
        _uiState.value=_uiState.value.copy(animal = animalName)
    }

    fun setConnect(){
        val n=_uiState.value.serialData+1
        _uiState.value=_uiState.value.copy(serialData = n )
    }

    fun ChangeAnimaParts(parts: Int){

        //パーツ更新(ゴリ押し)
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

    fun ChangeAnimaName(name:String){
        //名前更新
        _uiState.value=_uiState.value.copy(name=name)
    }

    //起動時にデータストアからviewModel更新
    fun loadData(name:String,body:Int,eye:Int,mouth:Int,accessory:Int,love:Int,feeling:Int){
        _uiState.value=_uiState.value.copy(name = name)
        _uiState.value=_uiState.value.copy(body = body)
        _uiState.value=_uiState.value.copy(eye = eye)
        _uiState.value=_uiState.value.copy(mouth = mouth)
        _uiState.value=_uiState.value.copy(accessory = accessory)
        _uiState.value=_uiState.value.copy(love = love)
        _uiState.value=_uiState.value.copy(feeling = feeling)
    }

    fun increaseLove() {
        val currentLove = _uiState.value.love
        val updatedLove = currentLove + 1
        _uiState.value = _uiState.value.copy(love = updatedLove)
    }


}

