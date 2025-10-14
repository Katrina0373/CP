package com.mocalovak.cp.presentation.library

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mocalovak.cp.R
import com.mocalovak.cp.domain.model.ArmorWeight
import com.mocalovak.cp.domain.model.EquipType
import com.mocalovak.cp.presentation.Character.ExpandableEquipmentCard
import com.mocalovak.cp.presentation.Character.cornerRadius
import com.mocalovak.cp.ui.theme.backColor
import com.mocalovak.cp.ui.theme.button2
import com.mocalovak.cp.ui.theme.containerColor
import com.mocalovak.cp.ui.theme.filterButtonBack
import com.mocalovak.cp.ui.theme.halfAppWhite
import com.mocalovak.cp.ui.theme.numBack
import com.mocalovak.cp.ui.theme.unfocusedFilterButtonBack
import com.mocalovak.cp.utils.NameConverter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquipmentExplorer(vm: EquipmentExplorerViewModel = hiltViewModel(),
                      withAdding: Boolean = false,
                      characterId:Int? = null,
                      onBackClick:() -> Unit = {}){
    val equipment by vm.filteredEquipment.collectAsState()
    val searchValue by vm.searchQuery.collectAsState()

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

    Column(modifier = Modifier.fillMaxSize()
        .background(color = backColor)
        .padding(8.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(painterResource(R.drawable.row_up_icon),
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
                }
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            filters.forEach { filter ->
                FilterChip(
                    selected = selectedFilters.getValue(filter),
                    onClick = {
                        vm.updateEquipmentFilter(filter)
                    },
                    label = {
                        Text(
                            text = NameConverter(filter),
                            color = if (selectedFilters[filter]!!) Color.White else Color.LightGray
                        )
                    },
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

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
                .padding(bottom = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(items = equipment, key = { it.id }) { equip ->
                val expanded = expandedStates[equip.id] ?: false
                ExpandableEquipmentCard(
                    equipment = equip,
                    expanded = expanded,
                    onExpandChange = { newState ->
                        expandedStates[equip.id] = newState
                    },
                    withAdd = withAdding,
                    onAddClick = { vm.addEquipmentToCharacter(equip.id) }
                )
            }

        }
    }
}