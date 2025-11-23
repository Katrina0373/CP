package com.mocalovak.cp.presentation.Libraries

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.query
import com.mocalovak.cp.domain.model.ActivePassive
import com.mocalovak.cp.domain.model.CombatMagic
import com.mocalovak.cp.domain.model.Skill
import com.mocalovak.cp.domain.model.Source
import com.mocalovak.cp.domain.usecase.AddSkillUseCase
import com.mocalovak.cp.domain.usecase.GetAllSkillsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SkillExplorerViewModel @Inject constructor(
    val getAllSkillsUseCase: GetAllSkillsUseCase,
    val addSkillUseCase: AddSkillUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel(){
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val characterId: Int = savedStateHandle["characterId"] ?: 0

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
    private val _skills = getAllSkillsUseCase()
    val skillsFilters:StateFlow<Map<Any, Boolean>> = _skillsFilters

    val filteredSkills: StateFlow<List<Skill>> =
        combine(_skills, _skillsFilters, _searchQuery) { skills, filt, query ->
            val activeTypes = filt.filter { it.value }.keys.filterIsInstance<ActivePassive>()
            val activeUseTypes = filt.filter { it.value }.keys.filterIsInstance<CombatMagic>()
            val activeSource = filt.filter { it.value }.keys.filterIsInstance<Source>()
            val q = query.trim()

            skills.filter { item ->
                val typeMatch = activeTypes.isEmpty() || item.type in activeTypes
                val useTypeMatch = activeUseTypes.isEmpty() || item.useType in activeUseTypes
                val sourceMatch = activeSource.isEmpty() || item.source in activeSource
                val searchContain = q.isBlank() || item.name.contains(q, ignoreCase = true)

                typeMatch && useTypeMatch && sourceMatch && searchContain
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

    fun onSearchChange(query:String){
        _searchQuery.value = query
    }

    fun addSkill(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            addSkillUseCase(characterId = characterId, skillId = id)
        }
    }

}