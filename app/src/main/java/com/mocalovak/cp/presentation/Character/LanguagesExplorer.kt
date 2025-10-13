package com.mocalovak.cp.presentation.Character

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.mocalovak.cp.R
import com.mocalovak.cp.ui.theme.containerColor
import com.mocalovak.cp.ui.theme.gradientButton
import com.mocalovak.cp.ui.theme.halfAppWhite
import com.mocalovak.cp.ui.theme.otherContainer

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

    val childCheckedStates = remember {
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

                Column {
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
                    childCheckedStates.forEachIndexed { index, checked ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Checkbox(
                                checked = checked,
                                onCheckedChange = { isChecked ->
                                    childCheckedStates[index] = isChecked
                                },
                                colors = CheckboxDefaults.colors(
                                    uncheckedColor = halfAppWhite,
                                )
                            )
                            Text(allLanguages[index], color = Color.White)
                        }
                    }
                }

                OutlinedTextField(
                    value = otherLanguages,
                    onValueChange = { otherLanguages = it },
                    label = { Text("Прочие языки", color = Color.White) },
                    singleLine = true,
                    modifier = Modifier.padding(start = 10.dp, bottom = 10.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White)
                )

            }
            Spacer(modifier = Modifier.height(8.dp))
            GradientButton(
                text = "Подтвердить",
                gradient = gradientButton,
                modifier = Modifier.fillMaxWidth()
            ) {
                onConfirm(allLanguages.filterIndexed { ind, _ -> childCheckedStates[ind] } + otherLanguages)
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