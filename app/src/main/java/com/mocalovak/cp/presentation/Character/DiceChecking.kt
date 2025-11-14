package com.mocalovak.cp.presentation.Character

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mocalovak.cp.R
import com.mocalovak.cp.domain.model.ArmorWeight
import com.mocalovak.cp.domain.model.BodyPart
import com.mocalovak.cp.domain.model.Character
import com.mocalovak.cp.domain.model.Equipment
import com.mocalovak.cp.domain.model.Modification
import com.mocalovak.cp.domain.model.PassiveEffect
import com.mocalovak.cp.domain.model.PassiveEffectBasic
import com.mocalovak.cp.domain.model.PassiveEffectWithCondition
import com.mocalovak.cp.domain.model.Race
import com.mocalovak.cp.domain.model.Skill
import com.mocalovak.cp.ui.theme.CPTheme
import com.mocalovak.cp.ui.theme.Green5C
import com.mocalovak.cp.ui.theme.button2
import com.mocalovak.cp.ui.theme.dropMenuBackColor
import com.mocalovak.cp.ui.theme.gradientButton
import com.mocalovak.cp.ui.theme.halfAppWhite
import com.mocalovak.cp.ui.theme.letterTextStyle
import com.mocalovak.cp.ui.theme.topContainer
import com.mocalovak.cp.utils.CustomDropdownMenu
import kotlin.random.Random


enum class Dices(val number:Int, val icRes: Int) {
    d20( 20, R.drawable.group_406),
    d12( 12, R.drawable.d12_ic),
    d10( 10, R.drawable.d10_ic),
    d8( 8, R.drawable.d8_ic),
    d6( 6, R.drawable.d6_ic),
    d4( 4, R.drawable.d4_ic),
    d100( 100, R.drawable.d100_ic),
}

@Composable
fun DiceChecking(onDismiss: () -> Unit,
                 paddingValues: PaddingValues,
                 character: Character,
                 equipment: List<Equipment>?,
                 skills: List<Skill>?) {

    var chosenDice by remember { mutableStateOf(Dices.d20) }
    var sum by remember { mutableIntStateOf(Random.nextInt(chosenDice.number) + 1) }
    val scrollState = rememberScrollState()

    val modifiers = mapOf(
        Modification.STRENGTH to character.strength,
        Modification.DEXTERITY to character.dexterity,
        Modification.CONSTITUTION to character.constitution,
        Modification.PERCEPTION to character.perception,
        Modification.INTELLIGENCE to character.intelligence,
        Modification.MAGIC to character.magic,
        Modification.CHARISMA to character.charisma
    )
    var chosenModifier by remember { mutableStateOf<Modification?>(null) }

    Card(modifier = Modifier
        .fillMaxSize()
        .padding(top = paddingValues.calculateTopPadding())
        .padding(10.dp),
        colors = CardDefaults.cardColors(containerColor = topContainer),
        shape = RoundedCornerShape(cornerRadius)
    ) {

        Column(
            modifier = Modifier.padding(horizontal = 15.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(7.dp),
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(
                    "Проверка",
                    modifier = Modifier.align(Alignment.Center)
                        .padding(top = 20.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,

                )
                Icon(imageVector = Icons.Default.Close,
                    contentDescription = "close",
                    tint = halfAppWhite,
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .clip(CircleShape)
                        .clickable { onDismiss() }
                        .align(Alignment.TopEnd))
            }
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                EditDropDownMenu(
                    options = Dices.entries.map { it.name },
                    onValueChange = { name -> chosenDice = Dices.valueOf(name) },
                    currentValue = 0,
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(10.dp))
                GradientButton(
                    text = "Бросить",
                    gradient = gradientButton,
                    onClick = {
                        sum = Random.nextInt(chosenDice.number) + 1
                    },
                )
            }
            EditDropDownMenu(
                options = modifiers.keys.map { it.title }.toList(),
                onValueChange = { name ->
                    chosenModifier = Modification.entries.find { it.title == name }
                },
                currentValue = -1,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(10.dp))

            Row(modifier = Modifier.fillMaxWidth()) {

                Column(
                    modifier = Modifier.weight(1f)
                        .padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        "Кубик", modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Box(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            painter = painterResource(chosenDice.icRes), "Dice",
                            modifier = Modifier.align(Alignment.Center)
                        )
                        Text(
                            sum.toString().uppercase(),
                            modifier = Modifier.align(Alignment.Center),
                            fontSize = 20.sp
                        )
                    }
                }
                Column(
                    modifier = Modifier.weight(1f)
                        .padding(horizontal = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        "Сумма", modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Box(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            painter = painterResource(R.drawable.sum_back_ic), "Dice",
                            modifier = Modifier.align(Alignment.Center)
                        )
                        Text(
                            (sum + (modifiers[chosenModifier] ?: 0)).toString().uppercase(),
                            modifier = Modifier.align(Alignment.Center),
                            fontSize = 20.sp
                        )
                    }
                }
            }
            val effects = character.hasUnregisterPassiveEffects(skills, equipment)
            if (effects.isNotEmpty()) {
                Spacer(Modifier.height(10.dp))
                Row {
                    Icon(
                        painter = painterResource(R.drawable.info_ic),
                        contentDescription = "info",
                        tint = Green5C
                    )
                    Spacer(Modifier.width(10.dp))
                    Column {
                        Text("Не забудьте добавить пассивные эффекты")
                        effects.forEach {
                            if (it.parameter.equals(
                                    chosenModifier?.name,
                                    ignoreCase = true
                                )//если параметр пассивки соответствует выбранному модификатору
                                || Modification.entries.none { mod ->
                                    mod.name.equals(
                                        it.parameter,
                                        ignoreCase = true
                                    )
                                }
                            ) { //или если это вообще не модификатор
                                Text(
                                    text = it.toString()
                                )
                            }
                        }
                    }
                }

            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDropDownMenu(options: List<String>,
                     onValueChange: (String) -> Unit,
                     currentValue: Int,
                     modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember(options, currentValue) { mutableStateOf(options.getOrNull(currentValue)) }

    val rotationState by animateFloatAsState(
        targetValue = if (!expanded) 180f else 0f,
        label = "arrowRotation"
    )
    var textFieldRect by remember { mutableStateOf<Rect?>(null) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {expanded = !expanded },
        modifier = modifier
    ) {
        BasicTextField(
            value = selectedOption ?: "Выбрать модификатор...",
            onValueChange = { },
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    textFieldRect = coordinates.boundsInWindow()
                }
                .clip(RoundedCornerShape(cornerRadius))
                .background(color = dropMenuBackColor)
                .menuAnchor(),
            readOnly = true,
            decorationBox = { innerText ->
              Row(modifier = Modifier.fillMaxWidth()
                  .defaultMinSize(minWidth = ButtonDefaults.MinWidth,
                      minHeight = ButtonDefaults.MinHeight)
                  .padding(ButtonDefaults.ContentPadding),
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.SpaceBetween
                  ){
                  Box(modifier = Modifier.weight(1f)) { // текст займет оставшееся место внутри поля
                      innerText()
                  }
                  Icon(
                      painterResource(R.drawable.row_up_icon),
                      "",
                      modifier = Modifier.rotate(rotationState),
                      tint = Color.White
                  )
              }
            },
            singleLine = true,
            textStyle = letterTextStyle
        )
        CustomDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false},
            anchorBounds = textFieldRect,
            options = options,
            selectedOption = selectedOption ?: "",
            onSelect = {selectedOption = it
                       onValueChange(it)},
            backColor = Color(0xFF353A7F),
            focusedItemColor = Color(0xFF3E4499),
        )
    }
}


