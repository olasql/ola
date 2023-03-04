package com.example.fitnessapp.models

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.fitnessapp.database.FitnessAppRoomDatabase
import com.example.fitnessapp.database.entities.Goals
import com.example.fitnessapp.database.entities.GoalsData
import com.example.fitnessapp.database.entities.GoalsHistory
import com.example.fitnessapp.database.entities.HistoryData
import com.example.fitnessapp.repositories.GoalsRepository
import java.text.SimpleDateFormat
import java.util.*

class GoalsModel(application: Application)  : ViewModel() {


    private val goalsRepo: GoalsRepository

    init {
        val fitnessAppDb = FitnessAppRoomDatabase.getInstance(application)
        val goalsDao = fitnessAppDb.goalsDao()
        goalsRepo = GoalsRepository(goalsDao)
    }
    val goalsList: LiveData<List<GoalsData>> = goalsRepo.allGoals
    val goalsHistList: LiveData<List<GoalsHistory>> = goalsRepo.goalsHistory
    val todayHistory: LiveData<HistoryData?> = goalsRepo.todayHistory

    fun deleteGoals(goalId: Int){
        goalsRepo.deleteGoals(goalId)
    }

    fun deleteHistory(histId: Int){
        goalsRepo.deleteHistory(histId)
    }

    fun deleteAllHistory(){
        goalsRepo.deleteAllHistory()
    }

    fun addGoalHistory(goalsHistory: GoalsHistory){
        goalsRepo.addGoalHistory(goalsHistory)
    }

    fun addGoals(goals: Goals){
        goalsRepo.addGoals(goals)
    }

    fun getGoalHistory(date: String): GoalsHistory?{
        return goalsRepo.getGoalsHistory(date)
    }
    fun setTodayGoal(goalsHistory: GoalsHistory) : String{
        var gH = getGoalHistory(goalsHistory.date)
        var nowDt = SimpleDateFormat("yyyy-MM-dd").format(Date())
        if(gH == null) {
            addGoalHistory(goalsHistory)
            return "Goal for the day set"
        }
        return "Goal already set for the day"
    }

    fun updateSteps(steps: Int, histId: Int){
        goalsRepo.updateSteps(steps, histId)
    }
}