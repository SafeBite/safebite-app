package com.celvine.deb.esail.bby.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.celvine.deb.esail.bby.data.model.Food

@Composable
fun FoodToAvoid(
    modifier: Modifier = Modifier,
    navController: NavController,
    foods: List<Food>,
) {
    Column(modifier = modifier) {
//        Spacer(modifier = Modifier.height(10.dp))
        foods.forEach { item ->
            SimpleCardCourse(item = item, navController = navController)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

