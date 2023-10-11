package com.example.annect.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialogDefaults.shape
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.format.TextStyle

//この画面でArduinoとの通信をしたい
@Composable
fun ConnectCheckScreen(onHomeButtonClicked: ()->Unit = {}, onNextButtonClicked: () -> Unit = {},context : Context){
    //判定
    var connectCheck : Int by remember { mutableIntStateOf(0) }
    //繋げるかどうかチェック
    val connect = USBSerial(context)
Box( modifier = Modifier
        .fillMaxSize()
        .background(
            Brush.linearGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.primaryContainer,
                    MaterialTheme.colorScheme.secondaryContainer,
                    Color.White
                )
            )
        )
) {
    Column(modifier=Modifier
        .displayCutoutPadding()
        .fillMaxWidth()
        .padding(10.dp),
        ) {
        Card(
            modifier = Modifier.align(Alignment.Start),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            ),
        ) {
            Text(
                "PetaVasと接続しよう", style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(10.dp).align(Alignment.Start)
            )
        }

        Text("PetaVasとケーブルで接続してから「接続する」ボタンを押してください",
            style = MaterialTheme.typography.titleLarge, fontSize = 25.sp,
            modifier = Modifier.weight(1f).padding(vertical = 15.dp))

        //接続できたら1。できなかったら0
        Text(text = if(connectCheck==0)"接続されていません" else if(connectCheck==1)"接続完了！" else "エラー！",
            modifier = Modifier.weight(1f).align(Alignment.CenterHorizontally))

        Button(onClick = { connectCheck = connect.open(context) },
            modifier = Modifier.weight(1f).align(Alignment.CenterHorizontally),
            shape = MaterialTheme.shapes.medium) {
            Text(text = "接続する", style = androidx.compose.ui.text.TextStyle(fontSize = 30.sp))
        }

        Row(modifier = Modifier.weight(1f)){

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Button(onClick = { onHomeButtonClicked() },
                    shape = MaterialTheme.shapes.medium) {
                    Text(text = "戻る")
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(onClick = { if(connectCheck==1) onNextButtonClicked() },
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.graphicsLayer {
                        if (connectCheck==0) {
                            this.alpha = 0.2f
                        }
                    }) {
                    Text(text = "次へ")
                }
            }


        }

    }
}
}




