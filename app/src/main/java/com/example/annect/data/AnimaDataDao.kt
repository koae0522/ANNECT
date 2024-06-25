package com.example.annect.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AnimaDataDao {

    @Insert
    fun insert(animaData: AnimaData)

    @Query("SELECT * FROM anima_data_table LIMIT 1")
    fun getFirstAnimaData(): AnimaData

    @Query("UPDATE anima_data_table SET first = 0")
    fun updateFirstLaunchFlag()

}