package com.ocnyang.compose_loading

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ocnyang.compose_loading.transition.fractionTransition

private const val DEFAULT_SEGMENT_COUNT = 8

@Composable
fun InstaSpinner(
    modifier: Modifier = Modifier,
    durationMillis: Int = 1000,
    size: Dp = 40.dp,
    color: Color = MaterialTheme.colorScheme.primary,
    segmentCount: Int = DEFAULT_SEGMENT_COUNT
) {
    val transition = rememberInfiniteTransition(label = "InstaSpinner")

    val alphas = (0 until segmentCount).map { index ->
        transition.fractionTransition(
            initialValue = 1f,
            targetValue = 0.1f,
            durationMillis = durationMillis,
            offsetMillis = durationMillis / segmentCount * index,
            easing = EaseInOut
        )
    }

    Canvas(modifier = modifier.size(size)) {
        val rectSize = Size(width = this.size.width / 4, height = this.size.height / 24)
        val angleStep = 360f / segmentCount

        for (i in 0 until segmentCount) {
            rotate(angleStep * i) {
                drawRect(
                    color = color.copy(alpha = alphas[i].value),
                    topLeft = center + Offset(x = rectSize.width, y = 0f),
                    size = rectSize,
                    style = Stroke(
                        width = rectSize.height * 2,
                        pathEffect = PathEffect.cornerPathEffect(rectSize.height)
                    )
                )
            }
        }
    }
}
