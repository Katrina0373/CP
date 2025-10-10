package com.mocalovak.cp.presentation.Character

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mocalovak.cp.R
import com.mocalovak.cp.domain.model.ArmorWeight
import com.mocalovak.cp.domain.model.BodyPart
import com.mocalovak.cp.domain.model.EquipType
import com.mocalovak.cp.domain.model.Equipment
import com.mocalovak.cp.domain.model.PassiveEffect
import com.mocalovak.cp.domain.model.takeString
import com.mocalovak.cp.ui.theme.BrightPurple
import com.mocalovak.cp.ui.theme.LightGreen
import com.mocalovak.cp.ui.theme.button2
import com.mocalovak.cp.ui.theme.containerColor
import com.mocalovak.cp.ui.theme.filterButtonBack
import com.mocalovak.cp.ui.theme.halfAppWhite
import com.mocalovak.cp.ui.theme.otherContainer
import com.mocalovak.cp.ui.theme.unfocusedFilterButtonBack
import com.mocalovak.cp.utils.NameConverter

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EquipmentList(vm: CharacterViewModel = hiltViewModel()){
    val equipment by vm.filteredEquipment.collectAsState()

    val filters: List<Any> = listOf(
        EquipType.Armor,
        EquipType.Weapon,
        EquipType.Potion,
        EquipType.Artifact,
        EquipType.Other,
        ArmorWeight.Heavy,
        ArmorWeight.Light,
        ArmorWeight.Magic
    )

    val selectedFilters by vm.equipmentFilters.collectAsState()

    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ){
            filters.forEach { filter ->
                FilterChip(
                    selected = selectedFilters.getValue(filter),
                    onClick = {
                        vm.updateEquipmentFilter(filter)
                    },
                    label = { Text(
                        text = NameConverter(filter),
                        color = if (selectedFilters[filter]!!) Color.White else Color.LightGray
                        )},
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = unfocusedFilterButtonBack,
                        selectedContainerColor = filterButtonBack,
                    ),
                    shape = RoundedCornerShape(cornerRadius),
                    border = null
                )
            }
        }
        Spacer(Modifier.height(5.dp))



        val expandedStates = remember { mutableStateMapOf<String, Boolean>() }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
                .padding(bottom = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            items(items = equipment, key = {it.id}) { equip ->
                val expanded = expandedStates[equip.id] ?: false
                ExpandableEquipmentCard(
                    equipment = equip,
                    expanded = expanded,
                    onExpandChange = { newState ->
                        expandedStates[equip.id] = newState
                    },
                    onEquipClick = {},
                    onUnequipClick = {}
                )
            }
            item{
                Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = button2)) {
                    Text("Добавить из библиотеки", color= Color.White )
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableEquipmentCard(
    equipment: Equipment,
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    onEquipClick: () -> Unit,
    onUnequipClick: () -> Unit
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
                    Text(equipment.name, color = Color.White, fontSize = 18.sp)
                    Text(
                        text = NameConverter(equipment),
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
                Text(equipment.description, color = Color.White, fontSize = 14.sp)
                Spacer(Modifier.height(8.dp))

                when (equipment) {
                    is Equipment.Weapon -> {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            ParameterView("Урон", equipment.damage)
                            Spacer(Modifier.height(5.dp))

                            equipment.chance?.let {
                                    ParameterView("Шанс", "${(it * 100).toInt()}%")
                            }
                            equipment.passiveEffects?.let {
                                ParameterView("Пассивный эффект:", it.takeString())
                            }
                        }
                    }

                    is Equipment.Clother -> {
                        Column {

                            ParameterView("Тип брони: ",
                                NameConverter(equipment.armorWeight ?: "—")
                            )
                            Spacer(Modifier.height(5.dp))
                            ParameterView("Пассивные эффекты: ",
                                equipment.passiveEffects?.takeString() ?: "—"
                            )

                        }
                    }

                    is Equipment.Artifact -> {
                        ParameterView("Пассивный эффект: ", equipment.passiveEffects.takeString())
                    }

                    is Equipment.Potion -> {
                        equipment.effect?.let {
                            ParameterView("Эффект: ", equipment.effect)
                        }
                    }

                    else -> {}
                }

                Spacer(Modifier.height(10.dp))

                //Кнопки "Надеть / Снять"
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (equipment is Equipment.Clother || equipment is Equipment.Weapon) {
                        if ((equipment as? Equipment.Weapon)?.isEquipped == true ||
                            (equipment as? Equipment.Clother)?.isEquipped == true
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Check,
                                contentDescription = "",
                                tint = LightGreen,
                                modifier = Modifier.size(15.dp)
                            )
                            Text("Надето", color = LightGreen,
                                )
                            Text(text = "Снять",
                                modifier = Modifier.padding(8.dp)
                                    .clip(RoundedCornerShape(38.dp))
                                    .background(otherContainer)
                                    .padding(horizontal = 20.dp, vertical = 5.dp)
                                    .clickable {  },
                                color = Color.White)

                        } else {
                            Text("В багаже", color = BrightPurple)
                            Text(text = "Надеть",
                            modifier = Modifier.padding(8.dp)
                                .clip(RoundedCornerShape(38.dp))
                                .background(otherContainer)
                                .padding(horizontal = 20.dp, vertical = 5.dp)
                                .clickable {  },
                            color = Color.White)
                        }
                    }
                    else if(equipment is Equipment.Potion){
                        Text(text = "Применить",
                            modifier = Modifier.padding(8.dp)
                                .clip(RoundedCornerShape(38.dp))
                                .background(otherContainer)
                                .padding(horizontal = 20.dp, vertical = 5.dp)
                                .clickable {  },
                            color = Color.White)
                    }
                }
            }

            HorizontalDivider(modifier = Modifier.padding(top = 10.dp))
        }
    }
}

