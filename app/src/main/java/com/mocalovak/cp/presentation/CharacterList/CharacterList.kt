package com.mocalovak.cp.presentation.CharacterList

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mocalovak.cp.domain.model.Character
import com.mocalovak.cp.presentation.HomePage.HomePageUiState
import com.mocalovak.cp.presentation.HomePage.HomePageViewModel
import com.mocalovak.cp.presentation.HomePage.components.CharacterItem

@Composable
fun CharacterList(vm: HomePageViewModel = hiltViewModel(), onCharacterClick: (String) -> Unit){
    val uiState by vm.uiState.collectAsState()
    val characters: List<Character> =
        if(uiState is HomePageUiState.Success)
        (uiState as HomePageUiState.Success).characters
    else
        emptyList()

    if(characters.isNotEmpty()) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(3.dp),)
        {
            items(characters) { character ->
                CharacterItem(character, { onCharacterClick(character.id) })
            }
        }
    }

}