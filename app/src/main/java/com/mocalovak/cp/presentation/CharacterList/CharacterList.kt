package com.mocalovak.cp.presentation.CharacterList

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mocalovak.cp.R
import com.mocalovak.cp.presentation.Character.cornerRadius
import com.mocalovak.cp.presentation.HomePage.components.CharacterItem
import com.mocalovak.cp.presentation.HomePage.components.CharacterItemWithMenu
import com.mocalovak.cp.ui.theme.button2
import com.mocalovak.cp.ui.theme.containerColor
import com.mocalovak.cp.ui.theme.numBack


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterList(vm: CharacterListViewModel = hiltViewModel(), onCharacterClick: (String) -> Unit){


    val searchValue by vm.searchQuery.collectAsState()
    val characters by vm.filteredCharacters.collectAsState()

    Column(modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)) {


        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            TextField(
                searchValue,
                onValueChange = { vm.onSearchChange(it) },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = containerColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(cornerRadius),
                placeholder = { Text(" Найти персонажа...", color = numBack) },
//                modifier = Modifier
//                    .padding(5.dp)
//                    .fillMaxWidth(),
                trailingIcon = {
                    Icon(painterResource(R.drawable.search_ic), contentDescription = "search")
                }
            )
            IconButton(
                onClick = {},
                modifier = Modifier.background(
                    color = button2,
                    shape = RoundedCornerShape(cornerRadius)
                )
                    .padding(horizontal = 3.dp)
            ) {
                Icon(
                    painterResource(R.drawable.ic_add_character), "add character",
                    tint = Color.LightGray,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        LazyColumn(
             modifier = Modifier
                 .fillMaxSize()
            )
        {
            items(characters) { character ->
                CharacterItemWithMenu(character = character, {
                    vm.updateLastCharacter(character.id)
                    onCharacterClick(character.id)
                },
                    {}, {})
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun searchItem(){

}