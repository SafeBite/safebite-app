package com.celvine.deb.esail.bby.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.celvine.deb.esail.bby.R
import com.celvine.deb.esail.bby.common.theme.BgColorNew
import com.celvine.deb.esail.bby.common.theme.ButtonColor
import com.celvine.deb.esail.bby.common.theme.White
import com.celvine.deb.esail.bby.data.viewmodels.FoodViewModel
import com.celvine.deb.esail.bby.route.Routes

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OnBoardingScreen1(navController: NavController, foodViewModel: FoodViewModel) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BgColorNew
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_food_allergen),
                contentDescription = null,
                modifier = Modifier.size(400.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .shadow(7.dp, shape = RoundedCornerShape(15))
                    .background(color = Color.White)
                    .padding(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(id = R.string.have_allergy_title),
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Justify
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(id = R.string.desc_food_allergy),
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Justify
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { navController.navigate(Routes.Home.routes) {
                    }},
                    modifier = Modifier
                        .shadow(5.dp, shape = CircleShape)
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = White)
                ) {
                    Text(text = stringResource(id = R.string.dont_have_allergy), color = Color.Black)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = {
                        navController.navigate(Routes.OnBoarding2.routes) {
                        }
                              },
                    modifier = Modifier
                        .shadow(5.dp, shape = CircleShape)
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)
                ) {
                    Text(text = stringResource(id = R.string.have_allergy))
                }
            }
        }

    }
}


