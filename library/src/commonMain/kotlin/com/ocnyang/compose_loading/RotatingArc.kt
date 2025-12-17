package com.ocnyang.compose_loading

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 旋转圆弧动画
 *
 * 一个圆点围绕中心逆时针旋转，同时从圆点拉伸成圆弧，
 * 到达最长状态后保持旋转，最后收缩回圆点。
 *
 * @param modifier Modifier
 * @param size 整体尺寸
 * @param durationMillis 完成一圈旋转的时间
 * @param color 圆弧颜色
 * @param strokeWidthRatio 线宽相对于尺寸的比例
 * @param maxArcRatio 圆弧最大长度占圆周的比例（默认 1/3）
 */
@Composable
fun RotatingArc(
    modifier: Modifier = Modifier,
    size: Dp = 60.dp,
    durationMillis: Int = 2000,
    color: Color = MaterialTheme.colorScheme.primary,
    strokeWidthRatio: Float = 0.08f,
    maxArcRatio: Float = 1f / 3f,
) {
    val transition = rememberInfiniteTransition(label = "RotatingArc")

    // 圆弧展开/收缩进度 (0~1~0)
    val arcSweep = transition.arcSweepTransition(durationMillis = durationMillis)

    // 头部旋转角度
    // 展开阶段：头部慢速旋转
    // 保持阶段：头部加速旋转
    // 收缩阶段：头部慢速旋转
    val headRotation = transition.animateFloat(
        initialValue = 0f,
        targetValue = -360f,
        label = "headRotation",
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                this.durationMillis = durationMillis
                0f at 0 using LinearEasing
                0f at (durationMillis / 3) using FastOutSlowInEasing    // 前1/3：头部不动
                -240f at (durationMillis * 2 / 3) using LinearEasing    // 中间1/3：加速旋转240°
                -360f at durationMillis using LinearEasing              // 后1/3：线性旋转120°

            },
            repeatMode = RepeatMode.Restart
        )
    )

    Canvas(modifier = modifier.size(size)) {
        val canvasSize = this.size.width
        val strokeWidth = canvasSize * strokeWidthRatio
        val radius = (canvasSize - strokeWidth) / 2

        // 圆弧的最大角度
        val maxSweepAngle = 360f * maxArcRatio

        // arcSweep.value 直接表示当前圆弧长度比例 (0~1)
        val sweepAngle = arcSweep.value * maxSweepAngle

        // 头部角度（从顶部开始，-90°）
        val startAngle = headRotation.value - 90f

        // 绘制圆弧（或圆点）
        val inset = strokeWidth / 2
        if (sweepAngle < 1f) {
            // 当 sweep 很小时，画一个圆点
            val dotAngleRad = Math.toRadians((startAngle).toDouble())
            val dotX = center.x + radius * kotlin.math.cos(dotAngleRad).toFloat()
            val dotY = center.y + radius * kotlin.math.sin(dotAngleRad).toFloat()
            drawCircle(
                color = color,
                radius = strokeWidth / 2,
                center = Offset(dotX, dotY)
            )
        } else {
            drawArc(
                color = color,
                startAngle = startAngle,
                sweepAngle = -sweepAngle,  // 负值表示逆时针方向延伸
                useCenter = false,
                topLeft = Offset(inset, inset),
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }
    }
}

/**
 * 圆弧展开/收缩动画
 * 前1/3: 0 -> 1 (展开)
 * 中1/3: 保持 1
 * 后1/3: 1 -> 0 (收缩)
 */
@Composable
private fun InfiniteTransition.arcSweepTransition(
    durationMillis: Int
): State<Float> = animateFloat(
    initialValue = 0f,
    targetValue = 0f,
    label = "arcSweep",
    animationSpec = infiniteRepeatable(
        animation = keyframes {
            this.durationMillis = durationMillis
            0f at 0 using LinearEasing
            1f at (durationMillis / 3) using LinearEasing
            1f at (durationMillis * 2 / 3) using LinearEasing
            0f at durationMillis using LinearEasing
        },
        repeatMode = RepeatMode.Restart,
        initialStartOffset = StartOffset(0)
    )
)
