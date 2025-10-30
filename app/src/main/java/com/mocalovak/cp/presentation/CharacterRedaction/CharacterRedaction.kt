package com.mocalovak.cp.presentation.CharacterRedaction

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mocalovak.cp.R
import com.mocalovak.cp.domain.model.Character
import com.mocalovak.cp.presentation.Character.cornerRadius
import com.mocalovak.cp.ui.theme.backColor
import com.mocalovak.cp.ui.theme.borderColor
import com.mocalovak.cp.ui.theme.containerColor
import com.mocalovak.cp.ui.theme.gradientButton
import com.mocalovak.cp.ui.theme.selectedBorderColor
import com.mocalovak.cp.ui.theme.selectedButtonColor
import com.mocalovak.cp.ui.theme.topContainer

@Composable
fun RedactionCharacterScreen(
    characterId:Int?,
    onBackClick: () -> Unit,
    vm: CharacterRedactionViewModel = hiltViewModel()
){
    val uiState by vm.uiState.collectAsState()

    when (uiState) {
        is EditScreenUiState.Loading -> CircularProgressIndicator()
        is EditScreenUiState.Success -> EditCharacterScreen(
            character = (uiState as EditScreenUiState.Success).character,
            onBackClick = {},
            onSaveClick = {character -> vm.updateCharacter(character)},
        )
        is EditScreenUiState.Error -> Text((uiState as EditScreenUiState.Error).message)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarRedaction(onBackClick: () -> Unit){

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 0.dp,
            bottomStart = 24.dp,
            bottomEnd = 24.dp),
        shadowElevation = 8.dp
    ) {
        TopAppBar(
            windowInsets = WindowInsets(0,4,0,0),
            title = { Text(text = "Редактирование персонажа") },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        painter = painterResource(R.drawable.row_up_icon),
                        contentDescription = "IconBack",
                        modifier = Modifier.rotate(-90f),
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = topContainer,
                titleContentColor = Color.White
            )
        )
    }
}

@Composable
fun EditCharacterScreen(character: Character,
                        onBackClick: () -> Unit,
                        onSaveClick: (Character) -> Unit
) {
    val scrollState = rememberScrollState()

    var characterCopy by remember { mutableStateOf(character) }
    var armor by remember { mutableStateOf(characterCopy.armorClass.toString()) }
    var maxhp by remember { mutableStateOf(characterCopy.maxHP.toString()) }
    var maxmana by remember { mutableStateOf(characterCopy.maxMana.toString()) }
    var speed by remember { mutableStateOf(characterCopy.speed.toString()) }

    val races = listOf(
        "Человек",
        "Людоящер",
        "Высший эльф",
        "Лесной эльф",
        "Тёмный эльф",
        "Дварф",
        "Орк",
        "Табакси"
    )
    Scaffold(topBar = {TopBarRedaction { onBackClick() }}) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backColor)
                .verticalScroll(scrollState)
                .padding(top = innerPadding.calculateTopPadding(), bottom = 5.dp)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(Modifier.height(10.dp))
            Text(
                text = "Общая информация",
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            // Поля
            CharacterTextField("Имя", characterCopy.name)
            { characterCopy = characterCopy.copy(name = it) }
            EditDropdownMenu("Раса", races, {characterCopy = characterCopy.copy(race = it)})
            
            // Уровень
            StatStepper(
                "Уровень",
                characterCopy.level,
                onIncrease = {
                    if(characterCopy.level < 20)
                        characterCopy = characterCopy.copy(level = characterCopy.level + 1) },
                onDecrease = {
                    if (characterCopy.level > 1)
                        characterCopy = characterCopy.copy(level = characterCopy.level-1) })

            // Классы
            Text("Класс", color = Color.White, modifier = Modifier.padding(top = 12.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                ToggleButton("Воин", true)
                ToggleButton("Маг", false)
            }

            CharacterTextField("Профессия", characterCopy.profession1 ?: "")
            { characterCopy = characterCopy.copy(profession1 = it) }
            CharacterTextField("Специальность", characterCopy.profession2 ?:"")
            { characterCopy = characterCopy.copy(profession2 = it) }

            // Портрет
            Text("Портрет", color = Color.White, modifier = Modifier.padding(top = 12.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .border(1.dp, borderColor, RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
                    .background(containerColor)
                    .clickable {  },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add portrait", tint = borderColor)
            }

            Spacer(Modifier.height(20.dp))

            Row(modifier = Modifier.fillMaxWidth()){
                Column(modifier = Modifier.weight(1f)
                    .padding(end = 10.dp)) {
                    ModifiedStats("Макс. здоровье",
                        maxhp, {maxhp = it})
                    ModifiedStats("Движение",
                        speed, {speed = it})
                }
                Column(modifier = Modifier.weight(1f)
                    .padding(horizontal = 10.dp)) {
                    ModifiedStats("Класс брони",
                        armor, { armor = it})
                    ModifiedStats("Макс. мана",
                        maxmana, {maxmana = it})
                }
            }

            // Параметры
            Text("Характеристики",
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth())

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)
                    .padding(end = 5.dp)){
                    StatStepper(
                        "Сила",
                        characterCopy.strength,
                        onDecrease = { characterCopy = characterCopy.copy(strength = characterCopy.strength - 1) },
                        onIncrease = { characterCopy = characterCopy.copy(strength = characterCopy.strength + 1) }
                    )
                    StatStepper(
                        "Ловк.",
                        characterCopy.dexterity,
                        onDecrease = { characterCopy = characterCopy.copy(dexterity = characterCopy.dexterity - 1) },
                        onIncrease = { characterCopy = characterCopy.copy(dexterity = characterCopy.dexterity + 1) }
                    )
                    StatStepper(
                        "Выносл.",
                        characterCopy.constitution,
                        onDecrease = { characterCopy = characterCopy.copy(constitution = characterCopy.constitution - 1) },
                        onIncrease = { characterCopy = characterCopy.copy(constitution = characterCopy.constitution + 1) }
                    )
                    StatStepper(
                        "Воспр.",
                        characterCopy.perception,
                        onDecrease = { characterCopy = characterCopy.copy(perception = characterCopy.perception - 1) },
                        onIncrease = { characterCopy = characterCopy.copy(perception = characterCopy.perception + 1) }
                    )
                }
                Column(modifier = Modifier.weight(1f)
                    .padding(start = 5.dp)){
                    StatStepper(
                        "Интелл.",
                        characterCopy.intelligence,
                        onDecrease = { characterCopy = characterCopy.copy(intelligence = characterCopy.intelligence - 1) },
                        onIncrease = { characterCopy = characterCopy.copy(intelligence = characterCopy.intelligence + 1) }
                    )
                    StatStepper(
                        "Магия",
                        characterCopy.magic,
                        onDecrease = { characterCopy = characterCopy.copy(magic = characterCopy.magic - 1) },
                        onIncrease = { characterCopy = characterCopy.copy(magic = characterCopy.magic + 1) }
                    )
                    StatStepper(
                        "Хар.",
                        characterCopy.charisma,
                        onDecrease = { characterCopy = characterCopy.copy(charisma = characterCopy.charisma - 1) },
                        onIncrease = { characterCopy = characterCopy.copy(charisma = characterCopy.charisma + 1) }
                    )
                    Text("")
                }
            }

            Spacer(Modifier.height(20.dp))

            // Кнопка "Сохранить"
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(gradientButton)
                    .clickable { onSaveClick(characterCopy.copy(
                        maxMana = if(maxmana.isBlank()) 0 else maxmana.toInt(),
                        speed = if(speed.isBlank()) 0 else speed.toInt(),
                        armorClass = if(armor.isBlank()) 0f else armor.toFloat(),
                        maxHP = if(maxhp.isBlank()) 1 else maxhp.toInt()
                    )) }
                    .padding(vertical = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Сохранить",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(30.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterTextField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(Modifier.fillMaxWidth()) {
        Text(label, color = Color.White)
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = containerColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            singleLine = true,
            shape = RoundedCornerShape(cornerRadius)
        )
    }
}

