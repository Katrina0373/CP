package com.mocalovak.cp.presentation.Character

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.mocalovak.cp.R
import com.mocalovak.cp.ui.theme.CPTheme
import com.mocalovak.cp.ui.theme.button2
import com.mocalovak.cp.ui.theme.halfAppWhite
import com.mocalovak.cp.ui.theme.otherContainer
import com.mocalovak.cp.ui.theme.subButton
import com.mocalovak.cp.ui.theme.topContainer
import kotlin.random.Random

@Composable
fun RestDialog(level:Int,
               onRestClick: (health: Int, mana: Int) -> Unit,
               onDismiss: () -> Unit) {
    val diceCount = level.div(10) + 1

    Dialog(onDismissRequest = onDismiss) {
        Card(modifier = Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(cornerRadius)),
            colors = CardDefaults.cardColors(containerColor = topContainer)
        ){

            Icon(imageVector = Icons.Default.Close,
                tint = halfAppWhite,
                contentDescription = "close icon",
                modifier = Modifier.align(Alignment.End)
                    .clickable { onDismiss() }
                    .padding(top = 9.dp, end = 9.dp))

            Column(modifier = Modifier.fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 17.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(17.dp),
                ){

                Text("Ваши кубики\n восстановления за отдых",
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth()
                        .background(color = otherContainer,
                            shape = RoundedCornerShape(cornerRadius))
                        .padding(horizontal = 13.dp, vertical = 20.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(painter = painterResource(R.drawable.moon_ic), "moon",
                        tint = Color.White)
                    Spacer(Modifier.width(8.dp))
                    Text(text = """Здоровье: ${diceCount}d12 + Выносливость
Мана: ${diceCount}d8 + Интеллект""".trimIndent(),
                        fontSize = 13.sp,
                        lineHeight = 20.sp)
                }
                Text("Бросить кубики и восстановить автоматически?",
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp)

                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center){
                    Button(
                        onClick = {
                            var health: Int = 0
                            var mana: Int = 0
                            for(i in 1..diceCount){
                                health += Random.nextInt(12) + 1
                                mana += Random.nextInt(8) + 1
                            }
                            onRestClick(health, mana)},
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = button2),
                        shape = RoundedCornerShape(cornerRadius)
                    ) {
                        Text("Да")
                    }
                    Spacer(Modifier.width(10.dp))
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(containerColor = subButton),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(cornerRadius)
                    ) {
                        Text("Отмена")
                    }
                }
                Spacer(Modifier.height(5.dp))
            }
        }
    }
}

@Preview
@Composable
fun RestPrev(){
    CPTheme {
        RestDialog(1, { h, m -> }) { }
    }
}