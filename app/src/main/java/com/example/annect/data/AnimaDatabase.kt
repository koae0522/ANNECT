package com.example.annect.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AnimaData::class],version=2,exportSchema = false)
abstract class AnimaDatabase : RoomDatabase() {
    abstract fun animaDataDao(): AnimaDataDao

    companion object {
        @Volatile
        private var INSTANCE: AnimaDatabase? = null
        fun buildAnimaDatabase(context: Context): AnimaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AnimaDatabase::class.java,
                    "anima_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}