package com.ocnyang.compose_loading

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

/**
 * 半圆小球传送动画
 *
 * 一个实线半圆弧以较慢速度逆时针旋转，同时小球从半圆弧末端出发，
 * 沿另一半圆弧顺时针飞到半圆弧起点，形成循环传送效果。
 *
 * @param modifier Modifier
 * @param size 整体尺寸
 * @param durationMillis 小球完成一次飞行的时间（半圆弧旋转一个小球宽度的时间）
 * @param color 颜色
 * @param strokeWidthRatio 线宽相对于尺寸的比例（也决定小球大小）
 */
@Preview(showBackground = true)
@Composable
fun SemicircleBallTransfer(
    modifier: Modifier = Modifier,
    size: Dp = 60.dp,
    durationMillis: Int = 600,
    color: Color = MaterialTheme.colorScheme.primary,
    strokeWidthRatio: Float = 0.15f,
) {
    val transition = rememberInfiniteTransition(label = "SemicircleBallTransfer")

    // 动画进度 (0 -> 1)，持续累加不重置
    // 使用较大的目标值让动画持续运行
    val progress = transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,  // 足够大的值，让动画持续旋转
        label = "progress",
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis * 360, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Canvas(modifier = modifier.size(size)) {
        val canvasSize = this.size.width
        val strokeWidth = canvasSize * strokeWidthRatio
        val radius = (canvasSize - strokeWidth) / 2
        val ballRadius = strokeWidth / 2  // 小球直径 = 线宽

        // 小球宽度对应的角度（弧度转角度）
        val ballAngleDegrees = (strokeWidth / radius) * (180f / Math.PI.toFloat())

        val currentProgress = progress.value

        // 半圆弧累计旋转角度（速度A：每个周期逆时针旋转一个小球宽度）
        // currentProgress 每增加 1，半圆弧旋转 ballAngleDegrees
        val arcRotation = -ballAngleDegrees * currentProgress

        // 半圆弧的起始角度和扫过角度
        val arcStartAngle = 0f + arcRotation
        val arcSweepAngle = 180f

        // 绘制半圆弧
        drawArc(
            color = color,
            startAngle = arcStartAngle,
            sweepAngle = arcSweepAngle,
            useCenter = false,
            topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
            size = Size(radius * 2, radius * 2),
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )

        // 小球的飞行角度（速度B：每个周期顺时针旋转180°）
        // 使用 currentProgress 的小数部分来计算当前周期内的飞行进度
        val cycleProgress = currentProgress % 1f
        val ballStartAngle = arcStartAngle + arcSweepAngle  // 半圆弧末端
        val ballFlyAngle = 180f * cycleProgress  // 顺时针飞行的角度
        val ballAngle = ballStartAngle + ballFlyAngle

        val ballAngleRad = Math.toRadians(ballAngle.toDouble())
        val ballX = center.x + radius * cos(ballAngleRad).toFloat()
        val ballY = center.y + radius * sin(ballAngleRad).toFloat()

        drawCircle(
            color = color,
            radius = ballRadius,
            center = Offset(ballX, ballY)
        )
    }
}
