package com.example.annect.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//ミニゲーム画面(仮)
@Composable
fun MiniGameScreen(onHomeButtonClicked: ()->Unit = {}){
    Column(){
        Text("this is MiniGameScreen")
        Button(onClick = { onHomeButtonClicked() }) {
            Text(text = "return to HomeScreen")
        }
    }
}