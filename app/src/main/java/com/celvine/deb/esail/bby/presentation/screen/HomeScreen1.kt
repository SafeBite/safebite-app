//package com.celvine.deb.esail.bby.presentation.screen
//
//import FoodCardList
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Search
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.draw.shadow
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//import com.celvine.deb.esail.bby.R
//import com.celvine.deb.esail.bby.common.theme.BgColorNew
//import com.celvine.deb.esail.bby.common.theme.Primary
//import com.celvine.deb.esail.bby.data.model.Food
//import com.celvine.deb.esail.bby.presentation.components.GreetingBar
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun HomeScreen1(
//    navController: NavController
//) {
//    Surface(
//        color = BgColorNew,
//        modifier = Modifier.fillMaxSize()
//    ) {
//        Column(
//            modifier = Modifier.padding(16.dp),
//            verticalArrangement = Arrangement.Top,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            GreetingBar(navController = navController)
//            Spacer(modifier = Modifier.height(16.dp))
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .shadow(7.dp, shape = RoundedCornerShape(15))
//                    .background(color = Color.White)
//            ) {
//                Image(
//                    painter = painterResource(R.drawable.logo_safebite),
//                    contentDescription = "Profile Image",
//                    modifier = Modifier
//                        .clip(RoundedCornerShape(32.dp)),
//                    contentScale = ContentScale.Fit
//                )
//            }
//            Spacer(modifier = Modifier.height(24.dp))
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(Color.White, shape = RoundedCornerShape(8.dp))
//                    .shadow(7.dp, shape = RoundedCornerShape(20.dp))
//            ) {
//                TextField(
//                    value = "", // Placeholder for the search query
//                    onValueChange = { /* Handle search query change */ },
//                    modifier = Modifier.fillMaxWidth(),
//                    colors = TextFieldDefaults.textFieldColors(
//                        focusedIndicatorColor = Color.Transparent,
//                        unfocusedIndicatorColor = Color.Transparent,
//                        cursorColor = Primary,
//                        placeholderColor = Color.Gray.copy(alpha = 0.6f)
//                    ),
//                    leadingIcon = {
//                        Icon(
//                            imageVector = Icons.Default.Search,
//                            contentDescription = "Search Icon",
//                            tint = Color.Gray
//                        )
//                    },
//                    placeholder = {
//                        Text(
//                            text = "Search...",
//                            style = MaterialTheme.typography.bodySmall,
//                            modifier = Modifier.padding(vertical = 8.dp)
//                        )
//                    }
//                )
//            }
//            Spacer(modifier = Modifier.height(24.dp))
//            Row(modifier = Modifier.fillMaxWidth()) {
//                Text(
//                    text = "Foods to Avoid",
//                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
//                    modifier = Modifier.weight(1f)
//                )
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(top = 8.dp),
//                    horizontalArrangement = Arrangement.End
//                ) {
//                    Text(
//                        text = "Change allergen",
//                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
//                        modifier = Modifier.padding(start = 8.dp),
//                        textAlign = TextAlign.End
//                    )
//                    Icon(
//                        painter = painterResource(R.drawable.back_right),
//                        contentDescription = "Allergen Icon",
//                        modifier = Modifier.size(20.dp)
//                    )
//                }
//            }
//            Spacer(modifier = Modifier.height(16.dp))
//            Box(modifier = Modifier.background(Color.White)) {
//                val foodList = listOf(
//                    Food(1, R.drawable.almond, "Pizza", R.drawable.almond, "Gluten"),
//                    Food(2, R.drawable.almond, "Burger", R.drawable.almond, "Lactose"),
//                    Food(3, R.drawable.almond, "Sushi", R.drawable.almond, "Seafood")
//                )
//
//                FoodCardList(foodList = foodList)
//            }
//        }
//    }
//}
//
//@Preview
//@Composable
//fun HomeScreenPreview() {
//    HomeScreen1(navController = rememberNavController())
//}
