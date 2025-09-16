package com.mocalovak.cp.presentation.Scaffold

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mocalovak.cp.presentation.nav.AppNavHost
import com.mocalovak.cp.presentation.nav.Screen
import com.mocalovak.cp.presentation.nav.navigateSingleTopTo
import com.mocalovak.cp.ui.theme.topContainer

@Composable
fun MainScreen() {

    val context = LocalContext.current
    val window = (context as Activity).window

    SideEffect {
        window.statusBarColor = topContainer.toArgb()
        window.navigationBarColor = topContainer.toArgb()
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = false // светлые иконки
            isAppearanceLightNavigationBars = false
        }

    }

    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { padding ->
        AppNavHost(
            navHostController = navController,
            modifier = Modifier.padding(padding)
        )
    }
}


@Composable
fun BottomBar(navController: NavController, scaffoldVM: ScaffoldViewModel = hiltViewModel()) {
    val items = listOf(Screen.HomePage, Screen.Rules, Screen.Search, Screen.Character)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        shadowElevation = 8.dp
    ) {
        NavigationBar(containerColor = topContainer) {
            items.forEach { screen ->
                val selected = currentRoute == screen.route
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(
                                id = if (selected) screen.activeIconRes else screen.notActiveIconRes
                            ),
                            contentDescription = screen.title,
                            tint = Color.Unspecified // чтобы не затирало цвет из ресурса
                        )
                    },
                    selected = selected,
                    onClick = {
                        val id = scaffoldVM.lastCharacterId.value
                        println("id scaffold: $id")
                        if (currentRoute != screen.route) {
                            if(screen is Screen.Character) {
                                if (id != "null") {
                                    navController.navigateSingleTopTo(screen.createRoute(id))
                                }
                                else {
                                    navController.navigateSingleTopTo(screen.createRoute("all"))
                                }
                            }
                                else
                                    navController.navigateSingleTopTo(screen.route)
                        }
                        else if(screen is Screen.Character && id != "null"){
                            scaffoldVM.removeLastCharacterId()
                            navController.navigateSingleTopTo(screen.createRoute("all"))
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent // убираем фон выделения
                    )
                )
            }
        }
    }
}
