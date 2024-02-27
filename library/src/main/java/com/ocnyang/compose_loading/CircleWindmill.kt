package com.ocnyang.compose_loading

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ocnyang.compose_loading.transition.fractionTransition
import kotlin.math.cos
import kotlin.math.sin

private const val DOTS_COUNT = 6

@Preview(showBackground = true)
@Composable
fun CircleWindmill(
    modifier: Modifier = Modifier,
    durationMillis: Int = 2000,
    delayMillis: Int = 0,
    delayBetweenDotsMillis: Int = durationMillis * 4 / 6 / 3 / 6,//默认值来历（大约值）：总循环时间(durationMillis*4)/总循环圈数(6)/球发布在圈的比例一半(3)/球数(6)
    size: Dp = 40.dp,
    circleRatio: Float = 0.25f,
    dotsCount: Int = DOTS_COUNT,
    colors: List<Color> = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow),
) {
    val transition = rememberInfiniteTransition(label = "")

    val angleMultiplier = transition.fractionTransition(
        initialValue = 0f,
        targetValue = 360f,
        durationMillis = durationMillis,
        delayMillis = delayMillis,
        repeatMode = RepeatMode.Restart,
        easing = LinearEasing
    )

    val oneAngle = 360f / dotsCount

    Canvas(modifier = modifier.size(size).rotate(angleMultiplier.value)) {
        for (i in 0 until dotsCount) {
            rotate(degrees = oneAngle * i) {
                drawArc(color = colors[i % colors.size], startAngle = -90f, sweepAngle = 60f, useCenter = true, style = Fill)


            }

            val y = center.y - (cos(Math.toRadians(oneAngle.toDouble())) * center.y).toFloat()
            val x = center.x + (sin(Math.toRadians(oneAngle.toDouble())) * (center.y)).toFloat()

            rotate(degrees = oneAngle * (i - 1)) {
                drawPath(
                    path = Path().apply {
                        moveTo(center.x, center.y)
                        quadraticBezierTo(x, center.y, x, y)
                        lineTo(center.x, center.y)
                        close()
                    },
                    color = colors[((i + dotsCount - 1) % dotsCount) % colors.size],
                    style = Fill
                )
            }
        }
    }
}