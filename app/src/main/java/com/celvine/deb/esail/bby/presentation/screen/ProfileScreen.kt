package com.celvine.deb.esail.bby.presentation.screen

import android.net.Uri
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.celvine.deb.esail.bby.R
import com.celvine.deb.esail.bby.common.theme.*
import com.celvine.deb.esail.bby.data.model.UserResponse
import com.celvine.deb.esail.bby.data.viewmodels.LoginViewModel
import com.celvine.deb.esail.bby.route.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, loginViewModel: LoginViewModel) {

    var isLoading by remember { mutableStateOf(false) }  // Loading state
    LaunchedEffect(Unit) {
        loginViewModel.getUser(
            onSuccess = {
            // Login successful, navigate to TokenScreen
            Log.d("Get User", "Get User Success")
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
                    Header(loginViewModel.userResponse.value, loginViewModel)
                    val context = LocalContext.current
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(Routes.ChangeAllergenScreen.routes) {
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
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextButton(
                            onClick = {
                                isLoading = true
                                loginViewModel.logout(
                                    onSuccess = {
                                        isLoading = false
                                        navController.navigate(Routes.Login.routes) {
                                            popUpTo(Routes.Login.routes) {
                                                inclusive = true
                                            }
                                        }
                                        Log.d("Logout", "Logout Success")
                                    },
                                    onError = { errorMessage ->
                                        isLoading = false
                                        Toast.makeText(context, "$errorMessage" +
                                                "Try again or check your connection", Toast.LENGTH_SHORT).show()
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
    }
}


@Composable
fun ImageProfile(userResponse: UserResponse, loginViewModel: LoginViewModel) {
    val imageUri = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val painter: Painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(
                if (imageUri.value.isEmpty()) {
                    R.drawable.profile // Use placeholder drawable
                } else {
                    userResponse.data.avatar // Use user's avatar
                }
            )
            .apply {
                crossfade(true)
            }
            .build()
    )

//    val painter: Painter = rememberAsyncImagePainter(
//        ImageRequest.Builder(LocalContext.current)
//            .data(data = if (imageUri.value.isEmpty()) imageUri.value else userResponse.data.avatar)
//            .apply(block = fun ImageRequest.Builder.() {
//                crossfade(true)
//                placeholder(R.drawable.profile)
//            }).build()
//    )
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