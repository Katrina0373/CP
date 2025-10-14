package com.mocalovak.cp.presentation.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mocalovak.cp.presentation.Character.CharacterScreen
import com.mocalovak.cp.presentation.Character.CharacterView
import com.mocalovak.cp.presentation.CharacterList.CharacterList
import com.mocalovak.cp.presentation.HomePage.HomeScreen
import com.mocalovak.cp.presentation.library.EquipmentExplorer

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
                navHostController.navigateSingleTopTo(Screen.Character.createRoute(0))
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
        composable(Screen.Character.route, arguments = listOf(navArgument("characterId") { type = NavType.IntType }))
        { backStackEntry ->
            println("route: $route")
            val characterId = backStackEntry.arguments?.getInt("characterId")
            println("character id = $characterId")
            if(characterId == 0)
                CharacterList(onCharacterClick = { characterId ->
                    navHostController.navigateSingleTopTo(Screen.Character.createRoute(characterId))
                })
            else
                CharacterScreen(characterId = characterId!!, onBackClick = { navHostController.popBackStack() }, navController = navHostController)
        }
        composable("EquipmentLibraryWithAdding/{characterId}", arguments = listOf(navArgument("characterId") { type = NavType.IntType })){ backStackEntry ->
            val characterId = backStackEntry.arguments?.getInt("characterId")
            EquipmentExplorer(withAdding = true, onBackClick = {navHostController.popBackStack()}, characterId = characterId)
        }
        composable("SkillLibraryWithAdding/{characterId}", arguments = listOf(navArgument("characterId") { type = NavType.IntType })){ backStackEntry ->
            val characterId = backStackEntry.arguments?.getInt("characterId")
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

