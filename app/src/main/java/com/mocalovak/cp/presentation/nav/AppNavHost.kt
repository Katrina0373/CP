package com.mocalovak.cp.presentation.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mocalovak.cp.presentation.HomePage.CharacterListScreen
import androidx.navigation.compose.composable

@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier = Modifier){
    NavHost(
        navController = navController,
        startDestination = Screen.HomePage.route,
        modifier = modifier
    ) {
        composable(Screen.HomePage.route){
            CharacterListScreen()
        }
        composable(Screen.Search.route){
            //CharacterListScreen()
        }
        composable(Screen.Rules.route){
            //CharacterListScreen()
        }
        composable(Screen.Character.route){
            //CharacterScreen()
        }
    }
}