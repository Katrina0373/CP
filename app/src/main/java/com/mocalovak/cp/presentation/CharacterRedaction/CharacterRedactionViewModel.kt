package com.mocalovak.cp.presentation.CharacterRedaction

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mocalovak.cp.domain.model.Character
import com.mocalovak.cp.domain.usecase.GetCharacterUseCase
import com.mocalovak.cp.domain.usecase.UpdateCharacterUseCase
import com.mocalovak.cp.presentation.HomePage.HomePageUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterRedactionViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase,
    private val updateCharacterUseCase: UpdateCharacterUseCase,
    stateHandle: SavedStateHandle
):ViewModel() {


    private val _uiState = MutableStateFlow<EditScreenUiState>(EditScreenUiState.Loading)
    val uiState: StateFlow<EditScreenUiState> = _uiState

    private val characterId:Int = stateHandle["characterId"] ?: 0
    private val _character = MutableStateFlow<Character?>(null)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _character.value = getCharacterUseCase(characterId, withPassives = false)
                .catch { _uiState.value = EditScreenUiState.Error("Ошибка загрузки") }
                .first()
            _uiState.value = EditScreenUiState.Success(_character.value!!)
        }
    }

    fun updateCharacter(character: Character){
        viewModelScope.launch(Dispatchers.IO) {
            updateCharacterUseCase(character)
        }
    }

}


sealed class EditScreenUiState {
    object Loading : EditScreenUiState()
    data class Success(val character: Character) : EditScreenUiState()
    data class Error(val message: String) : EditScreenUiState()
}