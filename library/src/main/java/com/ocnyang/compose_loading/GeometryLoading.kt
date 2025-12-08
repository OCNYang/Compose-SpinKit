package com.ocnyang.compose_loading

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.EaseOutBounce
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ocnyang.compose_loading.transition.fractionTransition

/**
 * 几何动画
 *
 * 四个几何图形排列成 2x2 网格：
 * - 左上：圆角正方形 → 缩小成圆
 * - 右上：圆 → 向下平移
 * - 左下：圆角三角形 → 持续顺时针旋转
 * - 右下：圆角正方形 → 高度缩小
 *
 * @param modifier Modifier
 * @param size 整体尺寸
 * @param durationMillis 单次动画周期时间（不包括三角形旋转）
 * @param colors 四个几何图形的颜色 [左上, 右上, 左下, 右下]
 * @param cornerRadius 圆角半径
 * @param gapRatio 图形之间间隙占比
 */
@Preview(showBackground = true)
@Composable
fun GeometryLoading(
    modifier: Modifier = Modifier,
    size: Dp = 60.dp,
    durationMillis: Int = 1500,
    colors: List<Color> = listOf(
        Color(0xFFE06B7D),  // 粉红色 - 左上
        Color(0xFF4A90A4),  // 蓝色 - 右上
        Color(0xFF6FCF97),  // 绿色 - 左下
        Color(0xFFF2C94C),  // 黄色 - 右下
    ),
    cornerRadius: Dp = 8.dp,
    gapRatio: Float = 0.08f,
) {
    val transition = rememberInfiniteTransition(label = "GeometryLoading")

    // 左上圆角正方形缩小动画 (0 -> 1: 正方形 -> 圆)
    val topLeftScale = transition.fractionTransition(
        initialValue = 0f,
        targetValue = 1f,
        durationMillis = durationMillis / 2,
        repeatMode = RepeatMode.Reverse,
        easing = EaseInOut
    )

    // 右下圆角正方形高度缩小动画（右上圆的平移与此同步）
    // 下降时平滑，恢复时有弹性回弹
    val shapeTransform = transition.elasticReturnTransition(
        durationMillis = durationMillis / 2
    )

    // 左下三角形持续旋转动画（不反向）
    val bottomLeftRotation = transition.fractionTransition(
        initialValue = 0f,
        targetValue = 360f,
        durationMillis = durationMillis,
        repeatMode = RepeatMode.Restart,
        easing = LinearEasing
    )

    Canvas(modifier = modifier.size(size)) {
        val canvasSize = this.size.width
        val gap = canvasSize * gapRatio
        val cellSize = (canvasSize - gap) / 2
        val cornerRadiusPx = cornerRadius.toPx()

        // 左上：圆角正方形 → 圆
        val topLeftSize = cellSize * (1f - topLeftScale.value * 0.5f)
        val topLeftCornerRadius = cornerRadiusPx + (topLeftSize / 2 - cornerRadiusPx) * topLeftScale.value
        val topLeftOffset = (cellSize - topLeftSize) / 2
        drawRoundRect(
            color = colors[0 % colors.size],
            topLeft = Offset(topLeftOffset, topLeftOffset),
            size = Size(topLeftSize, topLeftSize),
            cornerRadius = CornerRadius(topLeftCornerRadius, topLeftCornerRadius)
        )

        // 右上：圆向下平移
        val circleRadius = cellSize / 2
        val circleCenterX = cellSize + gap + cellSize / 2
        val maxTranslateY = cellSize * 0.6f * shapeTransform.value
        val circleCenterY = cellSize / 2 + maxTranslateY
        drawCircle(
            color = colors[1 % colors.size],
            radius = circleRadius,
            center = Offset(circleCenterX, circleCenterY)
        )

        // 左下：三角形持续旋转（无圆角，更大）
        val triangleCenterX = cellSize / 2
        val triangleCenterY = cellSize + gap + cellSize / 2
        rotate(
            degrees = bottomLeftRotation.value,
            pivot = Offset(triangleCenterX, triangleCenterY)
        ) {
            val trianglePath = createTrianglePath(
                centerX = triangleCenterX,
                centerY = triangleCenterY,
                size = cellSize
            )
            drawPath(
                path = trianglePath,
                color = colors[2 % colors.size]
            )
        }

        // 右下：圆角正方形高度缩小（底部位置不变）
        val bottomRightFullHeight = cellSize
        val bottomRightMinHeight = cellSize * 0.4f
        val bottomRightCurrentHeight = bottomRightFullHeight - (bottomRightFullHeight - bottomRightMinHeight) * shapeTransform.value
        // 底部位置固定，顶部向下收缩
        val bottomRightY = cellSize + gap + (cellSize - bottomRightCurrentHeight)
        drawRoundRect(
            color = colors[3 % colors.size],
            topLeft = Offset(cellSize + gap, bottomRightY),
            size = Size(cellSize, bottomRightCurrentHeight),
            cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx)
        )
    }
}

/**
 * 创建三角形路径（指向右边）
 */
private fun createTrianglePath(
    centerX: Float,
    centerY: Float,
    size: Float
): Path {
    val path = Path()
    val halfSize = size / 2

    // 三角形三个顶点（指向右边）
    val rightX = centerX + halfSize * 0.8f
    val rightY = centerY
    val topLeftX = centerX - halfSize * 0.5f
    val topLeftY = centerY - halfSize * 0.75f
    val bottomLeftX = centerX - halfSize * 0.5f
    val bottomLeftY = centerY + halfSize * 0.75f

    path.moveTo(topLeftX, topLeftY)
    path.lineTo(rightX, rightY)
    path.lineTo(bottomLeftX, bottomLeftY)
    path.close()

    return path
}

/**
 * 弹性恢复动画：下降时平滑，恢复时有弹性回弹效果
 */
@Composable
private fun InfiniteTransition.elasticReturnTransition(
    durationMillis: Int
) = animateFloat(
    initialValue = 0f,
    targetValue = 0f,
    label = "elasticReturn",
    animationSpec = infiniteRepeatable(
        animation = keyframes {
            this.durationMillis = durationMillis * 2
            0f at 0 using EaseInOut
            1f at durationMillis using EaseInOut
            // 弹性回弹：先超过目标值，再回弹
            -0.15f at (durationMillis * 1.4f).toInt() using EaseInOut
            0.08f at (durationMillis * 1.7f).toInt() using EaseInOut
            0f at durationMillis * 2 using EaseInOut
        },
        repeatMode = RepeatMode.Restart,
        initialStartOffset = StartOffset(0)
    )
)
