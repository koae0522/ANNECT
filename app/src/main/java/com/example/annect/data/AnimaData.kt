package com.example.annect.data

import android.content.ContentValues.TAG
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.annect.R


//Animaのデータ　値はすべて初期値
@Entity(tableName = "anima_data_table")
data class AnimaData(

    @PrimaryKey(autoGenerate = false) var name:String="",
    //体のパーツ
    @DrawableRes var body:Int = R.drawable.body1,
    @DrawableRes var eye:Int = R.drawable.eye2_under,
    @DrawableRes var eyeOver:Int = R.drawable.eye2_over,
    @DrawableRes var mouth:Int = R.drawable.mouth1,
    @DrawableRes var accessory:Int = R.drawable.accessory1,

    var maxR:Float = 5f,

    //親愛
    var love:Int=1,
    //気分(要るかわからんが)
    var feeling:Int=1,

    //大胆さ
    var boldness:Int =1,
    //探索性
    var exploration:Int=1,
    //攻撃性
    var aggressiveness :Int=1,
    //社会性
    var sociability:Int=1,
    //活動性
    var activity:Int=1,

    //初回起動チェック(ここのクラスに書くやつじゃない気がするごめん)
    var first:Boolean=true,
)

//connectMode関連のパラメータ
data class ConnectData(
    //まばたき
    var serialData:Int = 0,

    var interaction:Boolean = true,

    var displayFace:Boolean = true,

    var sleepTimer:Int =0,

    //選んでる生き物
    var animal:String=""

)


//partsを渡してAnimaを表示する関数

@Composable
fun DisplayAnima(
    body: Int, eye: Int, eyeOver: Int, mouth: Int, accessory: Int,
    modifier: Modifier,
    eyeOffset: State<Offset>
) {
    Box(Modifier.size(300.dp)){
        Image(painter = painterResource(id = body), contentDescription = null)
        Row(modifier = Modifier.align(androidx.compose.ui.Alignment.Center)
            .padding(bottom = 60.dp)){
            Box(modifier = Modifier.size(30.dp)){
                Image(painter = painterResource(id = eye), contentDescription = null,modifier)
                Image(painter = painterResource(id = eyeOver), contentDescription = null,
                    modifier = modifier.offset(x = eyeOffset.value.x.dp,y =  eyeOffset.value.y.dp)
                )
            }
            Spacer(modifier = Modifier.size(30.dp))
            Box(modifier = Modifier.size(30.dp)){
                Image(painter = painterResource(id = eye), contentDescription = null,modifier)
                Image(painter = painterResource(id = eyeOver), contentDescription = null,
                    modifier = modifier.offset(x = eyeOffset.value.x.dp,y =  eyeOffset.value.y.dp)
                )
            }
        }
        Image(painter = painterResource(id = mouth), contentDescription = null)
        Image(painter = painterResource(id = accessory), contentDescription = null)
    }

}
@Composable
fun DisplayAnimaNoOffset(
    body: Int, eye: Int, eyeOver: Int, mouth: Int, accessory: Int,
    modifier: Modifier,
) {
    Box(modifier.size(300.dp)){
        Image(painter = painterResource(id = body), contentDescription = null)
        Row(modifier = Modifier.align(androidx.compose.ui.Alignment.Center)
            .padding(bottom = 60.dp)){
            Box(modifier = Modifier.size(30.dp)){
                Image(painter = painterResource(id = eye), contentDescription = null,modifier)
                Image(painter = painterResource(id = eyeOver), contentDescription = null,)
            }
            Spacer(modifier = Modifier.size(30.dp))
            Box(modifier = Modifier.size(30.dp)){
                Image(painter = painterResource(id = eye), contentDescription = null,modifier)
                Image(painter = painterResource(id = eyeOver), contentDescription = null,)
            }
        }
        Image(painter = painterResource(id = mouth), contentDescription = null)
        Image(painter = painterResource(id = accessory), contentDescription = null)
    }

}
@Preview
@Composable
fun DisplayAnimaPreview(){
   // DisplayAnima(body = R.drawable.body1, eye = R.drawable.eye2_under, eyeOver = R.drawable.eye2_over, mouth = R.drawable.mouth1, accessory = R.drawable.accessory1, modifier = Modifier)
}

//パーツのデータリスト　キャラクリ時に使用
val bodyData = listOf(
    R.drawable.body1,
    R.drawable.body2,
    R.drawable.body3
)
val eyeData =listOf(
    R.drawable.eye2_over,
)

val eyeOverList = listOf(
    R.drawable.eye2_under,
)

val eyeMaxR = listOf(
    5f
)

val mouthData =listOf(
    R.drawable.mouth1,
    R.drawable.mouth2,
    R.drawable.mouth3,
    R.drawable.mouth4,
    R.drawable.mouth6,
    R.drawable.mouth7,
    R.drawable.mouth8,
    R.drawable.mouth9,
    R.drawable.mouth10,
    R.drawable.mouth11,
    R.drawable.mouth12,
)

val accessoryData =listOf(
    R.drawable.accessory1,
    R.drawable.accessory2,
    R.drawable.accessory3,
    R.drawable.accessory4,
    R.drawable.accessory5,
    R.drawable.accessory6,
    R.drawable.accessory7,
    R.drawable.accessory8,
    R.drawable.accessory9,
    R.drawable.accessory10,
)