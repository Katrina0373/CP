package com.mocalovak.cp.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.mocalovak.cp.ui.theme.ExpandedListBackColor
import com.mocalovak.cp.ui.theme.ExpandedListFocusedColor

@Composable
fun CustomDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    anchorBounds: Rect?, // координаты якоря, если нужно позиционировать
    options: List<String>,
    selectedOption: String,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 12.dp,
    backColor: Color,
    focusedItemColor: Color
) {
    if (expanded && anchorBounds != null) {
        val offsetX = anchorBounds.left.toInt()
        val offsetY = anchorBounds.bottom.toInt()

        Popup(
            alignment = Alignment.TopStart,
            onDismissRequest = onDismissRequest,
            properties = PopupProperties(focusable = true),
//            offset = IntOffset(
//                x = offsetX,
//                y = offsetY
//            )
        ) {
            // обертка для формы и тени
            Surface(
                shape = RoundedCornerShape(cornerRadius),
                color = backColor,
                shadowElevation = 8.dp,
                modifier = modifier
                    .padding(4.dp)
                    .clip(RoundedCornerShape(cornerRadius))
            ) {
                Column(modifier = Modifier.width(220.dp)) {
                    options.forEach { option ->
                        val bg = if (option == selectedOption)
                            focusedItemColor else Color.Transparent
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .background(bg)
                                .clickable {
                                    onSelect(option)
                                    onDismissRequest()
                                }
                                .padding(horizontal = 16.dp, vertical = 14.dp)
                        ) {
                            Text(option)
                        }
                    }
                }
            }

        }
    }
}
