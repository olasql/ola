package com.example.trigger

import android.content.Context
import com.example.trigger.DTO.NotificationData
import com.example.trigger.DTO.StepTriggerData
import com.example.trigger.alerts.NotificationAlert
import java.util.*

object ContextTrigger {

    lateinit var globalAppContext : Context;
    private var timer : Timer = Timer();
    private var stepsData: StepTriggerData = StepTriggerData();
    var LastStepCount: Int = 0
    init {
        this.timer.schedule(object : TimerTask() {
            override fun run() {
                triggerAppIdle()
            }
        }, 2000, 60000)

    }

    fun triggerStepCounter(goal: Int, steps: Int?){

        //if(stepsData.CurrentStep == 0){
            if(this.LastStepCount > goal){
                NotificationAlert().sendNotification(this.globalAppContext, NotificationData(
                    title = "Goal Count Trigger",
                    text = "You seems to have set a lesser goal. Hope you are fine?"
                ),2
                )
            }else
                this.LastStepCount = goal
        //}
        if (steps != null) {
            stepsData.CurrentStep = steps
        }
        stepsData.GoalSet = goal
        if(stepsData.CurrentStep == stepsData.GoalSet){
            NotificationAlert().sendNotification(this.globalAppContext, NotificationData(
                title = "Goal Achieved Trigger",
                text = "Goal achieved for the day. Set more goals"
            )
            );
        }else if(stepsData.CurrentStep > stepsData.GoalSet){
            var predict = ((stepsData.CurrentStep - stepsData.GoalSet ) + ((30.00 / 100.00).toDouble() * stepsData.CurrentStep)).toInt()
            NotificationAlert().sendNotification(this.globalAppContext, NotificationData(
                title = "Goal level Trigger",
                text = "You can increase your daily goal setting by $predict"
            ),3
            );
        }
    }

    fun registerPrevHistory(goal: Int){
        this.LastStepCount = goal
    }

    fun triggerAppIdle(){
        if(this.stepsData.CurrentStep == 0)
            return;
        if(this.stepsData.CurrentStep == this.stepsData.PreviousStep){
            if(this.stepsData.PreviousStep < this.stepsData.GoalSet) {
                NotificationAlert().sendNotification(
                    this.globalAppContext, NotificationData(
                        title = "App Idle Trigger",
                        text = "It seems you are inactive for a while. Resume noe"
                    )
                )
            }
        }else{
            this.stepsData.PreviousStep = this.stepsData.CurrentStep
        }
    }
}