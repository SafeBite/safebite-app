package com.celvine.deb.esail.bby.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.celvine.deb.esail.bby.R
import com.celvine.deb.esail.bby.common.Capitalize
import com.celvine.deb.esail.bby.common.UiState
import com.celvine.deb.esail.bby.common.theme.*
import com.celvine.deb.esail.bby.data.model.CourseModel
import com.celvine.deb.esail.bby.data.viewmodels.*
import com.celvine.deb.esail.bby.di.Injection
import com.celvine.deb.esail.bby.presentation.components.ExpandedText
import com.celvine.deb.esail.bby.presentation.components.PrimaryOutlineButton

@Composable
fun DetailScreen(
    navController: NavController,
    id: Int,
    contentViewModel: ContentViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelContentFactory(
            Injection.provideContentRepository()
        )
    ),
    wishListViewModel: WishlistViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelWishListFactory(
            Injection.provideWishlistRepository()
        )
    ),
    detailViewModel: DetailViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelDetailFactory(
            Injection.provideDetailRepository()
        )
    ),
    cartViewModel: CartViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelCartListFactory(
            Injection.provideCartRepository()
        )
    )
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White2)
    ) {
        item {
            detailViewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
                when (uiState) {
                    is UiState.Loading -> {
                        detailViewModel.getById(id = id)
                    }
                    is UiState.Success -> {
                        BannerCourse(navController = navController, image = uiState.data.banner)
                        DetailCourse(
                            wishListViewModel = wishListViewModel,
                            cartViewModel = cartViewModel,
                            detail = uiState.data
                        )
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
fun BannerCourse(navController: NavController, image: String) {
    Box {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .crossfade(true).build(),
            contentDescription = stringResource(id = R.string.image_profile),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(containerColor = White, contentColor = Dark)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = stringResource(id = R.string.back)
                    )
                    Text(text = stringResource(id = R.string.back))
                }
            }
        }
    }
}

@Composable
fun DetailCourse(
    wishListViewModel: WishlistViewModel,
    cartViewModel: CartViewModel,
    detail: CourseModel
) {
    wishListViewModel.isAdded(detail.id)
    cartViewModel.isAdded(detail.id)
    val isWishlist by wishListViewModel.inWishlist.collectAsState()
    Card(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = detail.category, style = MaterialTheme.typography.labelMedium.copy(
                        color = MaximumYellowRed,
                        fontWeight = FontWeight.SemiBold, fontSize = 12.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = Capitalize(detail.title),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Dark,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            Spacer(modifier = Modifier.height(12.dp))
            ExpandedText(text = detail.desc)
            Spacer(modifier = Modifier.height(6.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.width(5.dp))
                PrimaryOutlineButton(modifier = Modifier.weight(1f),
                    label = if (isWishlist) stringResource(id = R.string.remove) else stringResource(
                        id = R.string.add_to, stringResource(id = R.string.bookmarks)
                    ),
                    onClick = {
                        if (isWishlist) {
                            wishListViewModel.removeFromWishlist(detail.id)
                        } else {
                            wishListViewModel.addToWishlist(detail.id)
                        }
                    })
            }
        }
    }
}