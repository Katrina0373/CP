package com.mocalovak.cp.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun GradientButton(
    gradientColors: List<Color>,
    cornerRadius: Dp,
    contentButton:  @Composable() (BoxScope.() -> Unit),
    roundedCornerShape: Shape,
    isEnabled: Boolean = true,
    hasRipple: Boolean = true,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    (if (!hasRipple) remember { MutableInteractionSource() } else null)?.let {
        Button(
        modifier = Modifier
            .fillMaxWidth()
            //.padding(start = 32.dp, end = 32.dp),
            .then(modifier),
        onClick = onClick,
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Color.Gray
        ),
        shape = roundedCornerShape,
        enabled = isEnabled,
        interactionSource = it
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(colors = gradientColors),
                    shape = roundedCornerShape
                )
                .then(modifier),
            contentAlignment = Alignment.Center,
            content = contentButton
        )
    }
    }
}