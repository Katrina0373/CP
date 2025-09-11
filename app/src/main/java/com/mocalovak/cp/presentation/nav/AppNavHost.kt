package com.mocalovak.cp.presentation.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mocalovak.cp.presentation.Character.CharacterView
import com.mocalovak.cp.presentation.CharacterList.CharacterList
import com.mocalovak.cp.presentation.HomePage.HomeScreen

@Composable
fun AppNavHost(navHostController: NavHostController, modifier: Modifier = Modifier){
    println("Character route = ${Screen.Character.route}")
    NavHost(
        navController = navHostController,
        startDestination = Screen.HomePage.route,
        modifier = modifier
    ) {
        composable(Screen.HomePage.route){
            HomeScreen(onShowAllClick = {
                navHostController.navigateSingleTopTo(Screen.Character.createRoute("all"))
            }, onShowCharClick = { characterId ->
                navHostController.navigateSingleTopTo(Screen.Character.createRoute(characterId))
            })
        }
        composable(Screen.Search.route){
            //CharacterListScreen()
        }
        composable(Screen.Rules.route){
            //CharacterListScreen()
        }
        composable(Screen.Character.route)
        { backStackEntry ->
            println("route: $route")
            val characterId = backStackEntry.arguments?.getString("characterId")
            println("character id = $characterId")
            if(characterId.equals("all"))
                CharacterList(onCharacterClick = { characterId ->
                    navHostController.navigateSingleTopTo(Screen.Character.createRoute(characterId))
                })
            else
                CharacterView()
        }
    }
}

fun NavController.navigateSingleTopTo(route: String) {
    val hasArguments = route.contains("/")
    this.navigate(route) {
        popUpTo(this@navigateSingleTopTo.graph.startDestinationId) {
            saveState = true
        }
        launchSingleTop = !hasArguments
        restoreState = !hasArguments
    }
}

