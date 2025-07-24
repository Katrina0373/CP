package com.mocalovak.cp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mocalovak.cp.data.local.importer.CharacterImporterEntryPoint
import com.mocalovak.cp.data.local.importer.SkillImporterEntryPoint
import com.mocalovak.cp.presentation.nav.AppNavHost
import com.mocalovak.cp.presentation.nav.Screen
import com.mocalovak.cp.ui.theme.CPTheme
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
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { padding ->
        AppNavHost(
            navController = navController,
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
fun BottomBar(navController: NavController) {
    val items = listOf(Screen.HomePage, Screen.Rules, Screen.Search, Screen.Character)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                //label = { Text(screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
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