package com.ocnyang.compose_loading

import androidx.compose.animation.core.EaseInOutQuad
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ocnyang.compose_loading.transition.fractionTransition
import kotlin.math.sin

@Composable
fun DoubleRhombus(
    modifier: Modifier = Modifier,
    size: Dp = 50.dp,
    durationMillis: Int = 2000,
    delayMillis: Int = 0,
    strokeWidthRatio: Float = 0.04f,
    color: Color = MaterialTheme.colorScheme.primary,
    crossColor: Color = color.copy(alpha = 0.6f)
) {
    val transition = rememberInfiniteTransition(label = "DoubleRhombus")

    val density = LocalDensity.current
    val sizePx = remember(size, density) {
        with(density) { size.toPx() }
    }

    val strokeWidth = sizePx * strokeWidthRatio
    val diagonal = remember(sizePx, strokeWidth) {
        ((sizePx - (sin(Math.toRadians(45.0) * strokeWidth * 4))) / 2).toFloat()
    }

    val points = remember(sizePx, diagonal) {
        RhombusPoints(
            left = 0f to (sizePx / 2f),
            topLeft = (sizePx / 4f) to sizePx / 2f - diagonal / 2f,
            mid = (sizePx / 2f) to (sizePx / 2f),
            bottomLeft = (sizePx / 4f) to sizePx / 2f + diagonal / 2f,
            topRight = (sizePx / 4f + diagonal) to (sizePx / 2f - diagonal / 2f),
            right = sizePx to sizePx / 2f,
            bottomRight = (sizePx / 4f + diagonal) to (sizePx / 2f + diagonal / 2f)
        )
    }

    val translateMultiplier = transition.fractionTransition(
        initialValue = 0f,
        targetValue = diagonal / 2,
        durationMillis = durationMillis / 2,
        delayMillis = delayMillis,
        repeatMode = RepeatMode.Reverse,
        easing = EaseInOutQuad
    )

    Box(modifier = modifier.size(size), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(size)) {
            drawPath(
                path = Path().apply {
                    moveTo(points.mid.first, points.mid.second - translateMultiplier.value)
                    lineTo(points.mid.first + translateMultiplier.value, points.mid.second)
                    lineTo(points.mid.first, points.mid.second + translateMultiplier.value)
                    lineTo(points.mid.first - translateMultiplier.value, points.mid.second)
                    close()
                },
                color = crossColor,
                style = Fill
            )

            translate(left = translateMultiplier.value) {
                drawPath(
                    path = Path().apply {
                        moveTo(points.left.first, points.left.second)
                        lineTo(points.topLeft.first, points.topLeft.second)
                        lineTo(points.mid.first, points.mid.second)
                        lineTo(points.bottomLeft.first, points.bottomLeft.second)
                        close()
                    },
                    color = color,
                    style = Stroke(width = strokeWidth)
                )
            }

            translate(left = -translateMultiplier.value) {
                drawPath(
                    path = Path().apply {
                        moveTo(points.mid.first, points.mid.second)
                        lineTo(points.topRight.first, points.topRight.second)
                        lineTo(points.right.first, points.right.second)
                        lineTo(points.bottomRight.first, points.bottomRight.second)
                        close()
                    },
                    color = color,
                    style = Stroke(width = strokeWidth)
                )
            }
        }
    }
}

private data class RhombusPoints(
    val left: Pair<Float, Float>,
    val topLeft: Pair<Float, Float>,
    val mid: Pair<Float, Float>,
    val bottomLeft: Pair<Float, Float>,
    val topRight: Pair<Float, Float>,
    val right: Pair<Float, Float>,
    val bottomRight: Pair<Float, Float>
)
