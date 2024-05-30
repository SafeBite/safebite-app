package com.celvine.deb.esail.bby.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.celvine.deb.esail.bby.common.theme.White
import com.celvine.deb.esail.bby.data.model.Food
import com.celvine.deb.esail.bby.route.Routes
import com.celvine.deb.esail.bby.common.theme.DarkGreen


@Composable
fun SimpleCardCourse(
    modifier: Modifier = Modifier,
    item: Food,
    navController: NavController
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { navController.navigate("${Routes.DetailFoodScreen.routes}/${item.id}") },
        elevation = CardDefaults.cardElevation(defaultElevation = 0.8.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.picture)
                    .crossfade(true)
                    .build(),
                contentDescription = item.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(64.dp)
                    .height(64.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.width(15.dp))
            Column {
                Text(
                    text = item.name,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                Allergen(allergen = item.ingredients.map { it.name })
            }
        }
    }
}

@Composable
fun Allergen(allergen: List<String>) {
    Row(verticalAlignment = Alignment.CenterVertically) {
//        Icon(
//            painter = painterResource(id = R.drawable.round_icon),
//            contentDescription = "mentor",
//            tint = SoftGray2,
//            modifier = Modifier.size(12.dp)
//        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = "Contains: ",
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 9.sp,
                color = DarkGreen
            )
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = allergen.map { it.capitalize() }.joinToString(", "),
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 9.sp,
                color = Color.Red
            )
        )
    }
}
