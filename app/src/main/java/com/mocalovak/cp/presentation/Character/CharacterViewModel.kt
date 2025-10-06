package com.mocalovak.cp.presentation.Character

import androidx.compose.runtime.MutableState
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.take
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
    private val _equipType = MutableStateFlow<EquipType?>(null)
    val equipType: StateFlow<EquipType?> = _equipType
    private val _armorWeight = MutableStateFlow<ArmorWeight?>(null)
    val armorWeight: StateFlow<ArmorWeight?> = _armorWeight

    val filteredEquipment: StateFlow<List<Equipment>> =
        combine(_equipment, _equipType, _armorWeight) { equip, type, weight ->
            equip.filter { item ->
                // фильтр по типу
                val typeMatches = when (type) {
                    null -> true // если тип не выбран — показываем всё
                    EquipType.Weapon -> item is Equipment.Weapon
                    EquipType.Armor -> item is Equipment.Clother
                    EquipType.Potion -> item is Equipment.Potion
                    EquipType.Artifact -> item is Equipment.Artifact
                    EquipType.Other -> item is Equipment.Other
                }

                // фильтр по весу брони
                val weightMatches = when {
                    weight == null -> true
                    item is Equipment.Clother -> item.armorWeight == weight
                    else -> true // не броня → не фильтруем по весу
                }

                typeMatches && weightMatches
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun updateWeightFilter(weight: ArmorWeight?){
        _armorWeight.value = weight
    }

    fun updateTypeFilter(type: EquipType?){
        _equipType.value = type
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