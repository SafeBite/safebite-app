package com.celvine.deb.esail.bby.presentation.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.celvine.deb.esail.bby.R
import com.celvine.deb.esail.bby.common.theme.*
import com.celvine.deb.esail.bby.data.model.AccountVerificationOTP
import com.celvine.deb.esail.bby.data.viewmodels.LoginViewModel
import com.celvine.deb.esail.bby.presentation.components.PasswordTextField
import com.celvine.deb.esail.bby.presentation.components.PrimaryButton
import com.celvine.deb.esail.bby.route.Routes
import com.celvine.deb.esail.bby.ui.components.PrimaryTextField
import kotlin.reflect.KFunction1

@SuppressLint("SuspiciousIndentation")
@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel) {
    val scrollState = rememberScrollState()
    val authType = remember {
        mutableStateOf("email")
    }
    val emailState = remember { mutableStateOf(TextFieldValue()) }
    val passwordState = remember { mutableStateOf(TextFieldValue()) }

    Surface(
        color = BgColorNew,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .background(color = BgColorNew)
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            WelcomeText()
            Spacer(modifier = Modifier.height(20.dp))

            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = if (authType.value == "email") stringResource(id = R.string.email) else stringResource(
                    id = R.string.phone_number
                ),
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp)
            )
            Spacer(modifier = Modifier.height(3.dp))
            PrimaryTextField(
                placeholder = stringResource(id = R.string.email),
                value = emailState.value,
                onValueChange = { emailState.value = it }
            )

            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = stringResource(id = R.string.password),
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp)
            )
            Spacer(modifier = Modifier.height(3.dp))
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
                            color = SoftGray2
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(5.dp))
            PrimaryButton(text = stringResource(id = R.string.login), onClick = {
                val email = emailState.value.text
                val password = passwordState.value.text

                loginViewModel.login(email, password,
                    onSuccess = {
                        loginViewModel.getUser(                    onSuccess = {
                            // OTP verification successful, proceed with login
                            Log.d("Get Users", "Get Users success")
                        },
                            onError = { errorMessage ->
                                // Handle OTP verification error
                                // Show an error message or perform any other action
                            })
                        // OTP verification successful, proceed with login
                        Log.d("login", "login success")
                        navController.navigate(Routes.OnBoarding1.routes)
                    },
                    onError = { errorMessage ->
                        // Handle OTP verification error
                        // Show an error message or perform any other action
                    }
                )
            })



            Spacer(modifier = Modifier.height(20.dp))

            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Don't have an account ?",
                    style = MaterialTheme.typography.bodySmall.copy(color = SoftGray2)
                )
                Spacer(modifier = Modifier.width(5.dp))
                TextButton(onClick = {
                    navController.navigate(Routes.Register.routes) {
                        popUpTo(Routes.Login.routes) {
                            inclusive = true
                        }
                    }
                }) {
                    Text(
                        text = stringResource(id = R.string.sign_up),
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

@Composable
fun WelcomeText() {
    Column {
        Text(
            text = "Welcome Back 👋",
            style = MaterialTheme.typography.headlineSmall.copy(
                color = ButtonColor,
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
        Spacer(modifier = Modifier.height(7.dp))
        Text(
            text = "We happy to see you again, To use your account, your should login first",
            style = MaterialTheme.typography.bodySmall.copy(color = SoftGray2)
        )
    }
}

@Composable
fun Tabby(authType: MutableState<String>) {
    var currentActive by remember {
        mutableStateOf(0)
    }

    fun setCurrentActive(index: Int) {
        currentActive = index

        if (index == 1) {
            authType.value = "phone"
        } else {
            authType.value = "email"
        }
    }
    Card(
        modifier = Modifier
            .fillMaxWidth(), shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .background(SoftGray)
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TabbyCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), label = stringResource(id = R.string.email_address),
                isActive = currentActive == 0,
                index = 0,
                setCurrentActive = ::setCurrentActive
            )
            Spacer(modifier = Modifier.width(5.dp))
            TabbyCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), label = stringResource(id = R.string.phone_number),
                isActive = currentActive == 1,
                index = 1,
                setCurrentActive = ::setCurrentActive
            )
        }
    }
}

@Composable
fun TabbyCard(
    modifier: Modifier,
    label: String,
    isActive: Boolean = false,
    index: Int,
    setCurrentActive: KFunction1<Int, Unit>
) {
    Button(
        modifier = modifier,
        onClick = { setCurrentActive(index) },
        shape = Shapes.medium,
        colors = ButtonDefaults.buttonColors(containerColor = if (isActive) White else Transparant),
        elevation = ButtonDefaults.buttonElevation(0.dp),
    ) {
        Text(
            text = label,
            textAlign = TextAlign.Center,
            style = ButtonStyle.copy(color = Dark)
        )
    }
}
