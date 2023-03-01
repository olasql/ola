package com.example.fitnessapp.models

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.fitnessapp.database.FitnessAppRoomDatabase
import com.example.fitnessapp.database.entities.Goals
import com.example.fitnessapp.repositories.GoalsRepository
import kotlinx.coroutines.flow.Flow

class GoalsModel(application: Application)  : ViewModel() {


    private val goalsRepo: GoalsRepository

//    private var goalState = MutableStateFlow(listOf<Goals>())
    init {
        val fitnessAppDb = FitnessAppRoomDatabase.getInstance(application)
        val goalsDao = fitnessAppDb.goalsDao()
        goalsRepo = GoalsRepository(goalsDao)
        //goalsRepo.mAllGoals.collect()
        //goalState.value = goalsRepo.mAllGoals.value
    }
    val goalsList: LiveData<List<Goals>> = goalsRepo.allGoals
    val mGoalsList: Flow<List<Goals>> = goalsRepo.mAllGoals

    fun getAllGoals(): Flow<List<Goals>> {
       return goalsRepo.getAllGoals()
    }
    fun getGoals(): List<Goals>? {
        return goalsRepo.getGoals()
    }

    fun deleteGoals(goalId: Int){
        goalsRepo.deleteGoals(goalId)
    }

    fun addGoals(goals: Goals){
        goalsRepo.addGoals(goals)
        //getAllGoals()
    }
}