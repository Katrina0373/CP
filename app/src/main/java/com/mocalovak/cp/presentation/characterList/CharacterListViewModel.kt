package com.mocalovak.cp.presentation.characterList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mocalovak.cp.domain.usecase.GetCharacterListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharacterListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<CharacterListUiState>(CharacterListUiState.Loading)
    val uiState: StateFlow<CharacterListUiState> = _uiState

    init {
        loadCharacters()
    }

    private fun loadCharacters() {
        viewModelScope.launch {
            getCharactersUseCase()
                .catch { _uiState.value = CharacterListUiState.Error("Ошибка загрузки") }
                .collect { characters ->
                    _uiState.value = CharacterListUiState.Success(characters)
                }
        }
    }
}