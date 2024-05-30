package com.celvine.deb.esail.bby.route

sealed class Routes(val routes: String) {
    object Login : Routes("login")
    object Register : Routes("register")
    object Home : Routes("home")
    object Search : Routes("search")
    object Profile : Routes("profile")
    object DetailFoodScreen : Routes("detailFood")
    object OnBoarding1 : Routes("onBoarding1")
    object OnBoarding2 : Routes("onBoarding2")
    object Token: Routes("token")
    object ScanFood : Routes("scanFood")
    object Loading : Routes("loading")
    object PredictionScreen : Routes("predictionScreen")
    object EmergencyScreen : Routes("emergencyScreen")
    object ChangeAllergenScreen : Routes("changeAllergenScreen")
    object MyCourse : Routes("my_courses")
    object Detail : Routes("detail/{id}") {
        fun createRoute(id: Int) = "detail/$id"
    }
}