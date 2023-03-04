package com.example.fitnessapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitnessapp.models.GoalsModel

@Composable
fun savedRecBtn(){
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        border = BorderStroke(width = 1.dp, color = Color.Blue),
        contentPadding = PaddingValues(10.dp),
        elevation = null,

        ){
        Text("21-03-2023", color = Color.Blue)
    }
}


//COMPOSABLE FOR HOMAEPAHE
@Composable
fun HomePage(
    goalsModel: GoalsModel
){
    val todayHistory by goalsModel.todayHistory.observeAsState()
    var progressCnt by rememberSaveable { mutableStateOf(0f) }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ){
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Column(
                modifier = Modifier.fillMaxWidth().height(450.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if(todayHistory != null) {
                    progressCnt = (todayHistory!!.steps.toFloat() / todayHistory!!.goalSteps.toFloat())
                    println(progressCnt)
                    Surface(
                        elevation = 9.dp,
                        color = Color.White,
                        contentColor = Color.Black,
                        modifier = Modifier.padding(0.dp, 10.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(15.dp),

                            ) {
                            Text(
                                "${todayHistory?.date}",
                                modifier = Modifier.padding(0.dp, 5.dp),
                                fontSize = 25.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "Goal Set: ${todayHistory?.goalSteps} steps",
                                modifier = Modifier.padding(0.dp, 5.dp),
                                fontSize = 18.sp
                            )
                            Text(
                                "Steps Completed: ${todayHistory?.steps}",
                                modifier = Modifier.padding(0.dp, 5.dp),
                                fontSize = 18.sp
                            )
                        }
                    }
                }
                Box(
                    contentAlignment = Alignment.TopCenter
                ){
                    CircularProgressIndicator(
                        progress = 1f,
                        modifier = Modifier.size(200.dp),
                        strokeWidth = 10.dp,
                        color = Color.Blue
                    )
                    CircularProgressIndicator(
                        progress = progressCnt.toFloat(),
                        modifier = Modifier.size(200.dp),
                        strokeWidth = 10.dp,
                        color = Color.Green
                    )
                    var prg = (progressCnt*100).toInt()
                    Text("${if( prg <= 100 ) prg else 100}%", fontSize = 21.sp, color = Color.Black, fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(0.dp, 80.dp))
                }
                Button(
                    onClick = {
                        todayHistory?.steps = todayHistory?.steps!! + 1
                        todayHistory?.id?.let {
                            goalsModel.updateSteps(
                                todayHistory?.steps!!,
                                it
                            )
                        }
                    },
                    modifier = Modifier.padding(0.dp, 10.dp).width(120.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Blue,
                        disabledBackgroundColor = Color.Gray
                    ),
                    enabled = todayHistory != null
                ){
                    if(todayHistory == null)
                        Text(text = "Set a goal for the day")
                    else
                        Text("Add step")
                }
            }
//            Row(
//                modifier = Modifier.padding(15.dp, 7.dp).fillMaxWidth(),
//            ){
//                savedRecBtn()
//            }
        }
    }
}