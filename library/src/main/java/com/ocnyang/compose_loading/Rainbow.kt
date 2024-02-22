package com.ocnyang.compose_loading

import androidx.annotation.IntRange
import androidx.compose.animation.core.EaseInOutBack
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
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ocnyang.compose_loading.transition.fractionTransition

@Preview
@Composable
fun Rainbow(
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    durationMillis: Int = 2000,
    delayMillis: Int = 0,
    count: Int = 6,
    @IntRange(from = 1, to = 20) uniformityCoefficient: Int = 5,
    colors: List<Color> = List(count) { MaterialTheme.colorScheme.primary },
) {
    val transition = rememberInfiniteTransition(label = "")

    val angleMultipliers = (1..count).map {
        transition.fractionTransition(
            initialValue = 0f,
            targetValue = 360f,
            durationMillis = durationMillis,
            delayMillis = delayMillis,
            repeatMode = RepeatMode.Restart,
            easing = EaseInOutBack,
            offsetMillis = durationMillis / 10 / uniformityCoefficient * it
        )
    }

    Canvas(modifier = modifier.size(size)) {
        val strokeWidth = this.size.width.coerceAtMost(this.size.height) / ((count + 1) * 4)

        for ((index, angleMultiplier) in angleMultipliers.withIndex()) {
            withTransform({
                inset(strokeWidth * (index) * 2, strokeWidth * (index) * 2, strokeWidth * (index) * 2, strokeWidth * (index) * 2)
                rotate(degrees = angleMultiplier.value)
            }) {
                drawArc(
                    color = colors[index % colors.size],
                    startAngle = 0f,
                    sweepAngle = 180f,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
            }
        }
    }
}