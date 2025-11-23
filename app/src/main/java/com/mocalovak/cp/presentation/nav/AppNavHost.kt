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
import com.mocalovak.cp.presentation.CharacterList.CharacterList
import com.mocalovak.cp.presentation.CharacterRedaction.RedactionCharacterScreen
import com.mocalovak.cp.presentation.HomePage.HomeScreen
import com.mocalovak.cp.presentation.Libraries.EquipmentExplorer
import com.mocalovak.cp.presentation.Libraries.SkillExplorer

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
            },
                onOpenEquipLibClick = {
                    navHostController.navigateSingleTopTo("EquipmentLibrary")
                },
                onOpenSkillLibClick = {
                    navHostController.navigateSingleTopTo("SkillLibrary")
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
            val characterId = backStackEntry.arguments?.getInt("characterId")
            if(characterId == 0)
                CharacterList(onCharacterClick = { id ->
                    navHostController.navigateSingleTopTo(Screen.Character.createRoute(id))
                },
                    onRedactionClick = { id ->
                        println("id = $id")
                        navHostController.navigate("CharacterRedaction/$id")
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
             SkillExplorer(characterId = characterId, onBackClick = {navHostController.popBackStack()}, withAdd = true)
        }
        composable("CharacterRedaction/{characterId}", arguments = listOf(navArgument("characterId") { type = NavType.IntType })) { backStackEntry ->
            val characterId = backStackEntry.arguments?.getInt("characterId")
            RedactionCharacterScreen(characterId = characterId, onBackClick = { navHostController.popBackStack() })
        }
        composable("EquipmentLibrary"){
            EquipmentExplorer(withAdding = false, onBackClick = { navHostController.popBackStack() })
        }
        composable("SkillLibrary") {
            SkillExplorer(onBackClick = {navHostController.popBackStack()}, withAdd = false, withDelete = false)
        }
    }
}

fun NavController.navigateSingleTopTo(route: String) {
    val hasArguments = route.contains("/")
    this.navigate(route) {
        launchSingleTop = !hasArguments
        restoreState = !hasArguments
    }
}

