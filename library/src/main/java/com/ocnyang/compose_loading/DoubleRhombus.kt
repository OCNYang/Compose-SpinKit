package com.ocnyang.compose_loading

import androidx.compose.animation.core.EaseInOutQuad
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ocnyang.compose_loading.transition.fractionTransition
import kotlin.math.sin

@Preview
@Composable
fun DoubleRhombus(
    size: Dp = 50.dp,
    modifier: Modifier = Modifier.size(size),
    durationMillis: Int = 2000,
    delayMillis: Int = 0,
    strokeWidth: Float = 8f,
    color: Color = MaterialTheme.colorScheme.primary,
    crossColor: Color = color.copy(alpha = 0.6f)
) {
    val transition = rememberInfiniteTransition(label = "")

    val sizePx = with(LocalDensity.current) {
        size.toPx()
    }

    val diagonal = ((sizePx - (sin(Math.toRadians(45.0) * strokeWidth * 4))) / 2).toFloat()

    val pointLeft = 0f to (sizePx / 2f)
    val pointTopLeft = (sizePx / 4f) to sizePx / 2f - diagonal / 2f
    val pointMid = (sizePx / 2f) to (sizePx / 2f)
    val pointBottomLeft = (sizePx / 4f) to sizePx / 2f + diagonal / 2f
    val pointTopRight = pointTopLeft.first + diagonal to pointTopLeft.second
    val pointRight = sizePx to sizePx / 2f
    val pointBottomRight = pointTopRight.first to pointBottomLeft.second


    val translateMultiplier = transition.fractionTransition(
        initialValue = 0f,
        targetValue = diagonal / 2,
        durationMillis = durationMillis / 2,
        delayMillis = delayMillis,
        repeatMode = RepeatMode.Reverse,
        easing = EaseInOutQuad
    )

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(size)) {
            drawPath(
                path = Path().apply {
                    moveTo(pointMid.first, pointMid.second - translateMultiplier.value)
                    lineTo(pointMid.first + translateMultiplier.value, pointMid.second)
                    lineTo(pointMid.first, pointMid.second + translateMultiplier.value)
                    lineTo(pointMid.first - translateMultiplier.value, pointMid.second)
                    close()
                },
                color = color.copy(alpha = 0.6f),
                style = Fill
            )

            translate(left = translateMultiplier.value) {
                drawPath(
                    path = Path().apply {
                        moveTo(pointLeft.first, pointLeft.second)
                        lineTo(pointTopLeft.first, pointTopLeft.second)
                        lineTo(pointMid.first, pointMid.second)
                        lineTo(pointBottomLeft.first, pointBottomLeft.second)
                        close()
                    },
                    color = color,
                    style = Stroke(width = strokeWidth)
                )
            }

            translate(left = -translateMultiplier.value)
            {
                drawPath(
                    path = Path().apply {
                        moveTo(pointMid.first, pointMid.second)
                        lineTo(pointTopRight.first, pointTopRight.second)
                        lineTo(pointRight.first, pointRight.second)
                        lineTo(pointBottomRight.first, pointBottomRight.second)
                        close()
                    },
                    color = color,
                    style = Stroke(width = strokeWidth)
                )
            }
        }
    }
}