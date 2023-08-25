package com.example.annect.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
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

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "animaData")

class DataRepository (private val context: Context){
    //名前
    var NAME = stringPreferencesKey("name")
    //パーツ
    var BODY = intPreferencesKey("body")
    var EYE = intPreferencesKey("eye")
    var MOUTH = intPreferencesKey("mouth")
    var ACCESSORY = intPreferencesKey("accessory")
    var LOVE = intPreferencesKey("love")
    //気分
    var FEELING = intPreferencesKey("feeling")
    var CREATED_FLAG = booleanPreferencesKey("created_flag")

    //名前保存
    suspend fun saveName(name:String){
        context.dataStore.edit{ preferences ->
            preferences[NAME]= name
        }
    }

    //名前読み込み
    fun  loadName(): Flow<String?> {
        return context.dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[NAME]
        }
    }

    //名前削除
    suspend fun clearName() {
        context.dataStore.edit { preferences ->
            preferences.remove(NAME)
        }
    }

    //body保存
    suspend fun saveBody(body:Int){
        context.dataStore.edit{ preferences ->
            preferences[BODY]= body
        }
    }

    //body読み込み
    fun  loadBody(): Flow<Int?> {
        return context.dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[BODY]
        }
    }

    //bodyを削除
    suspend fun clearBody() {
        context.dataStore.edit { preferences ->
            preferences.remove(BODY) // 特定のキーに対応するデータを削除
        }
    }

    //eye保存
    suspend fun saveEye(eye:Int){
        context.dataStore.edit{ preferences ->
            preferences[EYE]= eye
        }
    }

    //eye読み込み
    fun  loadEye(): Flow<Int?> {
        return context.dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[EYE]
        }
    }

    //eye削除
    suspend fun clearEye() {
        context.dataStore.edit { preferences ->
            preferences.remove(EYE)
        }
    }

    //mouth保存
    suspend fun saveMouth(mouth:Int){
        context.dataStore.edit{ preferences ->
            preferences[MOUTH]= mouth
        }
    }

    //mouth読み込み
    fun  loadMouth(): Flow<Int?>{
        return context.dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[MOUTH]
        }
    }

    //mouth削除
    suspend fun clearMouth() {
        context.dataStore.edit { preferences ->
            preferences.remove(MOUTH)
        }
    }

    //accessory保存
    suspend fun saveAccessory(accessory:Int){
        context.dataStore.edit{ preferences ->
            preferences[ACCESSORY]= accessory
        }
    }

    //accessory読み込み
    fun  loadAccessory(): Flow<Int?> {
        return context.dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[ACCESSORY]
        }
    }

    //accessory削除
    suspend fun clearAccessory() {
        context.dataStore.edit { preferences ->
            preferences.remove(ACCESSORY)
        }
    }

    //love保存
    suspend fun saveLove(love:Int){
        context.dataStore.edit{ preferences ->
            preferences[LOVE]= love
        }
    }

    //love読み込み
    fun  loadLove(): Flow<Int?> {
        return context.dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[LOVE]
        }
    }

    //love削除
    suspend fun clearLove() {
        context.dataStore.edit { preferences ->
            preferences.remove(LOVE)
        }
    }


    //feeling保存
    suspend fun saveaFeeling(feeling:Int){
        context.dataStore.edit{ preferences ->
            preferences[FEELING]= feeling
        }
    }

    //feeling読み込み
    fun  loadFeeling(): Flow<Int?> {
        return context.dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[FEELING]
        }
    }

    //feeling削除
    suspend fun clearFeeling() {
        context.dataStore.edit { preferences ->
            preferences.remove(FEELING)
        }
    }

    //createdFlag保存
    suspend fun saveCreatedFlag(createdFlag:Boolean){
        context.dataStore.edit{ preferences ->
            preferences[CREATED_FLAG]= createdFlag
        }
    }

    //createdFlag読み込み
    fun  loadCreatedFlag(): Flow<Boolean?> {
        return context.dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[CREATED_FLAG]
        }
    }

    //createdFlag削除
    suspend fun clearCreatedFlag() {
        context.dataStore.edit { preferences ->
            preferences.remove(CREATED_FLAG)
        }
    }
}