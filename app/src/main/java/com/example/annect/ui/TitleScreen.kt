package com.example.annect.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

//タイトル画面(仮)
@Composable
fun TitleScreen(onScreenClicked: ()->Unit = {}){
    Box(modifier = Modifier.clickable { onScreenClicked() }){
        Column( ) {
            Text("ANNECT", style = MaterialTheme.typography.titleLarge)
            Text( "touch to start",style = MaterialTheme.typography.bodyLarge)
        }
    }
}