package com.mocalovak.cp.presentation.Character

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mocalovak.cp.R
import com.mocalovak.cp.domain.model.ActivePassive
import com.mocalovak.cp.domain.model.ArmorWeight
import com.mocalovak.cp.domain.model.CombatMagic
import com.mocalovak.cp.domain.model.EquipType
import com.mocalovak.cp.domain.model.Equipment
import com.mocalovak.cp.domain.model.Skill
import com.mocalovak.cp.domain.model.Source
import com.mocalovak.cp.domain.model.takeString
import com.mocalovak.cp.ui.theme.BrightPurple
import com.mocalovak.cp.ui.theme.LightGreen
import com.mocalovak.cp.ui.theme.containerColor
import com.mocalovak.cp.ui.theme.filterButtonBack
import com.mocalovak.cp.ui.theme.halfAppWhite
import com.mocalovak.cp.ui.theme.otherContainer
import com.mocalovak.cp.ui.theme.unfocusedFilterButtonBack
import com.mocalovak.cp.utils.NameConverter

@Composable
fun SkillsList(vm: CharacterViewModel = hiltViewModel()){
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

    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ){
            filters.forEach { filter ->
                FilterChip(
                    selected = selectedFilters[filter]!!,
                    onClick = {
                        vm.updateEquipmentFilter(filter)
                    },
                    label = { Text(
                        text = NameConverter(filter),
                        color = if (selectedFilters[filter]!!) Color.White else Color.LightGray
                    )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = unfocusedFilterButtonBack,
                        selectedContainerColor = filterButtonBack
                    )
                )
            }
        }
        Spacer(Modifier.height(5.dp))

        val expandedStates = remember { mutableStateMapOf<Int, Boolean>() }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()) {
            items(skills) { skill:Skill ->
                val expanded = expandedStates[skill.id] ?: false
                ExpandableSkillCard(
                    skill = skill,
                    expanded = expanded,
                    onExpandChange = { newState ->
                        expandedStates[skill.id] = newState
                    }
                )
            }
        }
    }

}

@Composable
fun ExpandableSkillCard(
    skill: Skill,
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit
) {

    val rotationState by animateFloatAsState(
        targetValue = if (!expanded) 90f else 180f,
        label = "arrowRotation"
    )

    Card(
        colors = CardDefaults.cardColors(containerColor = containerColor),
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .clickable(onClick = {onExpandChange(!expanded)})
    ) {
        Column(Modifier.padding(20.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

//                Image(
//                    painter = painterResource(iconRes),
//                    contentDescription = equipment.name,
//                    modifier = Modifier
//                        .size(48.dp)
//                        .clip(RoundedCornerShape(12.dp))
//                )

                Column(Modifier.weight(1f)) {
                    Text(skill.name, color = Color.White, fontSize = 18.sp)
                    Text(
                        text = "${NameConverter(skill.useType)} ${NameConverter(skill.type)}",
                        color = halfAppWhite,
                        fontSize = 14.sp
                    )
                }

                Icon(
                    painter = painterResource(R.drawable.row_up_icon),
                    contentDescription = "row",
                    modifier = Modifier.rotate(rotationState),
                    tint = Color.White
                )
            }

            if (expanded) {
                Spacer(Modifier.height(8.dp))
                Text(skill.description, color = Color.White, fontSize = 14.sp)
                Spacer(Modifier.height(8.dp))

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    skill.check?.let { ParameterView("Проверка", it) }
                    Spacer(Modifier.height(5.dp))
                    skill.damage?.let { ParameterView("Урон", it) }
                    Spacer(Modifier.height(5.dp))
                    skill.actionTime?.let { ParameterView("Время действия", it) }
                    Spacer(Modifier.height(5.dp))
                    skill.recharge?.let { ParameterView("Перезарядка", it) }
                    Spacer(Modifier.height(5.dp))
                    skill.mana?.let { ParameterView("Затрата маны", it.toString()) }
                    Spacer(Modifier.height(5.dp))

                }
            }

            Spacer(Modifier.height(10.dp))
        }


    }
}
