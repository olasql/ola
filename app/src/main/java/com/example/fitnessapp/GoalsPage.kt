package com.example.fitnessapp

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.text.isDigitsOnly
import com.example.fitnessapp.database.entities.Goals
import com.example.fitnessapp.database.entities.GoalsData
import com.example.fitnessapp.database.entities.GoalsHistory
import com.example.fitnessapp.models.GoalsModel
import com.example.trigger.ContextTrigger
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun GoalsPage(
    goalsModel: GoalsModel
){
    val allGoals by goalsModel.goalsList.observeAsState(listOf())
    var openAddModal by rememberSaveable {mutableStateOf(false)}
    var showNotice by rememberSaveable {mutableStateOf("")}

    Scaffold(
        backgroundColor = Color.White,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {openAddModal = !openAddModal},
                backgroundColor = Color.Blue
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = null,
                    modifier = Modifier.size(35.dp)
                )
            }
        }
    ) {
        if(openAddModal == true)
            AddGoalsModal(goalsModel, onCloseClick = { openAddModal = it}, onAdded = {showNotice = it})

        Column(
            modifier = Modifier.padding(7.dp, 0.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if(showNotice.isNotEmpty()) {
                Surface(
                    color = Color.Green
                ) {
                    Text(showNotice, color = Color.Black, modifier = Modifier.padding(15.dp))
                }
            }
            LazyColumn(
                modifier = Modifier.padding(7.dp).fillMaxSize().selectableGroup()
            ) {
                println("Fetched")
                //println(allGoals.toString())
                var firstData = allGoals.firstOrNull()
                if(firstData != null){
                    //var nowDt = SimpleDateFormat("yyyy-MM-dd").format(Date())
                    //if(firstData.date != nowDt){
                        ContextTrigger.registerPrevHistory(firstData.steps)
                    //}
                }
                items(allGoals) { goals ->
                    GoalsItem(goals, goalsModel, onAction = {showNotice = it})
                }
            }
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddGoalsModal(goalsModel: GoalsModel, onCloseClick: (Boolean) -> Unit, onAdded: (String) -> Unit, metFunc:String = "add", goalData:GoalsData = GoalsData()){
    var iniTitle:String = if(!goalData.title.isNullOrEmpty()) goalData.title else ""
    var iniSteps:String = if(goalData.steps != 0) goalData.steps.toString() else ""
    var goalTitle by rememberSaveable { mutableStateOf(iniTitle) }
    var goalSteps by rememberSaveable { mutableStateOf(iniSteps) }
    var goalTitleError by rememberSaveable { mutableStateOf("") }
    var goalStepsError by rememberSaveable { mutableStateOf("") }
    Dialog(
        properties = DialogProperties(
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = true,
            dismissOnBackPress = true
        ),
        onDismissRequest = {

        }
    ){
        Card(
            modifier = Modifier.fillMaxWidth().padding(8.dp),//.verticalScroll(rememberScrollState()),
            backgroundColor = Color.White,
            contentColor = Color.Black
        ){
            Column(
                modifier = Modifier.padding(15.dp).fillMaxWidth().verticalScroll(rememberScrollState())
                ){
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if(metFunc == "add")
                        Text("Add new goals", fontSize = 20.sp, modifier = Modifier.padding(2.dp, 8.dp))
                    else if (metFunc == "edit")
                        Text("Edit goal", fontSize = 20.sp, modifier = Modifier.padding(2.dp, 8.dp))
                    Button(
                        onClick = { onCloseClick(false) },
                        elevation = null,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Transparent,
                            contentColor = Color.Red
                        )
                    ) {
                        Icon(
                            Icons.Filled.Close,
                            contentDescription = null,
                        )
                    }
                }
                Divider(
                    color = Color.Gray
                )
                Column(
                    modifier = Modifier.padding().fillMaxWidth()
                ) {
                    OutlinedTextField(
                        modifier = Modifier.padding(0.dp, 5.dp),
                        enabled = metFunc == "add",
                        isError = goalTitleError.isNotEmpty(),
                        //                    modifier = Modifier.?
                        value = goalTitle, placeholder = { Text("Title") },
                        singleLine = true, onValueChange = { txt -> goalTitle = txt },
                        //                    label = { Text("Title") },
                        shape = RoundedCornerShape(10),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            placeholderColor = Color.Gray,
                            unfocusedBorderColor = Color.Gray,
                            focusedBorderColor = Color.Blue
                        )
                    )
                    if (goalTitleError.isNotEmpty())
                        Text(goalTitleError, color = Color.Red)
                    OutlinedTextField(
                        modifier = Modifier.padding(0.dp, 5.dp),
                        value = goalSteps,
                        isError = goalStepsError.isNotEmpty(),
                        placeholder = { Text("Steps") },
                        singleLine = true,
                        onValueChange = { txt -> goalSteps = txt },
                        shape = RoundedCornerShape(10),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            placeholderColor = Color.Gray,
                            unfocusedBorderColor = Color.Gray,
                            focusedBorderColor = Color.Blue
                        )
                    )
                }
                if(goalStepsError.isNotEmpty())
                    Text(goalStepsError, color = Color.Red)

                Button(
                    onClick = {
                        goalStepsError = ""
                        goalTitleError = ""
                          if(goalTitle.isEmpty()){
                              goalTitleError = "Title is required"
                          }
                        if(goalSteps.isEmpty()){
                            goalStepsError = "Steps is required"
                        }else{
                            if(!goalSteps.isDigitsOnly()){
                                goalStepsError = "Numbers is required"
                            }
                        }

                        if(goalStepsError.isEmpty() && goalTitleError.isEmpty()){
                            if(metFunc == "add") {
                                goalsModel.addGoals(
                                    Goals(
                                        1,
                                        goalTitle,
                                        goalSteps.toInt()
                                    )
                                )
                            }
                            else if (metFunc == "edit"){
                                goalsModel.updateGoalsSteps(
                                    goalSteps.toInt(),
                                    goalData.id
                                )
                                ContextTrigger.triggerStepCounter(goalSteps.toInt(),null)
                            }
                            onCloseClick(false)
                            if(metFunc == "add")
                                onAdded("New goal added")
                            else if(metFunc == "edit")
                                onAdded("${goalData.title} edited")
                            //Toast.makeText(cnt, "Goals added successfully", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Blue,
                        disabledBackgroundColor = Color.Gray
                    )
                ){
                    if(metFunc == "add")
                        Text("Add", fontSize = 15.sp, color = Color.White)
                    else if (metFunc == "edit")
                        Text("Edit", fontSize = 15.sp, color = Color.White)
                }
            }
        }
    }
}

