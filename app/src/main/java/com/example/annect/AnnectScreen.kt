package com.example.annect

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.annect.ui.AnimaViewModel
import com.example.annect.ui.CreateScreen
import com.example.annect.ui.HomeScreen

enum class AnnectScreen(){
    Home,
    Create
}

@Composable
fun AnnectScreen(
    animaViewModel:AnimaViewModel = viewModel()
){
    val animaUiState by animaViewModel.uiState.collectAsState()
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination =  AnnectScreen.Home.name
    ){
        composable(route = AnnectScreen.Home.name){
            HomeScreen(onClickButton = {navController.navigate("Create")},
            name = animaUiState.name,parts= animaUiState.animaParts)
        }
        composable(route = AnnectScreen.Create.name){
            CreateScreen(onClickButton = {navController.navigate("Home")})
        }

    }

}