package com.mocalovak.cp.presentation.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon:ImageVector){
    object HomePage: Screen("main", "main", Icons.Default.Home)
    object Search : Screen("characters", "Characters", Icons.Default.Search)
    object Rules : Screen("rules", "Rules", Icons.Default.Info)
    object Character: Screen("character", "Character", Icons.Default.Person)
}