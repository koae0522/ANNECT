package com.example.annect.ui.no_permission

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NoPermissionScreen(
    hasCameraPermission: Boolean,
    onRequestPermission: () -> Unit,
    onNextButtonClicked: () -> Unit
) {

    NoPermissionContent(
        hasCameraPermission = hasCameraPermission,
        onRequestPermission = onRequestPermission,
        onNextButtonClicked = onNextButtonClicked
    )
}

@Composable
fun NoPermissionContent(
    hasCameraPermission: Boolean,
    onRequestPermission: () -> Unit,
    modifier: Modifier = Modifier,
    onNextButtonClicked: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = "Please grant the permission to use the camera to use the core functionality of this app."
            )
            if(!hasCameraPermission){
                Button(onClick = onRequestPermission) {
                    Icon(imageVector = Icons.Default.Done, contentDescription = "Camera")
                    Text(text = "Grant permission")
                }
            }
            Button(onClick = onNextButtonClicked) {
                Text(text = "Next")
            }
        }
    }
}

