package com.example.leofindit.composables

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
/*********************************************************************************
 *                   Actual scanning radar animation logic
 *
 *********************************************************************************/
@Composable
fun ScanningAnimation(
    size: Float,
    withBackground: Boolean
) {
    // Animation state for rotation
    val rotationAnim = rememberInfiniteTransition()
    val rotation by rotationAnim.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    // Updated Colors
    val primaryColor = Color.Black
    val secondaryColor = Color(0xFFECC06C)

    // Background modifier
    val backgroundModifier = if (withBackground) {
        Modifier.background(
            Brush.verticalGradient(
                colors = listOf(primaryColor, secondaryColor)
            )
        )
    } else {
        Modifier
    }

    Box(
        modifier = Modifier
            .size(size.dp)
            .then(backgroundModifier),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .size((size * 0.8f).dp)
                .rotate(rotation)
        ) {
            // Angular Gradient
            val gradientBrush = Brush.sweepGradient(
                colors = listOf(primaryColor, secondaryColor)
            )
            drawCircle(brush = gradientBrush)

            // Smaller circles
            val smallerSize = size * 0.15f * 0.8f
            val circleStrokeWidth = size * 0.03f

            drawCircle(
                color = secondaryColor.copy(alpha = 0.4f),
                radius = size * 0.35f * 0.8f,
                style = Stroke(width = circleStrokeWidth)
            )

            val borderSize = smallerSize + size * 0.1f * 0.8f
            drawCircle(
                color = secondaryColor,
                radius = borderSize / 2f
            )
            drawCircle(
                color = primaryColor,
                radius = smallerSize / 2f
            )
        }
    }
}

@Preview
@Composable
fun PreviewScanAnimation() {
    ScanningAnimation(size = 250f, withBackground = true)
}

@Preview
@Composable
fun PreviewScanAnimationNoBackground() {
    ScanningAnimation(size = 250f, withBackground = false)
}
