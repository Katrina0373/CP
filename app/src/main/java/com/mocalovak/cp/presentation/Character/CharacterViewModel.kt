package com.mocalovak.cp.presentation.Character

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mocalovak.cp.domain.model.ActivePassive
import com.mocalovak.cp.domain.model.ArmorWeight
import com.mocalovak.cp.domain.model.BodyPart
import com.mocalovak.cp.domain.model.Character
import com.mocalovak.cp.domain.model.CombatMagic
import com.mocalovak.cp.domain.model.EquipType
import com.mocalovak.cp.domain.model.Equipment
import com.mocalovak.cp.domain.model.Skill
import com.mocalovak.cp.domain.model.Source
import com.mocalovak.cp.domain.usecase.DeleteItemUseCase
import com.mocalovak.cp.domain.usecase.DeleteSkillUseCase
import com.mocalovak.cp.domain.usecase.EquipItemUseCase
import com.mocalovak.cp.domain.usecase.GetAllEquipment
import com.mocalovak.cp.domain.usecase.GetCharacterUseCase
import com.mocalovak.cp.domain.usecase.GetCharactersEquipment
import com.mocalovak.cp.domain.usecase.GetCharactersSkillsUseCase
import com.mocalovak.cp.domain.usecase.UpdateCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase,
    private val getCharactersEquipment: GetCharactersEquipment,
    private val getCharactersSkillsUseCase: GetCharactersSkillsUseCase,
    private val updateCharacterUseCase: UpdateCharacterUseCase,
    private val equipItemUseCase: EquipItemUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
    private val deleteSkillUseCase: DeleteSkillUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<CharacterViewUIState>(CharacterViewUIState.Loading)
    val uiState: StateFlow<CharacterViewUIState> = _uiState

    private val characterId: Int = savedStateHandle["characterId"] ?: 0

    private val _character = getCharacterUseCase(characterId)
    val character: StateFlow<Character?> = _character.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        null
    )


    private val _equipment = getCharactersEquipment(characterId)
    private val _skills = getCharactersSkillsUseCase(characterId)

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
        combine(_equipment, _equipmentFilters) { equip, filt ->
            val activeTypes = filt.filter { it.value }.keys.filterIsInstance<EquipType>()
            val activeWeights = filt.filter { it.value }.keys.filterIsInstance<ArmorWeight>()

            // Если фильтры не выбраны — показываем всё
            if (activeTypes.isEmpty() && activeWeights.isEmpty()) return@combine equip

            equip.filter { item ->
                val typeMatch = when (item) {
                    is Equipment.Weapon -> EquipType.Weapon in activeTypes
                    is Equipment.Clothes -> EquipType.Armor in activeTypes
                    is Equipment.Potion -> EquipType.Potion in activeTypes
                    is Equipment.Artifact -> EquipType.Artifact in activeTypes
                    is Equipment.Other -> EquipType.Other in activeTypes
                }

                val weightMatch = when (item) {
                    is Equipment.Clothes -> (item.armorWeight in activeWeights)
                    else -> false
                }

                typeMatch || weightMatch
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    val allEquipment = _equipment.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val allSkills = _skills.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun updateEquipmentFilter(filter: Any){
        _equipmentFilters.update { current ->
            current.toMutableMap().apply {
                this[filter] = !(this[filter] ?: false)
            }
        }
    }

    private val _skillsFilters = MutableStateFlow(
        mapOf<Any, Boolean>(
            ActivePassive.Active to false,
            ActivePassive.Passive to false,
            CombatMagic.Combat to false,
            CombatMagic.Magic to false,
            Source.Race to false,
            Source.Common to false,
            Source.Profession to false,
        )
    )

    val skillsFilters:StateFlow<Map<Any, Boolean>> = _skillsFilters

    val filteredSkills: StateFlow<List<Skill>> =
        combine(_skills, _skillsFilters) { skills, filt ->
            val activeTypes = filt.filter { it.value }.keys.filterIsInstance<ActivePassive>()
            val activeUseTypes = filt.filter { it.value }.keys.filterIsInstance<CombatMagic>()
            val activeSource = filt.filter { it.value }.keys.filterIsInstance<Source>()

            // Если фильтры не выбраны — показываем всё
            if (activeTypes.isEmpty() && activeUseTypes.isEmpty() && activeSource.isEmpty()) return@combine skills

            skills.filter { item ->
                val typeMatch =  (item.type in activeTypes)
                val useTypeMatch = (item.useType in activeUseTypes)
                val sourceMatch = (item.source in activeSource)

                typeMatch || useTypeMatch || sourceMatch
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun updateSkillsFilter(filter: Any){
        _skillsFilters.update { current ->
            current.toMutableMap().apply {
                this[filter] = !(this[filter] ?: false)
            }
        }
    }

    fun updateGold(newValue:Int){
        if(newValue > 0) {
            viewModelScope.launch(Dispatchers.IO) {
                updateCharacterUseCase.updateGold(character.value!!.id, newValue)
            }
        }
    }
    fun updateHP(newValue:Int){
        if(newValue > 0) {
            viewModelScope.launch(Dispatchers.IO) {
                updateCharacterUseCase.updateHP(character.value!!.id, newValue)
            }
        }
    }

    fun updateMana(newValue:Int){
        if(newValue > 0) {
            viewModelScope.launch(Dispatchers.IO) {
                updateCharacterUseCase.updateMana(character.value!!.id, newValue)
            }
        }
    }

    fun updateLanguages(newValue: List<String>){
            viewModelScope.launch(Dispatchers.IO) {
                updateCharacterUseCase.updateLanguages(character.value!!.id, newValue)
            }
    }

    fun levelUp() {
            viewModelScope.launch(Dispatchers.IO) {
                updateCharacterUseCase.levelUp(character.value!!.id)
            }

    }

    fun equipItem(item: Equipment, slot:BodyPart){
        viewModelScope.launch(Dispatchers.IO) {
            equipItemUseCase(item.id, slot)
        }
    }

    fun takeEmptySlots(equipment: List<Equipment>, item: Equipment): List<BodyPart>{
        return equipItemUseCase.takeEmptySlots(equipment, item)
    }

    fun deleteItem(item:Equipment){
        viewModelScope.launch(Dispatchers.IO) {
            deleteItemUseCase(item.id)
        }
    }

    fun UnEquipItem(itemId: String){
        viewModelScope.launch(Dispatchers.IO) {
            equipItemUseCase.unEquipItem(itemId)
        }
    }

    fun deleteSkill(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteSkillUseCase(skillId = id, characterId = characterId)
        }
    }

}