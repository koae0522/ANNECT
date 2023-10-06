package com.example.annect.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


import androidx.compose.ui.unit.dp



@Composable
fun MiniGameScreen(onHomeButtonClicked: () -> Unit = {}, onQuizGameSelected: () -> Unit) {
    //グラデーション定義
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(Color(0xFFB8E986), Color.White)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradientBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("ミニゲームの選択")
        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { onQuizGameSelected() }, modifier = Modifier.fillMaxWidth(0.7f)) {
            Text("クイズゲームを開始")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Coming soonボタン1
        Button(onClick = {}, modifier = Modifier.fillMaxWidth(0.7f), enabled = false) {
            Text("Coming soon")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Coming soonボタン2
        Button(onClick = {}, modifier = Modifier.fillMaxWidth(0.7f), enabled = false) {
            Text("Coming soon")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { onHomeButtonClicked() }, modifier = Modifier.fillMaxWidth(0.7f)) {
            Text("ホームに戻る")
        }
    }
}
