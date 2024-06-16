package com.example.annect.data

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class AnimaViewModel @Inject constructor
    (app: Application) : AndroidViewModel(app) {

    private val context = getApplication<Application>().applicationContext
    private val _uiState = MutableStateFlow(AnimaData( ) )
    val uiState:StateFlow<AnimaData>  = _uiState.asStateFlow( )

    private val dao: AnimaDataDao
    init {

        val db = AnimaDatabase.buildAnimaDatabase(context)
        dao = db.animaDataDao()
        viewModelScope.launch(Dispatchers.IO) {
            if (databaseExists(context)) {
                val data = dao.getFirstAnimaData()
                withContext(Dispatchers.Main) {
                    _uiState.value = data
                }
            } else {
                _uiState.value = AnimaData()
            }
        }
    }

    private fun databaseExists(context: Context): Boolean {
        val dbFile = context.getDatabasePath("anima_database")
        return dbFile.exists()
    }
    fun changeLaunchFlag(){
        viewModelScope.launch (Dispatchers.IO) {
            dao.updateFirstLaunchFlag()
        }
    }

    //初回のセーブ
    fun saveData(){
        viewModelScope.launch (Dispatchers.IO){
            dao.insert(
                AnimaData(
                    name = _uiState.value.name,
                    body = _uiState.value.body,
                    eye = _uiState.value.eye,
                    mouth = _uiState.value.mouth,
                    accessory = _uiState.value.accessory,
                    love = _uiState.value.love,
                    feeling = _uiState.value.feeling,
                    boldness = _uiState.value.boldness,
                    exploration = _uiState.value.exploration,
                    aggressiveness = _uiState.value.aggressiveness,
                    sociability = _uiState.value.sociability,
                    activity = _uiState.value.activity,
                    first = false,
                )
            )
        }
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

    fun increaseLove() {
        val currentLove = _uiState.value.love
        val updatedLove = currentLove + 1
        _uiState.value = _uiState.value.copy(love = updatedLove)
    }


}

