package com.example.annect.connect

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class Lirax {
    private val _liraxState = MutableStateFlow(false)
    val uiState: StateFlow<Boolean> = _liraxState.asStateFlow()

    private var liraxValue = 0
    val scope = CoroutineScope(Job() + Dispatchers.Main)
    fun updateState() {
        scope.launch {
            while (true) {
                delay(1000)
                liraxValue++
                if (liraxValue >= 10) {
                    _liraxState.value = true
                } else {
                    _liraxState.value = false
                }
            }
        }
    }
}