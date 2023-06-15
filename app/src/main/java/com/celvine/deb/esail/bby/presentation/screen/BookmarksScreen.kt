//package com.celvine.deb.esail.bby.presentation.screen
//
//import FoodCardList
//import android.util.Log
//import android.widget.Toast
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.Icon
//import androidx.compose.material3.LocalContentColor
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.draw.shadow
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.ColorFilter
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.celvine.deb.esail.bby.R
//import com.celvine.deb.esail.bby.common.UiState
//import com.celvine.deb.esail.bby.common.theme.BgColorNew
//import com.celvine.deb.esail.bby.common.theme.ButtonColor
//import com.celvine.deb.esail.bby.common.theme.Ruby
//import com.celvine.deb.esail.bby.common.theme.SoftGray2
//import com.celvine.deb.esail.bby.common.theme.White2
//import com.celvine.deb.esail.bby.data.model.Food
//import com.celvine.deb.esail.bby.presentation.components.FlashSale
//import com.celvine.deb.esail.bby.presentation.components.GreetingBar
//import com.celvine.deb.esail.bby.presentation.components.PasswordTextField
//import com.celvine.deb.esail.bby.presentation.components.PrimaryButton
//import com.celvine.deb.esail.bby.presentation.components.SearchField
//import com.celvine.deb.esail.bby.presentation.components.TopBar
//import com.celvine.deb.esail.bby.route.Routes
//import com.celvine.deb.esail.bby.ui.components.PrimaryTextField
//
//@Composable
//fun BookmarksScreen() {
//    Surface(
//        color = BgColorNew,
//        modifier = Modifier.fillMaxSize()
//    ) {
//        LazyColumn(
//            modifier = Modifier
//                .padding(top=20.dp)
//                .fillMaxSize()
//                .background(color = White2)
//        ) {
//            item {
//                Header(loginViewModel.userResponse, loginViewModel)
////                Body()
//                val context = LocalContext.current
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clickable {
//                            navController.navigate(Routes.OnBoarding2.routes) {
//                            }
//                        }
//                        .padding(horizontal = 20.dp, vertical = 15.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Row {
//                        Icon(
//                            modifier = Modifier
//                                .width(20.dp)
//                                .height(20.dp),
//                            painter = painterResource(id = R.drawable.captain),
//                            contentDescription = "Icon",
//                            tint = SoftGray2
//                        )
//                        Spacer(modifier = Modifier.width(12.dp))
//                        Text(
//                            text = "Change Allergen",
//                            style = MaterialTheme.typography.bodyMedium.copy(
//                                fontSize = 14.sp,
//                                fontWeight = FontWeight.SemiBold
//                            )
//                        )
//                    }
//                    Icon(
//                        modifier = Modifier
//                            .width(20.dp)
//                            .height(20.dp),
//                        painter = painterResource(id = R.drawable.ic_baseline_keyboard_arrow_right_24),
//                        contentDescription = "Icon"
//                    )
//                }
//
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clickable {
//                            Toast.makeText(context, "Featured Development", Toast.LENGTH_SHORT).show()
//                        }
//                        .padding(horizontal = 20.dp, vertical = 15.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Row {
//                        Icon(
//                            modifier = Modifier
//                                .width(20.dp)
//                                .height(20.dp),
//                            painter = painterResource(id = R.drawable.earth_asia),
//                            contentDescription = "Icon",
//                            tint = SoftGray2
//                        )
//                        Spacer(modifier = Modifier.width(12.dp))
//                        Text(
//                            text = "Language",
//                            style = MaterialTheme.typography.bodyMedium.copy(
//                                fontSize = 14.sp,
//                                fontWeight = FontWeight.SemiBold
//                            )
//                        )
//                    }
//                    Icon(
//                        modifier = Modifier
//                            .width(20.dp)
//                            .height(20.dp),
//                        painter = painterResource(id = R.drawable.ic_baseline_keyboard_arrow_right_24),
//                        contentDescription = "Icon"
//                    )
//                }
//
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.Center
//                ) {
//                    TextButton(onClick = { loginViewModel.logout(
//                        onSuccess = {
//                            navController.navigate(Routes.Login.routes) {
//                                popUpTo(Routes.Home.routes) {
//                                    inclusive = true
//                                }
//                            }
//                            Log.d("Logout", "Logout Success")
//                        },
//                        onError = { errorMessage ->
//                            // Handle logout error, show an error message or perform any other action
//                            Log.d("Logout", "Logout error")
//                        }
//                    ) }) {
//                        Text(
//                            text = "Log Out",
//                            style = MaterialTheme.typography.bodyMedium.copy(
//                                fontWeight = FontWeight.SemiBold,
//                                color = Ruby
//                            )
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Preview
//@Composable
//fun BookmarksScreenPreview() {
//    BookmarksScreen()
//}
