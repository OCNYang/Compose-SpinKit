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
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ocnyang.compose_loading.transition.fractionTransition

@Preview
@Composable
fun DoubleSector(
    modifier: Modifier = Modifier,
    size: Dp = 30.dp,
    durationMillis: Int = 1000,
    delayMillis: Int = 0,
    strokeWidth: Float = 10f,
    count: Int = 4,
    colors: List<Color> = List(count) { MaterialTheme.colorScheme.primary },
) {
    val transition = rememberInfiniteTransition(label = "")

    val angleMultiplier = transition.fractionTransition(
        initialValue = 0f,
        targetValue = 360f * 3,
        durationMillis = durationMillis,
        delayMillis = delayMillis,
        repeatMode = RepeatMode.Restart,
        easing = EaseInOut
    )

    Canvas(modifier = modifier.size(size)) {
        rotate(degrees = angleMultiplier.value) {
            for (i in 0 until count) {
                drawArc(
                    color = colors[i % colors.size],
                    startAngle = i * (360f / count),
                    sweepAngle = 360f / count / 2,
                    useCenter = true,
                    style = Fill
                )
            }
        }
    }
}