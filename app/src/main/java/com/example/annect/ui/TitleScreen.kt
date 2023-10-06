package com.example.annect.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.annect.R

@Composable
fun TitleScreen(onScreenClicked: () -> Unit = {}) {
    val logoId = R.drawable.logo
    val backgroundId = R.drawable.title3
    val touchId = R.drawable.tap_to_care

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onScreenClicked() }
    ) {
        // 背景画像を表示
        Image(
            painter = painterResource(id = backgroundId),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        // ロゴを表示
        Image(
            painter = painterResource(id = logoId),
            contentDescription = null,
            modifier = Modifier
                .size(1000.dp) // ロゴのサイズを調整
                .align(Alignment.TopCenter)
                .offset(y = (-60).dp) // 上から50dpの位置にオフセット
        )

        // "Tap to care"を表示
        Image(
            painter = painterResource(id = touchId),
            contentDescription = null,
            modifier = Modifier
                .size(1000.dp) // "Tap to care"のサイズを調整
                .align(Alignment.BottomCenter)
                .offset(y = 155.dp) // 下から50dpの位置にオフセット
        )
    }
}
