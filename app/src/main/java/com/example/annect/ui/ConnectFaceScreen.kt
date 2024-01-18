package com.example.annect.ui

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
import androidx.compose.material3.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
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
import com.example.annect.data.AnimaViewModel
import java.util.Timer
import kotlin.concurrent.schedule
import kotlinx.coroutines.*

@Composable
fun ConnectFaceScreen(body:Int, eye:Int, mouth:Int, accessory:Int, animal:String,
                      context:Context, onHomeButtonClicked:() -> Unit = {}, viewmodel : AnimaViewModel,
                      serialData:Int, interaction:Boolean,displayFace:Boolean
){
    var recv by remember {
        mutableStateOf(0)
    }
    Log.d("face",displayFace.toString())
    var backgroundColor=Color(0xFFDAD6CD)
    var test = "not_recv"
    val connect =  remember { USBSerial(context, viewmodel)}
    val connectUistate by viewmodel.uiState.collectAsState()
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
        .setMaxStreams(10)
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
        "うさぎ"->backgroundColor=Color(0xFFFFFFFF)
    }

    if(check == 0){
        when(animal){
            "ねこ"-> animal_data = "a"
            "ユニコーン"->animal_data = "b"
            "うさぎ"->animal_data="u"
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
            if(lirax>0 && gorogoro){
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

    //インタラクション用
    //2秒に一回何らかの動作をさせることができる
    val interactionTimer = remember { Timer() }
    DisposableEffect(Unit) {
        //2秒に一回
        blinkTimer.schedule(100, 2000) {
            runBlocking {
               if(interaction){
                   val randomNum = (1..10).random()
                   when(randomNum){
                       1 -> {
                           //耳を動かす処理とか
                           connect.write(context, "c", 8)
                           Log.d("interaction","インタラクション：1")
                       }
                       2-> {
                           //しっぽを動かす処理とか
                           connect.write(context, "d", 8)
                           Log.d("interaction","インタラクション：2")
                       }
                       3->{
                           //鳴き声の処理とか
                           connect.write(context, "e", 8)
                           soundPool.play(catNya, 1.0f, 1.0f, 0, 0, 1.0f)
                           Log.d("interaction","インタラクション：3")
                       }
                   }
               }
            }
        }
        onDispose {
          interactionTimer.cancel()
        }
    }

    if(lirax>=6 && !gorogoro && interaction){
        //ゴロゴロ時の処理
        gorogoro=true
        soundPool.play(catNya, 1.0f, 1.0f, 0, 0, 1.0f)
        vibrator.vibrate(longArrayOf(0, 10, 10), 1)
        eyeResource=R.drawable.eye2
        mouthResource=R.drawable.mouth2
        Log.d("interaction","ゴロゴロ開始")

    }

    if(lirax<3 && gorogoro){
        gorogoro=false
        Log.d("interaction","ゴロゴロ終了")
        vibrator.cancel()
    }

    //USB受信
    DisposableEffect(serialData) {
        // myUiStateが変更されたときに実行する処理
        if(lirax>10) {
            lirax++
        }
        if(animal=="ねこ") {
            when ((1..10).random()) {
                1 -> soundPool.play(catNya, 1.0f, 1.0f, 0, 0, 1.0f)
                2 -> soundPool.play(catNyaun, 1.0f, 1.0f, 0, 0, 1.0f)
            }
        }
        onDispose {
            // 不要になったときの処理（例: リソースの解放など）

        }
    }

    //UI
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = backgroundColor),
        contentAlignment = Alignment.Center){


        //ひげ
        if(animal=="ねこ") {
            Image(painter = painterResource(id = R.drawable.hige),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        scaleX = 2F
                        scaleY = 2F
                    }
                    .offset(y = (70).dp)
            )
        }

        //うさぎのはな
//        if(animal=="うさぎ"){
//            Image( painter = painterResource(id = R.drawable.usagi_hana),
//                contentDescription = null,
//                contentScale = ContentScale.FillWidth,
//                modifier = Modifier.  fillMaxSize().graphicsLayer {
//                    scaleX = 2F
//                    scaleY = 2F
//                } .offset(y = (70).dp,x=(-5).dp)
//            )
//
//        }
        //め
        Image(painter = painterResource(id = eyeResource),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier= Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = 2F
                    scaleY = 2F
                }
                .offset(y = (70).dp)

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
                }
                .offset(y = (70).dp)

        )

        Box(modifier = Modifier.fillMaxSize()){

            if(!displayFace) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = backgroundColor)
                ) {

                }
            }
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

                                tryAwaitRelease()

                                // 離した
                                mouthResource = mouth
                                test = connect.write(context, "t", 8)
                                test += "t"
                                //connectUistate.serialData += 1
                                if(animal=="ねこ") {
                                    when ((1..20).random()) {
                                        1 -> soundPool.play(catNya, 1.0f, 1.0f, 0, 0, 1.0f)
                                        2 -> soundPool.play(catNyaun, 1.0f, 1.0f, 0, 0, 1.0f)
                                        3 -> lirax++
                                    }
                                }
                                Log.d(TAG, lirax.toString())
                            },
                            onTap = {

                            }

                        )
                    }) {
                    Row(){
                        Image(painter=painterResource(id=R.drawable.baseline_home_24),contentDescription = null,
                            modifier=Modifier.clickable {
                                onHomeButtonClicked()
                            }

                        )
                       //Text(lirax.toString())
//                        Text(serialData.toString())
//                        Text(recv.toString())
                    }
                }
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            //下側を触った時の処理
                            onTap = {
                                connect.write(context, "s", 8)
                                //ゴロゴロストップの処理
//                                gorogoro = false


//                                vibrator.cancel()
//                                mouthResource = mouth
                                if(animal=="ねこ") {
                                    when ((1..20).random()) {
                                        1 -> soundPool.play(catNya, 1.0f, 1.0f, 0, 0, 1.0f)
                                        2 -> soundPool.play(catNyaun, 1.0f, 1.0f, 0, 0, 1.0f)
                                        3 -> lirax++
                                    }
                                }
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
