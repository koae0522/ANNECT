package com.example.annect.ui

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.annect.R
import java.util.Timer
import kotlin.concurrent.schedule

@Composable
fun ConnectFaceScreen( body:Int,eye:Int,mouth:Int,accessory:Int,animal:String, ){

    var backgroundColor=Color(0xFFDAD6CD)
    //えらんだ動物で背景色を変える
    when(animal){
        "ねこ"-> backgroundColor=Color(0xFFDAD6CD)
        "ユニコーン"->backgroundColor= Color(0xFFD2A3CB)
    }

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

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = backgroundColor),
        contentAlignment = Alignment.Center){

        Image(painter = painterResource(id = eyeResource),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier=Modifier.fillMaxSize().graphicsLayer {
                scaleX= 2F
                scaleY=2F
            }.padding(top=150.dp)
        )
        Image(painter = painterResource(id = mouthResource),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier=Modifier.fillMaxSize().graphicsLayer {
                scaleX= 2F
                scaleY=2F
            }.padding(top=150.dp)
        )

        Box(modifier = Modifier.fillMaxSize()){
            Column {
                Box(modifier = Modifier.fillMaxWidth().weight(1f).pointerInput(Unit) {
                    detectTapGestures(
                        //上側を触った時の処理
                        onPress = {
                            // 押してる
                            mouthResource= R.drawable.mouth1

                            tryAwaitRelease()

                            // 離した
                            mouthResource=mouth
                        },
                    )
                }) {}
                Box(modifier = Modifier.fillMaxWidth().weight(1f).pointerInput(Unit){
                    detectTapGestures (
                        //下側を触った時の処理
                        onTap = {

                        }
                    )
                }){}
            }
        }
    }
}