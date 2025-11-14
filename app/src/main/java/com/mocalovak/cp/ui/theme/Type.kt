package com.mocalovak.cp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mocalovak.cp.R

val letterTextStyle = TextStyle(
    fontFamily = FontFamily(
        Font(R.font.raleway_medium, weight = FontWeight.Normal),
        Font(R.font.raleway_mediumitalic, weight = FontWeight.Normal, style = FontStyle.Italic),
    ),
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    letterSpacing = 0.5.sp,
    lineHeight = 20.sp,
    color = Color.White
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = letterTextStyle,
    bodyMedium = letterTextStyle,
    labelMedium = letterTextStyle,
titleLarge = letterTextStyle.copy(fontSize = 22.sp, lineHeight = 28.sp, letterSpacing = 0.sp),
    titleMedium = letterTextStyle.copy(fontSize = 20.sp)
//    bodyLarge = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp
//    ),
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)