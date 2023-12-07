package com.example.annect.ui

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Context.*
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Vibrator
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.annect.R
import java.util.Timer
import kotlin.concurrent.schedule
import kotlinx.coroutines.*

@Composable
fun ConnectFaceScreen( body:Int,eye:Int,mouth:Int,accessory:Int,animal:String,
                       context:Context,onHomeButtonClicked:() -> Unit = {}){

    var backgroundColor=Color(0xFFDAD6CD)
    var test = "not_recv"
    val connect =  remember { USBSerial(context)}
    val vibrator = context.getSystemService(VIBRATOR_SERVICE) as Vibrator
    var animal_data = ""
    var check by remember {
        mutableIntStateOf(0)
    }
    var gorogoro by remember{
        mutableStateOf(false)
    }
    var lirax by remember{
        mutableStateOf(0)
    }

    //サウンド関連の処理
    lateinit var soundPool: SoundPool
    //サウンドの設定をカプセル化する　用途と何を再生しているかを設定
    val audioAttributes = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_GAME)
        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
        .build()
    soundPool = SoundPool.Builder()
        .setAudioAttributes(audioAttributes)
        // ストリーム数に応じて
        .setMaxStreams(1)
        .build()
    var catNya = 0
    var catNyaun = 0
    var catAmae = 0
    var catSya = 0

    catNya = soundPool.load(context,R.raw.cat_nya,1)
    catNyaun = soundPool.load(context,R.raw.cat_nyaun,1)
    catAmae = soundPool.load(context,R.raw.cat_amae,1)
    catSya = soundPool.load(context,R.raw.cat_sya,1)

    //えらんだ動物で背景色を変える
    when(animal){
        "ねこ"-> backgroundColor=Color(0xFFafafb0)
        "ユニコーン"->backgroundColor= Color(0xFFF2F2F2)
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

    //lirax値を時間経過で下げるタイマー
    val liraxDownTimer = remember { Timer() }
    DisposableEffect(Unit) {
        liraxDownTimer.schedule(100, 30000) {
            if(lirax>=0){
                lirax--
                Log.d(TAG, lirax.toString())
            }
        }
        onDispose {
            liraxDownTimer.cancel()
        }
    }

    //瞬き改修版タイマー
    val blinkTimer = remember { Timer() }
    DisposableEffect(Unit) {
        //5秒に一回
        blinkTimer.schedule(100, 5000) {
            runBlocking {
                if(!gorogoro){
                    eyeResource = R.drawable.eye2
                    //0.5秒間まばたき
                    delay(500)
                    eyeResource = eye
                }
            }
        }
        onDispose {
            blinkTimer.cancel()
        }
    }

    if(lirax>=10 && !gorogoro){
        //ゴロゴロ時の処理
        gorogoro=true
        vibrator.vibrate(longArrayOf(0,10,10), 1)
        eyeResource=R.drawable.eye5
        mouthResource=R.drawable.mouth2
        Log.d(TAG,"c")
        soundPool.play(catNya,1.0f,1.0f,0,0,1.0f)
    }

    if(lirax<10 && gorogoro){
        gorogoro=false
        vibrator.cancel()
    }


    //UI
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = backgroundColor),
        contentAlignment = Alignment.Center){


        //ひげ
        Image(painter = painterResource(id = R.drawable.hige),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier= Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = 2F
                    scaleY = 2F
                }.offset(y = (70).dp)
        )

        //め
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

        //くち
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
                                test = connect.write(context, "t", 8)
                                test += "t"


                                lirax++


                                Log.d(TAG, lirax.toString())
                            },
                            onTap={

                            }

                            )
                    }) {
                    Row(){
                        Image(painter=painterResource(id=R.drawable.baseline_home_24),contentDescription = null,
                            modifier=Modifier.clickable {
                                onHomeButtonClicked()
                            }
                        )
                    }
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
                                //ゴロゴロストップの処理
                                gorogoro=false
                                lirax=0
                                vibrator.cancel()
                                mouthResource=mouth
                            }
                        )
                    }

                ){

                }
            }
        }
    }
}

//まばたき
//    val timer1 = remember { Timer() }
//    DisposableEffect(Unit) {
//        timer1.schedule(100, 1000) {
//            if(gorogoro==false){
//                eyeResource=eye
//                Log.d(ContentValues.TAG,"a")
//            }
//        }
//        onDispose {
//            timer1.cancel()
//        }
//    }
//    val timer2 = remember { Timer() }
//    DisposableEffect(Unit) {
//        timer2.schedule(100, 5000) {
//            if(gorogoro==false) {
//                eyeResource = R.drawable.eye2
//                Log.d(ContentValues.TAG, "b")
//            }
//        }
//        onDispose {
//            timer2.cancel()
//        }
//    }
