package com.example.fitnessapp.database.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goals_history")
class GoalsHistory{

    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int = 0

    @NonNull
    var userId: Int = 0

    @NonNull
    lateinit var date: String

    @NonNull
    var goal: Int = 0

    @ColumnInfo(defaultValue = "0")
    var steps: Int = 0

    constructor()
    constructor(Date: String, UserID: Int, GoalId: Int, Steps: Int){
        this.date = Date
        this.goal = GoalId
        this.steps = Steps
        this.userId = UserID
    }
}

class HistoryData{
    var id : Int = 0
    var date: String = ""
    var steps: Int = 0
    var goalSteps: Int = 0
}