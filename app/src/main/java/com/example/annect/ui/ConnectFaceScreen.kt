package com.example.annect.ui

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Context.*
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Vibrator
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.times
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.annect.R
import com.example.annect.ui.animaMotion.eyeAnimation
import com.example.annect.ui.camera.FaceRecognitionAnalyzer
import com.example.annect.ui.camera.angleCalc
import com.example.annect.ui.camera.distanceCalc
import java.util.Timer
import kotlin.concurrent.schedule
import kotlinx.coroutines.*
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun ConnectFaceScreen(body:Int, eye:Int,eyeOver:Int, mouth:Int, accessory:Int, animal:String,
                      onHomeButtonClicked:() -> Unit = {},
                      serialData:Int, interaction:Boolean,displayFace:Boolean,
){
    //Camera用変数準備
    val context: Context = LocalContext.current
    val lensFacing = CameraSelector.LENS_FACING_FRONT
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val cameraController: LifecycleCameraController = remember { LifecycleCameraController(context) }
    val screenWidth = remember { mutableIntStateOf(context.resources.displayMetrics.widthPixels/2) }
    val screenHeight = remember { mutableIntStateOf(context.resources.displayMetrics.heightPixels/2) }

    LaunchedEffect(Unit) {
        cameraController.cameraSelector = CameraSelector.Builder()
            .requireLensFacing(lensFacing)
            .build()
    }


    var smileCheck : Boolean by remember { mutableStateOf(false)}

    //angleに0から360度の角度が度数法で入ってます。（優しさでラジアンから直しておいたよ。）
    //distanceにスクリーン中心からの現在の自分の顔の距離がでるよ(優しさで0-20までの値に割合で変化するようにしてるよ)
    //うまく使ってください。
    var angle by remember { mutableFloatStateOf(0f) }
    var distance by remember { mutableFloatStateOf(0f) }

    var eyeAnimation by remember { mutableStateOf(eyeAnimation(2f)) }

    //カメラの設定の追加ができます

    //笑顔判定と顔の現在の角度、位置を最大20で変数に代入するすごい関数。
    // 好きな秒数ごとに反映変更したければanalyzerの中のTHROTTLE_TIMEOUT_MSを変更すること
    fun imageProc(box: Rect, smile: String, imageWidth: Float, imageHeight: Float) {
        CoroutineScope(Dispatchers.Default).launch {
            val newAngle = angleCalc(box, imageWidth, imageHeight, screenWidth.value, screenHeight.value)
            val newDistance = distanceCalc(box, imageWidth, imageHeight, screenWidth.value, screenHeight.value)
            val newSmileCheck = smile.toFloat() > 0.5

            withContext(Dispatchers.Main) {
                smileCheck = newSmileCheck
                angle = newAngle
                distance = newDistance
            }
        }
    }

    //ここまで



    var recv by remember {
        mutableStateOf(0)
    }
    Log.d("face",displayFace.toString())
    var backgroundColor=Color(0xFFDAD6CD)
    var test = "not_recv"
    val connect =  remember { USBSerial { receivedData ->
        // ここで受信したデータ(receivedData) を処理します。
        // Disposableも全部ここにロジック組み込めるかも
        test = receivedData
    }
    }
    connect.open(context)//ここ一回だけにしたい
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
        connect.write(animal_data,8)
        check = 1
    }

    var eyeResource by remember { mutableIntStateOf(eye) }
    var eyeOverResource by remember { mutableIntStateOf(eyeOver) }
    var mouthResource by remember { mutableIntStateOf(mouth) }

    //lirax値を時     間経過で下げるタイマー
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


    LaunchedEffect(Unit) {
        eyeAnimation.startBlink()
    }
    val blinking by eyeAnimation.blinking.collectAsState()
    val sleeping by eyeAnimation.sleeping.collectAsState()
    eyeResource = if (blinking||sleeping) R.drawable.eye_mabataki else eye
    eyeOverResource = if (blinking||sleeping) R.drawable.eye_mabataki else eyeOver
    mouthResource = if (sleeping) R.drawable.mouth2 else mouth
    val alpha = animateFloatAsState(
        targetValue = if (blinking) 0f else 1f,
        animationSpec = tween(durationMillis = 100)
    )


    if(lirax>=6 && !gorogoro && interaction){
        //ゴロゴロ時の処理
        gorogoro=true
        soundPool.play(catNya, 1.0f, 1.0f, 0, 0, 1.0f)
        vibrator.vibrate(longArrayOf(0, 10, 10), 1)
        eyeResource=R.drawable.eye2_over
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

    if (smileCheck) {
        mouthResource = R.drawable.mouth2
    }else{
        //mouthResource = mouth
    }

//

    Box(){
        if(!sleeping) {
            AndroidView(
                modifier = Modifier
                    .fillMaxSize(),
                factory = { context ->
                    PreviewView(context).apply {
                        layoutParams = LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        setBackgroundColor(android.graphics.Color.BLACK)
                        implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                        scaleType = PreviewView.ScaleType.FILL_START
                    }.also { previewView ->
                        startFaceRecognition(
                            context = context,
                            cameraController = cameraController,
                            lifecycleOwner = lifecycleOwner,
                            previewView = previewView,
                            onDetectedTextUpdated = ::imageProc
                        )
                    }
                }
            )
        }
    }

    //UI
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = backgroundColor)
        ,
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
        Row(modifier = Modifier
            .align(androidx.compose.ui.Alignment.Center)
            .padding(bottom = 60.dp)){
            Box(modifier = Modifier.size(180.dp)){
                Image(painter = painterResource(id = eyeResource), contentDescription = null,modifier = Modifier
                    .alpha(alpha.value))
                Image(painter = painterResource(id = eyeOverResource), contentDescription = null,
                    modifier = Modifier
                        .alpha(alpha.value)
                        .offset(
                            animateOffsetAsState(
                                targetValue = Offset(
                                    distance * cos(Math.toRadians(angle.toDouble())).toFloat(),
                                    distance * sin(Math.toRadians(angle.toDouble())).toFloat()
                                ), label = ""
                            ).value.x.dp,
                            animateOffsetAsState(
                                targetValue = Offset(
                                    distance * cos(Math.toRadians(angle.toDouble())).toFloat(),
                                    -distance * sin(Math.toRadians(angle.toDouble())).toFloat()
                                ), label = ""
                            ).value.y.dp,
                        )
                )
                Log.d("akane", (distance * cos(angle).toFloat()).toString()+"\n"+(distance * sin(angle).toFloat()).toString())
            }
            Spacer(modifier = Modifier.size(150.dp))
            Box(modifier = Modifier.size(180.dp)
                ){
                Image(painter = painterResource(id = eyeResource), contentDescription = null,modifier = Modifier
                    .alpha(alpha.value))
                Image(painter = painterResource(id = eyeOverResource), contentDescription = null,
                    modifier = Modifier
                        .alpha(alpha.value)
                        .offset(
                            animateOffsetAsState(
                                targetValue = Offset(
                                    distance * cos(Math.toRadians(angle.toDouble())).toFloat(),
                                    distance * sin(Math.toRadians(angle.toDouble())).toFloat()
                                ), label = ""
                            ).value.x.dp,
                            animateOffsetAsState(
                                targetValue = Offset(
                                    distance * cos(Math.toRadians(angle.toDouble())).toFloat(),
                                    -distance * sin(Math.toRadians(angle.toDouble())).toFloat()
                                ), label = ""
                            ).value.y.dp,
                        )
                )
            }
        }

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
                                tryAwaitRelease()
                                test = connect.write("t", 8)
                                test += "t"
                                //connectUistate.serialData += 1
                                if (animal == "ねこ") {
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
                                connect.write("s", 8)
                                eyeAnimation.resetSleep()
                                Log.d("tonkatu","clicked")
                                if (animal == "ねこ") {
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

            Text(text = "angle:$angle,distance:$distance,sleep:$sleeping",modifier = Modifier.align(Alignment.TopStart))

        }

    }

}

private fun startFaceRecognition(
    context: Context,
    cameraController: LifecycleCameraController,
    lifecycleOwner: LifecycleOwner,
    previewView: PreviewView,
    onDetectedTextUpdated: (Rect, String,Float,Float) -> Unit
) {


    cameraController.imageAnalysisTargetSize = CameraController.OutputSize(AspectRatio.RATIO_16_9)
    cameraController.setImageAnalysisAnalyzer(
        ContextCompat.getMainExecutor(context),
        FaceRecognitionAnalyzer(onDetectedTextUpdated = onDetectedTextUpdated)
    )

    cameraController.bindToLifecycle(lifecycleOwner)
    previewView.controller = cameraController
}