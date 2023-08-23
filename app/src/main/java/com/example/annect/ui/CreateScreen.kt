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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.graphicsLayer
import com.example.annect.data.DisplayAnima
import com.example.annect.data.accessoryData
import com.example.annect.data.bodyData
import com.example.annect.data.eyeData
import com.example.annect.data.mouthData

@Composable
fun CreateScreen(
    onNextButtonClicked: ()->Unit = {}, onArrowButtonClicked: (Int) -> Unit = { },
    body:Int,eye:Int,mouth:Int,accessory:Int
){
    Column(modifier = Modifier
        .displayCutoutPadding()
        .padding(start = 20.dp,top = 20.dp)){

        Card {
            Text("Animaの見た目を決めよう", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(10.dp) )
        }

        Row(){

            //Anima描画
            DisplayAnima( body,eye,mouth,accessory, modifier = Modifier.size(300.dp).padding(start = 50.dp,top = 20.dp))

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

        Button(onClick = onNextButtonClicked,Modifier.padding( end = 30.dp).align(Alignment.End).offset(y=(-10).dp)){
            Text("次へ")
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

    Column(modifier = Modifier.padding(start = 50.dp),) {

        Text(partsName, style = MaterialTheme.typography.bodyLarge)

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
