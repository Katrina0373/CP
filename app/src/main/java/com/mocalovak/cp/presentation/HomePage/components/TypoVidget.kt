package com.mocalovak.cp.presentation.HomePage.components

import android.graphics.Picture
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mocalovak.cp.R
import com.mocalovak.cp.ui.theme.button2
import com.mocalovak.cp.ui.theme.halfAppWhite

@Composable
fun TypoVidget(picture: Painter, title:String, description:String, buttonText:String){
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .width(200.dp)
            .height(230.dp)
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = button2)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            // Снизу текст и кнопка
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Image(
                    painter = picture,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(80.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(color = Color.White),
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall.copy(color = halfAppWhite),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Box(modifier = Modifier.fillMaxSize().align(Alignment.End)) {
                    Button(
                        onClick = { /* действие */ },
                        modifier = Modifier.size(100.dp, 35.dp).align(Alignment.BottomStart),
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                    ) {
                        Text(text = buttonText, color = button2)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun prevVid(){
    TypoVidget(picture = painterResource(R.drawable.heroes),
        title = "Книга правил",
        description = "Общие понятия, описания",
        buttonText = "Читать"
    )
}