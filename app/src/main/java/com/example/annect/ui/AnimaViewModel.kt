package com.example.annect.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AnimaViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AnimaData())
    val uiState:StateFlow<AnimaData> = _uiState.asStateFlow()


}

