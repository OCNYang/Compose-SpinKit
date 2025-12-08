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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ocnyang.compose_loading.transition.fractionTransition

private const val DEFAULT_ARC_COUNT = 3

@Preview
@Composable
fun OppositeArc(
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    durationMillis: Int = 2000,
    delayMillis: Int = 0,
    strokeWidthRatio: Float = 0.08f,
    arcCount: Int = DEFAULT_ARC_COUNT,
    colors: List<Color> = List(arcCount) { MaterialTheme.colorScheme.primary },
) {
    val transition = rememberInfiniteTransition(label = "OppositeArc")

    val angleMultipliers = (0 until arcCount).map { index ->
        val isClockwise = index % 2 == 0
        transition.fractionTransition(
            initialValue = if (isClockwise) 0f else 360f,
            targetValue = if (isClockwise) 360f else 0f,
            durationMillis = durationMillis / 2,
            delayMillis = delayMillis,
            repeatMode = RepeatMode.Restart,
            easing = LinearEasing
        )
    }

    Canvas(modifier = modifier.size(size)) {
        val strokeWidth = this.size.minDimension * strokeWidthRatio

        for (i in 0 until arcCount) {
            val insetValue = strokeWidth * 2 * i
            withTransform({
                inset(insetValue, insetValue, insetValue, insetValue)
                rotate(degrees = angleMultipliers[i].value)
            }) {
                drawArc(
                    color = colors[i % colors.size],
                    startAngle = 0f,
                    sweepAngle = 180f,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
            }
        }
    }
}
