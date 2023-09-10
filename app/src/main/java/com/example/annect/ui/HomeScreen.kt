package com.example.annect.ui

import android.content.ContentValues.TAG
import android.util.Log
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
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.annect.data.DisplayAnima


@Composable
fun HomeScreen(
    onMiniGameButtonClicked: ()->Unit = {}, onConnectButtonClicked: ()->Unit = {},
    onClearDataClicked: ()->Unit = {},onAnimaChannelButtonClicked: ()->Unit = {},
    name:String,body:Int,eye:Int,mouth:Int,accessory:Int
){

    Box {

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

        ){
            //名前表示
            Card(modifier = Modifier){
                Text(name,modifier = Modifier.padding(15.dp), style = MaterialTheme.typography.titleLarge)
            }

            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding(top=50.dp)){
                //ミニゲームのボタン
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    //押したらミニゲームに遷移
                    modifier = Modifier.clickable { onMiniGameButtonClicked()}){
                    Image(painterResource(id = R.drawable.toybox), contentDescription = null, modifier = Modifier.size(200.dp))
                    Text("ミニゲーム",style = MaterialTheme.typography.bodyLarge)
                }

                //Anima表示
             DisplayAnima( body,eye,mouth,accessory, modifier = Modifier.size(200.dp))

                //コネクトモードのボタン
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    //押したらコネクトモードに
                    modifier = Modifier.clickable { onConnectButtonClicked() }){
                    Image(painterResource(id = R.drawable.tansu), contentDescription = null, modifier = Modifier.size(200.dp))
                    Text("コネクトモード", style = MaterialTheme.typography.bodyLarge)
                }

                //アニマちゃんねる
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    //押したらアニマちゃんねるに
                    modifier = Modifier.clickable { onAnimaChannelButtonClicked() }){
                    Image(painterResource(id = R.drawable.pc), contentDescription = null, modifier = Modifier.size(200.dp))
                    Text("あにまちゃんねる", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }

        Button(onClick=onClearDataClicked, modifier = Modifier.align(Alignment.TopEnd)){
            Text("データ消去")
        }

    }



}
