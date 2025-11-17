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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mocalovak.cp.R
import com.mocalovak.cp.domain.model.ActivePassive
import com.mocalovak.cp.domain.model.CombatMagic
import com.mocalovak.cp.domain.model.Skill
import com.mocalovak.cp.domain.model.Source
import com.mocalovak.cp.ui.theme.backColor
import com.mocalovak.cp.ui.theme.button2
import com.mocalovak.cp.ui.theme.containerColor
import com.mocalovak.cp.ui.theme.filterButtonBack
import com.mocalovak.cp.ui.theme.halfAppWhite
import com.mocalovak.cp.ui.theme.hptems
import com.mocalovak.cp.ui.theme.otherContainer
import com.mocalovak.cp.ui.theme.selectedBorderColor
import com.mocalovak.cp.ui.theme.unfocusedFilterButtonBack
import com.mocalovak.cp.utils.NameConverter

@Composable
fun SkillsList(vm: CharacterViewModel = hiltViewModel(),
               openLibrary: () -> Unit){
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

    Column(modifier = Modifier.padding(10.dp)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
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
                    shape = RoundedCornerShape(18.dp),
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
                .padding(bottom = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            items(skills) { skill:Skill ->
                val expanded = expandedStates[skill.id] ?: false
                ExpandableSkillCard(
                    skill = skill,
                    expanded = expanded,
                    onExpandChange = { newState ->
                        expandedStates[skill.id] = newState
                    },
                    onDeleteClick = { vm.deleteSkill(skill.id) }
                )
                HorizontalDivider(//modifier = Modifier.padding(top = 10.dp),
                    color = hptems
                )
            }
            item {
                Button(onClick = {
                    openLibrary()
                }, colors = ButtonDefaults.buttonColors(containerColor = button2),
                    modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Text("Добавить из библиотеки", color= Color.White )
                }
            }
        }
    }
}

@Composable
fun ExpandableSkillCard(
    skill: Skill,
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    withAdd:Boolean = false,
    onAddClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
) {

    val rotationState by animateFloatAsState(
        targetValue = if (!expanded) 90f else 180f,
        label = "arrowRotation"
    )

    var showDeletingDialog by remember { mutableStateOf(false) }


    if (showDeletingDialog) {
        AcceptingDialog(text = "Удалить навык ${skill.name}?",
            onConfirm = {
                onDeleteClick()
                showDeletingDialog = false
            }) {
            showDeletingDialog = false
        }
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = if(withAdd) backColor else containerColor),
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .clickable(onClick = {onExpandChange(!expanded)})
    ) {
        Column(Modifier.padding(vertical = 10.dp)) {
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
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    skill.usageTime?.let { ParameterView("Время использования", it) }
                    skill.check?.let { ParameterView("Проверка", it) }
                    skill.damage?.let { ParameterView("Урон", it) }
                    skill.savingThrow?.let { ParameterView("Спасбросок", it) }
                    skill.difficulty?.let { ParameterView("Сложность", it.toString()) }
                    skill.actionTime?.let { ParameterView("Время действия", it) }
                    skill.recharge?.let { ParameterView("Перезарядка", it) }
                    skill.mana?.let { ParameterView("Затрата маны", it.toString()) }
                }
                Spacer(Modifier.height(10.dp))
                if(withAdd){
                    Text(
                        text = "Добавить",
                        modifier = Modifier.padding(vertical = 8.dp)
                            .clip(RoundedCornerShape(38.dp))
                            .clickable { onAddClick() }
                            .background(otherContainer)
                            .padding(horizontal = 20.dp, vertical = 5.dp),
                        color = Color.White
                    )
                }
                else {
                    Icon(
                        painter = painterResource(R.drawable.delete_ic),
                        "delete icon",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .padding(8.dp)
                            .padding(end = 10.dp)
                            .clip(CircleShape)
                            .align(Alignment.End)
                            .clickable {
                                showDeletingDialog = true
                            }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun prevSkillItem(){
    val sk = Skill(
        "01",
        "Огненный шар",
        description = "Запускает огненный снаряд в противника",
        type = ActivePassive.Active,
        useType = CombatMagic.Magic,
        source = Source.Profession,
        accessLevel = 1,
        check = "magic",
        savingThrow = null,
        difficulty = null,
        recharge = null,
        damage = "1d6",
        actionTime = null,
        usageTime = "1 действие",
        passiveEffect = null,
        mana = 1
    )

    ExpandableSkillCard(skill = sk, expanded = false, {}, true, {}, {})
}
