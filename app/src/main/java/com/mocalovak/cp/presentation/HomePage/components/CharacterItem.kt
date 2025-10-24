package com.mocalovak.cp.presentation.HomePage.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mocalovak.cp.domain.model.Character
import com.mocalovak.cp.presentation.Character.cornerRadius
import com.mocalovak.cp.ui.theme.containerColor
import com.mocalovak.cp.ui.theme.dropMenuBackColor
import com.mocalovak.cp.utils.loadImageFromAssets

@Composable
fun CharacterItem(character: Character,
                  openCharacter: () -> Unit){
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp),
        colors = CardColors(
            containerColor = containerColor,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = CardDefaults.cardColors().disabledContainerColor,
            disabledContentColor = CardDefaults.cardColors().disabledContentColor
        ),
        onClick = openCharacter
    ) {
        Row(Modifier.fillMaxWidth()
            .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            val painter = loadImageFromAssets(context, character.imagePath) ?:  rememberVectorPainter(Icons.Default.AccountCircle)

            Column(Modifier.padding(vertical = 16.dp, horizontal = 10.dp)) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
                Text(
                    text = "Уровень: ${character.level}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Класс: ${character.classification}",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Image(
                painter = painter,
                contentDescription = "View",
                modifier = Modifier
                    .padding(3.dp)
                    .size(65.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF1D254D))
                    .padding(2.dp)
            )



        }


        
    }
}


@Composable
fun CharacterItemWithMenu(character: Character,
                  openCharacter: () -> Unit,
                          onDeleteClick: () -> Unit,
                          onFavoriteClick: () -> Unit){
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp),
        colors = CardColors(
            containerColor = containerColor,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = CardDefaults.cardColors().disabledContainerColor,
            disabledContentColor = CardDefaults.cardColors().disabledContentColor
        ),
        onClick = openCharacter
    ) {
        Row(Modifier.fillMaxWidth()
            .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically) {
            val painter = loadImageFromAssets(context, character.imagePath) ?:  rememberVectorPainter(Icons.Default.AccountCircle)

            Image(
                painter = painter,
                contentDescription = "View",
                modifier = Modifier
                    .padding(3.dp)
                    .size(65.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF1D254D))
                    .padding(2.dp)
            )

            Column(Modifier.padding(vertical = 16.dp, horizontal = 10.dp)) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
                Text(
                    text = "Уровень: ${character.level}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
                Text(
                    text = "Класс: ${character.classification}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White
                )
            }

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End) {

                Box(
                    //modifier = Modifier.padding(16.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = "More options",
                            tint = Color.LightGray
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .clip(RoundedCornerShape(cornerRadius))
                            .background(color = dropMenuBackColor)
                    ) {
                        DropdownMenuItem(
                            text = { Text("В избранное") },
                            onClick = { /* Do something... */ }
                        )
                        DropdownMenuItem(
                            text = { Text("Удалить") },
                            onClick = { /* Do something... */ }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PrevCharCard(){
    CharacterItemWithMenu(
        Character(
            name = "Тристан", id = 8,
            classification = "Воин",
            profession1 = "Боец",
            profession2 = "Паладин",
            race = "Людоящер",
            imagePath = null,
            level = 16,
            maxHP = 64,
            currentHP = 64,
            gold = 100,
            armorClass = 18f,
            speed = 4,
            maxMana = 5,
            currentMana = 5,
            strength = 2,
            dexterity = 0,
            constitution = 5,
            intelligence = 2,
            magic = 3,
            charisma = -1,
            perception = 2,
            languages = emptyList(),
            initiative = 0
        ),
        openCharacter = {}, {}, {}
    )
}