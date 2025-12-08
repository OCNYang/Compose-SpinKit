package com.ocnyang.compose_loading

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ocnyang.compose_loading.transition.fractionTransition
import kotlin.math.cos
import kotlin.math.sin

private const val DEFAULT_CIRCLE_COUNT = 12

@Preview(showBackground = true)
@Composable
fun FadingCircle(
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    durationMillis: Int = 1200,
    color: Color = MaterialTheme.colorScheme.primary,
    circleCount: Int = DEFAULT_CIRCLE_COUNT,
    circleSizeRatio: Float = 1.0f
) {
    val transition = rememberInfiniteTransition(label = "FadingCircle")

    val durationPerFraction = durationMillis / 2

    val circleAlphas = (0 until circleCount).map { index ->
        transition.fractionTransition(
            initialValue = 1f,
            targetValue = 0f,
            durationMillis = durationPerFraction,
            delayMillis = durationPerFraction,
            offsetMillis = durationPerFraction / (circleCount / 2) * (index + 1),
            easing = EaseInOut
        )
    }

    Canvas(modifier = modifier.size(size)) {
        val pathRadius = this.size.height / 2
        val radius = this.size.height / circleCount * circleSizeRatio

        for (i in 0 until circleCount) {
            val angle = i.toDouble() / circleCount * 360.0
            val offsetX = -(pathRadius * sin(Math.toRadians(angle))).toFloat() + pathRadius
            val offsetY = (pathRadius * cos(Math.toRadians(angle))).toFloat() + pathRadius

            drawCircle(
                color = color,
                radius = radius,
                center = Offset(offsetX, offsetY),
                alpha = circleAlphas[i].value
            )
        }
    }
}