@Composable
fun SmallButton(modifier: Modifier = Modifier,
                label: String,
                onClick: () -> Unit){
    
    Button(
        modifier = Modifier.size(height = 35.dp, width = 70.dp)
            .then(modifier),
        onClick = onClick
    ) {
        Text(text = label, color = Color.White)
    }
    
    
    
}

private fun Equipment.matchesType(type: EquipType): Boolean {
    return when (type) {
        EquipType.Weapon -> this is Equipment.Weapon
        EquipType.Armor -> this is Equipment.Clother
        EquipType.Artifact -> this is Equipment.Artifact
        EquipType.Potion -> this is Equipment.Potion
        EquipType.Other -> this is Equipment.Other
    }
}


@Composable
fun ParameterView(parameter:String, description:String){

    Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){

        Text(text = parameter, color = halfAppWhite)
        Spacer(Modifier.width(20.dp))
        Text(text = description, color = Color.White)

    }

}

@Preview
@Composable
fun EquipListPreview(){
    val equipList = listOf(
        Equipment.Weapon(
            id = "lol",
            name = "Кинжал",
            description = "атака d4, легко метнуть, шанс на критическую атаку 10%",
            damage = "1d4",
            slot = listOf(BodyPart.RightHand),
            isEquipped = false,
            passiveEffects = null,
            activeEffect = "шанс на критическую атаку",
            chance = 0.1f
        ),
        Equipment.Clother(
            id = "kek",
            name = "Перчатки",
            description = "Лёгкие перчатки для воришек и лазутчиков",
            slot = listOf(BodyPart.RightHand),
            isEquipped = true,
            passiveEffects = listOf(PassiveEffect("armorClass", 1, "+1 к КБ")),
            armorWeight = ArmorWeight.Light
        ),
        Equipment.Potion(
            id = "pop",
            name = "Зелье лечения",
            description = "Бутолычка с заветной красной жидкостью",
            effect = "Восстанавливает 2к6 хитов"
        ),
        Equipment.Artifact(
            id = "kok",
            name = "Кольцо сметения",
            description = "Золотое колечко как раз по вашему пальцу",
            passiveEffects = listOf(PassiveEffect(
                "intellegence",
                1,
                "Заставляет ваших противников сомневаться в своей правоте"))
        ),
        Equipment.Other(
            id = "bruh",
            name = "Какая-то бумажка",
            description = "Написано на неизвестном языке"
        )
    )
    val expandedStates = remember { mutableStateMapOf<String, Boolean>() }

    ExpandableEquipmentCard(equipList[0], true, {}, {} ,{ })
}