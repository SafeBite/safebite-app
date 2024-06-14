package com.celvine.deb.esail.bby.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.celvine.deb.esail.bby.R
import com.celvine.deb.esail.bby.common.theme.Dark
import com.celvine.deb.esail.bby.common.theme.GreenNew

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun InfoProbability(probability: Double) {
    var expanded by remember { mutableStateOf(false) }

    Surface(
        color = Color.Transparent,
        onClick = { expanded = !expanded },
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        AnimatedContent(
            targetState = expanded,
            transitionSpec = {
                fadeIn(animationSpec = tween(150, 150)) with
                        fadeOut(animationSpec = tween(150)) using
                        SizeTransform { initialSize, targetSize ->
                            if (targetState) {
                                keyframes {
                                    IntSize(targetSize.width, initialSize.height) at 150
                                    durationMillis = 300
                                }
                            } else {
                                keyframes {
                                    IntSize(initialSize.width, targetSize.height) at 150
                                    durationMillis = 300
                                }
                            }
                        }
            }
        ) { targetExpanded ->
            if (targetExpanded) {
                ExpandedProbability(probability = probability)
            } else {
                InfoIcon(probability = probability)
            }
        }
    }
}

@Composable
fun InfoIcon(probability: Double) {
    val iconColor = if (probability >= 0.8) {
        GreenNew // If probability is greater than 80%, use green color
    } else {
        Color.Red // Otherwise, use red color
    }
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .size(40.dp)
            .background(Color.White) // Ensure background is transparent
//            .padding(8.dp) // Adjust padding if needed
    ) {
        Icon(
            painter = painterResource(id = R.drawable.alert_sign), // Replace with your info icon resource
            contentDescription = "Info",
            tint = iconColor,
            modifier = Modifier
                .fillMaxSize() // Ensure the icon takes the entire box size
        )
    }
}


@Composable
fun ExpandedProbability(probability: Double) {
    val textColor = if (probability >= 0.8) {
        GreenNew // If probability is greater than 80%, use green color
    } else {
        Color.Red // Otherwise, use red color
    }
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(8.dp)
            .heightIn(max = 100.dp)
            .widthIn(max = 100.dp)
    ) {
        Row {
            Text(
                text = "Probability: ",
                fontSize = 10.sp,
                style = MaterialTheme.typography.bodyMedium.copy(color = textColor)
            )
            Text(
                text = String.format("%.2f%%", probability * 100),
                fontSize = 10.sp,
                style = MaterialTheme.typography.bodyMedium.copy(color = textColor)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "You can look for experts or use the emergency feature for validation",
            fontSize = 9.sp,
            style = MaterialTheme.typography.bodyMedium.copy(color = Dark)
        )
    }
}

