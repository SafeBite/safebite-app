//package com.celvine.deb.esail.bby.presentation.components
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Card
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//
//@Composable
//fun FoodCardDetail(
//    foodImageRes: Int,
//    foodTitle: String,
//    allergenIconRes: Int,
//    allergenName: String,
//    description: String
//) {
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        shape = RoundedCornerShape(8.dp)
//    ) {
//        Column(
//            modifier = Modifier.padding(16.dp),
//        ) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//            ) {
//                Image(
//                    painter = painterResource(foodImageRes),
//                    contentDescription = "Food Image",
//                    modifier = Modifier.size(80.dp),
//                    contentScale = ContentScale.Crop
//                )
//
//                Column(
//                    modifier = Modifier.padding(start = 16.dp)
//                ) {
//                    Text(
//                        text = foodTitle,
//                        style = MaterialTheme.typography.headlineSmall,
//                        fontWeight = FontWeight.Bold
//                    )
//                    Text(
//                        text = "Contains",
//                        style = MaterialTheme.typography.bodySmall,
//                        color = Color.Gray
//                    )
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Icon(
//                            painter = painterResource(allergenIconRes),
//                            contentDescription = "Allergen Icon",
//                            tint = Color.Red,
//                            modifier = Modifier.size(16.dp)
//                        )
//                        Text(
//                            text = allergenName,
//                            style = MaterialTheme.typography.bodySmall,
//                            color = Color.Red,
//                            modifier = Modifier.padding(start = 4.dp)
//                        )
//                    }
//                }
//            }
//
//            Text(
//                text = "You can eat this food!",
//                style = MaterialTheme.typography.bodySmall,
//                fontWeight = FontWeight.Bold,
//                color = Color.Green,
//                modifier = Modifier.padding(top = 16.dp)
//            )
//
//            Text(
//                text = description,
//                style = MaterialTheme.typography.bodySmall,
//                color = Color.Black,
//                textAlign = TextAlign.Justify,
//                modifier = Modifier.padding(top = 8.dp)
//            )
//        }
//    }
//}