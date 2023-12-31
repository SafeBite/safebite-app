package com.celvine.deb.esail.bby.presentation.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.celvine.deb.esail.bby.R
import com.celvine.deb.esail.bby.common.theme.Dark
import com.celvine.deb.esail.bby.common.theme.Transparant
import com.celvine.deb.esail.bby.data.viewmodels.LoginViewModel
import com.celvine.deb.esail.bby.route.Routes

@Composable
fun GreetingBar(navController: NavController, loginViewModel: LoginViewModel) {

    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Welcome Back, ${loginViewModel.userResponse?.data?.name}",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp
                )
            )
        }
        FilledIconButton(
            onClick = {
                navController.navigate(Routes.Profile.routes)
            },
            colors = IconButtonDefaults.filledIconButtonColors(containerColor = Transparant)
        ) {
            Icon(
                modifier = Modifier.width(20.dp),
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Cart"
            )
        }
    }
}