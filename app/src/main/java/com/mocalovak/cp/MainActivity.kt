package com.mocalovak.cp

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mocalovak.cp.data.local.importer.CharacterImporterEntryPoint
import com.mocalovak.cp.data.local.importer.SkillImporterEntryPoint
import com.mocalovak.cp.presentation.nav.AppNavHost
import com.mocalovak.cp.presentation.nav.Screen
import com.mocalovak.cp.presentation.nav.navigateSingleTopTo
import com.mocalovak.cp.ui.theme.CPTheme
import com.mocalovak.cp.ui.theme.topContainer
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //импорты с json файлов
        val characterImporter = EntryPointAccessors.fromApplication(
            applicationContext,
            CharacterImporterEntryPoint::class.java
        ).characterImporter()
        val skillsImporter = EntryPointAccessors.fromApplication(
            applicationContext,
            SkillImporterEntryPoint::class.java
        ).skillImporter()

        lifecycleScope.launch {
            characterImporter.importIfNeeded()

            //skillsImporter.importIfNeeded()
        }

        setContent {
            CPTheme {
                MainScreen()
            }
        }
    }
}

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
fun BottomBar(navController: NavController) {
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
                        if (currentRoute != screen.route) {
                            if(screen is Screen.Character)
                                navController.navigateSingleTopTo(screen.createRoute("all"))
                            else
                                navController.navigateSingleTopTo(screen.route)
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


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CPTheme {
        MainScreen()
    }
}