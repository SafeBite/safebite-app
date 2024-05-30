package com.celvine.deb.esail.bby.presentation.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.celvine.deb.esail.bby.data.model.AccountVerificationOTP
import com.celvine.deb.esail.bby.data.viewmodels.RegisterViewModel
import com.celvine.deb.esail.bby.presentation.components.PrimaryButton
import com.celvine.deb.esail.bby.route.Routes
import com.celvine.deb.esail.bby.presentation.components.PrimaryTextField

@Composable
fun TokenScreen(navController: NavController, registerViewModel: RegisterViewModel) {

    var isLoading by remember { mutableStateOf(false) }  // Loading state
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val authType = remember {
        mutableStateOf("email")
    }
    val codeState = remember { mutableStateOf(TextFieldValue()) }
    val emailState = remember { mutableStateOf(TextFieldValue()) }

    Surface(
        color = BgColorNew,
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier
//                .background(color = BgColorNew)
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
            Spacer(modifier = Modifier.height(20.dp))
            PrimaryTextField(
                placeholder = stringResource(id = R.string.otp),
                value = codeState.value,
                onValueChange = { codeState.value = it }
            )

            Spacer(modifier = Modifier.height(30.dp))
            PrimaryButton(text = stringResource(id = R.string.sendOTP), onClick = {
                val email = emailState.value.text
                registerViewModel.sendActivationOTP(email,
                    onSuccess = {
                        // OTP sent successfully
                        // You can show a success message or perform any other action
                        Log.d("OTP", "OTP sent success")
                        Toast.makeText(context, "Sent OTP code to your email", Toast.LENGTH_SHORT).show()
                    },
                    onError = { errorMessage ->
                        Toast.makeText(context, "Cant sent OTP code, please check your email", Toast.LENGTH_SHORT).show()
                        // Handle OTP sending error
                        // Show an error message or perform any other action
                    }
                )
            })
            Spacer(modifier = Modifier.height(15.dp))
            PrimaryButton(text = stringResource(id = R.string.verifyOTP), onClick = {
                isLoading = true
                val email = emailState.value.text
                val code = codeState.value.text.toInt()
                val data: AccountVerificationOTP = AccountVerificationOTP(email, code)

                registerViewModel.verifyActivationOTP(data,
                    onSuccess = {
                        isLoading = false
                        Log.d("OTP", "OTP success")
                        navController.navigate(Routes.Login.routes){
                            popUpTo(Routes.Token.routes) {
                                inclusive = true
                            }
                        }
                    },
                    onError = { errorMessage ->
                        isLoading = false
                        Toast.makeText(context, "$errorMessage" +
                                "Your OTP code is not valid, please try again", Toast.LENGTH_SHORT).show()
                    })
            })

            Spacer(modifier = Modifier.height(20.dp))
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