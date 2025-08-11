package com.mocalovak.cp.presentation.HomePage.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mocalovak.cp.R

@Composable
fun TelegramRef(modifier: Modifier = Modifier){
    val context = LocalContext.current

    Card(onClick = {
        val telegramIntent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("https://t.me/your_channel") // Ссылка на канал или чат
                 )
        context.startActivity(telegramIntent) },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF171E48) // фон карточки
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)){
        Row(modifier = Modifier
            .padding(12.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Column() {
                Text("Наш Telegram", color = Color.White)
                Text("Новости и вопросы авторам", color = MaterialTheme.colorScheme.onSurface)
            }
            Image(painter = painterResource(R.drawable.fly),
                contentDescription = "Telegram",
                modifier = Modifier
                    .size(55.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF1D254D))
                    .padding(6.dp))
        }
    }
}

@Preview
@Composable
fun tRefPrev(){
    TelegramRef(modifier = Modifier)
}