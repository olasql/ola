package com.example.fitnessapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fitnessapp.database.entities.Goals
import com.example.fitnessapp.database.entities.GoalsHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalsDao {
    @Insert
    fun create(goals: Goals)

    @Query("SELECT * FROM goals WHERE id= :goalId")
    fun find(goalId: Int): List<Goals>

    @Query("SELECT * FROM goals")
    fun all(): LiveData<List<Goals>>

    @Query("SELECT * FROM goals_history")
    fun getHistory() : LiveData<List<GoalsHistory>>
    @Query("SELECT * FROM goals")
    fun getAll() : Flow<List<Goals>>

    @Query("DELETE FROM goals WHERE id = :goalId")
    fun delete(goalId: Int)
}