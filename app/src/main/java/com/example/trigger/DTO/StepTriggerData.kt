package com.example.trigger.DTO

data class StepTriggerData(
    var PreviousStep: Int = 0,
    var CurrentStep: Int = 0,
    var GoalSet: Int = 0
)