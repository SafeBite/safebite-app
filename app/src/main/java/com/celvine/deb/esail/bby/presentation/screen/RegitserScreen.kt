package com.celvine.deb.esail.bby.presentation.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.celvine.deb.esail.bby.common.theme.BgColorNew
import com.celvine.deb.esail.bby.common.theme.BlackText
import com.celvine.deb.esail.bby.common.theme.ButtonColor
import com.celvine.deb.esail.bby.data.viewmodels.RegisterViewModel
import com.celvine.deb.esail.bby.presentation.components.PasswordTextField
import com.celvine.deb.esail.bby.presentation.components.PrimaryButton
import com.celvine.deb.esail.bby.route.Routes
import com.celvine.deb.esail.bby.presentation.components.PrimaryTextField

@Composable
fun RegisterScreen(navController: NavController, registerViewModel: RegisterViewModel) {

    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current
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
                text = stringResource(id = R.string.register),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold,fontSize = 30.sp),
                color = BlackText,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Spacer(modifier = Modifier.height(3.dp))
            PrimaryTextField(
                placeholder = stringResource(id = R.string.user_name),
                value = nameState.value,
                onValueChange = { nameState.value = it }
            )

            Spacer(modifier = Modifier.height(18.dp))
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

            Spacer(modifier = Modifier.height(15.dp))
            Spacer(modifier = Modifier.height(20.dp))
            PrimaryButton(
                text = stringResource(id = R.string.register),
                onClick = {
                    isLoading = true
                    val email = emailState.value.text
                    val password = passwordState.value.text
                    val name = nameState.value.text
                    registerViewModel.registerUser(name, email, password,
                        onSuccess = {
                            registerViewModel.sendActivationOTP(email,
                                onSuccess = {
                                    isLoading = false
                                    // OTP sent successfully
                                    // You can show a success message or perform any other action
                                    Log.d("OTP", "OTP sent success")
                                    navController.navigate(Routes.Token.routes)
                                },
                                onError = { errorMessage ->
                                    isLoading = false
                                    Log.d("OTP", "OTP sent error")
                                    Toast.makeText(context, "$errorMessage" +
                                            "Try again or check your connection", Toast.LENGTH_SHORT).show()
                                }
                            )
                            // Login successful, navigate to TokenScreen
                            Log.d("register", "register success")
//                            navController.navigate(Routes.Token.routes)
                        },
                        onError = { errorMessage ->
                            isLoading = false
                            Toast.makeText(context, "$errorMessage" +
                                    "Try again or check your connection", Toast.LENGTH_SHORT).show()
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
                    text = stringResource(id = R.string.have_acc),
                    style = MaterialTheme.typography.bodyMedium.copy(color = BlackText)
                )
                Spacer(modifier = Modifier.width(2.dp))
                TextButton(onClick = {
                    navController.navigate(Routes.Login.routes) {
                        popUpTo(Routes.Register.routes) {
                            inclusive = true
                        }
                    }
                }) {
                    Text(
                        text = stringResource(id = R.string.login),
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = ButtonColor,
                            fontWeight = FontWeight.Bold
                        )
                    )
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