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
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.celvine.deb.esail.bby.R
import com.celvine.deb.esail.bby.common.LoginState
import com.celvine.deb.esail.bby.common.RegisterState
import com.celvine.deb.esail.bby.common.theme.BgColorNew
import com.celvine.deb.esail.bby.common.theme.BlackText
import com.celvine.deb.esail.bby.common.theme.ButtonColor
import com.celvine.deb.esail.bby.data.viewmodels.RegisterViewModel
import com.celvine.deb.esail.bby.presentation.components.Loading
import com.celvine.deb.esail.bby.presentation.components.PasswordTextField
import com.celvine.deb.esail.bby.presentation.components.PrimaryButton
import com.celvine.deb.esail.bby.route.Routes
import com.celvine.deb.esail.bby.presentation.components.PrimaryTextField

@Composable
fun RegisterScreen(navController: NavController, registerViewModel: RegisterViewModel) {

    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val uiState by registerViewModel.uiState.observeAsState(RegisterState())

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
                value = uiState.name,
                onValueChange = { registerViewModel.updateName(it) }
            )
            uiState.nameError?.let { error ->
                Text(
                    text = error,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
            PrimaryTextField(
                placeholder = stringResource(id = R.string.email),
                value = uiState.email,
                onValueChange = { registerViewModel.updateEmail(it) }
            )
            uiState.emailError?.let { error ->
                Text(
                    text = error,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(18.dp))
            PasswordTextField(
                placeholder = stringResource(id = R.string.password),
                value = uiState.password,
                onValueChange = { registerViewModel.updatePassword(it) }
            )
            uiState.passwordError?.let { error ->
                Text(
                    text = error,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(15.dp))
            Spacer(modifier = Modifier.height(20.dp))
            PrimaryButton(
                text = stringResource(id = R.string.register),
                onClick = {
                    registerViewModel.setLoading(true)
                    if (registerViewModel.isValidName(uiState.name.text) && registerViewModel.isValidEmail(uiState.email.text) && registerViewModel.isValidPassword(uiState.password.text)) {
                        val email = uiState.email.text
                        val password = uiState.password.text
                        val name = uiState.name.text
                        registerViewModel.registerUser(name, email, password,
                            onSuccess = {
                                Log.d("register", "Register success, calling sendActivationOTP")
                                registerViewModel.sendActivationOTP(email,
                                    onSuccess = {
                                        Log.d("OTP", "OTP sent success")
                                        navController.navigate(Routes.Token.routes)
                                        registerViewModel.setLoading(false)
                                    },
                                    onError = {
                                        registerViewModel.setLoading(false)
                                        Log.d("OTP", "OTP sent error")
                                        Toast.makeText(context, "Failed send OTP", Toast.LENGTH_SHORT).show()
                                    }
                                )
                                Log.d("register", "register success")
                            },
                            onError = {
                                registerViewModel.setLoading(false)
                                Toast.makeText(context, context.getString(R.string.invalidRegister), Toast.LENGTH_SHORT).show()
                            }
                        )
                    } else {
                        Toast.makeText(context, context.getString(R.string.invalidRegisterFormat), Toast.LENGTH_SHORT).show()
                        registerViewModel.updateName(uiState.name)
                        registerViewModel.updateEmail(uiState.email)
                        registerViewModel.updatePassword(uiState.password)
                    }
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
        if (uiState.isLoading) {
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

