package com.celvine.deb.esail.bby.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.celvine.deb.esail.bby.R
import com.celvine.deb.esail.bby.data.model.CourseModel

@Composable
fun FlashSale(
    modifier: Modifier = Modifier,
    navController: NavController,
    courses: List<CourseModel>
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Food to avoid",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ){
                Text(
                    text = "Change Allergen",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 10.sp
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                Icon(painter = painterResource(id = R.drawable.back_right), contentDescription = "more", modifier = Modifier.size(15.dp))
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        courses.forEachIndexed { _, item ->
            if (item.isFlashSale) {
                SimpleCardCourse(item = item, navController = navController)
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}
