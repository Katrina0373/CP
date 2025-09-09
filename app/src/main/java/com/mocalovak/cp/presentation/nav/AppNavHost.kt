package com.mocalovak.cp.presentation.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mocalovak.cp.presentation.CharacterList.CharacterList
import com.mocalovak.cp.presentation.HomePage.HomeScreen

@Composable
fun AppNavHost(navHostController: NavHostController, modifier: Modifier = Modifier){
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
        composable(route = Screen.Character.route,
            arguments = listOf(navArgument("characterId") {type = NavType.StringType})
        ){ backStackEntry ->
            val characterId = backStackEntry.arguments?.getString("characterId")
            if(characterId.equals("all"))
                CharacterList(onCharacterClick = { characterId ->
                    navHostController.navigateSingleTopTo(Screen.Character.createRoute(characterId))
                })
            else
                CharacterList(onCharacterClick = { characterId ->
                    navHostController.navigateSingleTopTo(Screen.Character.createRoute(characterId))
                })
        }
    }
}

fun NavController.navigateSingleTopTo(route: String) {
    this.navigate(route) {
        popUpTo(this@navigateSingleTopTo.graph.startDestinationId) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

