package com.example.fitnessapp.repositories

import androidx.lifecycle.LiveData
import com.example.fitnessapp.database.dao.GoalsDao
import com.example.fitnessapp.database.entities.Goals
import com.example.fitnessapp.database.entities.GoalsData
import com.example.fitnessapp.database.entities.GoalsHistory
import com.example.fitnessapp.database.entities.HistoryData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GoalsRepository(private val goalsDao: GoalsDao)
{
    val allGoals : LiveData<List<GoalsData>> = goalsDao.all() //MutableLiveData<List<Goals>>()
    val goalsHistory: LiveData<List<GoalsHistory>> = goalsDao.getHistory()
    val todayHistory: LiveData<HistoryData?> = goalsDao.getTodayHistory()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun addGoals(newGoals: Goals) {
        coroutineScope.launch(Dispatchers.IO) {
            goalsDao.create(newGoals)
        }
    }

    fun addGoalHistory(newHistory: GoalsHistory) {
        coroutineScope.launch(Dispatchers.IO) {
            goalsDao.createHistory(newHistory)
        }
    }

    fun deleteGoals(goalId: Int){
        coroutineScope.launch(Dispatchers.IO) {
            goalsDao.delete(goalId)
        }
    }

    fun updateSteps(steps: Int, histId: Int){
        coroutineScope.launch(Dispatchers.IO) {
            goalsDao.updateSteps(steps, histId)
        }
    }

    fun updateGoals(steps: Int, id: Int){
        coroutineScope.launch(Dispatchers.IO) {
            goalsDao.updateGoals(steps, id)
        }
    }

    fun deleteHistory(histId: Int){
        coroutineScope.launch(Dispatchers.IO) {
            goalsDao.deleteHistory(histId)
        }
    }

    fun deleteAllHistory(){
        coroutineScope.launch(Dispatchers.IO) {
            goalsDao.deleteAllHistory()
        }
    }

    fun getGoalsHistory(date:String) : GoalsHistory?{
        return goalsDao.findHistory(date)
    }

}