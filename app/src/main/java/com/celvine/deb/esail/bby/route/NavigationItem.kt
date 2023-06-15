package com.celvine.deb.esail.bby.route

import com.celvine.deb.esail.bby.R

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Home : NavigationItem(Routes.Home.routes, R.drawable.house_icon, "Home")
    object ScanFood : NavigationItem(Routes.ScanFood.routes, R.drawable.scan1, "ScanFood")
    object Wishlist : NavigationItem(Routes.Wishlist.routes, R.drawable.bookmark, "Bookmarks")
}
