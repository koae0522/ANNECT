package com.example.annect.connect

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.example.annect.R

class Sound(context: Context) {
    //サウンド関連の処理
    private lateinit var soundPool: SoundPool

    //サウンドの設定をカプセル化する　用途と何を再生しているかを設定
    private val audioAttributes: AudioAttributes = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_GAME)
        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
        .build()

    var catNya: Int = 0
    var catNyaun: Int = 0
    var catAmae: Int = 0
    var catSya: Int = 0

    init {
        soundBuild()
        catNya = soundPool.load(context, R.raw.cat_nya, 1)
        catNyaun = soundPool.load(context, R.raw.cat_nyaun, 1)
        catAmae = soundPool.load(context, R.raw.cat_amae, 1)
        catSya = soundPool.load(context, R.raw.cat_sya, 1)
    }

    private fun soundBuild() {
        soundPool = SoundPool.Builder()
            .setAudioAttributes(audioAttributes)
            // ストリーム数に応じて
            .setMaxStreams(10)
            .build()
    }

    fun playSound(sound: Int) {
        soundPool.play(sound, 1.0f, 1.0f, 0, 0, 1.0f)
    }
}