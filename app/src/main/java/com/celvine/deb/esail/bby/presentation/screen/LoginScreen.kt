package com.celvine.deb.esail.bby.presentation.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.celvine.deb.esail.bby.R
import com.celvine.deb.esail.bby.common.theme.*
import com.celvine.deb.esail.bby.data.viewmodels.LoginViewModel
import com.celvine.deb.esail.bby.presentation.components.Loading
import com.celvine.deb.esail.bby.presentation.components.PasswordTextField
import com.celvine.deb.esail.bby.presentation.components.PrimaryButton
import com.celvine.deb.esail.bby.route.Routes
import com.celvine.deb.esail.bby.presentation.components.PrimaryTextField

@SuppressLint("SuspiciousIndentation")
@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val authType = remember {
        mutableStateOf("email")
    }
    val emailState = remember { mutableStateOf(TextFieldValue()) }
    val passwordState = remember { mutableStateOf(TextFieldValue()) }
    var isLoading by remember { mutableStateOf(false) }

    // Function to toggle loading state
    val toggleLoading: (Boolean) -> Unit = { loading ->
        isLoading = loading
    }
    Surface(
        color = BgColorNew,
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLoading) {
            Loading()
        } else {
            Column(
                modifier = Modifier
                    .background(color = BgColorNew)
                    .verticalScroll(scrollState)
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(40.dp))
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
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(id = R.string.login),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold,fontSize = 30.sp),
                    color = BlackText,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Spacer(modifier = Modifier.height(3.dp))
                PrimaryTextField(
                    placeholder = stringResource(id = R.string.email),
                    value = emailState.value,
                    onValueChange = { emailState.value = it }
                )

                Spacer(modifier = Modifier.height(18.dp))
                PasswordTextField(
                    placeholder = stringResource(id = R.string.password),
                    value = passwordState.value,
                    onValueChange = { passwordState.value = it }
                )

                Spacer(modifier = Modifier.height(5.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(
                        onClick = {},
                    ) {
                        Text(
                            text = stringResource(id = R.string.forgot_password),
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 15.sp,
                                color = BlackText
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(5.dp))
                PrimaryButton(text = stringResource(id = R.string.login), onClick = {
                    toggleLoading(true)
                    val email = emailState.value.text
                    val password = passwordState.value.text

                    loginViewModel.performLogin(email, password,
                        onSuccess = {
                            loginViewModel.getUser(
                                onSuccess = {
                                    if (loginViewModel.hasAllergens()) {
                                        navController.navigate(Routes.Home.routes){
                                        }
                                    } else {
                                        navController.navigate(Routes.OnBoarding1.routes){
                                        }
                                    }
                                },
                                onError = { errorMessage ->
                                    toggleLoading(false)
                                    Toast.makeText(context, "$errorMessage" +
                                            "Try again or check your connection", Toast.LENGTH_SHORT).show()
                                })
                        },
                        onError = { errorMessage ->
                            toggleLoading(false)
                            Toast.makeText(context, "$errorMessage" +
                                    "Try again or check your connection", Toast.LENGTH_SHORT).show()
                        }
                    )
                })
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.dont_have_acc),
                        style = MaterialTheme.typography.bodySmall.copy(color = BlackText)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    TextButton(onClick = {
                        navController.navigate(Routes.Register.routes) {
//                            popUpTo(Routes.Login.routes) {
//                                inclusive = true
//                            }
                        }
                    }) {
                        Text(
                            text = stringResource(id = R.string.register),
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = ButtonColor,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }
    }
}


