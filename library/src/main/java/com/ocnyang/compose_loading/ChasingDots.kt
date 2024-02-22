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

/**
 * 在一定的时间内，每个球循环 6 圈；每个球的移动的速率不同，
 */

private const val DOTS_COUNT = 6

@Preview(showBackground = true)
@Composable
fun ChasingDots(
    modifier: Modifier = Modifier,
    durationMillis: Int = 2000,
    delayBetweenDotsMillis: Int = durationMillis * 4 / 6 / 3 / 6,//默认值来历（大约值）：总循环时间(durationMillis*4)/总循环圈数(6)/球发布在圈的比例一半(3)/球数(6)
    size: Dp = 40.dp,
    color: Color = MaterialTheme.colorScheme.primary,
    circleRatio: Float = 0.25f,
    dotsCount: Int = DOTS_COUNT
) {
    val transition = rememberInfiniteTransition(label = "")

    val pathAngleMultipliers = (1..dotsCount).map {
        transition.fractionTransition(
            initialValue = 0f,
            targetValue = 7f,
            fraction = 4,
            durationMillis = durationMillis * 4,
            offsetMillis = delayBetweenDotsMillis * (it - 1),
            easing = EaseInOut
        )
    }

    Canvas(modifier = modifier.size(size)) {
        val pathRadius = (this.size.height / 2)
        val radius = this.size.height / dotsCount
        val radiusCommon = radius * circleRatio

        for ((index, pathAngleMultiplier) in pathAngleMultipliers.withIndex()) {
            val angle = (pathAngleMultiplier.value * 360.0)
            val offsetX = -(pathRadius * sin(Math.toRadians(angle))).toFloat() + (this.size.width / 2)
            val offsetY = (pathRadius * cos(Math.toRadians(angle))).toFloat() + (this.size.height / 2)
            drawCircle(
                color = color,
                radius = radiusCommon * (1 + circleRatio * index),
                center = Offset(offsetX, offsetY)
            )
        }
    }
}