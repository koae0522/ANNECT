package com.example.annect.ui

data class Quiz(val question: String, val correctAnswer: String)

val quizList = listOf(
    Quiz("猫のお腹のたぷたぷしている部分を何と言うか","プライモーディアルポーチ") ,
    Quiz("※テストメッセージ　クイズ問題は後に増えます","aaaa"),
    // 他のクイズもここに追加できます
)