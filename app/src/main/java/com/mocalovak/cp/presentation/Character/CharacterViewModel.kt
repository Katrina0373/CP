package com.mocalovak.cp.presentation.Character

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mocalovak.cp.data.local.preferences.PreferenceManager
import com.mocalovak.cp.domain.model.Character
import com.mocalovak.cp.domain.usecase.GetCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<CharacterViewUIState>(CharacterViewUIState.Loading)
    val uiState: StateFlow<CharacterViewUIState> = _uiState

    private val characterId: String = savedStateHandle["characterId"] ?: ""

    private val _character = MutableStateFlow<Character?>(null)
    val character:StateFlow<Character?> = _character

    init{

    }

    fun updateHP(newHP:Int){
        _character.value = character.value?.copy(currentHP = newHP)
    }

    fun updateGold(newGold:Int){
        _character.value = character.value?.copy(gold = newGold)
    }

//    fun saveCharacter(){
//        viewModelScope.launch {
//            _character.value?.let { updateCharacterUseCase(it) }
//        }
//    }


}