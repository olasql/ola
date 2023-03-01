package com.example.fitnessapp.database.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "goals_history")
class GoalsHistory{

    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int = 0

    @NonNull
    //@ColumnInfo(defaultValue = today)
    lateinit var date: Date
    @NonNull
    var goal: Int = 0

    @ColumnInfo(defaultValue = "0")
    var steps: Int = 0

    constructor(Date: Date, GoalId: Int, Steps: Int){
        this.date = Date
        this.goal = GoalId
        this.steps = Steps
    }
}