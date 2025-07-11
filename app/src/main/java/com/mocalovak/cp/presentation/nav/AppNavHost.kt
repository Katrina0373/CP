package com.mocalovak.cp.presentation.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import com.mocalovak.cp.presentation.characterList.CharacterListScreen
import androidx.navigation.compose.composable

@Composable
fun AppNavHost(navController: NavController, modifier: Modifier = Modifier){
    NavHost(
        navController = navController,
        startDestination = Screen.ListCharacters.route,
        modifier = modifier
    ) {
        composable(Screen.ListCharacters.route){
            CharacterListScreen()
        }
        composable(Screen.Character.route){
            CharacterScreen()
        }
    }
}