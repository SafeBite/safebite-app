package com.celvine.deb.esail.bby.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun GridItem(index: Int, imageResId: Int, isSelected: Boolean) {
    val shadowElevation = if (isSelected) 4.dp else 0.dp
    Image(
        painter = painterResource(id = imageResId),
        contentDescription = "Gambar $index",
        contentScale = ContentScale.Inside,
        modifier = Modifier
            .fillMaxSize()
            .size(72.dp)
            .shadow(
                elevation = shadowElevation,
                shape = CircleShape)

    )
}