package com.celvine.deb.esail.bby.presentation.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.celvine.deb.esail.bby.R
import com.celvine.deb.esail.bby.common.UiState
import com.celvine.deb.esail.bby.common.theme.BgColorNew
import com.celvine.deb.esail.bby.common.theme.White2
import com.celvine.deb.esail.bby.data.viewmodels.FlashSaleCoursesViewModel
import com.celvine.deb.esail.bby.data.viewmodels.LoginViewModel
import com.celvine.deb.esail.bby.data.viewmodels.PopularCourseViewModel
import com.celvine.deb.esail.bby.data.viewmodels.ViewModelCoursesFactory
import com.celvine.deb.esail.bby.di.Injection
import com.celvine.deb.esail.bby.presentation.components.*
import com.celvine.deb.esail.bby.route.Routes

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    navController: NavController,
    flashSaleViewModel: FlashSaleCoursesViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelCoursesFactory(
            Injection.provideCourseRepository()
        )
    ),
    loginViewModel: LoginViewModel
) {

    LaunchedEffect(Unit) {
        loginViewModel.getUser(
            onSuccess = {
                // Login successful, navigate to TokenScreen
                Log.d("getUser", "getUser success")
            },
            onError = { errorMessage ->
                // Handle login error
                // Show an error message or perform any other action
            })
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
                    GreetingBar(navController = navController, loginViewModel = loginViewModel)
                    Spacer(modifier = Modifier.height(15.dp))
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
                }
//            Column(modifier = Modifier.padding(start = 16.dp, end = 0.dp)) {
//                Spacer(modifier = Modifier.height(20.dp))
//                popularViewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
//                    when (uiState) {
//                        is UiState.Loading -> {
//                            popularViewModel.getPopular()
//                        }
//                        is UiState.Success -> {
//                            PopularCourse(navController = navController, courses = uiState.data)
//                        }
//                        is UiState.Error -> {}
//                    }
//                }
//                Spacer(modifier = Modifier.height(20.dp))
//            }
            }
            item {
                flashSaleViewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {
                            flashSaleViewModel.getFlashSale()
                        }
                        is UiState.Success -> {
                            FlashSale(
                                modifier = Modifier.padding(
                                    start = 16.dp,
                                    end = 16.dp,
                                    bottom = paddingValues.calculateBottomPadding()
                                ),
                                navController = navController,
                                courses = uiState.data
                            )
                        }
                        is UiState.Error -> {
                            Text(text = stringResource(id = R.string.error))
                        }
                    }
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun HomeScreenPreview() {
//    HomeScreen(paddingValues = PaddingValues(0.dp), navController = NavController())
//}