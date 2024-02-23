package com.ocnyang.compose_loading

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ocnyang.compose_loading.transition.fractionTransition

@Preview(showBackground = true)
@Composable
fun Wave(
    size: Dp = 40.dp,
    modifier: Modifier = Modifier.size(size),
    durationMillis: Int = 1200,
    delayMillis: Int = 400,
    color: Color = MaterialTheme.colorScheme.primary,
    shape: Shape = RectangleShape,
) {
    val transition = rememberInfiniteTransition(label = "")

    val aspectRatio1 = transition.fractionTransition(
        initialValue = 0.25f,
        targetValue = 0.1f,
        durationMillis = durationMillis / 5,
        delayMillis = delayMillis,
        repeatMode = RepeatMode.Reverse,
        easing = EaseInOut
    )
    val aspectRatio2 = transition.fractionTransition(
        initialValue = 0.25f,
        targetValue = 0.1f,
        durationMillis = durationMillis / 5,
        delayMillis = delayMillis,
        offsetMillis = durationMillis / 12,
        repeatMode = RepeatMode.Reverse,
        easing = EaseInOut
    )
    val aspectRatio3 = transition.fractionTransition(
        initialValue = 0.25f,
        targetValue = 0.1f,
        durationMillis = durationMillis / 5,
        delayMillis = delayMillis,
        offsetMillis = durationMillis / 12 * 2,
        repeatMode = RepeatMode.Reverse,
        easing = EaseInOut
    )
    val aspectRatio4 = transition.fractionTransition(
        initialValue = 0.25f,
        targetValue = 0.1f,
        durationMillis = durationMillis / 5,
        delayMillis = delayMillis,
        offsetMillis = durationMillis / 12 * 3,
        repeatMode = RepeatMode.Reverse,
        easing = EaseInOut
    )
    val aspectRatio5 = transition.fractionTransition(
        initialValue = 0.25f,
        targetValue = 0.1f,
        durationMillis = durationMillis / 5,
        delayMillis = delayMillis,
        offsetMillis = durationMillis / 12 * 4,
        repeatMode = RepeatMode.Reverse,
        easing = EaseInOut
    )
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.width(size * 5 / 75))
        Surface(
            modifier = Modifier
                .size(size *8 / 75)
                .aspectRatio(aspectRatio1.value),
            color = color,
            shape = shape
        ) {}
        Spacer(modifier = Modifier.width(size * 5 / 75))
        Surface(
            modifier = Modifier
                .size(size *8 / 75)
                .aspectRatio(aspectRatio2.value),
            color = color,
            shape = shape
        ) {}
        Spacer(modifier = Modifier.width(size * 5 / 75))
        Surface(
            modifier = Modifier
                .size(size *8 / 75)
                .aspectRatio(aspectRatio3.value),
            color = color,
            shape = shape
        ) {}
        Spacer(modifier = Modifier.width(size * 5 / 75))
        Surface(
            modifier = Modifier
                .size(size *8 / 75)
                .aspectRatio(aspectRatio4.value),
            color = color,
            shape = shape
        ) {}
        Spacer(modifier = Modifier.width(size * 5 / 75))
        Surface(
            modifier = Modifier
                .size(size *8 / 75)
                .aspectRatio(aspectRatio5.value),
            color = color,
            shape = shape
        ) {}
        Spacer(modifier = Modifier.width(size * 5 / 75))
    }
}