package com.mocalovak.cp.presentation.HomePage
import com.mocalovak.cp.domain.model.Character

sealed class HomePageUiState {
    object Loading : HomePageUiState()
    data class Success(val characters: List<Character>) : HomePageUiState()
    data class Error(val message: String) : HomePageUiState()
}