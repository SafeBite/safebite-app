package com.celvine.deb.esail.bby.presentation

import android.app.Activity
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.celvine.deb.esail.bby.api.ApiConfig
import com.celvine.deb.esail.bby.data.repositories.FoodDetailRepo
import com.celvine.deb.esail.bby.data.repositories.FoodRepo
import com.celvine.deb.esail.bby.data.repositories.HospitalRepository
import com.celvine.deb.esail.bby.data.viewmodels.*
import com.celvine.deb.esail.bby.presentation.components.BottomNavigationBar
import com.celvine.deb.esail.bby.presentation.components.Loading
import com.celvine.deb.esail.bby.presentation.screen.*
import com.celvine.deb.esail.bby.route.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    context: Context) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val loginViewModel = remember { LoginViewModel(context) }
    val registerViewModel = remember { RegisterViewModel() }
    val foodViewModel = remember { FoodViewModel(repository = FoodRepo()) }
    val scanFoodViewModel = remember { ScanFoodViewModel(context) }
    val profileViewModel = remember { ProfileViewModel(context) }
    val emergencyViewModel = remember { EmergencyViewModel(hospitalRepository = HospitalRepository()) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(bottomBar = {
        if (currentRoute != null && !currentRoute.startsWith(Routes.Detail.routes) && !currentRoute.startsWith(Routes.DetailFoodScreen.routes) && currentRoute != Routes.Detail.routes && currentRoute != Routes.Search.routes && currentRoute != Routes.Profile.routes && currentRoute != Routes.OnBoarding2.routes && currentRoute != Routes.OnBoarding1.routes && currentRoute != Routes.Login.routes && currentRoute != Routes.Register.routes && currentRoute != Routes.Token.routes && currentRoute != Routes.ScanFood.routes && currentRoute != Routes.ChangeAllergenScreen.routes && currentRoute != Routes.DetailFoodScreen.routes && currentRoute != Routes.PredictionScreen.routes&& currentRoute != Routes.Loading.routes && currentRoute != Routes.EditProfile.routes) {
            BottomNavigationBar(navController)
        }
    }) { paddingValues ->
        NavHost(navController = navController, startDestination = Routes.Login.routes) {
            composable(Routes.Login.routes) {
                LoginScreen(navController = navController, loginViewModel = loginViewModel)
            }
            composable(Routes.Register.routes) {
                RegisterScreen(navController = navController, registerViewModel = registerViewModel)
            }
            composable(Routes.Token.routes) {
                TokenScreen(navController = navController, registerViewModel = registerViewModel)
            }
            composable(Routes.OnBoarding1.routes) {
                OnBoardingScreen1(navController = navController, foodViewModel = foodViewModel)
            }
            composable(Routes.OnBoarding2.routes) {
                OnBoardingScreen2(navController = navController, loginViewModel = loginViewModel, profileViewModel = profileViewModel)
            }
            composable(Routes.PredictionScreen.routes) {
                PredictionScreen(navController = navController, scanFoodViewModel = scanFoodViewModel, loginViewModel = loginViewModel)
            }
            composable(Routes.EmergencyScreen.routes) {
                EmergencyScreen(navController = navController, emergencyViewModel = emergencyViewModel)
            }
            composable(Routes.Profile.routes) {
                ProfileScreen(navController = navController, loginViewModel = loginViewModel, profileViewModel = profileViewModel)
            }
            composable(Routes.EditProfile.routes) {
                EditProfileScreen(navController = navController, loginViewModel = loginViewModel, profileViewModel = profileViewModel)
            }
            composable(Routes.Home.routes) {
                HomeScreen(navController = navController, paddingValues = paddingValues, loginViewModel = loginViewModel)
            }
            composable(Routes.Search.routes) {
                SearchScreen(navController = navController)
            }
            composable(Routes.ChangeAllergenScreen.routes) {
                ChangeAllergenScreen(navController = navController, loginViewModel = loginViewModel, profileViewModel = profileViewModel)
            }
            composable(Routes.ScanFood.routes) {
                ScanFoodScreen(navController = navController, scanFoodViewModel = scanFoodViewModel)
            }
            composable(Routes.Loading.routes) {
                Loading()
            }
            composable(
                route = "${Routes.DetailFoodScreen.routes}/{foodId}",
                arguments = listOf(navArgument("foodId") { type = NavType.IntType })
            ) { backStackEntry ->
                val foodId = backStackEntry.arguments?.getInt("foodId") ?: 0
                val foodDetailRepo = FoodDetailRepo(ApiConfig.instanceRetrofit)

                DetailFoodScreen(
                    navController = navController,
                    foodId = foodId,
                    foodDetailRepo = foodDetailRepo,
                    loginViewModel = loginViewModel
                )
            }
        }
        BackHandler(enabled = true) {
            when (currentRoute) {
                Routes.Login.routes -> {
                    (context as? Activity)?.finish()
                }
                Routes.OnBoarding1.routes -> {
                    (context as? Activity)?.moveTaskToBack(true)
                }
                Routes.Home.routes -> {
                    (context as? Activity)?.moveTaskToBack(true)
                }
                else -> {
                    navController.popBackStack()
                }
            }
        }
    }
}
