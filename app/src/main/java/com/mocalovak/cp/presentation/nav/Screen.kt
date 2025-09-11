package com.mocalovak.cp.presentation.nav

import androidx.annotation.DrawableRes
import com.mocalovak.cp.R

sealed class Screen(val route: String,
                    val title: String,
                    @DrawableRes val activeIconRes: Int,
                    @DrawableRes val notActiveIconRes:Int
){
    object HomePage: Screen("main", "main", R.drawable.home_active, R.drawable.home_not_active)
    object Search : Screen("characters", "Characters", R.drawable.search_active, R.drawable.search_not_active)
    object Rules : Screen("rules", "Rules", R.drawable.book_not_active, R.drawable.book_active)
    object Character: Screen("character/{characterId}", "Character", R.drawable.character_active, R.drawable.character_not_active){
        fun createRoute(characterId:String) = "character/${characterId}"
    }
}