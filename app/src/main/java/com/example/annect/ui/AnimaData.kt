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
    var  animaParts: MutableMap<String,Int> = mutableMapOf(
        "body" to R.drawable.body1,
        "eye" to R.drawable.eye1,
        "mouth" to R.drawable.mouth1,
        "accessory" to R.drawable.accessory1
    ),

    //親愛度
    var love:Int=1,
    //気分(要るかわからんが)
    var feeling:Int=1,
    //名前
    var name:String="misuzu"
)

//partsを渡してAnimaを表示する関数
@Composable
fun DisplayAnima(parts:MutableMap<String, Int>){
    Box(){
        Image(painter = painterResource(id = parts["body"] ?: 0), contentDescription = null)
        Image(painter = painterResource(id = parts["eye"] ?: 0), contentDescription = null)
        Image(painter = painterResource(id = parts["mouth"] ?: 0), contentDescription = null)
        Image(painter = painterResource(id = parts["accessory"] ?: 0), contentDescription = null)
    }
}