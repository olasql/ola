package com.example.fitnessapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitnessapp.models.GoalsModel

@Composable
fun HistoryPage(
    goalsModel: GoalsModel
) {
    val goalHistory by goalsModel.goalsHistList.observeAsState(listOf())
    Column(

    ) {
        Column(
            modifier = Modifier.padding(7.dp, 0.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ){
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White
                ),
                border = BorderStroke(1.dp, color = Color.Blue)
            ){
                Text("clear all", color = Color.Blue)
            }
        }
        LazyColumn(
            modifier = Modifier.padding(7.dp).fillMaxSize()
        ) {
            items(goalHistory) { history ->
                HistoryItem(history.date.toString(), history.steps)
            }
        }
    }
}
@Composable
fun HistoryItem(title: String, steps: Int){
    Card(
        modifier = Modifier.fillMaxWidth().padding(0.dp, 7.dp),
        backgroundColor = Color.White,
        border = BorderStroke(1.dp, color = Color.Gray)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(15.dp, 5.dp)
            ) {
                Text(text = "$title", fontSize = 19.sp, color = Color.Blue, fontWeight = FontWeight.Bold)
                Text(text = "$steps steps", fontSize = 18.sp, color = Color.Blue)
            }
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White
                ),
                elevation = null
            ){
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = null,
                    tint = Color.Red
                )
            }
        }
    }
}