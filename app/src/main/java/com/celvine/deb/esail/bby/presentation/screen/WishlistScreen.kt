package com.celvine.deb.esail.bby.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.celvine.deb.esail.bby.R
import com.celvine.deb.esail.bby.common.UiState
import com.celvine.deb.esail.bby.common.theme.BgColorNew
import com.celvine.deb.esail.bby.common.theme.White2
import com.celvine.deb.esail.bby.data.model.CourseModel
import com.celvine.deb.esail.bby.data.model.WishlistModel
import com.celvine.deb.esail.bby.data.viewmodels.DetailViewModel
import com.celvine.deb.esail.bby.data.viewmodels.ViewModelDetailFactory
import com.celvine.deb.esail.bby.data.viewmodels.ViewModelWishListFactory
import com.celvine.deb.esail.bby.data.viewmodels.WishlistViewModel
import com.celvine.deb.esail.bby.di.Injection
import com.celvine.deb.esail.bby.presentation.components.SimpleCardCourse
import com.celvine.deb.esail.bby.presentation.components.TopBar
import com.celvine.deb.esail.bby.route.Routes

@Composable
fun WishlistScreen(
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: WishlistViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelWishListFactory(
            Injection.provideWishlistRepository()
        )
    ),
    detailViewModel: DetailViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelDetailFactory(
            Injection.provideDetailRepository()
        )
    ),
) {
    Surface(
        color = BgColorNew,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            TopBar(title = "Bookmarks")
            viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
                when (uiState) {
                    is UiState.Loading -> {
                        viewModel.getAddedWishlist()
                    }
                    is UiState.Success -> {
                        if (uiState.data.wishlist.isNotEmpty()) {
                            LazyColumn(
                                modifier = Modifier
                                    .background(color = BgColorNew)
                                    .fillMaxHeight()
                                    .padding(
                                        start = 16.dp,
                                        end = 16.dp,
                                        top = 16.dp,
                                        bottom = paddingValues.calculateBottomPadding()
                                    ),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                items(uiState.data.wishlist.size, key = { it }) {
                                    WishListItem(
                                        detailViewModel = detailViewModel,
                                        navController = navController,
                                        wishlist = uiState.data.wishlist[it]
                                    )
                                }
                            }
                        }
                    }
                    is UiState.Error -> {
                        Text(text = stringResource(id = R.string.error))
                    }
                }
            }

        }
    }
}

@Composable
fun WishListItem(
    detailViewModel: DetailViewModel,
    navController: NavController,
    wishlist: WishlistModel
) {
    val item: CourseModel = detailViewModel.getDataById(wishlist.Id)
    SimpleCardCourse(item = item, navController = navController)
}