package com.example.annect.ui

import android.content.ContentValues
import android.content.Context
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
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
fun ConnectFaceScreen( body:Int,eye:Int,mouth:Int,accessory:Int,animal:String,
                       context:Context,onHomeButtonClicked:() -> Unit = {}){


    var backgroundColor=Color(0xFFDAD6CD)
    var test = "not_recv"
    val connect =  remember { USBSerial(context)}

    var animal_data = ""
    var check by remember {
        mutableIntStateOf(0)
    }
    //えらんだ動物で背景色を変える
    when(animal){
        "ねこ"-> backgroundColor=Color(0xFFDAD6CD)
        "ユニコーン"->backgroundColor= Color(0xFFD2A3CB)
    }

    if(check == 0){
        when(animal){
            "ねこ"-> animal_data = "a"
            "ユニコーン"->animal_data = "b"
        }
        connect.write(context,animal_data,8)
        check = 1
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
            modifier= Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = 2F
                    scaleY = 2F
                }.offset(y = (70).dp)

        )
        Image(painter = painterResource(id = mouthResource),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier= Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = 2F
                    scaleY = 2F
                }.offset(y = (70).dp)

        )

        Box(modifier = Modifier.fillMaxSize()){
            Column {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            //上側を触った時の処理
                            onPress = {
                                // 押してる
                                mouthResource = R.drawable.mouth1
                                test = connect.write(context, "t", 8)
                                test += "t"

                                tryAwaitRelease()

                                // 離した
                                mouthResource = mouth
//                                test = connect.write(context, "t", 8)
//                                test += "t"
                            },

                            )
                    }) {
                    //ホームボタン
                Image(painter=painterResource(id=R.drawable.baseline_home_24),contentDescription = null,
                    modifier=Modifier.clickable {
                        onHomeButtonClicked()
                    })
                }
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            //下側を触った時の処理
                            onTap = {
                                test = connect.write(context, "s", 8)
                                test += "s"
                            }
                        )
                    }

                ){}
            }
        }
    }
}