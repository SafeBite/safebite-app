@file:OptIn(ExperimentalMaterial3Api::class)

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.celvine.deb.esail.bby.R
import com.celvine.deb.esail.bby.common.theme.BgColorNew
import com.celvine.deb.esail.bby.common.theme.ButtonColor
import com.celvine.deb.esail.bby.common.theme.DarkGreen
import com.celvine.deb.esail.bby.common.theme.Ruby
import com.celvine.deb.esail.bby.common.theme.SoftGray2
import com.celvine.deb.esail.bby.common.theme.White
import com.celvine.deb.esail.bby.data.model.UpdateUserNameRequest
import com.celvine.deb.esail.bby.data.model.UpdateUserRequest
import com.celvine.deb.esail.bby.data.viewmodels.LoginViewModel
import com.celvine.deb.esail.bby.data.viewmodels.ProfileViewModel
import com.celvine.deb.esail.bby.presentation.components.PrimaryTextField
import com.celvine.deb.esail.bby.route.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(navController: NavController, profileViewModel: ProfileViewModel, loginViewModel: LoginViewModel) {
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val userResponse by loginViewModel.userResponse.observeAsState()
    var newNameState by remember { mutableStateOf(TextFieldValue(userResponse?.data?.name ?: "")) }
    val imageUri by profileViewModel.imageUri.collectAsState()
    val painter: Painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(imageUri ?: userResponse?.data?.avatar ?: R.drawable.profile)
            .apply {
                crossfade(true)
            }
            .build()
    )

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            profileViewModel.setImageUri(it)
        }
    }

    Surface(
        color = BgColorNew,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BgColorNew)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
//                Box(
//                    modifier = Modifier
//                        .size(120.dp)
//                        .clip(CircleShape)
//                        .background(Color.White)
//                        .border(width = 2.dp, color = DarkGreen, shape = CircleShape)
//                        .clickable { launcher.launch("image/*") }
//                ) {
//                    Image(
//                        painter = painter,
//                        contentDescription = "Profile Image",
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier.size(120.dp)
//                    )
//                    Box(
//                        modifier = Modifier
//                            .align(Alignment.Center)
//                            .size(32.dp)
//                            .background(Color.Transparent)
//                            .clickable { launcher.launch("image/*") }
//                    ) {
//                        Icon(
//                            painter = painterResource(R.drawable.edit_ic),
//                            contentDescription = "Edit Icon",
//                            tint = DarkGreen,
//                            modifier = Modifier
//                                .alpha(0.8f)
//                                .size(20.dp)
//                                .align(Alignment.Center)
//                        )
//                    }
//                }
                Text(
                    text = stringResource(id = R.string.edit_profile),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp
                    )
                )
                Spacer(modifier = Modifier.height(32.dp))
                PrimaryTextField(
                    placeholder = stringResource(id = R.string.user_name),
                    value = newNameState,
                    onValueChange = { newNameState = it }
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 48.dp, start = 8.dp, end = 8.dp, bottom = 16.dp),
                ) {
                    Button(
                        onClick = {
                            navController.popBackStack()
                        },
                        modifier = Modifier
                            .shadow(5.dp, shape = CircleShape)
                            .weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = White)
                    ) {
                        Text(text = stringResource(id = R.string.back), color = Color.Black)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = {
                            isLoading = true
                            profileViewModel.updateUsersName(
                                UpdateUserNameRequest(name = newNameState.text),
                                onSuccess = {
                                    isLoading = false
                                    navController.navigate(Routes.Profile.routes) {
                                        popUpTo(Routes.Profile.routes) {
                                            inclusive = true
                                        }
                                    }
                                    Toast.makeText(context, context.getString(R.string.success_edit_profile), Toast.LENGTH_SHORT).show()
                                },
                                onError = {
                                    isLoading = false
                                    Toast.makeText(context, context.getString(R.string.failed_update_profile), Toast.LENGTH_SHORT).show()
                                }
                            )
                        },
                        modifier = Modifier
                            .shadow(5.dp, shape = CircleShape)
                            .weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ButtonColor
                        )
                    ) {
                        Text(text = stringResource(id = R.string.save), color = Color.White)
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
