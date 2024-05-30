package com.celvine.deb.esail.bby.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GridItem(index: Int, imageResId: Int, isSelected: Boolean, text: String) {
    val shadowElevation = if (isSelected) 6.dp else 0.dp
    Column(
        modifier = Modifier
            .size(100.dp), // Adjust size as needed
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "Image $index",
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .shadow(elevation = shadowElevation, shape = CircleShape)
                .size(72.dp)
                .background(if (isSelected) Color.LightGray else Color.Transparent)
                .padding(2.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 9.sp),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}