package com.mocalovak.cp.presentation.Character

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mocalovak.cp.data.local.preferences.PreferenceManager
import com.mocalovak.cp.domain.model.ArmorWeight
import com.mocalovak.cp.domain.model.Character
import com.mocalovak.cp.domain.model.EquipType
import com.mocalovak.cp.domain.model.Equipment
import com.mocalovak.cp.domain.usecase.GetAllEquipment
import com.mocalovak.cp.domain.usecase.GetCharacterUseCase
import com.mocalovak.cp.domain.usecase.GetCharactersEquipment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase,
    private val getCharactersEquipment: GetCharactersEquipment,
    private val getAllEquipment: GetAllEquipment,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<CharacterViewUIState>(CharacterViewUIState.Loading)
    val uiState: StateFlow<CharacterViewUIState> = _uiState

    private val characterId: String = savedStateHandle["characterId"] ?: ""

    private val _character = getCharacterUseCase(characterId)
    val character: StateFlow<Character?> = _character.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        null
    )


    private val _equipment = getAllEquipment()

    val _filters = MutableStateFlow(
        mapOf<Any, Boolean>(
            EquipType.Armor to false,
            EquipType.Weapon to false,
            EquipType.Potion to false,
            EquipType.Artifact to false,
            EquipType.Other to false,
            ArmorWeight.Heavy to false,
            ArmorWeight.Light to false,
            ArmorWeight.Magic to false
        )
    )
    val filters: StateFlow<Map<Any, Boolean>> = _filters

    val filteredEquipment: StateFlow<List<Equipment>> =
        combine(_equipment, _filters) { equip, filt ->
            val activeTypes = filt.filter { it.value }.keys.filterIsInstance<EquipType>()
            val activeWeights = filt.filter { it.value }.keys.filterIsInstance<ArmorWeight>()

            // Если фильтры не выбраны — показываем всё
            if (activeTypes.isEmpty() && activeWeights.isEmpty()) return@combine equip

            equip.filter { item ->
                val typeMatch = when (item) {
                    is Equipment.Weapon -> EquipType.Weapon in activeTypes
                    is Equipment.Clother -> EquipType.Armor in activeTypes
                    is Equipment.Potion -> EquipType.Potion in activeTypes
                    is Equipment.Artifact -> EquipType.Artifact in activeTypes
                    is Equipment.Other -> EquipType.Other in activeTypes
                }

                val weightMatch = when (item) {
                    is Equipment.Clother -> activeWeights.isEmpty() || (item.armorWeight in activeWeights)
                    else -> true
                }

                typeMatch && weightMatch
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun updateFilter(filter: Any){
        _filters.update { current ->
            current.toMutableMap().apply {
                this[filter] = !(this[filter] ?: false)
            }
        }
    }

    fun isFilterSelected(filter:Any):Boolean{
        return filters.value[filter]!!
    }


//    fun updateHP(newHP:Int){
//        _character.value?.currentHP = newHP
//    }
//
//    fun updateGold(newGold:Int){
//        _character.value?.gold = newGold
//    }

//    fun saveCharacter(){
//        viewModelScope.launch {
//            _character.value?.let { updateCharacterUseCase(it) }
//        }
//    }


}