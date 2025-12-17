package com.ocnyang.compose_loading

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ocnyang.compose_loading.transition.fractionTransition

@Composable
fun DoubleArc(
    modifier: Modifier = Modifier,
    size: Dp = 30.dp,
    durationMillis: Int = 2000,
    delayMillis: Int = 0,
    strokeWidthRatio: Float = 0.1f,
    color: Pair<Color, Color> = MaterialTheme.colorScheme.primary to MaterialTheme.colorScheme.primary,
) {
    val transition = rememberInfiniteTransition(label = "DoubleArc")

    val angleMultiplier1 = transition.fractionTransition(
        initialValue = 0f,
        targetValue = 360f,
        durationMillis = durationMillis / 4,
        delayMillis = delayMillis,
        repeatMode = RepeatMode.Restart,
        easing = EaseInOut
    )

    val angleMultiplier2 = transition.fractionTransition(
        initialValue = 0f,
        targetValue = 360f,
        durationMillis = durationMillis / 2,
        delayMillis = delayMillis,
        repeatMode = RepeatMode.Restart,
        easing = EaseInOut
    )

    Canvas(modifier = modifier.size(size)) {
        val strokeWidth = this.size.minDimension * strokeWidthRatio

        rotate(degrees = angleMultiplier2.value) {
            drawArc(
                color = color.first,
                startAngle = 0f,
                sweepAngle = 180f,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }

        withTransform({
            inset(strokeWidth * 2, strokeWidth * 2, strokeWidth * 2, strokeWidth * 2)
            rotate(degrees = angleMultiplier1.value)
        }) {
            drawArc(
                color = color.second,
                startAngle = 0f,
                sweepAngle = 60f,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }
    }
}