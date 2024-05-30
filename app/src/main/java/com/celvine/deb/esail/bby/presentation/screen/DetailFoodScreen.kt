package com.celvine.deb.esail.bby.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.celvine.deb.esail.bby.R
import com.celvine.deb.esail.bby.common.UiState
import com.celvine.deb.esail.bby.common.theme.BgColorNew
import com.celvine.deb.esail.bby.common.theme.Dark
import com.celvine.deb.esail.bby.common.theme.Green
import com.celvine.deb.esail.bby.common.theme.GreenNew
import com.celvine.deb.esail.bby.common.theme.White
import com.celvine.deb.esail.bby.common.theme.desColor
import com.celvine.deb.esail.bby.data.repositories.FoodDetailRepo
import com.celvine.deb.esail.bby.data.viewmodels.FoodDetailViewModel
import com.celvine.deb.esail.bby.data.viewmodels.FoodDetailViewModelFactory
import com.celvine.deb.esail.bby.data.viewmodels.LoginViewModel
import com.celvine.deb.esail.bby.presentation.components.ExpandedText
import com.celvine.deb.esail.bby.presentation.components.Loading

@Composable
fun DetailFoodScreen(
    navController: NavController,
    foodId: Int,
    foodDetailRepo: FoodDetailRepo,
    loginViewModel: LoginViewModel
) {
    val foodDetailViewModel: FoodDetailViewModel = viewModel(
        factory = FoodDetailViewModelFactory(repository = foodDetailRepo)
    )
    val foodDetailState by foodDetailViewModel.foodDetailState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(foodId) {
        foodDetailViewModel.getFoodDetail(foodId)
    }

    when (val state = foodDetailState) {
        is UiState.Loading -> Loading()
        is UiState.Success -> {
            val userMainAllergens = loginViewModel.userResponse.value?.data?.alergens?.map { it.name } ?: emptyList() // Replace with actual user allergens
            val food = state.data
            val allIngredients = food.data.ingredients.map { it.name } // Map to ingredient names
            val ingredients = food.data.ingredients
                .filter { it.isMainAlergen } // Filter ingredients where isMainAlergen is true
                .map { it.name } // Map to ingredient names
            val containsAllergen = allIngredients.any { it in userMainAllergens }

            if (containsAllergen) {
                NotAllowFood(
                    foodName = food.data.name,
                    ingredients = ingredients,
                    description = food.data.description,
                    imageUrl = food.data.picture,
                    navController = navController,
                    allIngredients = allIngredients
                )
            } else {
                AllowFood(
                    foodName = food.data.name,
                    ingredients = ingredients,
                    description = food.data.description,
                    imageUrl = food.data.picture,
                    navController = navController,
                    allIngredients = allIngredients
                )
            }
        }
        is UiState.Error -> Toast.makeText(context, "Error 503" +
                "Try again or check your connection", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun AllowFood(
    navController: NavController,
    foodName: String,
    allIngredients: List<String>,
    ingredients: List<String>,
    description: String,
    imageUrl: String
){
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.background_allow),
            contentDescription = "backgroundAllow",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.matchParentSize()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                     navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(containerColor = White, contentColor = Dark)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = stringResource(id = R.string.back)
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(color = Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            ElevatedCard(
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                modifier = Modifier
                    .size(width = 360.dp, height = 480.dp)
                    .background(color = BgColorNew, shape = RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(containerColor = BgColorNew, contentColor = Dark)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(top = 42.dp)
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(imageUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = "Food Image",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .size(150.dp)
                                .clip(shape = CircleShape)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(

                        ) {
                            Text(
                                text = foodName,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = Dark,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                            Text(
                                text = "contains:",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = desColor,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row {
                                ingredients.forEach { ingredient ->
                                    Box(
                                        modifier = Modifier
                                            .background(color = GreenNew, shape = RoundedCornerShape(8.dp))
                                            .padding(horizontal = 4.dp, vertical = 2.dp)
                                            .padding(bottom = 4.dp) // Add spacing between boxes
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
                                    Spacer(modifier = Modifier.width(6.dp))
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "You Can Eat This Food!",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = Green,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Description",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = Dark,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ExpandedText(text = description)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Composition",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = Dark,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = allIngredients.joinToString(", "),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Dark,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp
                        )
                    )
                }
            }
            Box(
                modifier = Modifier
                    .padding(top = 64.dp)
                    .align(Alignment.TopCenter)
            ) {
                Image(
                    painter = painterResource(R.drawable.allow_sign),
                    contentDescription = "Allow Sign Image",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.size(100.dp)
                )
            }
        }
    }

}

@Composable
fun NotAllowFood(
    navController: NavController,
    foodName: String,
    allIngredients: List<String>,
    ingredients: List<String>,
    description: String,
    imageUrl: String
){
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.background_not_allowed),
            contentDescription = "backgroundNotAllow",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.matchParentSize()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                     navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(containerColor = White, contentColor = Dark)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = stringResource(id = R.string.back)
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(color = Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            ElevatedCard(
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                modifier = Modifier
                    .size(width = 360.dp, height = 480.dp)
                    .background(color = BgColorNew, shape = RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(containerColor = BgColorNew, contentColor = Dark)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(top = 42.dp)
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(imageUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = "Food Image",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .size(150.dp)
                                .clip(shape = CircleShape)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(

                        ) {
                            Text(
                                text = foodName,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = Dark,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                            Text(
                                text = "contains:",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = desColor,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
//                                modifier = Modifier
//                                    .padding(horizontal = 4.dp)
                            ) {
                                ingredients.forEach { ingredient ->
                                    Box(
                                        modifier = Modifier
                                            .background(color = Color.Red, shape = RoundedCornerShape(8.dp))
                                            .padding(horizontal = 4.dp, vertical = 2.dp)
                                            .padding(bottom = 4.dp) // Add spacing between boxes
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
                                    Spacer(modifier = Modifier.width(6.dp))
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "You Can't Eat This Food!",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = Color.Red,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Description",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = Dark,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ExpandedText(text = description)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Composition: ",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = Dark,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = allIngredients.joinToString(", "),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Dark,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp
                        )
                    )
                }
            }
            Box(
                modifier = Modifier
                    .padding(top = 64.dp)
                    .align(Alignment.TopCenter)
            ) {
                Image(
                    painter = painterResource(R.drawable.not_allowed_sign),
                    contentDescription = "Allow Sign Image",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.size(100.dp)
                )
            }
        }
    }
}


