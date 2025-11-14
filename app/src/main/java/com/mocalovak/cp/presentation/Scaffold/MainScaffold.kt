package com.mocalovak.cp.presentation.Scaffold

import android.app.Activity
import androidx.annotation.RestrictTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
import com.mocalovak.cp.utils.CustomToastHost
import com.mocalovak.cp.utils.ToastState
import kotlinx.coroutines.coroutineScope

@Composable
fun MainScreen() {

    val context = LocalContext.current
    val window = (context as Activity).window

    //смена иконок на светлые
    SideEffect {
        window.statusBarColor = topContainer.toArgb()
        window.navigationBarColor = topContainer.toArgb()
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = false
            isAppearanceLightNavigationBars = false
        }

    }

    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { innerPadding ->
        AppNavHost(
            navHostController = navController,
            modifier = Modifier.padding(innerPadding)
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
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        shadowElevation = 8.dp,
    ) {
        NavigationBar(
            containerColor = topContainer,
        ) {
            Spacer(Modifier.width(10.dp))
            items.forEach { screen ->
                val selected = currentRoute == screen.route
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(
                                id = if (selected) screen.activeIconRes else screen.notActiveIconRes
                            ),
                            contentDescription = screen.title,
                            tint = Color.Unspecified
                        )
                    },
                    selected = selected,
                    onClick = {
                        val id = scaffoldVM.lastCharacterId.value
                        if (currentRoute != screen.route) {
                            if(screen is Screen.Character) {
                                navController.navigateSingleTopTo(screen.createRoute(id))
                            }
                                else
                                    navController.navigateSingleTopTo(screen.route)
                        }
                        else if(screen is Screen.Character && id != 0){
                            scaffoldVM.removeLastCharacterId()
                            navController.navigateSingleTopTo(screen.createRoute(0))
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent,
                    )
                )
            }
            Spacer(Modifier.width(10.dp))
        }
    }
}
