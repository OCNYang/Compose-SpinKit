package com.ocnyang.compose_loading

import androidx.compose.animation.core.LinearEasing
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ocnyang.compose_loading.transition.fractionTransition

/**
 * todo
 * 颜色自定义
 * 数量自定义
 * easing 自定义
 */
@Preview
@Composable
fun OppositeArc(
    modifier: Modifier = Modifier.size(40.dp),
    durationMillis: Int = 2000,
    delayMillis: Int = 0,
    strokeWidth: Float = 8f,
    colors: List<Color> = List(3) { MaterialTheme.colorScheme.primary },
) {
    val transition = rememberInfiniteTransition(label = "")

    val angleMultiplier1 = transition.fractionTransition(
        initialValue = 360f,
        targetValue = 0f,
        durationMillis = durationMillis / 2,
        delayMillis = delayMillis,
        repeatMode = RepeatMode.Restart,
        easing = LinearEasing
    )

    val angleMultiplier2 = transition.fractionTransition(
        initialValue = 0f,
        targetValue = 360f,
        durationMillis = durationMillis / 2,
        delayMillis = delayMillis,
        repeatMode = RepeatMode.Restart,
        easing = LinearEasing
    )

    Canvas(modifier = modifier) {
        rotate(degrees = angleMultiplier2.value) {
            drawArc(
                color = colors[0],
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
                color = colors[1 % colors.size],
                startAngle = 0f,
                sweepAngle = 180f,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }

        withTransform({
            inset(strokeWidth * 4, strokeWidth * 4, strokeWidth * 4, strokeWidth * 4)
            rotate(degrees = angleMultiplier2.value)
        }) {
            drawArc(
                color = colors[2 % colors.size],
                startAngle = 0f,
                sweepAngle = 180f,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }
    }
}