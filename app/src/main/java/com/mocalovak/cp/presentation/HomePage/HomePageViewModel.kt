package com.mocalovak.cp.presentation.HomePage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mocalovak.cp.data.local.preferences.PreferenceManager
import com.mocalovak.cp.domain.model.Character
import com.mocalovak.cp.domain.usecase.GetCharacterListUseCase
import com.mocalovak.cp.presentation.nav.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharacterListUseCase,
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomePageUiState>(HomePageUiState.Loading)
    val uiState: StateFlow<HomePageUiState> = _uiState


//    private val _uiEvent = MutableSharedFlow<String>()
//    val uiEvent = _uiEvent.asSharedFlow()
//
//    fun onCharacterListClick() {
//        viewModelScope.launch {
//            _uiEvent.emit(Screen.Character.createRoute("all"))
//        }
//    }
//
//    fun onCharacterClick(characterId: String) {
//        viewModelScope.launch {
//            _uiEvent.emit(Screen.Character.createRoute(characterId))
//        }
//    }

    init {
        loadCharacters()
    }

    fun updateLastCharacter(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            preferenceManager.setLastCharacterId(id)
        }
    }

    fun removeLastCharacterId() {
        viewModelScope.launch(Dispatchers.IO) {
            preferenceManager.deleteLastCharacterId()
        }
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