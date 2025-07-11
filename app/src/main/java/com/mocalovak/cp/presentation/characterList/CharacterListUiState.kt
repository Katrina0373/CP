package com.mocalovak.cp.presentation.characterList
import com.mocalovak.cp.domain.model.Character

sealed class CharacterListUiState {
    object Loading : CharacterListUiState()
    data class Success(val characters: List<Character>) : CharacterListUiState()
    data class Error(val message: String) : CharacterListUiState()
}