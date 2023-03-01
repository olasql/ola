package com.example.fitnessapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
fun HomePage(){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ){
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Column(
                modifier = Modifier.fillMaxWidth().height(400.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
                        progress = 0.19f,
                        modifier = Modifier.size(200.dp),
                        strokeWidth = 10.dp,
                        color = Color.Green
                    )
                    Text("19%", fontSize = 21.sp, color = Color.Black, fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(0.dp, 80.dp))
                }
                Button(
                    onClick = {},
                    modifier = Modifier.padding(0.dp, 10.dp).width(120.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Blue
                    )
                ){
                    Text(text = "Add Step")
                }
            }
            Row(
                modifier = Modifier.padding(15.dp, 7.dp).fillMaxWidth(),
            ){
                savedRecBtn()
            }
        }
    }
}