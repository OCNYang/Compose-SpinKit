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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ocnyang.compose_loading.transition.fractionTransition

@Composable
fun ArcInRing(
    modifier: Modifier = Modifier,
    size: Dp = 30.dp,
    durationMillis: Int = 2000,
    delayMillis: Int = 0,
    strokeWidthRatio: Float = 0.1f,
    ringColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
    arcColor: Color = MaterialTheme.colorScheme.primary
) {
    val transition = rememberInfiniteTransition(label = "ArcInRing")

    val angleMultiplier = transition.fractionTransition(
        initialValue = 0f, targetValue = 360f,
        durationMillis = durationMillis / 2,
        delayMillis = delayMillis / 2,
        repeatMode = RepeatMode.Restart,
        easing = EaseInOut
    )

    Canvas(modifier = modifier.size(size)) {
        val strokeWidth = this.size.minDimension * strokeWidthRatio

        drawArc(
            color = ringColor,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )
        rotate(degrees = angleMultiplier.value) {
            drawArc(
                color = arcColor,
                startAngle = 0f,
                sweepAngle = 30f,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }
    }
}
