package com.celvine.deb.esail.bby.presentation.screen

import User
import android.net.Uri
import android.service.autofill.UserData
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.celvine.deb.esail.bby.R
import com.celvine.deb.esail.bby.common.theme.*
import com.celvine.deb.esail.bby.data.model.ProfileItemModel
import com.celvine.deb.esail.bby.data.model.UserResponse
import com.celvine.deb.esail.bby.data.sources.ProfileItem
import com.celvine.deb.esail.bby.data.viewmodels.LoginViewModel
import com.celvine.deb.esail.bby.route.Routes
import retrofit2.http.Body

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, loginViewModel: LoginViewModel) {
    LaunchedEffect(Unit) {
        loginViewModel.getUser(
            onSuccess = {
            // Login successful, navigate to TokenScreen
            Log.d("login", "login success")
        },
            onError = { errorMessage ->
                // Handle login error
                // Show an error message or perform any other action
            })
    }

    Scaffold { paddingValues ->
        val padding = paddingValues
        Surface(
            color = BgColorNew,
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(top=20.dp)
                    .fillMaxSize()
                    .background(color = BgColorNew)
            ) {
                item {
                    Header(loginViewModel.userResponse, loginViewModel)
//                Body()
                    val context = LocalContext.current
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(Routes.OnBoarding2.routes) {
                                }
                            }
                            .padding(horizontal = 20.dp, vertical = 15.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row {
                            Icon(
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp),
                                painter = painterResource(id = R.drawable.captain),
                                contentDescription = "Icon",
                                tint = SoftGray2
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Change Allergen",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                        Icon(
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp),
                            painter = painterResource(id = R.drawable.ic_baseline_keyboard_arrow_right_24),
                            contentDescription = "Icon"
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                Toast.makeText(context, "Featured Development", Toast.LENGTH_SHORT).show()
                            }
                            .padding(horizontal = 20.dp, vertical = 15.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row {
                            Icon(
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp),
                                painter = painterResource(id = R.drawable.earth_asia),
                                contentDescription = "Icon",
                                tint = SoftGray2
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Language",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                        Icon(
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp),
                            painter = painterResource(id = R.drawable.ic_baseline_keyboard_arrow_right_24),
                            contentDescription = "Icon"
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextButton(onClick = { loginViewModel.logout(
                            onSuccess = {
                                navController.navigate(Routes.Login.routes) {
                                    popUpTo(Routes.Home.routes) {
                                        inclusive = true
                                    }
                                }
                                Log.d("Logout", "Logout Success")
                            },
                            onError = { errorMessage ->
                                // Handle logout error, show an error message or perform any other action
                                Log.d("Logout", "Logout error")
                            }
                        ) }) {
                            Text(
                                text = "Log Out",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = Ruby
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ImageProfile(userResponse: UserResponse, loginViewModel: LoginViewModel) {
    val imageUri = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val painter: Painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(data = if (imageUri.value.isEmpty()) imageUri.value else userResponse.data.avatar)
            .apply(block = fun ImageRequest.Builder.() {
                crossfade(true)
                placeholder(R.drawable.profile)
            }).build()
    )
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { imageUri.value = it.toString() }
        uri?.let { loginViewModel.uploadProfilePicture(
            imageUri = it,
            onSuccess = { Log.d("Upload", "Upload success") },
            onError = { /* Handle error */ }
        )}
    }
    Box(
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
            .border(width = 2.dp, color = DarkGreen, shape = CircleShape)
            .clickable { launcher.launch("image/*") }
    ) {
        Image(
            painter = painter,
            contentDescription = "Image Profile",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(120.dp)
        )
    }
}




@Composable
fun ItemMenuProfile(item: ProfileItemModel, navController: NavController) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                when (item.Id) {
                    1 -> navController.navigate(Routes.OnBoarding2.routes) {
                        popUpTo(Routes.Profile.routes) {
                            inclusive = true
                        }
                    }
                    2 -> Toast.makeText(context, "Clicked item with ID 2", Toast.LENGTH_SHORT).show()
                }
            }
            .padding(horizontal = 20.dp, vertical = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Icon(
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp),
                painter = painterResource(id = item.Icon),
                contentDescription = "Icon",
                tint = SoftGray2
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = item.Label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
        Icon(
            modifier = Modifier
                .width(20.dp)
                .height(20.dp),
            painter = painterResource(id = R.drawable.ic_baseline_keyboard_arrow_right_24),
            contentDescription = "Icon"
        )
    }
}

@Composable
fun Header(userResponse: UserResponse?, loginViewModel: LoginViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = BgColorNew)
            .padding(12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (userResponse != null) {
            ImageProfile(userResponse, loginViewModel)
        }
        Spacer(modifier = Modifier.height(12.dp))
        if (userResponse != null) {
            Text(
                text = userResponse.data.name,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        if (userResponse != null) {
            Text(
                text = userResponse.data.email,
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Body() {
    val navController = rememberNavController()
    Column(modifier = Modifier.padding(20.dp)) {
        Card(colors = CardDefaults.cardColors(containerColor = White)) {
            ProfileItem.data.forEach { item ->
                ItemMenuProfile(item = item, navController = navController)
            }
        }
    }
}