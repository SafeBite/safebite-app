//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.outlined.Info
//import androidx.compose.material.icons.outlined.Warning
//import androidx.compose.material3.Card
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//
//@Composable
//fun FoodCard(
//    foodImageRes: Int,
//    foodTitle: String,
//    allergenIcon: ImageVector,
//    allergenName: String
//) {
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        shape = RoundedCornerShape(8.dp)
//    ) {
//        Row(
//            modifier = Modifier.padding(16.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Image(
//                painter = painterResource(foodImageRes),
//                contentDescription = "Food Image",
//                modifier = Modifier.size(80.dp),
//                contentScale = ContentScale.Crop
//            )
//            Column(
//                modifier = Modifier.padding(start = 16.dp)
//            ) {
//                Text(
//                    text = foodTitle,
//                    style = MaterialTheme.typography.headlineSmall,
//                    fontWeight = FontWeight.Bold
//                )
//                Text(
//                    text = "Contains",
//                    style = MaterialTheme.typography.bodySmall,
//                    color = Color.Gray
//                )
//                Row(
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Icon(
//                        imageVector = allergenIcon,
//                        contentDescription = "Allergen Icon",
//                        tint = Color.Red,
//                        modifier = Modifier.size(16.dp)
//                    )
//                    Text(
//                        text = allergenName,
//                        style = MaterialTheme.typography.bodySmall,
//                        color = Color.Red,
//                        fontSize = 14.sp,
//                        modifier = Modifier.padding(start = 4.dp)
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun FoodCardList(foodList: List<Food>) {
//    LazyColumn {
//        items(foodList) { food ->
//            FoodCard(
//                foodImageRes = food.imageRes,
//                foodTitle = food.title,
//                allergenIcon = when (food.allergenIconRes) {
//                    1 -> Icons.Outlined.Info
//                    2 -> Icons.Outlined.Warning
//                    else -> Icons.Outlined.Info
//                },
//                allergenName = food.allergenName
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//        }
//    }
//}
