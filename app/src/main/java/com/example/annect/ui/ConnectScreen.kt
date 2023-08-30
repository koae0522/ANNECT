package com.example.annect.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//この画面でArduinoとの通信をしたい
@Composable
fun ConnectScreen(onHomeButtonClicked: ()->Unit = {}){
    Column(){
        Text("this is ConnectScreen")
        Button(onClick = {onHomeButtonClicked()}) {
            Text(text = "return to HomeScreen")
        }
    }


}