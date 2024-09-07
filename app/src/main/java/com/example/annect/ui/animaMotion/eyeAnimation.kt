package com.example.annect.ui.animaMotion

import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerInputChange
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class eyeAnimation(maxR: Float){

    var maxR = maxR
    var r: Float = 0.0f
    var angle = 0f
    var eyeOffset  = Offset(0f,0f)
    var origin = Offset(0f,0f)
    var startOffset = Offset(0f,0f)
    var sleep = 0
    val blinking = MutableStateFlow(false)
    var sleeping = MutableStateFlow(false)
    fun calculateEyeOffset():Offset{
        return Offset(
            r * cos(angle),
            r * sin(angle)
        )
    }
    fun startAnimation(offset: Offset){
        startOffset = offset - origin
        angle = atan2(startOffset.y, startOffset.x)
        r = sqrt(((offset.x-origin.x).pow(2)+(offset.y-origin.y).pow(2)).toDouble()).toFloat()/origin.y*maxR
        calculateEyeOffset()
    }

    fun onDragAnimation(change:PointerInputChange){
        val dragOffset = (change.position - origin)
        angle = atan2(dragOffset.y, dragOffset.x)
        r = sqrt(((change.position.x-origin.x).pow(2)+(change.position.y-origin.y).pow(2)).toDouble()).toFloat()/origin.y*maxR
        calculateEyeOffset()
    }

    fun onDragEndAnimation(){
        r = 0f
        angle = 0f
    }

    fun startBlink() {
        CoroutineScope(Dispatchers.Default).launch { // Main スレッドで実行
            while (true) {
                if(!sleeping.value){
                    delay(4000)
                    blinking.value = true
                    sleep++
                    delay(100)
                    blinking.value = false
                }
                if(sleep >5 && sleeping.value == false){
                    sleeping.value = true
                }
            }
        }
    }

    fun resetSleep(){
        sleep=0
        sleeping.value = false
    }
}