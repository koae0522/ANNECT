package com.example.annect.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.annect.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.sp
import com.example.annect.data.DisplayAnima
import com.example.annect.data.accessoryData
import com.example.annect.data.bodyData
import com.example.annect.data.eyeData
import com.example.annect.data.mouthData

//キャラクタを作る画面
@Composable
fun CreateScreen(
    onNextButtonClicked: ()->Unit = {}, onArrowButtonClicked: (Int) -> Unit = { },
    body:Int,eye:Int,mouth:Int,accessory:Int
){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            Brush.linearGradient(
                colors = listOf(MaterialTheme.colorScheme.primaryContainer,MaterialTheme.colorScheme.secondaryContainer,Color.White )
            )
        )){
        Column(modifier = Modifier
            .displayCutoutPadding()
            .fillMaxWidth()
            .padding(start = 20.dp, top = 20.dp,end=20.dp)

        ){

            Card (modifier = Modifier,
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),

            ){
                Text("Animaの見た目を決めよう", style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(10.dp)

                )
            }

            Row(){

                //Anima描画
                DisplayAnima( body,eye,mouth,accessory, modifier = Modifier
                    .size(280.dp)
                    .padding(start = 50.dp, top = 20.dp))

                //パーツリスト
                Column(modifier = Modifier.padding(start = 50.dp)) {
                    Row(modifier = Modifier.padding(20.dp)){
                        PartsSelectRow(partsName = "からだ", partsList = bodyData, onArrowButtonClicked =onArrowButtonClicked)
                        PartsSelectRow(partsName = "め", partsList = eyeData, onArrowButtonClicked =onArrowButtonClicked)
                    }
                    Row(modifier = Modifier.padding(start =20.dp)){
                        PartsSelectRow(partsName = "くち", partsList = mouthData, onArrowButtonClicked =onArrowButtonClicked)
                        PartsSelectRow(partsName = "アクセサリー", partsList = accessoryData, onArrowButtonClicked =onArrowButtonClicked)
                    }
                }

            }

            Button(onClick = onNextButtonClicked,
                Modifier
                    .padding(end = 0.dp)
                    .align(Alignment.End)
                    .offset(y = (-10).dp)
                    .size(100.dp)
                        ,elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 20.dp,
                pressedElevation = 5.dp,
                disabledElevation = 0.dp,
            ),
                colors = ButtonDefaults.textButtonColors(
                    containerColor=MaterialTheme.colorScheme.secondary,
                    contentColor = Color.White,
                    disabledContentColor = Color.LightGray
                ),
                shape = MaterialTheme.shapes.medium){
                Text("次へ", fontSize = 20.sp)
            }

        }
    }


}


@Composable
fun PartsSelectRow(
    partsName:String, partsList:List<Int>,
    onArrowButtonClicked: (Int) -> Unit
){

    //現在表示しているパーツの番号
    var currentParts by remember { mutableIntStateOf(0) }
    var partsIcon=R.drawable.eye_icon
    when(partsName){
        "からだ"->partsIcon=R.drawable.body_icon
        "め"->partsIcon=R.drawable.eye_icon
        "くち"->partsIcon=R.drawable.mouth_icon
        "アクセサリー"->partsIcon=R.drawable.accessory_icon
    }
    Column(modifier = Modifier.padding(start = 50.dp),) {
        Row(){
            Icon(painterResource(id = partsIcon),contentDescription = null, modifier = Modifier.size(30.dp))
            Text(partsName, style = MaterialTheme.typography.bodyLarge)
        }


        Row(){
            Icon(painter = painterResource(id = R.drawable.left), contentDescription = null,
                modifier = Modifier
                    .padding(top = 40.dp)
                    //クリックしたら番号をひとつへらす 番号が0の時は何もしない
                    .clickable {
                        if (currentParts != 0) {
                            currentParts--
                            onArrowButtonClicked(partsList[currentParts])
                        }
                    }
                    .
                        //番号が0の時にグレーぽくする
                    graphicsLayer {
                        if (currentParts == 0) {
                            this.alpha = 0.2f
                        }
                    })

            //パーツの画像をリストから表示
            Image(painter = painterResource(id =partsList[currentParts]),
                contentDescription = null,
                modifier = Modifier.size(90.dp), )

            Icon(painter = painterResource(id = R.drawable.right), contentDescription = null,
                modifier = Modifier
                    .padding(top = 40.dp)
                    //クリックしたら番号をひとつ増やす 番号が配列の上限の時は何もしない
                    .clickable {
                        if (currentParts != partsList.size - 1) {
                            currentParts++
                            onArrowButtonClicked(partsList[currentParts])
                        }

                    }
                    //番号が配列の上限の時にグレーぽくする
                    .graphicsLayer {
                        if (currentParts == partsList.size - 1) {
                            this.alpha = 0.2f
                        }
                    })
        }
    }
}
