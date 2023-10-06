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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
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
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { this.alpha = 0.5f },
            //画面いっぱいに表示する
            contentScale = ContentScale.FillBounds)

        Column(
            modifier = Modifier
                .displayCutoutPadding()
                .fillMaxSize()
                .padding(10.dp),

        ){
            //名前表示
            Card(modifier = Modifier,elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            ), ){
                Text(name,modifier = Modifier.padding(15.dp), style = MaterialTheme.typography.titleLarge)
            }

            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding()){
                //Anima表示
                DisplayAnima( body,eye,mouth,accessory, modifier = Modifier.size(250.dp))

                //ミニゲームのボタン
                Menu(clickAction =  onMiniGameButtonClicked,name="ミニゲーム",image=R.drawable.toybox1)

                //アニマちゃんねる
                Menu(clickAction =  onAnimaChannelButtonClicked,name="あにま\nちゃんねる",image=R.drawable.pc)

                //コネクトモードのボタン
                Menu(clickAction =  onConnectButtonClicked,name="コネクト\nモード",image=R.drawable.tansu1)

                }

        }

        Button(onClick=onClearDataClicked, modifier = Modifier.align(Alignment.TopEnd)){
            Text("データ消去")
        }

    }

}

@Composable
fun Menu(clickAction: ()->Unit = {},name: String,image:Int){
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            //押したらコネクトモードに
            modifier = Modifier.clickable { clickAction() }){
            Card(shape=MaterialTheme.shapes.large,
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),
                modifier = Modifier.padding(top=20.dp,start=10.dp,end=10.dp).width(180.dp).height(250.dp)) {
                Image(
                    painterResource(id = image),
                    contentDescription = null,
                    modifier = Modifier.size(160.dp)
                )
                Text(name, style = MaterialTheme.typography.titleLarge,textAlign = TextAlign.Center)
            }

        }


}
