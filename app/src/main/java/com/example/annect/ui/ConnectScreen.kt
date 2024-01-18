package com.example.annect.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.annect.R

//この画面でArduinoとの通信をしたい
@Composable
fun ConnectScreen(onHomeButtonClicked: ()->Unit = {},
                  onCatButtonClicked: ()->Unit = {},onUnicornButtonClicked: ()->Unit = {},onUsagiButtonClicked: ()->Unit = {}){
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
            .displayCutoutPadding()
            .fillMaxWidth()
            .padding(start = 20.dp, top = 20.dp, end = 20.dp)){

            Card (modifier = Modifier,
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),
                ){
                Text("変身したい生き物を選ぼう", style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(10.dp)
                )
            }

            Row(modifier=Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center,) {
                Image(painter = painterResource(id = R.drawable.neko3),
                    contentDescription = null,
                    modifier = Modifier.
                    size(250.dp).
                    clickable { onCatButtonClicked() })
                Image(painter = painterResource(id = R.drawable.unicorn3),
                    contentDescription = null,
                    modifier = Modifier.
                    size(250.dp).
                    clickable { onUnicornButtonClicked() })
                Image(painter = painterResource(id = R.drawable.usagi),
                    contentDescription = null,
                    modifier = Modifier.
                    size(250.dp).
                    clickable { onUsagiButtonClicked() })
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

//@Composable
//fun AnimalSelectCard(animalName:String,img:Int,buttonAction:()->Unit = {}){
//
//        Column(modifier = Modifier.clickable { buttonAction() }){
//            Text(animalName,textAlign = TextAlign.Center)
//            Image(painter = painterResource(id = img), contentDescription = null,
//                modifier = Modifier.size(200.dp))
//        }
//
//}