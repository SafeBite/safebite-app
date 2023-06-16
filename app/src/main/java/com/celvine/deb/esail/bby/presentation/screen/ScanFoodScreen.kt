package com.celvine.deb.esail.bby.presentation.screen

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.key.Key.Companion.Button3
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.celvine.deb.esail.bby.BuildConfig
import com.celvine.deb.esail.bby.R
import com.celvine.deb.esail.bby.common.theme.BgColorNew
import com.celvine.deb.esail.bby.common.theme.ButtonColor
import com.celvine.deb.esail.bby.common.theme.DarkGreen
import com.celvine.deb.esail.bby.common.theme.White
import com.celvine.deb.esail.bby.data.viewmodels.LoginViewModel
import com.celvine.deb.esail.bby.data.viewmodels.ScanFoodViewModel
import com.celvine.deb.esail.bby.route.Routes
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScanFoodScreen(navController: NavController, scanFoodViewModel: ScanFoodViewModel) {


    val responModel by remember(scanFoodViewModel) {
        scanFoodViewModel.responModel }

    fun Context.createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val image = File.createTempFile(
            imageFileName, /* prefix */
            ".jpg", /* suffix */
            externalCacheDir      /* directory */
        )
        return image
    }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val bitmap =  remember {
        mutableStateOf<Bitmap?>(null)
    }
    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        BuildConfig.APPLICATION_ID + ".provider", file
    )

    var capturedImageUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }

    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            capturedImageUri = uri
        }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }
    imageUri?.let {
        if (Build.VERSION.SDK_INT < 28) {
            bitmap.value = MediaStore.Images
                .Media.getBitmap(context.contentResolver,it)

        } else {
            val source = ImageDecoder
                .createSource(context.contentResolver,it)
            bitmap.value = ImageDecoder.decodeBitmap(source)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BgColorNew
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Scan Your Food",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))
            Box(
                modifier = Modifier
                    .shadow(7.dp, shape = RoundedCornerShape(15))
                    .background(color = Color.White)
                    .padding(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    if (capturedImageUri.path?.isNotEmpty() == true) {
                        Image(
                            modifier = Modifier
                                .padding(16.dp)
                                .size(250.dp),
                            painter = rememberImagePainter(capturedImageUri),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    } else if(bitmap.value != null) {
                        bitmap.value?.let {  btm ->
                            Image(bitmap = btm.asImageBitmap(),
                                contentDescription =null,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .size(250.dp))
                        }
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.iv_image),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(300.dp)
                            )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        // Bottom Bar
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 80.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        launcher.launch("image/*")},
                    modifier = Modifier
                        .shadow(5.dp, shape = CircleShape)
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)
                ) {
                    Text(text = "Gallery")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = {
                        val permissionCheckResult =
                        ContextCompat.checkSelfPermission(context, "android.permission.CAMERA")
                        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                            cameraLauncher.launch(uri)
                        } else {
                            // Request a permission
                            permissionLauncher.launch("android.permission.CAMERA")
                        }},
                    modifier = Modifier
                        .shadow(5.dp, shape = CircleShape)
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)
                ) {
                    Text(text = "Camera")
                }
            }
            Spacer(modifier = Modifier.width(32.dp))
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Button(
                    onClick = {  if (imageUri != null) {
                        scanFoodViewModel.scanFood(
                            imageUri!!,
                            onSuccess = {
                                // OTP verification successful, proceed with login
                                Log.d("Scan", "Scan success")
                                navController.navigate(Routes.DetailAllergic.routes)
                            },
                            onError = { errorMessage ->
                                // Handle OTP verification error
                                // Show an error message or perform any other action
                            }
                        )
                    } else {
                        // Handle case when imageUri is null
                        Toast.makeText(context, "Please select an image", Toast.LENGTH_SHORT).show()
                    }},
                    modifier = Modifier
                        .shadow(5.dp, shape = CircleShape)
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)
                ) {
                    Text(text = "Scan")
                }
            }

        }
    }
}

//@OptIn(ExperimentalFoundationApi::class)
//@Preview
//@Composable
//fun ScanFoodScreenPreview() {
//    val navController = rememberNavController() // Create a mock NavController instance
//    ScanFoodScreen(navController = navController)
//}

