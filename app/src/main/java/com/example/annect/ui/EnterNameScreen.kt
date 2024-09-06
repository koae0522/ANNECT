package com.example.annect.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.annect.data.DisplayAnima
import com.example.annect.data.DisplayAnimaNoOffset

//名前入力画面
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterNameScreen(body:Int,eye:Int,eyeOver:Int,mouth:Int,accessory:Int,
                    onNextButtonClicked: ()->Unit = {},onDecideButtonClicked :(String)->Unit = {}){
    var text by remember { mutableStateOf("") }

    //名前は6文字まで
    val maxLength=6

    //決定ボタンを押したかどうかフラグ　trueになったらポップアップを表示
    var decideButtonFlag by remember { mutableStateOf(false) }

Box(modifier = Modifier
    .fillMaxSize()
    .background(
        Brush.linearGradient(
            colors = listOf(MaterialTheme.colorScheme.primaryContainer,MaterialTheme.colorScheme.secondaryContainer,
                Color.White )
        )

    )){

    Column(modifier = Modifier
        .displayCutoutPadding()
        .fillMaxWidth()
        .padding(top = 20.dp, start = 20.dp,end=20.dp)){
    Card (
        elevation = CardDefaults.cardElevation(
        defaultElevation = 10.dp
    )){
        Text("Animaの名前を決めよう", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(10.dp) )
    }
    Row(){
        DisplayAnimaNoOffset(body = body, eye = eye, eyeOver = eyeOver, mouth = mouth, accessory = accessory,modifier = Modifier)
        Column() {

            OutlinedTextField(
                value = text,
                onValueChange = { if(it.length<=maxLength) text = it },
                modifier = Modifier
                    .padding(start = 125.dp, top = 80.dp, bottom = 20.dp),
                label = { Text("名前を入力(${maxLength}文字まで)") },
                singleLine = true,

                )

            
        }

    }

            Button(onClick = {
                //名前が入力されていないときは押せない
                if (text.isNotEmpty()) {
                    onDecideButtonClicked (text)
                    decideButtonFlag = true
                }
            },

                Modifier
                    .padding(end = 0.dp)
                    .offset(y = (-10).dp)
                    .align(Alignment.End)
                    .size(120.dp)
                    //名前が入力されていないときはグレーっぽくする
                    .graphicsLayer {
                        if (text.isEmpty()) {
                            this.alpha = 0.2f
                        }
                    },
                colors = ButtonDefaults.textButtonColors(
                    containerColor=MaterialTheme.colorScheme.secondary,
                    contentColor = Color.White,
                    disabledContentColor = Color.LightGray
                ),
                shape = MaterialTheme.shapes.medium){
                Text("決定！",fontSize = 20.sp)
            }
}

//ポップアップ
    AnimatedVisibility(decideButtonFlag,) {
        Card(
            Modifier
                .offset(x = LocalConfiguration.current.screenWidthDp.dp / 2,y = (100).dp)
                .size(width = 350.dp,height = 200.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            )){
            Column( verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(start=20.dp)) {
                Text(text="あなたのAnimaです!\nかわいがってあげましょう！", modifier = Modifier.padding(top=40.dp),
                    style=MaterialTheme.typography.bodyLarge,textAlign = TextAlign.Center,)
                    Button(onClick = { onNextButtonClicked() },
                        Modifier){
                        Text("ホームへ")
                    }
            }
        }
    }

}

}

