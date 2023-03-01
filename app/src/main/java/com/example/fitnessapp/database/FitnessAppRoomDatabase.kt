package com.example.fitnessapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fitnessapp.database.dao.GoalsDao
import com.example.fitnessapp.database.entities.Goals
import com.example.fitnessapp.database.entities.GoalsHistory

@Database(entities = [(Goals::class), (GoalsHistory::class)], version = 1)
abstract class FitnessAppRoomDatabase: RoomDatabase() {
    abstract fun goalsDao(): GoalsDao
    companion object{
        private var INSTANCE: FitnessAppRoomDatabase? = null

        fun getInstance(context: Context): FitnessAppRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FitnessAppRoomDatabase::class.java,
                        "fitness_app_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}