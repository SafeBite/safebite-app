package com.celvine.deb.esail.bby.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.celvine.deb.esail.bby.R
import com.celvine.deb.esail.bby.common.UiState
import com.celvine.deb.esail.bby.common.theme.BgColorNew
import com.celvine.deb.esail.bby.common.theme.Dark
import com.celvine.deb.esail.bby.common.theme.White
import com.celvine.deb.esail.bby.data.model.Food
import com.celvine.deb.esail.bby.data.viewmodels.FoodViewModel
import com.celvine.deb.esail.bby.data.viewmodels.ViewModelFoodFactory
import com.celvine.deb.esail.bby.di.Injection
import com.celvine.deb.esail.bby.presentation.components.SearchField
import com.celvine.deb.esail.bby.presentation.components.ShimmerCardFood
import com.celvine.deb.esail.bby.presentation.components.SimpleCardCourse

@Composable
fun SearchScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: FoodViewModel = viewModel(factory = ViewModelFoodFactory(Injection.provideFoodRepository()))
) {
    val searchText by viewModel.query.collectAsState()
    val listState = rememberLazyListState()

    var loading by remember { mutableStateOf(true) }

    var foods by remember { mutableStateOf<List<Food>>(emptyList()) }

    LaunchedEffect(Unit) {
        viewModel.getAllFoods()
    }

    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is UiState.Loading -> {
            loading = true
        }
        is UiState.Success -> {
            foods = (uiState as UiState.Success<List<Food>>).data
            loading = false // Update loading state
        }
        is UiState.Error -> {
            loading = false // Update loading state
        }
    }

    Surface(
        color = BgColorNew, // Set your desired background color here
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Top bar with search field
            SearchForm(viewModel = viewModel, navController = navController)

            // Content based on loading state
            if (loading) {
                // Display shimmer while loading
                ShimmerSearchResults()
            } else {
                if (foods.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        state = listState
                    ) {
                        items(foods.size) { index ->
                            SimpleCardCourse(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                item = foods[index],
                                navController = navController
                            )
                        }
                    }
                } else if (foods.isEmpty()){
                    // Display message when no foods found
                    Text(
                        text = "No foods found",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        textAlign = TextAlign.Center
                    )
                } else {
                    Text(
                        text = "Error",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun ShimmerSearchResults() {
    Column(
//        modifier = Modifier.padding(
//            start = 16.dp,
//            end = 16.dp
//        )
    ) {
        repeat(8) {
            ShimmerCardFood(
                isLoading = true,
                contentAfterLoading = {}
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}


@Composable
fun SearchForm(viewModel: FoodViewModel, navController: NavController) {
    val query by viewModel.query.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = White, contentColor = Dark),
            modifier = Modifier.padding(4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = stringResource(id = R.string.back)
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            SearchField(
                placeholder = "Search Food",
                value = query,
                onValueChange = viewModel::search,
                onClear = viewModel::removeQuery
            )
        }
    }
}
