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
import com.example.fitnessapp.models.GoalsModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GoalsPage(
    goalsModel: GoalsModel
){
//    var Items = List<>
    //goalsModel.getAllGoals()
    var openAddModal by rememberSaveable {mutableStateOf(false)}
    var showNotice by rememberSaveable {mutableStateOf("")}
    val allGoals by goalsModel.goalsList.observeAsState(listOf())
    //val mGoals by goalsModel.mGoalsList.collectAsState(listOf())//Lifecycle.State List<Goals>(200) { Goals() }
    val mGoals = goalsModel.getAllGoals().collectAsState(listOf())
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
        var lop = listOf<Goals>()
        lop = mGoals.value
//        if(allGoals.isNullOrEmpty())
//            lop = mGoals
//        else
//            lop = allGoals
        if(openAddModal == true)
            AddGoalsModal(goalsModel, onCloseClick = { openAddModal = it}, onAdded = {showNotice = it})
        if(showNotice.isNotEmpty()) {
            Column(
                modifier = Modifier.padding(7.dp, 0.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Surface(
                    color = Color.Green
                ) {
                    Text(showNotice, color = Color.Black, modifier = Modifier.padding(15.dp))
                }
                LazyColumn(
                    modifier = Modifier.padding(7.dp).fillMaxSize().selectableGroup()
                ) {
                    items(allGoals) { goals ->
                        GoalsItem(goals.id, goals.title, goals.steps, goalsModel, onAction = {showNotice = it})
                    }
                }
            }
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddGoalsModal(goalsModel: GoalsModel, onCloseClick: (Boolean) -> Unit, onAdded: (String) -> Unit){
    var goalTitle by rememberSaveable { mutableStateOf("") }
    var goalSteps by rememberSaveable { mutableStateOf("") }
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
                    Text("Add new goals", fontSize = 20.sp, modifier = Modifier.padding(2.dp, 8.dp))
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
                        isError = !goalTitleError.isNullOrEmpty(),
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
                        isError = !goalStepsError.isNullOrEmpty(),
                        placeholder = { Text("Steps") },
                        singleLine = true, onValueChange = { txt -> goalSteps = txt },
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
                          if(goalTitle.isNullOrEmpty()){
                              goalTitleError = "Title is required"
                          }
                        if(goalSteps.isNullOrEmpty()){
                            goalStepsError = "Steps is required"
                        }else{
                            if(!goalSteps.isDigitsOnly()){
                                goalStepsError = "Numbers is required"
                            }
                        }

                        if(goalStepsError.isEmpty() && goalTitleError.isEmpty()){
                            goalsModel.addGoals(Goals(
                                1,
                                goalTitle,
                                goalSteps.toInt()
                            ))
                            onCloseClick(false)
                            onAdded("New goal added")
                            //Toast.makeText(cnt, "Goals added successfully", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Blue,
                        disabledBackgroundColor = Color.Gray
                    )
                ){
                    Text("Add", fontSize = 15.sp, color = Color.White)
                }
            }
        }
    }
}

fun validateTitle(){

}
@Composable
fun GoalsItem(Id: Int, title: String, steps: Int, goalsModel: GoalsModel, onAction: (String) -> Unit){
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
                        Text(text = "$title", fontSize = 29.sp, color = Color.Blue)
                        Text(text = "$steps steps", fontSize = 18.sp, color = Color.Blue)
                    }
                Button(
                    onClick = {
                        goalsModel.deleteGoals(Id)
                        onAction("$title goal deleted")
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