package com.mocalovak.cp.presentation.Libraries

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mocalovak.cp.R
import com.mocalovak.cp.domain.model.ActivePassive
import com.mocalovak.cp.domain.model.CombatMagic
import com.mocalovak.cp.domain.model.Skill
import com.mocalovak.cp.domain.model.Source
import com.mocalovak.cp.presentation.Character.ExpandableSkillCard
import com.mocalovak.cp.presentation.Character.cornerRadius
import com.mocalovak.cp.ui.theme.button2
import com.mocalovak.cp.ui.theme.containerColor
import com.mocalovak.cp.ui.theme.filterButtonBack
import com.mocalovak.cp.ui.theme.halfAppWhite
import com.mocalovak.cp.ui.theme.hptems
import com.mocalovak.cp.ui.theme.numBack
import com.mocalovak.cp.ui.theme.selectedBorderColor
import com.mocalovak.cp.ui.theme.unfocusedFilterButtonBack
import com.mocalovak.cp.utils.NameConverter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkillExplorer(vm: SkillExplorerViewModel = hiltViewModel(),
                  characterId: Int?,
                  onBackClick: () -> Unit){

    val skills by vm.filteredSkills.collectAsState()

    val selectedFilters by vm.skillsFilters.collectAsState()

    val filters = listOf(
        ActivePassive.Active,
        ActivePassive.Passive,
        CombatMagic.Combat,
        CombatMagic.Magic,
        Source.Race,
        Source.Common,
        Source.Profession,
    )

    val searchValue by vm.searchQuery.collectAsState()

    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
        Row(modifier = Modifier.fillMaxWidth()
            .padding(top = 10.dp)) {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    painterResource(R.drawable.row_up_icon),
                    "", tint = halfAppWhite,
                    modifier = Modifier.rotate(-90f))
            }
            TextField(
                searchValue,
                onValueChange = { vm.onSearchChange(it) },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = containerColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White
                ),
                shape = RoundedCornerShape(cornerRadius),
                placeholder = { Text(" Найти снаряжение...", color = numBack) },
                trailingIcon = {
                    Icon(painterResource(R.drawable.search_ic), contentDescription = "search")
                },
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ){
            filters.forEach { filter ->
                FilterChip(
                    selected = selectedFilters[filter]!!,
                    onClick = {
                        vm.updateSkillsFilter(filter)
                    },
                    label = { Text(
                        text = NameConverter(filter),
                        color = if (selectedFilters[filter]!!) Color.White else Color.LightGray
                    )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = unfocusedFilterButtonBack,
                        selectedContainerColor = filterButtonBack
                    ),
                    shape = RoundedCornerShape(cornerRadius),
                    border = FilterChipDefaults.filterChipBorder(enabled = true,
                        selected = selectedFilters.getValue(filter),
                        borderColor = Color.Transparent,
                        selectedBorderColor = selectedBorderColor
                    )
                )
            }
        }
        Spacer(Modifier.height(5.dp))

        val expandedStates = remember { mutableStateMapOf<String, Boolean>() }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
                .padding(end = 10.dp)) {
            items(skills) { skill: Skill ->
                val expanded = expandedStates[skill.id] ?: false
                ExpandableSkillCard(
                    skill = skill,
                    expanded = expanded,
                    onExpandChange = { newState ->
                        expandedStates[skill.id] = newState
                    },
                    withAdd = true,
                    onAddClick = { vm.addSkill(skill.id) }
                )
                HorizontalDivider(//modifier = Modifier.padding(top = 10.dp),
                    color = hptems
                )
            }
        }
    }
}
