package com.mocalovak.cp.utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mocalovak.cp.R
import com.mocalovak.cp.presentation.Character.cornerRadius
import com.mocalovak.cp.ui.theme.ErrorRed
import com.mocalovak.cp.ui.theme.otherContainer
import com.mocalovak.cp.ui.theme.textColor
import com.mocalovak.cp.utils.ToastState.toastType
import kotlinx.coroutines.delay

enum class ToastType(val backcolor: Color, val icon: Int) {
    ERROR(ErrorRed, R.drawable.info_ic),
    INFO(otherContainer, R.drawable.done_ic)
}

object ToastState {
    var showToast by mutableStateOf(false)
    var message by mutableStateOf("")
    var toastType by mutableStateOf(ToastType.INFO)

    fun show(message: String, type: ToastType = ToastType.INFO) {
        this.message = message
        this.toastType = type
        showToast = true
    }

    fun hide() {
        showToast = false
    }
}

@Composable
fun CustomToastHost(
    modifier: Modifier = Modifier,
    durationMillis: Long = 1500L
) {
    if (ToastState.showToast) {
        // авто-скрытие после задержки
        LaunchedEffect(ToastState.message) {
            delay(durationMillis)
            ToastState.hide()
        }

        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            AnimatedVisibility(
                visible = ToastState.showToast,
                enter = fadeIn() + slideInVertically { it / 2 },
                exit = fadeOut() + slideOutVertically { it / 2 }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 30.dp)
                        .background(
                            ToastState.toastType.backcolor,
                            RoundedCornerShape(cornerRadius)
                        )
                        .padding(horizontal = 10.dp, vertical = 15.dp)
                ) {
                    Icon(
                        painter = painterResource(ToastState.toastType.icon),
                        contentDescription = null,
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(18.dp))
                    Text(
                        text = ToastState.message,
                        color = Color.White
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun toastPrev(){
    //CustomToastHost("Этот слот уже используется", toastType = ToastType.ERROR)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .background(toastType.backcolor, RoundedCornerShape(cornerRadius))
            .padding(horizontal = 16.dp, vertical = 15.dp)
    ) {
        Icon(
            painter = painterResource(toastType.icon),
            contentDescription = null,
            tint = textColor,
            //modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(18.dp))
        Text(
            text = "Предмет добавлен в багаж",
            color = textColor,
            //fontSize = 14.sp
        )
    }
}