fun validateTitle(){

}
@Composable
fun GoalsItem (goal:GoalsData, goalsModel: GoalsModel, onAction: (String) -> Unit){
    var openEditModal by rememberSaveable {mutableStateOf(false)}
    var showNotice by rememberSaveable {mutableStateOf("")}
    Column(
        modifier = Modifier.selectableGroup().fillMaxSize(),
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(0.dp, 7.dp),
            backgroundColor = Color.White,
            border = BorderStroke(1.dp, color = Color.Gray)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(10.dp, 5.dp)
                ) {
                    Text(text = "${goal.title}", fontSize = 29.sp, color = Color.Blue)
                    Text(text = "${goal.steps} steps", fontSize = 18.sp, color = Color.Blue)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(13.dp, 0.dp)
                ) {
                    var nowDt = SimpleDateFormat("yyyy-MM-dd").format(Date())
                    var isSelected = goal.date == nowDt

                    if (openEditModal)
                        AddGoalsModal(
                            goalsModel,
                            onCloseClick = { openEditModal = it },
                            onAdded = { onAction(it) },
                            "edit",
                            goal
                        )

                    Button(
                        onClick = {
                            onAction("")
                            var res = goalsModel.setTodayGoal(
                                GoalsHistory(
                                    nowDt,
                                    1,
                                    goal.id,
                                    0
                                )
                            )
                            ContextTrigger.triggerStepCounter(goal.steps, 0)
                            onAction(res)
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = if (isSelected) Color.Gray else Color.Blue,
                            contentColor = Color.White
                        )
                    ) {
                        if (isSelected) Text("selected") else Text("select")
                    }

                    Button(
                        onClick = {
                            openEditModal = !openEditModal
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Yellow,
                            contentColor = Color.Black
                        ),
                        modifier = Modifier.padding(5.dp, 0.dp)
                    ) {
                        Text("edit")
                    }
                    if (!isSelected) {
                        Button(
                            onClick = {
                                onAction("")
                                goalsModel.deleteGoals(goal.id)
                                onAction("${goal.title} goal deleted")
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.White
                            ),
                            elevation = null
                        ) {
                            Icon(
                                Icons.Filled.Delete,
                                contentDescription = null,
                                tint = Color.Red
                            )
                        }
                    }
                }
            }
        }
    }
}