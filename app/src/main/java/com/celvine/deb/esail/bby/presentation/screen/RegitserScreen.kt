package com.celvine.deb.esail.bby.presentation.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.celvine.deb.esail.bby.R
import com.celvine.deb.esail.bby.common.theme.BgColorNew
import com.celvine.deb.esail.bby.common.theme.ButtonColor
import com.celvine.deb.esail.bby.common.theme.DodgerBlue
import com.celvine.deb.esail.bby.common.theme.SoftGray2
import com.celvine.deb.esail.bby.data.viewmodels.RegisterViewModel
import com.celvine.deb.esail.bby.presentation.components.AuthSocial
import com.celvine.deb.esail.bby.presentation.components.PasswordTextField
import com.celvine.deb.esail.bby.presentation.components.PrimaryButton
import com.celvine.deb.esail.bby.route.Routes
import com.celvine.deb.esail.bby.ui.components.PrimaryTextField

@Composable
fun RegisterScreen(navController: NavController, registerViewModel: RegisterViewModel) {
    val scrollState = rememberScrollState()

    // State variables for the registration form fields
    val emailState = remember { mutableStateOf(TextFieldValue()) }
    val passwordState = remember { mutableStateOf(TextFieldValue()) }
    val nameState = remember { mutableStateOf(TextFieldValue()) }

    Surface(
        color = BgColorNew,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(16.dp)
                .background(color = BgColorNew)
        ) {
            WelcomeTextx()
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(id = R.string.user_name),
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp)
            )
            Spacer(modifier = Modifier.height(3.dp))
            PrimaryTextField(
                placeholder = stringResource(id = R.string.user_name),
                value = nameState.value,
                onValueChange = { nameState.value = it }
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = stringResource(id = R.string.email),
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

            Spacer(modifier = Modifier.height(15.dp))
//        PasswordTextField(placeholder = stringResource(id = R.string.confirm_password))

            Spacer(modifier = Modifier.height(20.dp))
            PrimaryButton(
                text = stringResource(id = R.string.register),
                onClick = {
                    val email = emailState.value.text
                    val password = passwordState.value.text
                    val name = nameState.value.text
                    registerViewModel.registerUser(name, email, password,
                        onSuccess = {
                            // Login successful, navigate to TokenScreen
                            Log.d("login", "login success")
                            navController.navigate(Routes.Token.routes)
                        },
                        onError = { errorMessage ->
                            // Handle login error
                            // Show an error message or perform any other action
                        }
                    )
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Already have an account?",
                    style = MaterialTheme.typography.bodyMedium.copy(color = SoftGray2)
                )
                Spacer(modifier = Modifier.width(5.dp))
                TextButton(onClick = {
                    navController.navigate(Routes.Login.routes) {
                        popUpTo(Routes.Register.routes) {
                            inclusive = true
                        }
                    }
                }) {
                    Text(
                        text = stringResource(id = R.string.sign_in),
                        style = MaterialTheme.typography.labelMedium.copy(
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
fun WelcomeTextx() {
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
            style = MaterialTheme.typography.bodyMedium.copy(color = SoftGray2)
        )
    }
}