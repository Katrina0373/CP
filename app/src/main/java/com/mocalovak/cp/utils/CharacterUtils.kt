package com.mocalovak.cp.utils

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter

fun loadImageFromAssets(context: Context, path: String?): Painter? {
    return try {
        if (path.isNullOrEmpty()) return null
        val inputStream = context.assets.open(path)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        BitmapPainter(bitmap.asImageBitmap())
    } catch (e: Exception) {
        null
    }
}
