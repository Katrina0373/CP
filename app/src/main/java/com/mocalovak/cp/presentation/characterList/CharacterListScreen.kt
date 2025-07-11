package com.mocalovak.cp.presentation.characterList

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.mocalovak.cp.domain.model.Character
import com.mocalovak.cp.presentation.characterList.components.CharacterItem


@Composable
fun CharacterListScreen(viewModel: CharacterListViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is CharacterListUiState.Loading -> CircularProgressIndicator()
        is CharacterListUiState.Success -> CharacterList((uiState as CharacterListUiState.Success).characters)
        is CharacterListUiState.Error -> Text("Ошибка загрузки персонажей")
    }
}

@Composable
fun CharacterList(characters: List<Character>) {
    LazyColumn {
        items(characters) { character ->
            CharacterItem(character)
        }
    }
}