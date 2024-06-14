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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.celvine.deb.esail.bby.R
import com.celvine.deb.esail.bby.common.RegisterState
import com.celvine.deb.esail.bby.common.theme.BgColorNew
import com.celvine.deb.esail.bby.common.theme.BlackText
import com.celvine.deb.esail.bby.data.model.AccountVerificationOTP
import com.celvine.deb.esail.bby.data.viewmodels.RegisterViewModel
import com.celvine.deb.esail.bby.presentation.components.PrimaryButton
import com.celvine.deb.esail.bby.presentation.components.PrimaryTextField
import com.celvine.deb.esail.bby.route.Routes

@Composable
fun TokenScreen(navController: NavController, registerViewModel: RegisterViewModel) {

    val context = LocalContext.current
    val uiState by registerViewModel.uiState.observeAsState(RegisterState())
    val scrollState = rememberScrollState()

    Surface(
        color = BgColorNew,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
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
                text = stringResource(id = R.string.otpTitle),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, fontSize = 30.sp),
                color = BlackText,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Spacer(modifier = Modifier.height(3.dp))
            PrimaryTextField(
                placeholder = stringResource(id = R.string.email),
                value = uiState.email,
                onValueChange = {
                    registerViewModel.updateEmail(it)
                }
            )
            uiState.emailError?.let { error ->
                Text(
                    text = error,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            PrimaryTextField(
                placeholder = stringResource(id = R.string.otp),
                value = uiState.code,
                onValueChange = {
                    registerViewModel.updateCode(it)
                }
            )
            uiState.codeError?.let { error ->
                Text(
                    text = error,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            PrimaryButton(text = stringResource(id = R.string.sendOTP), onClick = {
                registerViewModel.setLoading(true)
                if (registerViewModel.isValidEmail(uiState.email.text)) {
                    registerViewModel.sendActivationOTP(uiState.email.text,
                        onSuccess = {
                            Log.d("OTP", "OTP sent success")
                            Toast.makeText(context, "Successfully sent OTP code", Toast.LENGTH_SHORT).show()
                            registerViewModel.setLoading(false)
                        },
                        onError = {
                            registerViewModel.setLoading(false)
                            Toast.makeText(context, context.getString(R.string.errorSentOTP), Toast.LENGTH_SHORT).show()
                        }
                    )
                } else {
                    registerViewModel.setLoading(false)
                    Toast.makeText(context, context.getString(R.string.invalidEmail), Toast.LENGTH_SHORT).show()
                    registerViewModel.updateEmail(uiState.email)
                }
            })
            Spacer(modifier = Modifier.height(15.dp))
            PrimaryButton(text = stringResource(id = R.string.verifyOTP), onClick = {
                registerViewModel.setLoading(true)
                if (registerViewModel.isValidEmail(uiState.email.text) && registerViewModel.isValidCode(uiState.code.text)) {
                    val email = uiState.email.text
                    val code = uiState.code.text.toInt()
                    val data = AccountVerificationOTP(email, code)

                    registerViewModel.verifyActivationOTP(data,
                        onSuccess = {
//                                registerViewModel.setLoading(false)
                            Log.d("OTP", "OTP success")
                            navController.navigate(Routes.Login.routes) {
                                popUpTo(Routes.Token.routes) {
                                    inclusive = true
                                }
                            }
                            registerViewModel.setLoading(false)
                        },
                        onError = {
                            registerViewModel.setLoading(false)
                            Toast.makeText(context, context.getString(R.string.errorOTP), Toast.LENGTH_SHORT).show()
                        })
                } else {
                    Toast.makeText(context, context.getString(R.string.invalidOTP), Toast.LENGTH_SHORT).show()
                    registerViewModel.updateEmail(uiState.email)
                    registerViewModel.updateEmail(uiState.code)
                }
            })
            Spacer(modifier = Modifier.height(20.dp))
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

