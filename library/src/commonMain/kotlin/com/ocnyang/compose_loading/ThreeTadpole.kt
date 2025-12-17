package com.ocnyang.compose_loading

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ocnyang.compose_loading.transition.fractionTransition

private const val DEFAULT_TADPOLE_COUNT = 3

@Composable
fun ThreeTadpole(
    modifier: Modifier = Modifier,
    size: Dp = 30.dp,
    durationMillis: Int = 2000,
    delayMillis: Int = 0,
    strokeWidthRatio: Float = 0.13f,
    tadpoleCount: Int = DEFAULT_TADPOLE_COUNT,
    headColor: Color = MaterialTheme.colorScheme.primary,
    tailColors: List<Color> = listOf(
        headColor.copy(alpha = 0.25f),
        headColor.copy(alpha = 0.5f),
        headColor.copy(alpha = 0.75f),
    ),
) {
    val transition = rememberInfiniteTransition(label = "ThreeTadpole")

    val angleMultiplier = transition.fractionTransition(
        initialValue = 0f,
        targetValue = 360f,
        durationMillis = durationMillis / 2,
        delayMillis = delayMillis,
        repeatMode = RepeatMode.Restart,
        easing = EaseInOut
    )

    Canvas(modifier = modifier.size(size)) {
        val strokeWidth = this.size.minDimension * strokeWidthRatio
        val angleStep = 360f / tadpoleCount

        for (i in 0 until tadpoleCount) {
            rotate(degrees = angleMultiplier.value + angleStep * i) {
                // Draw tail segments
                tailColors.forEachIndexed { index, color ->
                    drawArc(
                        color = color,
                        startAngle = 210f + index * 20f,
                        sweepAngle = 20f,
                        useCenter = false,
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )
                }
                // Draw head
                drawCircle(
                    color = headColor,
                    radius = strokeWidth,
                    center = Offset(x = this.center.x, y = strokeWidth / 2)
                )
            }
        }
    }
}
