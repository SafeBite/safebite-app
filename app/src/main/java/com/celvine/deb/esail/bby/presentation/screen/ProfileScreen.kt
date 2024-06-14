package com.celvine.deb.esail.bby.presentation.screen

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.celvine.deb.esail.bby.R
import com.celvine.deb.esail.bby.common.theme.*
import com.celvine.deb.esail.bby.data.model.UserResponse
import com.celvine.deb.esail.bby.data.viewmodels.LoginViewModel
import com.celvine.deb.esail.bby.data.viewmodels.ProfileViewModel
import com.celvine.deb.esail.bby.route.Routes

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, loginViewModel: LoginViewModel, profileViewModel: ProfileViewModel) {

    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }
    val userResponse by loginViewModel.userResponse.observeAsState()
    var showLogoutConfirmationDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        loginViewModel.getUser(
            onSuccess = {
                // Fetch user profile picture URI after getting user details
                Log.d("Get User", "Get User Success")
            },
            onError = { errorMessage ->
                // Handle login error
//                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            })
    }

//    LaunchedEffect(userResponse) {
//        userResponse?.data?.avatar?.let { avatarUrl ->
//            profileViewModel.setImageUri(Uri.parse(avatarUrl))
//        }
//    }
    Scaffold {
        Surface(
            color = BgColorNew,
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxSize()
                    .background(color = BgColorNew)
            ) {
                item {
                    Header(loginViewModel.userResponse.value, profileViewModel)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(Routes.EditProfile.routes)
//                                Toast.makeText(context, context.getString(R.string.future_development), Toast.LENGTH_SHORT).show()
                            }
                            .padding(horizontal = 20.dp, vertical = 15.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row {
                            Icon(
                                painter = painterResource(id = R.drawable.profile),
                                contentDescription = "Icon",
                                tint = GreenNew,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = stringResource(id = R.string.edit_profile),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_keyboard_arrow_right_24),
                            contentDescription = "Icon",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(Routes.ChangeAllergenScreen.routes)
                            }
                            .padding(horizontal = 20.dp, vertical = 15.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row {
                            Icon(
                                painter = painterResource(id = R.drawable.change_ic),
                                contentDescription = "Icon",
                                tint = GreenNew,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = stringResource(id = R.string.change_allergen),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_keyboard_arrow_right_24),
                            contentDescription = "Icon",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextButton(
                            onClick = { showLogoutConfirmationDialog = true }
                        ) {
                            Text(
                                text = stringResource(id =R.string.logout),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = Ruby
                                )
                            )
                        }
                    }
                }
            }
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(64.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        strokeWidth = 4.dp
                    )
                }
            }
        }
        if (showLogoutConfirmationDialog) {
            AlertDialog(
                onDismissRequest = { showLogoutConfirmationDialog = false },
                title = { Text("Are you sure you want to logout?") },
                confirmButton = {
                    TextButton(onClick = {
                        showLogoutConfirmationDialog = false
                        isLoading = true
                        loginViewModel.logout(
                            onSuccess = {
                                isLoading = false
                                profileViewModel.resetImageUri()
                                navController.navigate(Routes.Login.routes) {
                                    popUpTo(Routes.Login.routes) {
                                        inclusive = true
                                    }
                                }
                                Log.d("Logout", "Logout Success")
                            },
                            onError = { errorMessage ->
                                isLoading = false
                                Toast.makeText(
                                    context,
                                    "$errorMessage. Try again or check your connection",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Log.d("Logout", "Logout error")
                            }
                        )
                    }) {
                        Text(
                            text = "Yes",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.Black
                            )
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showLogoutConfirmationDialog = false
                    }) {
                        Text(text = "No",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color(0xFFFFA500), // Orange color
                                fontWeight = FontWeight.Bold)
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun ImageProfile(userResponse: UserResponse, profileViewModel: ProfileViewModel) {
    val imageUri by profileViewModel.imageUri.collectAsState()
    val painter = rememberAsyncImagePainter(
        R.drawable.safebite_splashscreen
    )

    Box(
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
            .background(color = Color.Transparent)
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
fun Header(userResponse: UserResponse?, profileViewModel: ProfileViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = BgColorNew)
            .padding(12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        userResponse?.let {
            ImageProfile(it, profileViewModel)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = it.data.name,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = it.data.email,
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                )
            )
        }
    }
}
