package com.example.annect.ui


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import com.example.annect.R


@Composable
fun ConnectAnimationScreen(body:Int,eye:Int,mouth:Int,accessory:Int,animal:String,modifier: Modifier,onNextButtonClicked: () -> Unit = {}){
    var bodyImg= ImageBitmap.imageResource(id=body)
    var eyeImg=ImageBitmap.imageResource(id = eye)
    var mouthImg=ImageBitmap.imageResource(id = mouth)
    var accessoryImg=ImageBitmap.imageResource(id = accessory)


    var animalImg=if(animal=="ねこ")
        ImageBitmap.imageResource(id = R.drawable.neko3)
    else ImageBitmap.imageResource(id = R.drawable.unicorn3)

    var timeSec by remember { mutableStateOf(0f) }
    var timeSec_first by remember { mutableStateOf(0) }
    var timeSecStart by remember { mutableStateOf(0f) }
    var flag by remember { mutableStateOf(0) }
    LaunchedEffect(true) {
        while (true) {
            if(timeSec_first == 0){
                timeSecStart = withFrameMillis { it / 1000f }
                timeSec_first = 1
            }else{
                timeSec = withFrameMillis { it / 1000f } - timeSecStart
            }
        }
    }

    var sizeAnima = 500f

    var sizeAnimal = 350f



    Canvas(modifier = Modifier.fillMaxSize()){
        val speed = Offset(300f, 0f)
        val y = 0f
        // 円の中心が移動できる領域の大きさ 固定
        val validSize = Size(size.width, size.height)
        // 時刻 timeSec におけるx, y座標を計算。端まで到達したら折り返す。
        val xTmp = (timeSec * speed.x) % (validSize.width * 2)
        val x = if (xTmp > size.width/2 - sizeAnima) size.width/2 - sizeAnima else xTmp

        drawImage(bodyImg, topLeft = Offset(x,y))
        drawImage(eyeImg,  topLeft = Offset(x,y))
        drawImage(mouthImg,  topLeft = Offset(x,y))
        drawImage(accessoryImg,  topLeft = Offset(x,y))
    }

    Canvas(modifier = Modifier.fillMaxSize()){
        val speed = Offset(-300f, 0f)
        val y = 100f
        // 円の中心が移動できる領域の大きさ 固定
        val validSize = Size(size.width , size.height)
        // 時刻 timeSec におけるx, y座標を計算。端まで到達したら折り返す。
        val xTmp = (timeSec * speed.x) % (validSize.width * 2) + size.width - sizeAnimal
        val x = if (xTmp < size.width/2 - sizeAnimal) size.width/2 - sizeAnimal else xTmp

        if (xTmp < size.width/2 - sizeAnimal){
            flag = 1
        }
        drawImage(animalImg,topLeft= Offset(x,y),)

    }

    if(flag == 1){
        onNextButtonClicked()
    }

}