package com.example.annect.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CreateScreen(onClickButton: ()->Unit = {}){
    Column(modifier = Modifier.displayCutoutPadding(),){
        Text("this is CreateScreen")
        Button(onClick = onClickButton){
            Text("go to HomeScreen")
        }
    }
}
