package com.celvine.deb.esail.bby.presentation.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.celvine.deb.esail.bby.R
import com.celvine.deb.esail.bby.common.UiState
import com.celvine.deb.esail.bby.common.theme.BgColorNew
import com.celvine.deb.esail.bby.common.theme.White
import com.celvine.deb.esail.bby.data.model.Food
import com.celvine.deb.esail.bby.data.repositories.FoodRepo
import com.celvine.deb.esail.bby.data.viewmodels.FoodViewModel
import com.celvine.deb.esail.bby.data.viewmodels.LoginViewModel
import com.celvine.deb.esail.bby.data.viewmodels.ViewModelFoodFactory
import com.celvine.deb.esail.bby.presentation.components.*
import com.celvine.deb.esail.bby.route.Routes

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    navController: NavController,
    foodViewModel: FoodViewModel = viewModel(
        factory = ViewModelFoodFactory(repository = FoodRepo())
    ),
    loginViewModel: LoginViewModel
) {
    var loading by remember { mutableStateOf(true) }
    var loadingFood by remember { mutableStateOf(true) }
    var foodsToAvoid by remember { mutableStateOf(emptyList<Food>()) }

    LaunchedEffect(Unit) {
        loginViewModel.getUser(
            onSuccess = {
                if (loginViewModel.userResponse.value?.data?.name != null) {
                    foodViewModel.getAllFoods()
                    loading = false
                }
                Log.d("getUser", "getUser success")
            },
            onError = { errorMessage -> }
        )
    }

    val userResponse by loginViewModel.userResponse.observeAsState()
    if (userResponse?.data?.name != null) {
        loading = false
    }

    val uiState by foodViewModel.uiState.collectAsState()

    if (!loading) {
        when (uiState) {
            is UiState.Success -> {
                val userAllergens = userResponse?.data?.alergens?.map { it.name } ?: emptyList()
                foodsToAvoid = (uiState as UiState.Success<List<Food>>).data.filter { food ->
                    food.ingredients.any { it.name in userAllergens }
                }
                loadingFood = false
            }
            is UiState.Error -> {
                Text(text = stringResource(id = R.string.error))
                loadingFood = false
            }
            is UiState.Loading -> {
                loadingFood = true
            }
        }
    }

    Surface(
        color = BgColorNew,
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BgColorNew)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (loading){
                            ShimmerNameHome()
                        } else {
                            Text(
                                text = "Welcome back, ${loginViewModel.userResponse?.value?.data?.name ?: "Guest"}",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 12.sp
                                )
                            )
                        }
                        FilledIconButton(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp)),
                            onClick = {
                                navController.navigate(Routes.Profile.routes)

                            },
                            colors = IconButtonDefaults.filledIconButtonColors(containerColor = White)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(20.dp),
                                painter = painterResource(id = R.drawable.profile),
                                contentDescription = "Profile"
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(7.dp, shape = RoundedCornerShape(15))
                            .background(color = Color.White)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.logo_safebite),
                            contentDescription = "Profile Image",
                            modifier = Modifier
                                .clip(RoundedCornerShape(32.dp)),
                            contentScale = ContentScale.Fit
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    SearchField(
                        placeholder = stringResource(id = R.string.search),
                        enable = false,
                        onClick = {
                            navController.navigate(Routes.Search.routes)
                        },
                        value = ""
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = stringResource(id = R.string.food_to_avoid),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )
                }
            }
            item {
                if (loadingFood) {
                    ShimmerFoodToAvoid()
                } else {
                    if (foodsToAvoid.isNotEmpty()) {
                        FoodToAvoid(
                            modifier = Modifier.padding(
                                start = 16.dp,
                                end = 16.dp,
                                bottom = paddingValues.calculateBottomPadding()
                            ),
                            navController = navController,
                            foods = foodsToAvoid
                        )
                    } else {
                        Text(
                            text = stringResource(id = R.string.empty_food_avoid),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ShimmerFoodToAvoid() {
    Column(
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp
        )
    ) {
        repeat(7) {
            ShimmerCardFood(
                isLoading = true,
                contentAfterLoading = {}
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun ShimmerNameHome() {
    Column {
        ShimmerName(
            isLoading = true,
            contentAfterLoading = {}
        )
    }
}
