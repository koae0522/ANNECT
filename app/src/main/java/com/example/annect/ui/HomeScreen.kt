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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.Timer
import kotlin.concurrent.schedule


@OptIn(ExperimentalMaterial3Api::class)
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

    Scaffold(
        bottomBar={
            BottomAppBar(
                modifier = Modifier
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    BottomBarMenu(icon=R.drawable.pets_icon, text="コネクト", onClick=onConnectButtonClicked)
                    BottomBarMenu(icon=R.drawable.cloth_icon, text="アニマ着替え", onClick={})
                    BottomBarMenu(icon=R.drawable.forum_icon, text="あにまちゃんねる", onClick=onAnimaChannelButtonClicked)
                    BottomBarMenu(icon=R.drawable.game_icon, text="ミニゲーム", onClick=onMiniGameButtonClicked)
                }
            }
        },
        content={
            Box (modifier = Modifier.padding(it)){

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

//                Switch(checked = interaction,
//                    onCheckedChange = {
//                        onInteractionSwitchClicked(it)
//                    })
//                Switch(checked = displayFace,
//                    onCheckedChange = {
//                        onDisplayFaceSwitchClicked(it)
//                    })
                    }



                    Row( modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center){
                        //Anima表示
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onPress = {
                                        // 押してる
                                        mouthResource = R.drawable.mouth4

                                        tryAwaitRelease()

                                        // 離した
                                        mouthResource = mouth
                                    }
                                )
                            },
                            ){
                            Image(modifier = Modifier.align(Alignment.Center),painter = painterResource(id = body), contentDescription = null)
                            Image(modifier = Modifier.align(Alignment.Center),painter = painterResource(id =  eyeResource), contentDescription = null)
                            Image(modifier = Modifier.align(Alignment.Center),painter = painterResource(id = mouthResource), contentDescription = null)
                            Image(modifier = Modifier.align(Alignment.Center),painter = painterResource(id = accessory), contentDescription = null)
                        }

                    }

                }

//        Button(onClick=onClearDataClicked, modifier = Modifier.align(Alignment.TopEnd)){
//            Text("データ消去")
//        }

            }
        }
    )


}

@Composable
fun BottomBarMenu(icon:Int,text:String,onClick: ()->Unit){
    Column(){
        IconButton(onClick =onClick,
            modifier = Modifier.padding(horizontal = 40.dp)) {
            Icon(painter=painterResource(id =icon),
                contentDescription = null)
        }
        Text(text,modifier = Modifier.align(Alignment.CenterHorizontally))
    }
}

@Preview(showBackground = true,widthDp = 800, heightDp = 400)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        body = R.drawable.body1,
        eye = R.drawable.eye1,
        mouth = R.drawable.mouth1,
        accessory = R.drawable.accessory1,
        interaction = false,
        name="テスト",
        displayFace = false,
    )
}
