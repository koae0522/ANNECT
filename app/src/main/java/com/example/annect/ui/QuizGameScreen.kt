package com.example.annect.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.annect.data.AnimaViewModel
import com.example.annect.data.Quiz
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizGameScreen(
    animaViewModel: AnimaViewModel,
    quizList: List<Quiz>,
    onBackButtonClicked: () -> Unit = {}
) {
    val shuffledQuizList = remember { quizList.shuffled(Random(System.currentTimeMillis())) }
    val quizIndex = remember { mutableStateOf(0) }
    val currentQuiz = shuffledQuizList[quizIndex.value]

    val (answer, setAnswer) = remember { mutableStateOf(TextFieldValue()) }
    val (feedback, setFeedback) = remember { mutableStateOf("") }
    val (showCorrectAnswer, setShowCorrectAnswer) = remember { mutableStateOf(false) }


    val animaUiState by animaViewModel.uiState.collectAsState()
    val currentLove = animaUiState.love


    val titleStyle = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
    val bodyStyle = TextStyle(fontSize = 18.sp)
    val feedbackStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0F7FA))
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(Color.White)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                currentQuiz.question,
                style = titleStyle,
                modifier = Modifier.height(70.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = answer,
                onValueChange = { setAnswer(it) },
                label = { Text("回答を入力") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                if (answer.text == currentQuiz.correctAnswer) {
                    setFeedback("正解です！")
                    animaViewModel.increaseLove()
                    quizIndex.value = (quizIndex.value + 1) % shuffledQuizList.size
                    setAnswer(TextFieldValue(""))
                } else {
                    setFeedback("残念、不正解です。正解は${currentQuiz.correctAnswer}でした。")
                    setShowCorrectAnswer(true)
                }
            }) {
                Text("送信")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "現在の親密度: $currentLove", style = bodyStyle)

            Spacer(modifier = Modifier.height(16.dp))

            Text(feedback, style = feedbackStyle)
        }


        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(y = (5).dp)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)  // ボタン間のスペース
        ) {


            if (showCorrectAnswer) {
                Button(onClick = {
                    setShowCorrectAnswer(false)
                    setFeedback("")
                    quizIndex.value = (quizIndex.value + 1) % shuffledQuizList.size
                    setAnswer(TextFieldValue(""))
                }) {
                    Text("次の問題へ")
                }
            }

            Button(onClick = { onBackButtonClicked() }) {
                Text("ミニゲームの選択に戻る")
            }
        }
    }
}