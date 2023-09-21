package com.example.annect.ui

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

//この画面でArduinoとの通信をしたい
@Composable
fun ConnectCheckScreen(onHomeButtonClicked: ()->Unit = {}, onNextButtonClicked: () -> Unit = {},context : Context){

    var connectCheck : Int by remember { mutableIntStateOf(0) }

    val connect = USBSerial(context)

    Column(){
        Text("this is ConnectCheck")

        Button(onClick = {connectCheck = connect.open(context)}) {
            Text(text = "Connect!")
        }

        Text(text = connectCheck.toString())

        Button(onClick = {onHomeButtonClicked()}) {
            Text(text = "return to HomeScreen")
        }

        if(connectCheck == 1){
            Button(onClick = {onNextButtonClicked()}) {
                Text(text = "Next to ConnectScreen")
            }
        }

    }


}