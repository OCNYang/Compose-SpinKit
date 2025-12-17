package com.ocnyang.compose_loading

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ocnyang.compose_loading.transition.fractionTransition

private const val DEFAULT_DOT_COUNT = 4

/**
 * 跳动的点动画
 *
 * 多个圆点排成一行，从第一个圆点开始，每个圆点依次向上移动同时放大，
 * 到达一定高度后，又迅速向初始状态恢复（下移到初始位置的同时缩小到初始大小）。
 *
 * @param modifier Modifier
 * @param size 整体尺寸
 * @param durationMillis 单个圆点完成一次跳动的时间
 * @param delayBetweenDotsMillis 相邻圆点之间的动画延迟
 * @param dotCount 圆点数量 (建议 4~6 个)
 * @param jumpHeightRatio 跳动高度相对于 size 的比例
 * @param scaleMultiplier 放大倍数 (1.0 表示不放大)
 * @param colors 圆点颜色列表，会循环使用
 */
@Composable
fun BouncingDots(
    modifier: Modifier = Modifier,
    size: Dp = 60.dp,
    durationMillis: Int = 600,
    delayBetweenDotsMillis: Int = 100,
    dotCount: Int = DEFAULT_DOT_COUNT,
    jumpHeightRatio: Float = 0.35f,
    scaleMultiplier: Float = 1.5f,
    colors: List<Color> = List(dotCount) { MaterialTheme.colorScheme.primary },
) {
    val transition = rememberInfiniteTransition(label = "BouncingDots")

    // 每个圆点的跳动动画（0->1 表示从初始状态到最高点）
    val dotAnimations = (0 until dotCount).map { index ->
        transition.fractionTransition(
            initialValue = 0f,
            targetValue = 1f,
            durationMillis = durationMillis,
            offsetMillis = delayBetweenDotsMillis * index,
            repeatMode = RepeatMode.Reverse,
            easing = EaseOutCubic
        )
    }

    Canvas(modifier = modifier.size(size)) {
        val canvasWidth = this.size.width
        val canvasHeight = this.size.height

        // 计算圆点基础半径和间距
        // 放大后圆点之间保留小间隙（间隙为放大后半径的 20%）
        val gapRatio = 0.2f
        val maxRadius = canvasWidth / (dotCount * 2 * scaleMultiplier + (dotCount - 1) * gapRatio * scaleMultiplier + gapRatio * scaleMultiplier * 2)
        val baseRadius = maxRadius / scaleMultiplier
        val gap = maxRadius * gapRatio

        // 计算所有圆点的总宽度，用于居中
        val totalWidth = dotCount * maxRadius * 2 + (dotCount - 1) * gap
        val startX = (canvasWidth - totalWidth) / 2 + maxRadius

        val jumpHeight = canvasHeight * jumpHeightRatio

        // 圆点的垂直基准位置（底部偏上一点，留出跳动空间）
        val baseY = canvasHeight * 0.7f

        for (i in 0 until dotCount) {
            val animValue = dotAnimations[i].value

            // 计算当前圆点的位置和大小
            val currentRadius = baseRadius * (1f + (scaleMultiplier - 1f) * animValue)
            val currentY = baseY - (jumpHeight * animValue)
            // 每个圆点的 X 位置（从居中起点开始）
            val currentX = startX + i * (maxRadius * 2 + gap)

            drawCircle(
                color = colors[i % colors.size],
                radius = currentRadius,
                center = Offset(currentX, currentY)
            )
        }
    }
}
