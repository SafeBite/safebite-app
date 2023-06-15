package com.celvine.deb.esail.bby

import User
import android.content.Context
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.celvine.deb.esail.bby.data.model.UserResponse
import com.celvine.deb.esail.bby.data.viewmodels.LoginViewModel
import com.celvine.deb.esail.bby.data.viewmodels.RegisterViewModel
import com.celvine.deb.esail.bby.presentation.screen.*
import com.celvine.deb.esail.bby.route.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartSail(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    registerViewModel: RegisterViewModel = remember { RegisterViewModel() },
    context: Context// Provide an instance of RegisterViewModel
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val loginViewModel = remember { LoginViewModel(context) }
    Scaffold { paddingValues ->
        val padding = paddingValues
        NavHost(navController = navController, startDestination = Routes.Login.routes) {
            composable(Routes.Login.routes) {
                LoginScreen(navController = navController, loginViewModel = loginViewModel)
            }
            composable(Routes.Register.routes) {
                RegisterScreen(navController = navController, registerViewModel = registerViewModel)
            }
            composable(Routes.Token.routes) {
                TokenScreen(navController = navController, loginViewModel = loginViewModel)
            }
            composable(Routes.OnBoarding1.routes) {
                OnBoardingScreen1(navController = navController)
            }
            composable(Routes.OnBoarding2.routes) {
                OnBoardingScreen2(navController = navController)
            }
            composable(Routes.Dashboard.routes) {
                DashboardScreen(context = context)
            }
        }
    }
}

//@Preview
//@Composable
//fun StartSailPreview() {
//    val context = LocalContext.current // Get the current context
//    StartSail(context = context)
//}