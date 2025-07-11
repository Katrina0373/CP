package com.mocalovak.cp.presentation.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon:ImageVector){
    object Character: Screen("character", "Character", Icons.Default.Person)
    object ListCharacters : Screen("characters", "Characters", Icons.Default.Home)
}