package com.celvine.deb.esail.bby.presentation.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.celvine.deb.esail.bby.common.theme.BgColorNew
import com.celvine.deb.esail.bby.common.theme.DarkGreen
import com.celvine.deb.esail.bby.common.theme.GreenNew

@Composable
fun LoadingScanning() {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Surface(
            modifier = Modifier
                .align(Alignment.Center)
                .background(color = BgColorNew)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(28.dp))

                Box(
                    modifier = Modifier
                        .size(400.dp)
                        .padding(24.dp)
                ) {
                    Text(
                        text = "Scanning",
                        style = MaterialTheme.typography.bodySmall.copy(
                            Color(0xFF4CAF50),
                            shadow = Shadow(
                                color = GreenNew,
                                offset = Offset(1f, 1f),
                                blurRadius = 5f
                            )
                        ),
                        modifier = Modifier
                            .align(Alignment.Center)
                    )

                    Canvas(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(150.dp),
                        onDraw = {
                            drawCircle(
                                color = DarkGreen,
                                style = Stroke(width = 5f)
                            )
                        }
                    )

                    Canvas(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(150.dp),
                        onDraw = {
                            drawArc(
                                color = GreenNew,
                                style = Stroke(
                                    width = 5f,
                                    cap = StrokeCap.Round,
                                    join = StrokeJoin.Round
                                ),
                                startAngle = angle,
                                sweepAngle = 360 / 4f,
                                useCenter = false
                            )
                        }
                    )
                }
            }
        }
    }
}