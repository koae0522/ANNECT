package com.example.annect.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.annect.R
import androidx.compose.foundation.Image


@Composable
fun HomeScreen(onClickButton: ()->Unit = {},name:String,
               parts:MutableMap<String, Int>){

    Column(modifier = Modifier
        .displayCutoutPadding()
        .fillMaxSize(),){
        Card(){
            Text("なまえ:${name}")
        }
        Row(){
            Image(painterResource(id = R.drawable.toybox), contentDescription = null)
            DisplayAnima(parts = parts)
            Image(painterResource(id = R.drawable.tansu), contentDescription = null)
        }
    }

}
