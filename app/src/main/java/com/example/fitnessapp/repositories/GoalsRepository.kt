package com.example.fitnessapp.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fitnessapp.database.dao.GoalsDao
import com.example.fitnessapp.database.entities.Goals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class GoalsRepository(private val goalsDao: GoalsDao)
{
    val allGoals : LiveData<List<Goals>> = goalsDao.all() //MutableLiveData<List<Goals>>()
    private var goalState = MutableLiveData<List<Goals>>(listOf<Goals>())
    var mAllGoals : Flow<List<Goals>> = goalsDao.getAll()
//    var lo : LiveData<Goals> = MutableLiveData<>
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun addGoals(newGoals: Goals) {
        coroutineScope.launch(Dispatchers.IO) {
            goalsDao.create(newGoals)
        }
    }

    fun getGoals() : List<Goals>? {
//        coroutineScope.launch(Dispatchers.IO) {
            return goalsDao.all().value
//        }
    }
    fun getAllGoals() : Flow<List<Goals>> {
//        coroutineScope.launch(Dispatchers.IO) {
            return goalsDao.getAll()
            println("Fetched")
//        }
    }

    fun deleteGoals(goalId: Int){
        coroutineScope.launch(Dispatchers.IO) {
            goalsDao.delete(goalId)
        }
    }

}