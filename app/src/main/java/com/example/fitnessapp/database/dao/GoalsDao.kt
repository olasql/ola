package com.example.fitnessapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fitnessapp.database.entities.Goals
import com.example.fitnessapp.database.entities.GoalsData
import com.example.fitnessapp.database.entities.GoalsHistory
import com.example.fitnessapp.database.entities.HistoryData

@Dao
interface GoalsDao {

    @Insert
    fun create(goals: Goals)
    @Insert
    fun createHistory(history: GoalsHistory)
    @Query("SELECT * FROM goals WHERE id= :goalId")
    fun find(goalId: Int): List<Goals>
    @Query("SELECT goals.*,goals_history.goal, goals_history.date FROM goals LEFT JOIN goals_history ON goals.id = goals_history.goal")
    fun all() : LiveData<List<GoalsData>>

    @Query("DELETE FROM goals WHERE id = :goalId")
    fun delete(goalId: Int)
    @Query("SELECT * FROM goals_history")
    fun getHistory() : LiveData<List<GoalsHistory>>

    @Query("SELECT goals_history.*, goals.steps as goalSteps FROM goals_history INNER JOIN goals ON goals_history.goal = goals.id WHERE date = date()")
    fun getTodayHistory() : LiveData<HistoryData?>
    @Query("SELECT * FROM goals_history WHERE date = :date")
    fun findHistory(date:String) : GoalsHistory?
    @Query("DELETE FROM goals_history WHERE date <> date()")
    fun deleteAllHistory()

    @Query("UPDATE goals SET steps = :steps WHERE id = :id")
    fun updateGoals(steps:Int, id:Int)
    @Query("UPDATE goals_history SET steps = :steps WHERE id = :id")
    fun updateSteps(steps: Int, id: Int)
    @Query("DELETE FROM goals_history WHERE id = :histId AND date <> date()")
    fun deleteHistory(histId: Int)
}