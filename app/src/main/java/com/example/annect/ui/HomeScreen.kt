package com.example.annect.ui

import android.content.ContentValues
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
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import java.util.Timer
import kotlin.concurrent.schedule


@Composable
fun HomeScreen(
    onMiniGameButtonClicked: ()->Unit = {}, onConnectButtonClicked: ()->Unit = {},
    onClearDataClicked: ()->Unit = {},onAnimaChannelButtonClicked: ()->Unit = {},
    name:String,body:Int,eye:Int,mouth:Int,accessory:Int,onInteractionSwitchClicked: (Boolean)->Unit = {},
    onDisplayFaceSwitchClicked:(Boolean)->Unit = {},interaction:Boolean,displayFace:Boolean
){
    var eyeResource by remember { mutableIntStateOf(eye) }
    var mouthResource by remember { mutableIntStateOf(mouth) }

    //まばたき
    val timer1 = remember { Timer() }
    DisposableEffect(Unit) {
        timer1.schedule(100, 1000) {
            eyeResource=eye
            Log.d(ContentValues.TAG,"a")
        }
        onDispose {
            timer1.cancel()
        }
    }


    val timer2 = remember { Timer() }
    DisposableEffect(Unit) {
        timer2.schedule(100, 5000) {
            eyeResource=R.drawable.eye2
            Log.d(ContentValues.TAG,"b")
        }
        onDispose {
            timer2.cancel()
        }
    }

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
            Row(){
                Card(modifier = Modifier,elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ), ){
                    Text(name,modifier = Modifier.padding(15.dp), style = MaterialTheme.typography.titleLarge)
                }

                Switch(checked = interaction,
                    onCheckedChange = {
                        onInteractionSwitchClicked(it)
                    })
                Switch(checked = displayFace,
                    onCheckedChange = {
                        onDisplayFaceSwitchClicked(it)
                    })
            }



            Row( verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding()){

                //Anima表示
                Box(modifier = Modifier.weight(1.5f).pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            // 押してる
                            mouthResource = R.drawable.mouth4

                            tryAwaitRelease()

                            // 離した
                            mouthResource = mouth
                        }
                    )
                }){
                    Image(painter = painterResource(id = body), contentDescription = null)
                    Image(painter = painterResource(id =  eyeResource), contentDescription = null)
                    Image(painter = painterResource(id = mouthResource), contentDescription = null)
                    Image(painter = painterResource(id = accessory), contentDescription = null)
                }

                //ミニゲームのボタン
                Image(painter= painterResource(id = R.drawable.minigame),
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onMiniGameButtonClicked() }
                )

                //アニマチャンネルのぼたん
                Image(painter= painterResource(id = R.drawable.animachannel),
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onAnimaChannelButtonClicked() }
                    )

                //コネクトモードのボタン
                Image(painter= painterResource(id = R.drawable.connectmode),
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onConnectButtonClicked() }
                    )

//                //ミニゲームのボタン
//                Menu(clickAction =  onMiniGameButtonClicked,name="ミニゲーム",image=R.drawable.toybox1)
//
//                //アニマちゃんねる
//                Menu(clickAction =  onAnimaChannelButtonClicked,name="あにま\nちゃんねる",image=R.drawable.pc)
//
//                //コネクトモードのボタン
//                Menu(clickAction =  onConnectButtonClicked,name="コネクト\nモード",image=R.drawable.tansu1)

                }

        }

//        Button(onClick=onClearDataClicked, modifier = Modifier.align(Alignment.TopEnd)){
//            Text("データ消去")
//        }

    }

}

//
//@Composable
//fun Menu(clickAction: ()->Unit = {},name: String,image:Int){
//    Column(horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center,
//        //押したらコネクトモードに
//        modifier = Modifier.clickable { clickAction() }){
//        Card(shape=MaterialTheme.shapes.large,
//            elevation = CardDefaults.cardElevation(
//                defaultElevation = 10.dp
//            ),
//            modifier = Modifier.padding(top=20.dp,start=10.dp,end=10.dp).width(180.dp).height(250.dp)) {
//            Image(
//                painterResource(id = image),
//                contentDescription = null,
//                modifier = Modifier.size(160.dp)
//            )
//            Text(name, style = MaterialTheme.typography.titleLarge,textAlign = TextAlign.Center)
//        }
//
//    }
//
//
//}