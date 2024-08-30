package com.example.annect

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.annect.data.AnimaViewModel
import com.example.annect.data.ConnectViewModel
import com.example.annect.ui.AnimaChannelScreen
import com.example.annect.ui.ConnectAnimationScreen
import com.example.annect.ui.ConnectCheckScreen
import com.example.annect.ui.ConnectFaceScreen
import com.example.annect.ui.ConnectScreen
import com.example.annect.ui.CreateScreen
import com.example.annect.ui.EnterNameScreen
import com.example.annect.ui.HomeScreen
import com.example.annect.ui.MiniGameScreen
import com.example.annect.ui.QuizGameScreen
import com.example.annect.ui.TitleScreen
import com.example.annect.data.quizList
import com.example.annect.ui.no_permission.NoPermissionScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState


//画面の名前を追加
enum class AnnectScreen(){
    Home,
    Create,
    EnterName,
    MiniGame,
    Connect,
    Title,
    AnimaChannel,
    ConnectCheck,
    ConnectFace,
    QuizGame,
    ConnectAnimation,
    Permission
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AnnectScreen(
    animaViewModel: AnimaViewModel = viewModel() ,
    connectViewModel:ConnectViewModel = viewModel(),
    context: Context
){


    val animaUiState by animaViewModel.uiState.collectAsState()
    val connectUiState by connectViewModel.uiState.collectAsState()

    val navController = rememberNavController()

    val cameraPermissionState: PermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    val onRequest = cameraPermissionState::launchPermissionRequest

    val cameraPermissionGranted = cameraPermissionState.status.isGranted


    //画面遷移の設定
    NavHost(
        navController = navController,
        //最初に遷移するところ
        startDestination =  AnnectScreen.Title.name
    ){

        //画面ごとにcomposableを書いて、呼び出したいScreenの@Composable関数を呼び出す

        //Title画面
        composable(route=AnnectScreen.Title.name){
            TitleScreen(onScreenClicked = {
                var startScreen = AnnectScreen.Create.name
                if (!animaUiState.first) {
                    animaViewModel.changeLaunchFlag()
                    startScreen = AnnectScreen.Home.name
                }
                navController.navigate(startScreen)
            },
                onDataDeleteClicked = {
                    runBlocking(Dispatchers.IO) {

                    }
                })
        }

        //Home画面
        composable(route = AnnectScreen.Home.name){

            HomeScreen(
                onMiniGameButtonClicked ={navController.navigate("MiniGame")},
                //onConnectButtonClicked ={navController.navigate("ConnectCheck")},
                onConnectButtonClicked ={navController.navigate("Permission")},
                //カメラの権限取得を無理やりぶち込んでますPermission -> ConnectCheckに進ませてます。パン屋
                //デバッグ用。チェック飛ばしてfaceへ遷移
                //onConnectButtonClicked ={navController.navigate("ConnectFace")},
                onAnimaChannelButtonClicked = {navController.navigate("AnimaChannel")},
                onInteractionSwitchClicked ={ connectViewModel.ChangeIntaractionState(it) },
                onDisplayFaceSwitchClicked ={connectViewModel.ChangeDisplayFace(it)},
                onClearDataClicked={
                    //データ消去
                    runBlocking(Dispatchers.IO) {

                    }

                },
                //viewModelから値を渡す
                name = animaUiState.name,
                body = animaUiState.body, eye = animaUiState.eye, mouth = animaUiState.mouth, accessory = animaUiState.accessory,
                interaction = connectUiState.interaction, displayFace = connectUiState.displayFace)

        }

        //Create画面
        composable(route = AnnectScreen.Create.name){
            CreateScreen(
                //次へを押したら名前入力へ
                onNextButtonClicked = {navController.navigate("EnterName")},
                //パーツ
                body = animaUiState.body, eye = animaUiState.eye, mouth = animaUiState.mouth, accessory = animaUiState.accessory,
                //矢印が押された時の関数を呼び出す
                onArrowButtonClicked = {animaViewModel.ChangeAnimaParts(it)})
        }

        //EnterName画面
        composable(route = AnnectScreen.EnterName.name){
            EnterNameScreen(
                //uiStateからAnimaのパーツを渡す
                body = animaUiState.body, eye = animaUiState.eye, mouth = animaUiState.mouth, accessory = animaUiState.accessory,
                onDecideButtonClicked = {
                    //決定ボタン　ViewModel更新
                    animaViewModel.ChangeAnimaName(it)
                },
                onNextButtonClicked={
                    //次へボタン dataStoreにセーブ（非同期処理)
                    runBlocking(Dispatchers.IO) {
                       animaViewModel.saveData()
                    }
                    //ホームへ移動
                    navController.navigate("Home")
                }
            )
        }

        //MiniGame画面
        composable(route = AnnectScreen.MiniGame.name){
            MiniGameScreen(onHomeButtonClicked = {
                navController.navigate("Home")
            }, onQuizGameSelected = {
                navController.navigate("QuizGame")  // クイズゲームへの遷移
            })
        }

        //Connect画面
        composable(route = AnnectScreen.Connect.name){
           ConnectScreen(onHomeButtonClicked = {navController.navigate("Home")},
               onCatButtonClicked = {
                   connectViewModel.ChangeAnimalName("ねこ")
                   navController.navigate("ConnectAnimation")
                 },
               onUnicornButtonClicked = {
                   connectViewModel.ChangeAnimalName("ユニコーン")
                   navController.navigate("ConnectAnimation")
                },
               onUsagiButtonClicked = {
                   connectViewModel.ChangeAnimalName("うさぎ")
                   navController.navigate("ConnectAnimation")
               }

           )
        }

        //QuizGame画面
        composable(route = AnnectScreen.QuizGame.name) {
            QuizGameScreen(
                animaViewModel = animaViewModel,
                quizList = quizList,
                onBackButtonClicked = { navController.popBackStack() }


            )

            runBlocking(Dispatchers.IO) {
               //自分へ　親愛度増加処理を書いてね　
            }
        }

        //ConnectCheck画面
        composable(route = AnnectScreen.ConnectCheck.name) {
            ConnectCheckScreen(onHomeButtonClicked = {navController.navigate("Home")},
                onNextButtonClicked = { navController.navigate("Connect") },
                context = context, viewmodel = connectViewModel)
        }

        //AnimaChannel画面
        composable(route = AnnectScreen.AnimaChannel.name){
            AnimaChannelScreen(
                name = animaUiState.name,body = animaUiState.body, eye = animaUiState.eye, mouth = animaUiState.mouth, accessory = animaUiState.accessory,
                onHomeButtonClicked = {navController.navigate("Home") }
            )
        }

        //connectFace画面
        composable(route=AnnectScreen.ConnectFace.name){
            ConnectFaceScreen( body = animaUiState.body,
                eye = animaUiState.eye,
                mouth = animaUiState.mouth,
                accessory = animaUiState.accessory,
                animal = connectUiState.animal,
                context = context,
                onHomeButtonClicked = { navController.navigate("Home") },
                viewmodel = animaViewModel,
                connectViewModel = connectViewModel,
                serialData = connectUiState.serialData,
                interaction = connectUiState.interaction,
                displayFace = connectUiState.displayFace)
        }

        //ConnectAnimation画面
        composable(route = AnnectScreen.ConnectAnimation.name){
            ConnectAnimationScreen(
                body = animaUiState.body, eye = animaUiState.eye, mouth = animaUiState.mouth, accessory = animaUiState.accessory
                , animal = connectUiState.animal, modifier = Modifier,onNextButtonClicked = {navController.navigate("ConnectFace")}
            )
        }

        composable(route = AnnectScreen.Permission.name){
            NoPermissionScreen (
                hasCameraPermission = cameraPermissionGranted,
                onRequestPermission = onRequest,
                onNextButtonClicked = {navController.navigate("ConnectCheck")}
            )
        }

    }

}