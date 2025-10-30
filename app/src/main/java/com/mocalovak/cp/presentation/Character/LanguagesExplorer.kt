package com.mocalovak.cp.presentation.Character

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.mocalovak.cp.ui.theme.BrightPurple
import com.mocalovak.cp.ui.theme.containerColor
import com.mocalovak.cp.ui.theme.gradientButton
import com.mocalovak.cp.ui.theme.halfAppWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguagesExplorer(
    knownLanguages:List<String>?,
    onConfirm: (List<String>) -> Unit,
    onDismiss: () -> Unit
    ){

    val allLanguages =
        listOf(
            "Общий",
            "Эльфийский",
            "Древне-эльфийский",
            "Язык леса",
            "Язык мертвых",
            "Дварфийский",
            "Орочий",
            "Язык огров и великанов",
            "Язык табакси",
            "Язык ящеров"
        )

    val languagesOptions = remember {
        mutableStateListOf(*allLanguages.map { knownLanguages?.contains(it) ?: false }.toTypedArray())
    }

    var otherLanguages by remember { mutableStateOf(
        (knownLanguages?.minus(allLanguages))?.firstOrNull() ?: "") }

    Dialog(onDismissRequest = onDismiss) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = containerColor),
                shape = RoundedCornerShape(cornerRadius)
            ) {

                Column(
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {

                        Text(
                            text = "Языки",
                            color = Color.White,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(15.dp)
                                .align(Alignment.Center)
                        )

                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier.align(Alignment.TopEnd)
                        ) {
                            Icon(
                                Icons.Default.Close,
                                "close",
                                tint = halfAppWhite
                            )
                        }
                    }

                    // Checkboxes
                    languagesOptions.forEachIndexed { index, checked ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 7.dp, horizontal = 10.dp)
                                .clickable {
                                    // Переключаем чекбокс при клике на текст или пустое место рядом
                                    languagesOptions[index] = !checked
                                }
                        ) {
                            Checkbox(
                                checked = checked,
                                onCheckedChange = { isChecked ->
                                    languagesOptions[index] = isChecked
                                },
                                colors = CheckboxDefaults.colors(
                                    uncheckedColor = halfAppWhite,
                                    checkedColor = BrightPurple
                                ),
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = allLanguages[index],
                                color = Color.White,
                                modifier = Modifier.padding(start = 12.dp)
                            )
                        }
                    }

                }
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 7.dp, end = 12.dp, top = 10.dp, bottom = 10.dp)) {
                    BasicTextField(
                        value = otherLanguages,
                        onValueChange = { otherLanguages = it },
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(
                            color = Color.White,
                            fontSize = 16.sp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, bottom = 10.dp),
                        decorationBox = { innerTextField ->
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "Другое:",
                                        color = Color.White
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(bottom = 2.dp) // чуть опустить текст к линии
                                    ) {
                                        innerTextField()
                                    }
                                }
                                Box(
                                    modifier = Modifier
                                        .padding(start = 60.dp) // чтобы линия не лезла под "Другое:"
                                        .height(1.dp)
                                        .fillMaxWidth()
                                        .background(BrightPurple)
                                )
                            }
                        }
                    )

                }

            }
            Spacer(modifier = Modifier.height(8.dp))
            GradientButton(
                text = "Подтвердить",
                gradient = gradientButton,
                modifier = Modifier.fillMaxWidth()
            ) {
                var chosenLanguages = allLanguages.filterIndexed { ind, _ -> languagesOptions[ind]}
                if(otherLanguages.trim().isNotBlank()) chosenLanguages += otherLanguages
                    onConfirm(chosenLanguages)
            }
        }
    }
}

@Preview
@Composable
fun LanguagesExplorerPrev(){

    LanguagesExplorer(
        null, {}
    ) { }

}