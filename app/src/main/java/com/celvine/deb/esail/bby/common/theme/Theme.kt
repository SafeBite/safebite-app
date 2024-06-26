package com.celvine.deb.esail.bby.common.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorPalette = lightColorScheme(
    primary = GreenNew,
    primaryContainer = ButtonColor,
    secondary = GreenNew
)

@Composable
fun ESailTheme(content: @Composable () -> Unit) {
    val colors = LightColorPalette

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}