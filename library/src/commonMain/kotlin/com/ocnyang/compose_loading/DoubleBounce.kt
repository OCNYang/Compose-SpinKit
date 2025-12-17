package com.ocnyang.compose_loading

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.ocnyang.compose_loading.transition.fractionTransition

@Composable
fun DoubleBounce(
    size: DpSize = DpSize(40.dp, 40.dp),
    modifier: Modifier = Modifier.size(size),
    durationMillis: Int = 2000,
    delayMillis: Int = 0,
    color: Color = MaterialTheme.colorScheme.primary,
    shape: Shape = CircleShape
) {
    val transition = rememberInfiniteTransition(label = "")

    val sizeMultiplier1 = transition.fractionTransition(
        initialValue = 0f,
        targetValue = 1f,
        durationMillis = durationMillis / 2,
        delayMillis = delayMillis,
        repeatMode = RepeatMode.Reverse,
        easing = EaseInOut
    )

    val alphaMultiplier1 = transition.fractionTransition(
        initialValue = 1f,
        targetValue = 0.5f,
        durationMillis = durationMillis / 2,
        delayMillis = delayMillis,
        repeatMode = RepeatMode.Reverse,
        easing = EaseInOut
    )

    val sizeMultiplier2 = transition.fractionTransition(
        initialValue = 0f,
        targetValue = 1f,
        durationMillis = durationMillis / 2,
        delayMillis = delayMillis,
        offsetMillis = durationMillis / 2,
        repeatMode = RepeatMode.Reverse,
        easing = EaseInOut
    )

    val alphaMultiplier2 = transition.fractionTransition(
        initialValue = 1f,
        targetValue = 0.5f,
        durationMillis = durationMillis / 2,
        delayMillis = delayMillis,
        offsetMillis = durationMillis / 2,
        repeatMode = RepeatMode.Reverse,
        easing = EaseInOut
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier.size(size * sizeMultiplier1.value)
                .background(color = color.copy(alphaMultiplier1.value), shape = shape),
        )
        Box(
            modifier = Modifier.size(size * sizeMultiplier2.value)
                .background(color = color.copy(alphaMultiplier2.value), shape = shape),
        )
    }
}