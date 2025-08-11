package com.mocalovak.cp.presentation.HomePage.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mocalovak.cp.domain.model.Character

@Composable
fun CharacterItem(character: Character){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = CardDefaults.cardColors().disabledContainerColor,
            disabledContentColor = CardDefaults.cardColors().disabledContentColor
        ),
        onClick = {}
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(text = character.name, style = MaterialTheme.typography.titleLarge, color = Color.White)
            Text(text = "Уровень: ${character.level}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Класс: ${character.classification}", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview
@Composable
fun PrevCharCard(){
    CharacterItem(
        Character(
            name = "Тристан", id = "8hfoeou",
            classification = "Воин",
            profession1 = "Боец",
            profession2 = "Паладин",
            race = "Людоящер",
            level = 16,
            maxHP = 64,
            currentHP = 64,
            gold = 100,
            armorClass = 18,
            speed = 4,
            maxMana = 5,
            currentMana = 5,
            strength = 2,
            dexterity = 0,
            constitution = 5,
            intelligence = 2,
            wisdom = 3,
            charisma = -1,
        ))
}