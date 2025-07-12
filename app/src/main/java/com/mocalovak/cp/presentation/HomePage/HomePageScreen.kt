package com.mocalovak.cp.presentation.HomePage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.mocalovak.cp.domain.model.Character
import com.mocalovak.cp.presentation.HomePage.components.CharacterItem
import com.mocalovak.cp.presentation.HomePage.components.SearchItem
import com.mocalovak.cp.presentation.HomePage.components.TelegramRef
import com.mocalovak.cp.presentation.HomePage.components.TypoVidget


@Composable
fun CharacterListScreen(viewModel: HomePageViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is HomePageUiState.Loading -> CircularProgressIndicator()
        is HomePageUiState.Success -> HomePage((uiState as HomePageUiState.Success).characters, 200)
        is HomePageUiState.Error -> HomePage(emptyList(), 404)
    }
}

@Composable
fun HomePage(characters: List<Character>?, errorCode: Int) {
    Column {
        SearchItem()
        Text("Мои персонажи")
        if(errorCode == 200) {
            CharacterItem(characters!![0])
            if(characters.size > 1){
                CharacterItem(characters[1])
            }
            TextButton(onClick = TODO("not implemented yet")) {
                Text("смотреть все")
            }
        }
        else {
            Text("Персонажи не найдены")
            Image(Icons.Default.PlayArrow, "")
        }
        Row {
            TypoVidget(picture =, title =, description = , buttonText =)
            TypoVidget(picture =, title =, description = , buttonText =)
            TypoVidget(picture =, title =, description = , buttonText =)
            TypoVidget(picture =, title =, description = , buttonText =)
        }
        TelegramRef()
    }
}