package com.ocnyang.compose_loading

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ocnyang.compose_loading.transition.fractionTransition

@Preview
@Composable
fun ThreeTadpole(
    modifier: Modifier = Modifier.size(30.dp),
    durationMillis: Int = 2000,
    delayMillis: Int = 0,
    strokeWidth: Float = 8f,
    headColor: Color = MaterialTheme.colorScheme.primary,
    tailColor: Triple<Color,Color,Color> = Triple(
        headColor.copy(alpha = 0.25f),
        headColor.copy(alpha = 0.5f),
        headColor.copy(alpha = 0.75f),
    ),
) {
    val transition = rememberInfiniteTransition(label = "")

    val angleMultiplier = transition.fractionTransition(
        initialValue = 0f,
        targetValue = 360f,
        durationMillis = durationMillis / 2,
        delayMillis = delayMillis,
        repeatMode = RepeatMode.Restart,
        easing = EaseInOut
    )

    Canvas(modifier = modifier) {
        rotate(degrees = angleMultiplier.value) {
            drawArc(
                color = tailColor.first,
                startAngle = 210f,
                sweepAngle = 20f,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
            drawArc(
                color = tailColor.second,
                startAngle = 230f,
                sweepAngle = 20f,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
            drawArc(
                color = tailColor.third,
                startAngle = 250f,
                sweepAngle = 20f,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
            drawCircle(
                color = headColor, radius = strokeWidth,
                center = Offset(x = this.center.x, y = strokeWidth / 2)
            )
        }
        rotate(degrees = angleMultiplier.value + 120) {
            drawArc(
                color = tailColor.first,
                startAngle = 210f,
                sweepAngle = 20f,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
            drawArc(
                color = tailColor.second,
                startAngle = 230f,
                sweepAngle = 20f,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
            drawArc(
                color = tailColor.third,
                startAngle = 250f,
                sweepAngle = 20f,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
            drawCircle(
                color = headColor, radius = strokeWidth,
                center = Offset(x = this.center.x, y = strokeWidth / 2)
            )
        }
        rotate(degrees = angleMultiplier.value + 240) {
            drawArc(
                color = tailColor.first,
                startAngle = 210f,
                sweepAngle = 20f,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
            drawArc(
                color = tailColor.second,
                startAngle = 230f,
                sweepAngle = 20f,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
            drawArc(
                color = tailColor.third,
                startAngle = 250f,
                sweepAngle = 20f,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
            drawCircle(
                color = headColor, radius = strokeWidth,
                center = Offset(x = this.center.x, y = strokeWidth / 2)
            )
        }
    }
}

