package com.example.annect

import android.R
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.example.annect.ui.theme.ANNECTTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        //ステータスバーを非表示にして全画面
        if (Build.VERSION.SDK_INT >= 28) {
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // Immersiveモードを設定
        val decorView = window.decorView
        val uiOptions =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE
        decorView.systemUiVisibility = uiOptions


        setContent {

            ANNECTTheme {
                // A surface container using the 'background' color from the theme

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }



}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.displayCutoutPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,) {
        Card(shape = MaterialTheme.shapes.medium,
                modifier = modifier.padding(10.dp)) {
            Text(
                text = "DEATH†ZIGOQ ",
                style = MaterialTheme.typography.titleLarge,
                //modifierにdisplayCutoutPadding()をつけるとカメラの内側に描画される
                modifier = modifier.displayCutoutPadding().padding(3.dp)
            )

        }
        Card(shape = MaterialTheme.shapes.medium,
            modifier = modifier.padding(10.dp)) {
            Text(
                text = "今日も俺は荒野を爆走する\n" +
                        "わずかな希望を停留させる人々を乗せて､共に戦いながら\n" +
                        "地の果てにはあるだろう､穏やかな生活を取り戻せる場所に辿り着くまで",
                style = MaterialTheme.typography.bodyLarge,
                //modifierにdisplayCutoutPadding()をつけるとカメラの内側に描画される
                modifier = modifier.displayCutoutPadding().padding(3.dp)
            )

        }
    }


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ANNECTTheme {
        Greeting("Android")
    }
}

