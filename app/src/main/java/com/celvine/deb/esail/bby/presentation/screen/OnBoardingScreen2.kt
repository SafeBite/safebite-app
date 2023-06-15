package com.celvine.deb.esail.bby.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import com.celvine.deb.esail.bby.common.theme.BgColorNew
import com.celvine.deb.esail.bby.common.theme.ButtonColor
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.celvine.deb.esail.bby.R
import com.celvine.deb.esail.bby.common.theme.White
import com.celvine.deb.esail.bby.presentation.components.GridItem
import com.celvine.deb.esail.bby.route.Routes

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun OnBoardingScreen2(navController: NavController) {
    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = BgColorNew)
                    .padding(24.dp)
                    .padding(top = 10.dp, bottom = 20.dp),
                verticalArrangement = Arrangement.Top,
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
                                    0 -> R.drawable.milk
                                    1 -> R.drawable.cheese
                                    2 -> R.drawable.pork
                                    3 -> R.drawable.egg
                                    4 -> R.drawable.shrimp
                                    5 -> R.drawable.almond
                                    6 -> R.drawable.peanuts
                                    7 -> R.drawable.soy
                                    8 -> R.drawable.fish
                                    else -> R.drawable.duck
                                }
                                val isSelected = remember { mutableStateOf(false) }

                                Box(
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .clickable {
                                            isSelected.value = !isSelected.value
                                            // Handle item click here
                                        }
                                ) {
                                    GridItem(index = index, imageResId = imageResId, isSelected = isSelected.value)
                                }
                            }
                        }
                    }
                }
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 32.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { navController.navigate(Routes.OnBoarding1.routes) {
                            popUpTo(Routes.OnBoarding2.routes) {
                                inclusive = true
                            }
                        }},
                        modifier = Modifier
                            .shadow(5.dp, shape = CircleShape)
                            .weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = White)
                    ) {
                        Text(text = "Back", color = Color.Black)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = { navController.navigate(Routes.Dashboard.routes) {
                            popUpTo(Routes.OnBoarding2.routes) {
                                inclusive = true
                            }
                        }},
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
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun OnBoardingScreen2Preview() {
    val navController = rememberNavController() // Create a mock NavController instance
    OnBoardingScreen2(navController = navController)
}
