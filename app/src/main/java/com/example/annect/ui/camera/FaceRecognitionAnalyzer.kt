package com.example.annect.ui.camera

import android.graphics.PointF
import android.media.Image
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.toComposeRect
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FaceRecognitionAnalyzer(
    private val onDetectedTextUpdated: (Rect, String,Float,Float) -> Unit
) : ImageAnalysis.Analyzer {

    companion object {
        const val THROTTLE_TIMEOUT_MS = 1_000L
    }

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    val option = FaceDetectorOptions.Builder()
        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
        .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
        .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
        .enableTracking()
        .build()

    private val faceDetector = FaceDetection.getClient(option)

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        scope.launch {
            val mediaImage: Image = imageProxy.image ?: run { imageProxy.close(); return@launch }
            val inputImage: InputImage =
                InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            suspendCoroutine { continuation ->
                faceDetector.process(inputImage)
                    .addOnSuccessListener { faces ->
                        // val boundingBox = faces[0].boundingBox.toComposeRect()
                        // そのうちランダムな人の目線になるようにするね今は二人以上いるとバグると思います。
                        val imageWidth = inputImage.width
                        val imageHeight = inputImage.height

                        for (face in faces) {

                            val boundingBox = face.boundingBox.toComposeRect()
                            if (face.smilingProbability != null) {
                                val smileProb = face.smilingProbability
                                onDetectedTextUpdated(boundingBox,smileProb.toString(),imageWidth.toFloat(),imageHeight.toFloat())
                            }else{
                                onDetectedTextUpdated(boundingBox,0f.toString(),imageWidth.toFloat(),imageHeight.toFloat())
                            }
                        }
                    }
                    .addOnCompleteListener {
                        continuation.resume(Unit)
                    }

            }
            delay(THROTTLE_TIMEOUT_MS)
        }.invokeOnCompletion { exception ->
            exception?.printStackTrace()
            imageProxy.close()
        }
    }
}