package com.example.annect.ui.camera

import android.media.Image
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceContour
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FaceRecognitionAnalyzer(
    private val onDetectedTextUpdated: (String) -> Unit
) : ImageAnalysis.Analyzer {

    companion object {
        const val THROTTLE_TIMEOUT_MS = 1_000L
    }

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
//    private val textRecognizer: TextRecognizer =
//        TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    val realTimeOpts = FaceDetectorOptions.Builder()
        .setClassificationMode( FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
        .build()

    private val faceDetector = FaceDetection.getClient(realTimeOpts)

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        scope.launch {
            val mediaImage: Image = imageProxy.image ?: run { imageProxy.close(); return@launch }
            val inputImage: InputImage =
                InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            suspendCoroutine { continuation ->
                faceDetector.process(inputImage)
                    .addOnSuccessListener { faces ->
                        // Task completed successfully
                        // ...
                        for (face in faces) {
//                            val leftEyeContour = face.getContour(FaceContour.LEFT_EYE)?.points
//                            val upperLipBottomContour = face.getContour(FaceContour.UPPER_LIP_BOTTOM)?.points
//                            if (face.trackingId != null) {
//                                val id = face.trackingId
//                                onDetectedTextUpdated(id.toString())
//                            }
                            if (face.smilingProbability != null) {
                                val smileProb = face.smilingProbability
                                onDetectedTextUpdated(smileProb.toString())
                            }
//                            if (face.rightEyeOpenProbability != null) {
//                                val rightEyeOpenProb = face.rightEyeOpenProbability
//                                onDetectedTextUpdated(rightEyeOpenProb.toString())
//                            }
//                            if (face.leftEyeOpenProbability != null) {
//                                val leftEyeOpenProb = face.leftEyeOpenProbability
//                                onDetectedTextUpdated(leftEyeOpenProb.toString())
//                            }
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