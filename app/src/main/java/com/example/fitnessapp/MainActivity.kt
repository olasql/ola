package com.example.fitnessapp

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fitnessapp.models.GoalsModel
import com.example.fitnessapp.ui.theme.FitnessAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitnessAppTheme {
                // A surface container using the 'background' color from the theme
                val owner = LocalViewModelStoreOwner.current

                owner?.let {
                    val goalsModel: GoalsModel = viewModel(
                        it,
                        "GoalsModel",
                        GoalsModelFactory(
                            LocalContext.current.applicationContext
                                    as Application
                        )
                    )

                    MainContent(goalsModel)
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainContent(goalsModel: GoalsModel){
    var navigator by rememberSaveable {mutableStateOf("Home")}
    Scaffold(
//                    backgroundColor = Color.White,
        topBar = {
            TopAppBar(
                title = { Text("FITNESS APP ACTIVITY RECORDING") },
                backgroundColor = Color.Blue
            )
        },
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color.Blue,
                modifier = Modifier.padding()
            ){
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
//                                 verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ){
                    NavIcon(btnText = "Home", Icons.Filled.Home, onNavClicked = {navigator = it})
                    NavIcon(btnText = "Goals", Icons.Filled.PlayArrow, onNavClicked = {navigator = it})
                    NavIcon(btnText = "History", Icons.Filled.Refresh, onNavClicked = {navigator = it})
                }
            }
        }
    ){
        Surface(
            modifier = Modifier.padding(0.dp,0.dp,0.dp,50.dp).fillMaxSize(),
            color = Color.White,

            ) {
            if(navigator == "Home")
                HomePage(goalsModel)
            else if(navigator == "Goals")
                GoalsPage(goalsModel)
            else if(navigator == "History")
                HistoryPage(goalsModel)
        }
    }

}
@Composable
fun NavIcon(btnText: String, icon: ImageVector, onNavClicked: (String) -> Unit){
    Button(
        onClick = {
            onNavClicked(btnText)
                  },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
//        border = BorderStroke(width = 0.dp, color = Color.Transparent)
        elevation = null,
    ){
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(28.dp)
            )
            Text(text = btnText, fontSize = 10.sp)
        }
    }
}