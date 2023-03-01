package com.example.fitnessapp.database.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goals")
class Goals{
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int = 0

    @NonNull
    var userId: Int = 0

    @NonNull
    lateinit var title: String

    @NonNull
    var steps: Int = 0

    constructor(){}
    constructor(User: Int, Title: String, Steps: Int){
        this.userId = User
        this.title  = Title
        this.steps = Steps
    }

//    constructor(Id: Int, User: Int, Title: String, Steps: Int){
//        this.id = Id
//        this.userId = User
//        this.title  = Title
//        this.steps = Steps
//    }
}