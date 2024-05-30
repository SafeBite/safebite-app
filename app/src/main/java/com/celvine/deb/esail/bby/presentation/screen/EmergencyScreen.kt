package com.celvine.deb.esail.bby.presentation.screen

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.celvine.deb.esail.bby.common.theme.BgColorNew
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.celvine.deb.esail.bby.R
import com.celvine.deb.esail.bby.common.Capitalize
import com.celvine.deb.esail.bby.common.theme.Dark
import com.celvine.deb.esail.bby.common.theme.SoftGray2
import com.celvine.deb.esail.bby.common.theme.White
import com.celvine.deb.esail.bby.data.model.HospitalModel
import com.celvine.deb.esail.bby.data.viewmodels.EmergencyViewModel

@Composable
fun EmergencyScreen(
    paddingValues: PaddingValues,
    navController: NavController,
    emergencyViewModel: EmergencyViewModel
){
    Surface(
        color = BgColorNew,
        modifier = Modifier.fillMaxSize()
    ){
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Banner photo
            Image(
                painter = painterResource(id = R.drawable.banner_emergency),
                contentDescription = null, // Add a proper content description
                modifier = Modifier
                    .fillMaxWidth()
//                    .height(180.dp) // Adjust the height as needed
                    .align(Alignment.TopStart)
            )
            // Back button
            Button(
                onClick = {
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(containerColor = White, contentColor = Dark),
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopStart)
                    .alpha(1f) // Ensure back button is fully visible
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = stringResource(id = R.string.back)
                    )
//                    Text(text = stringResource(id = R.string.back))
                }
            }
            // Box containing the white background, emergency text, and hospital list
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 132.dp) // Adjust top padding to match banner height
                    .background(color = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Emergency",
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 24.sp),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(top = 0.dp)
                    ) {
                        items(emergencyViewModel.hospitals.value) { item ->
                            Hospital(item = item, viewModel = EmergencyViewModel())
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Hospital(
    modifier: Modifier = Modifier,
    item: HospitalModel,
    viewModel: EmergencyViewModel
) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            // Permission is granted, make the phone call
            viewModel.makePhoneCall(context, item.noTelp)
        } else {
            // Permission denied
            Toast.makeText(context, "Phone call permission denied", Toast.LENGTH_SHORT).show()
        }
    }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                permissionLauncher.launch(Manifest.permission.CALL_PHONE)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 0.4.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.photo)
                    .crossfade(true).build(),
                contentDescription = item.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(64.dp)
                    .height(64.dp)
                    .clip(
                        shape = RoundedCornerShape(12.dp)
                    )
            )
            Spacer(modifier = Modifier.width(15.dp))
            Column {
                Text(
                    text = Capitalize(item.name),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Text(
                    text = item.address,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = SoftGray2
                    )
                )
                Text(
                    text = item.noTelp,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = SoftGray2
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}

