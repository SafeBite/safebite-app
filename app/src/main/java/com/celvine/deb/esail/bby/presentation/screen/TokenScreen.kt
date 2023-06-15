package com.celvine.deb.esail.bby.presentation.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.celvine.deb.esail.bby.R
import com.celvine.deb.esail.bby.common.theme.ButtonColor
import com.celvine.deb.esail.bby.common.theme.DodgerBlue
import com.celvine.deb.esail.bby.common.theme.SoftGray2
import com.celvine.deb.esail.bby.data.model.AccountVerificationOTP
import com.celvine.deb.esail.bby.data.viewmodels.LoginViewModel
import com.celvine.deb.esail.bby.presentation.components.PrimaryButton
import com.celvine.deb.esail.bby.route.Routes
import com.celvine.deb.esail.bby.ui.components.PrimaryTextField

@Composable
fun TokenScreen(navController: NavController, loginViewModel: LoginViewModel) {
    val scrollState = rememberScrollState()
    val authType = remember {
        mutableStateOf("email")
    }
    val codeState = remember { mutableStateOf(TextFieldValue()) }
    val emailState = remember { mutableStateOf(TextFieldValue()) }
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        WelcomeTextxx()
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
            text = if (authType.value == "email") stringResource(id = R.string.otp) else stringResource(
                id = R.string.phone_number
            ),
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp)
        )
        Spacer(modifier = Modifier.height(3.dp))
        PrimaryTextField(
            placeholder = stringResource(id = R.string.otp),
            value = codeState.value,
            onValueChange = { codeState.value = it }
        )

        Spacer(modifier = Modifier.height(15.dp))
        PrimaryButton(text = stringResource(id = R.string.sendOTP), onClick = {
            val email = emailState.value.text

            loginViewModel.sendActivationOTP(email,
                onSuccess = {
                    // OTP sent successfully
                    // You can show a success message or perform any other action
                    Log.d("OTP", "OTP sent success")
                },
                onError = { errorMessage ->
                    // Handle OTP sending error
                    // Show an error message or perform any other action
                }
            )
        })
        Spacer(modifier = Modifier.height(15.dp))
        PrimaryButton(text = stringResource(id = R.string.verifyOTP), onClick = {
            val email = emailState.value.text
            val code = codeState.value.text.toInt()
            val data: AccountVerificationOTP = AccountVerificationOTP(email, code)

            loginViewModel.verifyActivationOTP(data,
                onSuccess = {
                    Log.d("OTP", "OTP success")
                    navController.navigate(Routes.Login.routes){
                        popUpTo(Routes.Token.routes) {
                            inclusive = true
                        }
                    }
                },
                onError = { errorMessage ->
                    // Handle OTP sending error
                    // Show an error message or perform any other action
                })
        })

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun WelcomeTextxx() {
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