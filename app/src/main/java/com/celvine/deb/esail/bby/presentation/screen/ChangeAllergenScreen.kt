package com.celvine.deb.esail.bby.presentation.screen

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import com.celvine.deb.esail.bby.common.theme.BgColorNew
import com.celvine.deb.esail.bby.common.theme.ButtonColor
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.celvine.deb.esail.bby.R
import com.celvine.deb.esail.bby.common.theme.White
import com.celvine.deb.esail.bby.data.model.UpdateUserRequest
import com.celvine.deb.esail.bby.data.viewmodels.LoginViewModel
import com.celvine.deb.esail.bby.data.viewmodels.ProfileViewModel
import com.celvine.deb.esail.bby.presentation.components.GridItem
import com.celvine.deb.esail.bby.route.Routes

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ChangeAllergenScreen(navController: NavController, loginViewModel: LoginViewModel, profileViewModel: ProfileViewModel) {

    val userAllergenIds by loginViewModel.userAllergenIds.observeAsState(emptyList())
    var isLoading by remember { mutableStateOf(false) }  // Loading state
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        loginViewModel.getUser(
            onSuccess = {
                loginViewModel.hasAllergens()
                // Login successful, navigate to TokenScreen
                Log.d("getUser", "getUser success")
            },
            onError = { errorMessage ->
                // Handle login error
                // Show an error message or perform any other action
            })
    }
    val customIndices = listOf(30, 2, 1, 16, 24, 114, 17, 119, 85, 18, 120, 61)
    val customTexts = listOf("Milk", "Soy", "Pork", "Egg", "Shrimp", "Peanut", "Beef", "Oyster", "Fish", "Chicken", "Sesame", "Wheat" )

//    val initialSelection = remember { mutableStateListOf<Int>() }

    LaunchedEffect(Unit) {
        profileViewModel.setInitialSelectedIcons(userAllergenIds)
    }
    BackHandler {
        profileViewModel.clearSelectedIcons()
        profileViewModel.setInitialSelectedIcons(userAllergenIds)
        navController.popBackStack()
    }
//    val currentSelection = remember { mutableStateListOf<Int>().apply { addAll(profileViewModel.selectedIconIds) } }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BgColorNew)
//            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.choose_allergen_title),
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Box(
                modifier = Modifier
                    .shadow(6.dp, shape = RoundedCornerShape(10))
                    .background(color = Color.White)
                    .padding(top = 16.dp, bottom = 16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = stringResource(id = R.string.choose_allergen_sub_tittle))
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyVerticalGrid(columns = GridCells.Fixed(4)) {
                        items(customIndices.size) { idx ->
                            val index = customIndices[idx]
                            val text = customTexts[idx]
                            val imageResId = when (index) {
                                30 -> R.drawable.milk_icon
                                2 -> R.drawable.soy_icon
                                1 -> R.drawable.pork_icon
                                16 -> R.drawable.egg_icon
                                24 -> R.drawable.shrimp_icon
                                114 -> R.drawable.peanut_icon
                                17 -> R.drawable.beef_icon
                                119 -> R.drawable.oyster
                                85 -> R.drawable.fish_icon
                                18 -> R.drawable.chicken
                                120 -> R.drawable.sesame
                                61 -> R.drawable.wheat
                                else -> R.drawable.wheat
                            }
                            val isSelected = remember {
                                mutableStateOf(
                                    index in profileViewModel.selectedIconIds ||
                                            index in userAllergenIds
                                )
                            }
//                            val isSelected = remember { mutableStateOf(index in profileViewModel.selectedIconIds) }
                            Box(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .clickable {
                                        isSelected.value = !isSelected.value
                                        if (isSelected.value) {
                                            profileViewModel.addIconId(index)
//                                            currentSelection.add(index)
                                        } else {
                                            profileViewModel.removeIconId(index)
//                                            currentSelection.remove(index)
                                        }
                                        Log.d(
                                            "Selected Allergens",
                                            "Selected allergens: ${profileViewModel.selectedIconIds}"
                                        )
                                    }

                            ) {
                                GridItem(index = index, imageResId = imageResId, isSelected = isSelected.value,text = text)
                            }
                        }
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 32.dp, top = 24.dp, end = 32.dp, bottom = 64.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        profileViewModel.clearSelectedIcons()  // Clear selected icons
                        profileViewModel.setInitialSelectedIcons(userAllergenIds)  // Reset to initial state
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .shadow(5.dp, shape = CircleShape)
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = White)
                ) {
                    Text(text = stringResource(id = R.string.back), color = Color.Black)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = {
                        isLoading = true
                        // First, send an empty list
                        val emptyRequest = UpdateUserRequest(
                            name = loginViewModel.userResponse.value?.data?.name,
                            allergens = emptyList()
                        )
                        profileViewModel.updateUsers(
                            emptyRequest,
                            onSuccess = {
                                // Then, send the updated allergen list
                                val allergensList = profileViewModel.selectedIconIds
                                Log.d("Selected Allergens", "Selected allergens: $allergensList")
                                val request = UpdateUserRequest(
                                    name = loginViewModel.userResponse.value?.data?.name,
                                    allergens = allergensList
                                )
                                profileViewModel.updateUsers(
                                    request,
                                    onSuccess = {
                                        isLoading = false
                                        Log.d("UpdateUser", "UpdateUser success ${loginViewModel.userResponse.value?.data}")
                                        navController.navigate(Routes.Profile.routes) {
                                            popUpTo(Routes.Profile.routes) {
                                                inclusive = true
                                            }
                                        }
                                        Toast.makeText(context, context.getString(R.string.success_change_allergen), Toast.LENGTH_SHORT).show()
                                    },
                                    onError = { errorMessage ->
                                        isLoading = false
                                        Toast.makeText(context, context.getString(R.string.failed_change_allergen), Toast.LENGTH_SHORT).show()
                                    }
                                )
                            },
                            onError = { errorMessage ->
                                isLoading = false
                                Toast.makeText(context, context.getString(R.string.failed_change_allergen), Toast.LENGTH_SHORT).show()
                            }
                        )
                    },
//                    enabled = hasChanges.value,
                    modifier = Modifier
                        .shadow(5.dp, shape = CircleShape)
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ButtonColor
                    )
                ) {
                    Text(text = stringResource(id = R.string.save), color =  Color.White)
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