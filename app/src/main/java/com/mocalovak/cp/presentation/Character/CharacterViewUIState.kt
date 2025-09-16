package com.mocalovak.cp.presentation.Character

import com.mocalovak.cp.domain.model.Character

sealed class CharacterViewUIState {
    object Loading : CharacterViewUIState()
    data class Success(val character: Character) : CharacterViewUIState()
    data class Error(val message: String) : CharacterViewUIState()
}