@Preview
@Composable
fun prevCheck() {
    val character = Character(
        1,
        "Марсиль",
        "Воин",
        "Варвар",
        "Master Fire",
        Race.Tabaksi,
        level = 20,
        maxHP = 100,
        imagePath = null,
        currentHP = 100,
        gold = 0,
        armorClass = 20f,
        speed = 10,
        maxMana = 500,
        currentMana = 500,
        strength = 10,
        dexterity = 10,
        constitution = 10,
        intelligence = 20,
        magic = 20,
        charisma = 10,
        perception = 20,
        languages = emptyList(),
        initiative = 20
    )
    val equipList = listOf(
        Equipment.Weapon(
            id = "lol",
            name = "Кинжал",
            description = "атака d4, легко метнуть, шанс на критическую атаку 10%",
            damage = "1d4",
            slot = listOf(BodyPart.RightHand),
            isEquipped = null,
            passiveEffects = null,
            activeEffect = "шанс на критическую атаку",
            chance = 0.1f,
            tir = 1
        ),
        Equipment.Clothes(
            id = "kek",
            name = "Перчатки",
            description = "Лёгкие перчатки для воришек и лазутчиков",
            slot = listOf(BodyPart.RightHand),
            isEquipped = null,
            passiveEffects = listOf(PassiveEffectBasic("armorClass", 1f, "+1 к КБ")),
            armorWeight = ArmorWeight.Light,
            tir = 1
        ),
        Equipment.Potion(
            id = "pop",
            name = "Зелье лечения",
            description = "Бутолычка с заветной красной жидкостью",
            effect = "Восстанавливает 2к6 хитов",
            tir = 1
        ),
        Equipment.Artifact(
            id = "kok",
            name = "Кольцо сметения",
            description = "Золотое колечко как раз по вашему пальцу",
            isSet = true,
            passiveEffects = listOf(
                PassiveEffectBasic(
                "intellegence",
                1f,
                "Заставляет ваших противников сомневаться в своей правоте")
            )
        ),
        Equipment.Other(
            id = "bruh",
            name = "Какая-то бумажка",
            description = "Написано на неизвестном языке"
        )
    )
    CPTheme {
        DiceChecking({}, PaddingValues(), character, equipList, emptyList())
    }
}