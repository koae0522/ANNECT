package com.example.annect.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.annect.R

//Animaのデータ　値はすべて初期値
data class AnimaData(

    //体のパーツ

    @DrawableRes var body:Int = R.drawable.body1,
    @DrawableRes var eye:Int = R.drawable.eye1,
    @DrawableRes var mouth:Int = R.drawable.mouth1,
    @DrawableRes var accessory:Int = R.drawable.accessory1,

    //親愛度
    var love:Int=1,
    //気分(要るかわからんが)
    var feeling:Int=1,
    //名前
    var name:String="misuzu"
)

//partsを渡してAnimaを表示する関数
@Composable
fun DisplayAnima( name:String,body:Int,eye:Int,mouth:Int,accessory:Int){
    Box(){
        Image(painter = painterResource(id = body), contentDescription = null)
        Image(painter = painterResource(id = eye), contentDescription = null)
        Image(painter = painterResource(id = mouth), contentDescription = null)
        Image(painter = painterResource(id = accessory), contentDescription = null)
    }
}

val bodyData = listOf(
    R.drawable.body1,
    R.drawable.body2,
    R.drawable.body3
)
val eyeData =listOf(
    R.drawable.eye1,
    R.drawable.eye2,
    R.drawable.eye3
)

val mouthData =listOf(
    R.drawable.mouth1,
    R.drawable.mouth2,
    R.drawable.mouth3
)

val accessoryData =listOf(
    R.drawable.accessory1,
    R.drawable.accessory2,
    R.drawable.accessory3
)