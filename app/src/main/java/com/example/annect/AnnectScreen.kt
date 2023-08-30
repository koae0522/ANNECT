package com.example.annect

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.datastore.dataStore
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.annect.data.AnimaViewModel
import com.example.annect.data.DataRepository
import com.example.annect.ui.ConnectScreen
import com.example.annect.ui.CreateScreen
import com.example.annect.ui.EnterNameScreen
import com.example.annect.ui.HomeScreen
import com.example.annect.ui.MiniGameScreen
import com.example.annect.ui.TitleScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking


//画面の名前を追加
enum class AnnectScreen(){
    Home,
    Create,
    EnterName,
    MiniGame,
    Connect,
    Title
}

@Composable
fun AnnectScreen(
    animaViewModel: AnimaViewModel = viewModel() ,context: Context
){

    val animaUiState by animaViewModel.uiState.collectAsState()
    val navController = rememberNavController()

    //DataStoreの処理
    val dataRepository=DataRepository(context)

    //データ読み込み
    val nameFlow = dataRepository.loadName()
    val nameState by nameFlow.collectAsState(initial = null)

    val bodyFlow = dataRepository.loadBody()
    val bodyState by bodyFlow.collectAsState(initial = null)

    val eyeFlow = dataRepository.loadEye()
    val eyeState by eyeFlow.collectAsState(initial = null)

    val mouthFlow = dataRepository.loadMouth()
    val mouthState by mouthFlow.collectAsState(initial = null)

    val accessoryFlow = dataRepository.loadAccessory()
    val accessoryState by accessoryFlow.collectAsState(initial = null)

    val loveFlow = dataRepository.loadLove()
    val loveState by loveFlow.collectAsState(initial = null)

    val feelingFlow = dataRepository.loadFeeling()
    val feelingState by feelingFlow.collectAsState(initial = null)

    val createdFlagFlow = dataRepository.loadCreatedFlag()
    val createdFlagState by createdFlagFlow.collectAsState(initial = null)


    var startScreen = "Home"

    ////初回起動チェック　初回でない場合はデータをviewModelに
    if(createdFlagState==true){
        //dataStoreからviewModelに読み込み
        nameState?.let { bodyState?.let { it1 ->
            eyeState?.let { it2 ->
                mouthState?.let { it3 ->
                    accessoryState?.let { it4 ->
                        loveState?.let { it5 ->
                            feelingState?.let { it6 ->
                                animaViewModel.loadData(it,
                                    it1, it2, it3, it4, it5, it6
                                )
                            }
                        }
                    }
                }
            }
        } }
    }else{
        //初回起動の場合はCreateScreenからスタート
        startScreen= "Create"
    }

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
                navController.navigate(startScreen)
            })
        }

        //Home画面
        composable(route = AnnectScreen.Home.name){

            HomeScreen(
                onMiniGameButtonClicked ={navController.navigate("MiniGame")},
                onConnectButtonClicked ={navController.navigate("Connect")},
                onClearDataClicked={
                    //データ消去
                    runBlocking(Dispatchers.IO) {
                        dataRepository.clearName()
                        dataRepository.clearEye()
                        dataRepository.clearMouth()
                        dataRepository.clearAccessory()
                        dataRepository.clearLove()
                        dataRepository.clearFeeling()
                        dataRepository.clearCreatedFlag()
                    }

                },
                //viewModelから値を渡す
                name = animaUiState.name,
                body = animaUiState.body, eye = animaUiState.eye, mouth = animaUiState.mouth, accessory = animaUiState.accessory)
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
                        dataRepository.saveBody(animaUiState.body)
                        dataRepository.saveEye(animaUiState.eye)
                        dataRepository.saveMouth(animaUiState.mouth)
                        dataRepository.saveAccessory(animaUiState.accessory)
                        dataRepository.saveLove(1)
                        dataRepository.saveaFeeling(1)
                        dataRepository.saveCreatedFlag(true)
                        dataRepository.saveName(animaUiState.name)
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
            })
        }

        //Connect画面
        composable(route = AnnectScreen.Connect.name){
           ConnectScreen(onHomeButtonClicked = {navController.navigate("Home")})
        }

    }

}