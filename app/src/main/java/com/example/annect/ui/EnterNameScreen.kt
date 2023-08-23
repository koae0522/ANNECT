package com.example.annect.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.annect.data.DisplayAnima

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterNameScreen(body:Int,eye:Int,mouth:Int,accessory:Int,onNextButtonClicked: (String)->Unit = {}){
    var text by remember { mutableStateOf("") }

    //名前は6文字まで
    var maxLength=6

    Column(modifier = Modifier
        .displayCutoutPadding()
        .padding(top = 20.dp,start = 20.dp)){
        Card {
            Text("Animaの名前を決めよう", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(10.dp) )
        }
        Row(){
            DisplayAnima( body,eye,mouth,accessory, modifier = Modifier
                .size(300.dp)
                .padding(20.dp))
            Column() {

                OutlinedTextField(
                    value = text,
                    onValueChange = { if(it.length<=maxLength) text = it },
                    modifier = Modifier
                        .padding(start = 125.dp, top = 80.dp, bottom = 20.dp),
                    label = { Text("名前を入力(${maxLength}文字まで)") },
                    singleLine = true,

                )

                //ボタンを押したらviewModel更新
                Button(onClick = { onNextButtonClicked(text) },
                        Modifier.padding(start =  80.dp).align(Alignment.End)){
                            Text("決定！")
                }

            }

        }

    }
}

