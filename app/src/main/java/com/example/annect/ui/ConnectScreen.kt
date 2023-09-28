package com.example.annect.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.annect.R

//この画面でArduinoとの通信をしたい
@Composable
@Preview
fun ConnectScreen(onHomeButtonClicked: ()->Unit = {},
                  onCatButtonClicked: ()->Unit = {},onUnicornButtonClicked: ()->Unit = {}){
    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.displayCutoutPadding().fillMaxSize().padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,){
            Text("変身したい生き物を選ぼう！", style = MaterialTheme.typography.titleLarge)
            Row {
                AnimalSelectCard("ねこ", R.drawable.cat,onCatButtonClicked)
                AnimalSelectCard(animalName = "ユニコおん", img = R.drawable.unicoooooooooooooooon,onUnicornButtonClicked)
            }
            Button(onClick = {onHomeButtonClicked()},
                Modifier
                    .padding(end = 0.dp)
                    .align(Alignment.Start),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = "戻る" ,fontSize = 20.sp)
            }
        }
    }

}

@Composable
fun AnimalSelectCard(animalName:String,img:Int,buttonAction:()->Unit = {}){

        Column(modifier = Modifier.clickable { buttonAction() }){
            Text(animalName,textAlign = TextAlign.Center)
            Image(painter = painterResource(id = img), contentDescription = null,
                modifier = Modifier.size(200.dp))
        }

}