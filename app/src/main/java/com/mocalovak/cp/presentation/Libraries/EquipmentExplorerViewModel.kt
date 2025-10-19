package com.mocalovak.cp.presentation.Libraries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mocalovak.cp.domain.model.ArmorWeight
import com.mocalovak.cp.domain.model.EquipType
import com.mocalovak.cp.domain.model.Equipment
import com.mocalovak.cp.domain.usecase.GetAllEquipment
import com.mocalovak.cp.presentation.Character.CharacterViewUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import androidx.lifecycle.SavedStateHandle
import com.mocalovak.cp.domain.usecase.AddEquipmentUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class EquipmentExplorerViewModel @Inject constructor(
    private val getAllEquipment: GetAllEquipment,
    private val addEquipmentToCharacter: AddEquipmentUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {


    private val _uiState = MutableStateFlow<CharacterViewUIState>(CharacterViewUIState.Loading)
    val uiState: StateFlow<CharacterViewUIState> = _uiState

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val characterId: Int = savedStateHandle["characterId"] ?: 0

    private val _equipment = getAllEquipment()

    private val _equipmentFilters = MutableStateFlow(
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
    val equipmentFilters: StateFlow<Map<Any, Boolean>> = _equipmentFilters

    val filteredEquipment: StateFlow<List<Equipment>> =
        combine(_equipment, _equipmentFilters, _searchQuery) { equip, filt, query ->
            val activeTypes = filt.filter { it.value }.keys.filterIsInstance<EquipType>()
            val activeWeights = filt.filter { it.value }.keys.filterIsInstance<ArmorWeight>()
            val q = query.trim()

            equip.filter { item ->
                val typeMatch = if (activeTypes.isEmpty()) {
                    true
                } else {
                    when (item) {
                        is Equipment.Weapon -> EquipType.Weapon in activeTypes
                        is Equipment.Clothes -> EquipType.Armor in activeTypes
                        is Equipment.Potion -> EquipType.Potion in activeTypes
                        is Equipment.Artifact -> EquipType.Artifact in activeTypes
                        is Equipment.Other -> EquipType.Other in activeTypes
                    }
                }

                val weightMatch = if (activeWeights.isEmpty()) {
                    true
                } else {
                    when (item) {
                        is Equipment.Clothes -> item.armorWeight in activeWeights
                        else -> false
                    }
                }

                val searchContain = q.isBlank() || item.name.contains(q, ignoreCase = true)

                typeMatch && weightMatch && searchContain
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )


    fun updateEquipmentFilter(filter: Any){
        _equipmentFilters.update { current ->
            current.toMutableMap().apply {
                this[filter] = !(this[filter] ?: false)
            }
        }
    }

    fun addEquipmentToCharacter(equipmentId:String){
        println("character id: $characterId \nequipment id: $equipmentId")
        viewModelScope.launch(Dispatchers.IO) {
            addEquipmentToCharacter(equipmentId = equipmentId, characterId = characterId)
        }
    }

    fun onSearchChange(query:String){
        _searchQuery.value = query
    }
}