package com.mocalovak.cp.presentation.Character

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mocalovak.cp.R
import com.mocalovak.cp.domain.model.Character
import com.mocalovak.cp.domain.model.Equipment
import com.mocalovak.cp.ui.theme.halfAppWhite
import com.mocalovak.cp.ui.theme.topContainer

@Composable
fun DiceChecking(onDismiss: () -> Unit,
                 character: Character,
                 equipment: Equipment) {

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp),
        contentAlignment = Alignment.TopEnd) {
        Icon(imageVector = Icons.Default.Close,
            contentDescription = "close",
            tint = halfAppWhite,
            modifier = Modifier.clip(CircleShape)
                .clickable { onDismiss() })
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = topContainer, shape = RoundedCornerShape(cornerRadius))
        .padding(horizontal = 7.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)) {



    }

}