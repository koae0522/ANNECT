package com.example.annect.ui

import android.content.ContentValues
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.annect.data.DisplayAnima
import com.example.annect.ui.animaMotion.eyeAnimation
import java.util.Timer
import kotlin.concurrent.schedule


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onMiniGameButtonClicked: ()->Unit = {}, onConnectButtonClicked: ()->Unit = {},
    onClearDataClicked: ()->Unit = {},onAnimaChannelButtonClicked: ()->Unit = {},
    name:String,body:Int,eye:Int,eyeOver:Int,mouth:Int,accessory:Int,onInteractionSwitchClicked: (Boolean)->Unit = {},
    onDisplayFaceSwitchClicked:(Boolean)->Unit = {},interaction:Boolean,displayFace:Boolean
){
    var eyeResource by remember { mutableIntStateOf(eye) }
    var eyeOverResource by remember { mutableIntStateOf(eyeOver) }
    var mouthResource by remember { mutableIntStateOf(mouth) }
    var eyeAnimation by remember { mutableStateOf(eyeAnimation(2f)) }
    var eyeOffset by remember { mutableStateOf(Offset.Zero) }

    LaunchedEffect(Unit) {
        eyeAnimation.startBlink()
    }

    val blinking by eyeAnimation.blinking.collectAsState()
    eyeResource = if (blinking) R.drawable.eye_mabataki else eye
    eyeOverResource = if (blinking) R.drawable.eye_mabataki else eyeOver

    Scaffold(
        bottomBar={
            BottomAppBar(
                containerColor = Color.Transparent
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
            padding ->
            Box (
                modifier = Modifier
                    .onSizeChanged { size ->
                        eyeAnimation.origin = Offset(size.width / 2f, size.height / 2f)
                        Log.d("size", eyeAnimation.origin.toString())
                    }
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                eyeAnimation.startAnimation(offset)
                                eyeOffset = eyeAnimation.calculateEyeOffset()
                            },
                            onDrag = { change, _ ->
                                eyeAnimation.onDragAnimation(change)
                                eyeOffset = eyeAnimation.calculateEyeOffset() // eyeOffset
                            },
                            onDragEnd = {
                                eyeOffset = Offset.Zero
                                eyeAnimation.onDragEndAnimation()
                            }
                        )
                    }
            ){

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
                        ,

                    ){
                    //名前表示
                    Row(){
                        Card(modifier = Modifier,elevation = CardDefaults.cardElevation(
                            defaultElevation = 10.dp
                        ), ){
                            Text(name,modifier = Modifier.padding(15.dp), style = MaterialTheme.typography.titleLarge)
                        }
                    }
                    Row( modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center){
                            DisplayAnima(body,eyeResource,eyeOverResource,mouthResource,accessory,modifier = Modifier
                                .alpha(
                                    animateFloatAsState(
                                        targetValue = if (blinking) 0f else 1f,
                                        animationSpec = tween(durationMillis = 100)
                                    ).value
                                ),
                                animateOffsetAsState(targetValue = eyeOffset,
                                    label = ""
                                )
                            )
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

@Preview(widthDp = 915, heightDp = 412)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        body = R.drawable.body1,
        eye = R.drawable.eye2_under,
        mouth = R.drawable.mouth1,
        accessory = R.drawable.accessory1,
        interaction = false,
        name="テスト",
        displayFace = false,
        eyeOver = R.drawable.eye2_over,
    )
}
