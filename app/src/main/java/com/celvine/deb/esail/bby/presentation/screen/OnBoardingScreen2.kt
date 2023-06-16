package com.celvine.deb.esail.bby.presentation.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import com.celvine.deb.esail.bby.common.theme.BgColorNew
import com.celvine.deb.esail.bby.common.theme.ButtonColor
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.celvine.deb.esail.bby.R
import com.celvine.deb.esail.bby.common.theme.White
import com.celvine.deb.esail.bby.data.model.AccountVerificationOTP
import com.celvine.deb.esail.bby.data.model.UpdateUserRequest
import com.celvine.deb.esail.bby.data.viewmodels.FoodViewModel
import com.celvine.deb.esail.bby.data.viewmodels.LoginViewModel
import com.celvine.deb.esail.bby.presentation.components.GridItem
import com.celvine.deb.esail.bby.route.Routes

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun OnBoardingScreen2(navController: NavController, foodViewModel: FoodViewModel, loginViewModel: LoginViewModel) {
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

    val selectedIconIds = remember { mutableStateListOf<Int>() }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BgColorNew)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = "What should you avoid?",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Box(
                modifier = Modifier
                    .shadow(6.dp, shape = RoundedCornerShape(10))
                    .background(color = Color.White)
                    .padding(top = 16.dp, bottom = 16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Select one or more")
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyVerticalGrid(columns = GridCells.Fixed(4)) {
                        items(9) { index ->
                            val imageResId = when (index) {
                                1 -> R.drawable.milk_icon
                                2 -> R.drawable.cheese_icon
                                3 -> R.drawable.pork_icon
                                4 -> R.drawable.egg_icon
                                5 -> R.drawable.shrimp_icon
                                6 -> R.drawable.almond_icon
                                7 -> R.drawable.peanut_icon
                                8 -> R.drawable.soy_icon
                                9 -> R.drawable.fish_icon
                                else -> R.drawable.duck
                            }
                            val isSelected = remember { mutableStateOf(false) }
                            Box(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .clickable {
                                        if (isSelected.value) {













                                            selectedIconIds.add(index)
                                        } else {
                                            selectedIconIds.remove(index)
                                        }
                                    }
                            ) {
                                GridItem(index = index, imageResId = imageResId, isSelected = isSelected.value)
                            }
                        }
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        navController.navigate(Routes.OnBoarding1.routes) {
                            popUpTo(Routes.OnBoarding2.routes) {
                                inclusive = true
                            }
                        }
                    },
                    modifier = Modifier
                        .shadow(5.dp, shape = CircleShape)
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = White)
                ) {
                    Text(text = "Back", color = Color.Black)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = {
                        val request: UpdateUserRequest = UpdateUserRequest(name = loginViewModel.userResponse?.data?.name, allergens = selectedIconIds)
                        foodViewModel.updateUsers(request,
                            onSuccess = {
                                Log.d("UpdateUser", "UpdateUser success ${loginViewModel.userResponse?.data}")
                                navController.navigate(Routes.Dashboard.routes){
                                    popUpTo(Routes.OnBoarding2.routes) {
                                        inclusive = true
                                    }
                                }
                            },
                            onError = { errorMessage ->
                                // Handle OTP sending error
                                // Show an error message or perform any other action
                            })
                    },
                    modifier = Modifier
                        .shadow(5.dp, shape = CircleShape)
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)
                ) {
                    Text(text = "Continue")
                }
            }
        }
    }
}


//@OptIn(ExperimentalFoundationApi::class)
//@Preview
//@Composable
//fun OnBoardingScreen2Preview() {
//    val navController = rememberNavController() // Create a mock NavController instance
//    OnBoardingScreen2(navController = navController)
//}
