package com.example.annect.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.annect.data.AnimaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizGameScreen(
    animaViewModel: AnimaViewModel,
    quizList: List<Quiz>,
    onBackButtonClicked: () -> Unit = {}
) {
    val quizIndex = remember { mutableStateOf(0) }
    val currentQuiz = quizList[quizIndex.value]

    val (answer, setAnswer) = remember { mutableStateOf(TextFieldValue()) }
    val (feedback, setFeedback) = remember { mutableStateOf("") }

    // StateFlowをStateとして収集
    val animaUiState by animaViewModel.uiState.collectAsState()
    val currentLove = animaUiState.love

    // Directly defined text styles
    val titleStyle = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
    val bodyStyle = TextStyle(fontSize = 16.sp)

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
            Text(currentQuiz.question, style = titleStyle)
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
                    animaViewModel.increaseLove()  //親密度を+1
                    quizIndex.value = (quizIndex.value + 1) % quizList.size
                } else {
                    setFeedback("残念、不正解です。")
                }
            }) {
                Text("送信")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "現在の親密度: $currentLove", style = bodyStyle)

            Spacer(modifier = Modifier.height(16.dp))

            Text(feedback, style = bodyStyle)

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { onBackButtonClicked() }) {
                Text("ミニゲームの選択に戻る")
            }
        }
    }
}