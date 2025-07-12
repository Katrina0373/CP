package com.mocalovak.cp.presentation.HomePage

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
class HomePageViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharacterListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomePageUiState>(HomePageUiState.Loading)
    val uiState: StateFlow<HomePageUiState> = _uiState

    init {
        loadCharacters()
    }

    private fun loadCharacters() {
        viewModelScope.launch {
            getCharactersUseCase()
                .catch { _uiState.value = HomePageUiState.Error("Ошибка загрузки") }
                .collect { characters ->
                    _uiState.value = HomePageUiState.Success(characters)
                }
        }
    }
}