package com.celvine.deb.esail.bby.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.celvine.deb.esail.bby.common.theme.GreenNew
import com.celvine.deb.esail.bby.common.theme.White

@Composable
fun IngredientGrid(
    ingredients: List<String>,
    modifier: Modifier = Modifier,
    rowMaxWidth: Dp = 360.dp,
    horizontalSpacing: Dp = 8.dp,
    verticalSpacing: Dp = 4.dp
) {
    val density = LocalDensity.current
    val rowMaxWidthPx = with(density) { rowMaxWidth.toPx() }
    val horizontalSpacingPx = with(density) { horizontalSpacing.toPx() }
    val verticalSpacingPx = with(density) { verticalSpacing.toPx() }

    Layout(
        modifier = modifier,
        content = {
            ingredients.forEach { ingredient ->
                Box(
                    modifier = Modifier
                        .background(
                            color = GreenNew,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = ingredient,
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }
    ) { measurables, constraints ->
        val itemConstraints = constraints.copy(minWidth = 0)
        val placeables = measurables.map { it.measure(itemConstraints) }

        var currentRowWidth = 0
        var currentRowHeight = 0
        var totalHeight = 0
        val rowHeights = mutableListOf<Int>()
        val rows = mutableListOf<List<Placeable>>()
        var currentRow = mutableListOf<Placeable>()

        placeables.forEach { placeable ->
            if (currentRowWidth + placeable.width + horizontalSpacingPx > rowMaxWidthPx) {
                rows.add(currentRow)
                rowHeights.add(currentRowHeight)
                totalHeight += currentRowHeight + verticalSpacingPx.toInt()
                currentRow = mutableListOf()
                currentRowWidth = 0
                currentRowHeight = 0
            }
            currentRow.add(placeable)
            currentRowWidth += placeable.width + horizontalSpacingPx.toInt()
            currentRowHeight = maxOf(currentRowHeight, placeable.height)
        }
        if (currentRow.isNotEmpty()) {
            rows.add(currentRow)
            rowHeights.add(currentRowHeight)
            totalHeight += currentRowHeight
        }

        layout(constraints.maxWidth, totalHeight) {
            var yOffset = 0
            rows.zip(rowHeights).forEach { (row, height) ->
                var xOffset = 0
                row.forEach { placeable ->
                    placeable.placeRelative(x = xOffset, y = yOffset)
                    xOffset += placeable.width + horizontalSpacingPx.toInt()
                }
                yOffset += height + verticalSpacingPx.toInt()
            }
        }
    }
}
