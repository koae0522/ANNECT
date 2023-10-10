package com.example.annect.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


//この画面でArduinoとの通信をしたい
@Composable
fun ConnectCheckScreen(onHomeButtonClicked: ()->Unit = {}, onNextButtonClicked: () -> Unit = {},context : Context){

    //判定
    var connectCheck : Int by remember { mutableIntStateOf(0) }

    //繋げるかどうかチェック
    val connect = USBSerial(context)

    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            Brush.linearGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.primaryContainer,
                    MaterialTheme.colorScheme.secondaryContainer,
                    Color.White
                )
            )
        )){

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp, start = 20.dp,end=20.dp)
                .displayCutoutPadding()) {

                Card (
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 10.dp
                    )){
                    Text("デバイスとの接続確認", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(10.dp) )
                }

                Text(text = connectCheck.toString(), modifier = Modifier
                    .padding(20.dp)
                    .align(CenterHorizontally))

                Button(onClick = { connectCheck = connect.open(context) },
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(text = "Connect!",
                        modifier = Modifier
                        .padding(20.dp))
                }


                Row(modifier = Modifier
                    .fillMaxSize()){
                    Button(onClick = { onHomeButtonClicked() },
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(text = "return to HomeScreen",modifier = Modifier
                            .padding(20.dp)
                            .wrapContentSize(Alignment.BottomStart))
                    }



                    Button(onClick = {
                        if(connectCheck == 1){
                            onNextButtonClicked()
                        }
                                     },
                        shape = MaterialTheme.shapes.medium) {
                        Text(text = "Next to ConnectScreen",modifier = Modifier
                            .padding(20.dp)
                            .wrapContentSize(Alignment.BottomEnd))
                    }

                }

            }


    }


}