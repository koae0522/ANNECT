package com.example.annect.ui.camera

import androidx.compose.ui.geometry.Rect
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt

fun angleCalc(
    box: Rect,
    imageWidth: Float,
    imageHeight: Float,
    screenWidth: Int,
    screenHeight: Int
): Float {
    val topLeftX = box.topLeft.x
    val topLeftY = box.topLeft.y

    val width = (box.width / imageWidth * screenWidth)
    val height = box.height / imageHeight * screenHeight

    val refactorTopLeftX =
        screenWidth - (topLeftX / imageWidth * screenWidth) - (box.width / imageWidth * screenWidth)
    val refactorTopLeftY = topLeftY / imageHeight * screenHeight

    val refactorX = refactorTopLeftX + width / 2
    val refactorY = refactorTopLeftY + height / 2

    var angle = Math.toDegrees(
        atan2(
            (screenHeight / 2) - refactorY,
            refactorX - (screenWidth / 2)
        ).toDouble()
    )
    if (angle < 0) {
        angle += 360
    }
    return angle.toFloat()
}

fun distanceCalc(
    box: Rect,
    imageWidth: Float,
    imageHeight: Float,
    screenWidth: Int,
    screenHeight: Int
): Float {

    val topLeftX = box.topLeft.x
    val topLeftY = box.topLeft.y

    val width = (box.width / imageWidth * screenWidth)
    val height = box.height / imageHeight * screenHeight

    val refactorTopLeftX =
        screenWidth - (topLeftX / imageWidth * screenWidth) - (box.width / imageWidth * screenWidth)
    val refactorTopLeftY = topLeftY / imageHeight * screenHeight

    val refactorX = refactorTopLeftX + width / 2
    val refactorY = refactorTopLeftY + height / 2

    val maxLength = sqrt(
        (screenWidth / 2).toDouble().pow(2.0) + (screenHeight / 2).toDouble().pow(2.0)
    ).toFloat()

    val length = ((sqrt(
        (refactorX - (screenWidth / 2)).toDouble()
            .pow(2.0) + (refactorY - (screenHeight / 2)).toDouble().pow(2.0)
    ).toFloat()) / maxLength) * 20

    return length
    //2点間の距離
}


