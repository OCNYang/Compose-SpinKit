package com.ocnyang.compose_loading

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ocnyang.compose_loading.transition.fractionTransition

@Preview(showBackground = true)
@Composable
fun Windmill(
    modifier: Modifier = Modifier.size(40.dp),
    durationMillis: Int = 4000,
    delayMillis: Int = 0,
    colors: List<Color> = List(4) { MaterialTheme.colorScheme.primary },
) {
    val transition = rememberInfiniteTransition(label = "Windmill")

    val angleMultiplier = transition.fractionTransition(
        initialValue = 0f,
        targetValue = 360f,
        durationMillis = durationMillis / 4,
        delayMillis = delayMillis,
        repeatMode = RepeatMode.Restart,
        easing = EaseInOut
    )

    Canvas(modifier = modifier) {
        rotate(degrees = angleMultiplier.value) {
            drawArc(
                color = colors[0],
                startAngle = -90f,
                sweepAngle = 180f,
                useCenter = true,
                size = Size(size.width * .50f, size.height * .50f),
                topLeft = Offset(size.width * .25f, 0f)
            )

            drawArc(
                color = colors[1 % colors.size],
                startAngle = 0f,
                sweepAngle = 180f,
                useCenter = true,
                size = Size(size.width * .50f, size.height * .50f),
                topLeft = Offset(size.width * .50f, size.height * .25f)
            )

            drawArc(
                color = colors[2 % colors.size],
                startAngle = 0f,
                sweepAngle = -180f,
                useCenter = true,
                size = Size(size.width * .50f, size.height * .50f),
                topLeft = Offset(0f, size.height * .25f)
            )

            drawArc(
                color = colors[3 % colors.size],
                startAngle = 270f,
                sweepAngle = -180f,
                useCenter = true,
                size = Size(size.width * .50f, size.height * .50f),
                topLeft = Offset(size.width * .25f, size.height * .50f)
            )
        }

    }
}