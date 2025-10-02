package com.mocalovak.cp.presentation.CharacterList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mocalovak.cp.data.local.preferences.PreferenceManager
import com.mocalovak.cp.domain.usecase.GetCharacterListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import com.mocalovak.cp.domain.model.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getCharacterListUseCase: GetCharacterListUseCase,
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val charactersFlow = getCharacterListUseCase()

    val filteredCharacters: StateFlow<List<Character>> =
        combine(charactersFlow, _searchQuery) { characters, query ->
            if (query.isBlank()) characters
            else characters.filter { it.name.contains(query, ignoreCase = true) }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun onSearchChange(query: String) {
        _searchQuery.value = query
    }

    fun updateLastCharacter(id:String){
        viewModelScope.launch(Dispatchers.IO) {
            preferenceManager.setLastCharacterId(id)
        }
    }
}
