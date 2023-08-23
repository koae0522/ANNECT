package com.example.annect.data

import android.content.ContentValues.TAG
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataRepository(private val dataStore: DataStore<Preferences>) {
    private companion object{
        //名前
        var NAME = stringPreferencesKey("anima_name")
        //パーツ
        var BODY = intPreferencesKey("body")
        var EYE = intPreferencesKey("eye")
        var MOUTH = intPreferencesKey("mouth")
        var ACCESSORY = intPreferencesKey("accessory")
        //親愛度
        var LOVE = intPreferencesKey("love")
        //気分
        var FEELING = intPreferencesKey("feeling")
        //キャラクリ済みフラグ(trueだと起動時にHomeに遷移)
        var CREATED_FLAG = booleanPreferencesKey("created_flag")
    }

    //名前保存
    suspend fun saveName(name:String){
        dataStore.edit{ preferences ->
            preferences[NAME]= name
        }
    }

    //名前読み込み
    fun  loadName(): Flow<String?> = dataStore.data
        .catch{
        if(it is IOException){
            Log.e(TAG, "Error reading preferences.", it)
            emit(emptyPreferences())
        }
        }.map{ preferences ->
            preferences[NAME]
        }

    //body保存
    suspend fun saveBody(body:Int){
        dataStore.edit{ preferences ->
            preferences[BODY]= body
        }
    }

    //body読み込み
    fun  loadBody(): Flow<Int?> = dataStore.data
        .catch{
            if(it is IOException){
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            }
        }.map{ preferences ->
            preferences[BODY]
        }

    //eye保存
    suspend fun saveEye(eye:Int){
        dataStore.edit{ preferences ->
            preferences[EYE]= eye
        }
    }

    //eye読み込み
    fun  loadEye(): Flow<Int?> = dataStore.data
        .catch{
            if(it is IOException){
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            }
        }.map{ preferences ->
            preferences[EYE]
        }

    //mouth保存
    suspend fun saveMouth(mouth:Int){
        dataStore.edit{ preferences ->
            preferences[MOUTH]= mouth
        }
    }

    //mouth読み込み
    fun  loadMouth(): Flow<Int?> = dataStore.data
        .catch{
            if(it is IOException){
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            }
        }.map{ preferences ->
            preferences[MOUTH]
        }

    //accessory保存
    suspend fun saveAccessory(accessory:Int){
        dataStore.edit{ preferences ->
            preferences[ACCESSORY]= accessory
        }
    }

    //accessory読み込み
    fun  loadAccessory(): Flow<Int?> = dataStore.data
        .catch{
            if(it is IOException){
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            }
        }.map{ preferences ->
            preferences[ACCESSORY]
        }

    //love保存
    suspend fun saveLove(love:Int){
        dataStore.edit{ preferences ->
            preferences[LOVE]= love
        }
    }

    //love読み込み
    fun  loadLove(): Flow<Int?> = dataStore.data
        .catch{
            if(it is IOException){
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            }
        }.map{ preferences ->
            preferences[LOVE]
        }

    //feeling保存
    suspend fun saveaFeeling(feeling:Int){
        dataStore.edit{ preferences ->
            preferences[FEELING]= feeling
        }
    }

    //feeling読み込み
    fun  loadFeeling(): Flow<Int?> = dataStore.data
        .catch{
            if(it is IOException){
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            }
        }.map{ preferences ->
            preferences[FEELING]
        }

    //createdFlag保存
    suspend fun saveCreatedFlag(createdFlag:Boolean){
        dataStore.edit{ preferences ->
            preferences[CREATED_FLAG]= createdFlag
        }
    }

    //createdFlag読み込み
    fun  loadCreatedFlag(): Flow<Boolean?> = dataStore.data
        .catch{
            if(it is IOException){
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            }
        }.map{ preferences ->
            preferences[CREATED_FLAG]
        }

}
