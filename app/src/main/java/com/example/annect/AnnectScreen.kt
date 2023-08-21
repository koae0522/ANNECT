package com.example.annect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.annect.ui.AnimaViewModel
import com.example.annect.ui.ConnectScreen
import com.example.annect.ui.CreateScreen
import com.example.annect.ui.HomeScreen
import com.example.annect.ui.MiniGameScreen


//画面の名前を追加
enum class AnnectScreen(){
    Home,
    Create,
    MiniGame,
    Connect
}

@Composable
fun AnnectScreen(
    animaViewModel:AnimaViewModel = viewModel()
){

    val animaUiState by animaViewModel.uiState.collectAsState()
    val navController = rememberNavController()

    //画面遷移の設定
    NavHost(
        navController = navController,
        //最初はCreateScreenに
        startDestination =  AnnectScreen.Create.name
    ){

        //画面ごとにcomposableを書いて、呼び出したいScreenの@Composable関数を呼び出す

        //Home画面
        composable(route = AnnectScreen.Home.name){

            HomeScreen(
                onMiniGameButtonClicked ={navController.navigate("MiniGame")},
                onConnectButtonClicked ={navController.navigate("Connect")},
                //viewModelから値を渡す
                name = animaUiState.name,
                body = animaUiState.body, eye = animaUiState.eye, mouth = animaUiState.mouth, accessory = animaUiState.accessory)
        }

        //Create画面
        composable(route = AnnectScreen.Create.name){
            CreateScreen(
                onNextButtonClicked = {navController.navigate("Home")},
                //uiStateからAnimaのパーツを渡す
                body = animaUiState.body, eye = animaUiState.eye, mouth = animaUiState.mouth, accessory = animaUiState.accessory,
                //矢印が押された時の関数を呼び出す
                name = animaUiState.name,
                onArrowButtonClicked = {animaViewModel.ChangeAnimaParts(it)})
        }

        //MiniGame画面
        composable(route = AnnectScreen.MiniGame.name){
            MiniGameScreen(onHomeButtonClicked = {navController.navigate("Home")})
        }

        //Connect画面
        composable(route = AnnectScreen.Connect.name){
           ConnectScreen(onHomeButtonClicked = {navController.navigate("Home")})
        }

    }

}