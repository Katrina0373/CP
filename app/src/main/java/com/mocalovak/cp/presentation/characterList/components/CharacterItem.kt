package com.mocalovak.cp.presentation.characterList.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mocalovak.cp.domain.model.Character

@Composable
fun CharacterItem(character: Character){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(text = character.name, style = MaterialTheme.typography.titleLarge)
            Text(text = "Level: ${character.level}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Class: ${character.characterClass}", style = MaterialTheme.typography.bodySmall)
        }
    }
}