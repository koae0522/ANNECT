package com.example.annect.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.annect.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp


@Composable
fun HomeScreen(
    onMiniGameButtonClicked: ()->Unit = {}, onConnectButtonClicked: ()->Unit = {},
    name:String,body:Int,eye:Int,mouth:Int,accessory:Int
){

    Box(){

        //背景の設定
        Image(
            painter = painterResource(id = R.drawable.room),
            contentDescription = null,

            //透明度50%
            modifier = Modifier.fillMaxSize().
            graphicsLayer { this.alpha = 0.5f },
            //画面いっぱいに表示
            contentScale = ContentScale.FillBounds)

        Column(
            modifier = Modifier
            .displayCutoutPadding()
            .fillMaxSize().padding(10.dp),
            verticalArrangement = Arrangement.Center
        ){

            //名前表示
            Card(modifier = Modifier){
                Text(name,modifier = Modifier.padding(15.dp), style = MaterialTheme.typography.titleLarge)
            }

            Row(){

                //ミニゲームのボタン
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    //押したらミニゲームに遷移
                    modifier = Modifier.clickable { onMiniGameButtonClicked()}){
                    Image(painterResource(id = R.drawable.toybox), contentDescription = null,)
                    Text("ミニゲーム",style = MaterialTheme.typography.bodyLarge)
                }

                //Anima表示
                DisplayAnima( name,body,eye,mouth,accessory)

                //コネクトモードのボタン
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    //押したらコネクトモードに
                    modifier = Modifier.clickable { onConnectButtonClicked() }){
                    Image(painterResource(id = R.drawable.tansu), contentDescription = null)
                    Text("コネクトモード", style = MaterialTheme.typography.bodyLarge)
                }

            }
        }
    }



}
