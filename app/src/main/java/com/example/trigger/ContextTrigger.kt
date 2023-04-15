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
    init {
        this.timer.schedule(object : TimerTask() {
            override fun run() {
                triggerAppIdle()
            }
        }, 2000, 30000)

    }

    fun triggerStepCounter(goal: Int, steps: Int){
        stepsData.CurrentStep = steps
        stepsData.GoalSet = goal
        if(steps == goal){
            NotificationAlert().sendNotificication(this.globalAppContext, NotificationData(
                title = "Goal Achieved Trigger",
                text = "Goal achieved for the day. Set more goals"
            )
            );
        }else if(steps > goal){
            var predict = ((steps - goal ) + ((30.00 / 100.00).toDouble() * steps)).toInt()
            NotificationAlert().sendNotificication(this.globalAppContext, NotificationData(
                title = "Goal level Trigger",
                text = "You can increase your daily goal setting by $predict"
            )
            );
        }
    }

    fun triggerAppIdle(){
        if(this.stepsData.CurrentStep == 0)
            return;
        if(this.stepsData.CurrentStep == this.stepsData.PreviousStep){
            if(this.stepsData.PreviousStep < this.stepsData.GoalSet) {
                NotificationAlert().sendNotificication(
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