@Composable
fun StatStepper(label: String, value: Int, onIncrease: () -> Unit, onDecrease: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(vertical = 6.dp)
            .fillMaxWidth()
    ) {
        Text(label, color = Color.White,
            fontSize = 14.sp)
        Spacer(modifier = Modifier.width(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            IconButton(onClick = onDecrease, modifier = Modifier.size(20.dp)) {
                Icon(painterResource(R.drawable.minus_icon),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.clip(CircleShape))
            }
            Text(value.toString(), color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier
                    .width(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(containerColor)
                    .padding(horizontal = 6.dp, vertical = 7.dp),
                textAlign = TextAlign.Center)
            IconButton(onClick = onIncrease, modifier = Modifier.size(20.dp)) {
                Icon(painterResource(R.drawable.plus_icon),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.clip(CircleShape))
            }
        }
    }
}

@Composable
fun ModifiedStats(label:String,
                  value: String,
                  onStatChange:(String) -> Unit){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(vertical = 6.dp)
            .fillMaxWidth()
    ) {
        Text(label, color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier.weight(0.6f))
        Spacer(modifier = Modifier.width(10.dp))
            BasicTextField(
                value = value.toString(),
                onValueChange = { onStatChange(it)},
                modifier = Modifier
                    .width(50.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(containerColor)
                    .padding(horizontal = 6.dp, vertical = 7.dp)
                    .weight(0.4f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = TextStyle.Default.copy(color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp)
            )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDropdownMenu(label:String, options: List<String>, onValueChange: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options[0]) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(label, color = Color.White)

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            // Поле, по которому нажимаем
            TextField(
                value = selectedOption,
                onValueChange = { onValueChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
                    .menuAnchor(),
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = containerColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                singleLine = true,
                shape = RoundedCornerShape(cornerRadius)
            )

            // Само выпадающее меню
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedOption = option
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ToggleButton(text: String, selected: Boolean) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(if (selected) selectedButtonColor else containerColor)
            .border(0.5.dp, color = if(selected) selectedBorderColor else Color.Transparent, RoundedCornerShape(50))
            .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
        Text(text, color = Color.White)
    }
}

@Preview
@Composable
fun prevEdit(){
    val character = Character(
        id = 1,
        name = "Такуя",
        classification = "Воин",
        profession1 = "Боец",
        profession2 = "Варвар",
        race = "Дварф",
        imagePath = null,
        level = 13,
        maxHP = 65,
        currentHP = 65,
        gold = 100,
        armorClass = 14f,
        speed = 4,
        languages = null,
        maxMana = 10,
        currentMana = 10,
        strength = 2,
        dexterity = 5,
        constitution = 1,
        intelligence = 0,
        magic = -1,
        charisma = 0,
        perception = 4,
        initiative = 0
    )
    EditCharacterScreen(character, {}, {})
}