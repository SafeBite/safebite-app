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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.celvine.deb.esail.bby.BuildConfig
import com.celvine.deb.esail.bby.R
import com.celvine.deb.esail.bby.common.theme.BgColorNew
import com.celvine.deb.esail.bby.common.theme.ButtonColor
import com.celvine.deb.esail.bby.common.theme.Dark
import com.celvine.deb.esail.bby.common.theme.White
import com.celvine.deb.esail.bby.data.viewmodels.ScanFoodViewModel
import com.celvine.deb.esail.bby.presentation.components.LoadingScanning
import com.celvine.deb.esail.bby.route.Routes
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScanFoodScreen(navController: NavController, scanFoodViewModel: ScanFoodViewModel) {

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
    // Function to request storage permissions

    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { selectedUri ->
            // Load bitmap from the selected URI
            val bitmap: Bitmap? = loadBitmapFromUri(context, selectedUri)

            if (bitmap != null) {
                // Convert bitmap to JPEG format if not already in JPEG
                if (!selectedUri.toString().endsWith(".jpg")) {
                    imageUri = saveBitmapAsJpegAndGetUri(context, bitmap)
                } else {
                    // If already in JPEG format, no need to convert
                    imageUri = selectedUri
                }
            }
        }
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { isCaptured ->
            if (isCaptured) {
                val capturedBitmap: Bitmap? = loadBitmapFromUri(context, uri)

                if (capturedBitmap != null) {
                    capturedImageUri = saveBitmapAsJpegAndGetUri(context, capturedBitmap)
                    imageUri = capturedImageUri  // Update imageUri when photo is captured
                }
            }
        }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    imageUri?.let {
        bitmap.value = loadBitmapFromUri(context, it)
    }
//    imageUri?.let {
//        if (Build.VERSION.SDK_INT < 28) {
//            bitmap.value = MediaStore.Images
//                .Media.getBitmap(context.contentResolver,it)
//
//        } else {
//            val source = ImageDecoder
//                .createSource(context.contentResolver,it)
//            bitmap.value = ImageDecoder.decodeBitmap(source)
//        }
//    }
    var isLoading by remember { mutableStateOf(false) }
    val toggleLoading: (Boolean) -> Unit = { loading ->
        isLoading = loading
    }

    if (isLoading) {
        LoadingScanning()
    } else {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = BgColorNew
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.banner_scan),
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
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 132.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Scan Your Food",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, fontSize = 24.sp),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
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
                                contentScale = ContentScale.Fit
                            )
                        } else if(bitmap.value != null) {
                            bitmap.value?.let {  btm ->
                                Image(bitmap = btm.asImageBitmap(),
                                    contentDescription =null,
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .size(250.dp),
                                    contentScale = ContentScale.Fit)

                            }
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.iv_image),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(250.dp)
                            )
                        }
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
                        colors = ButtonDefaults.buttonColors(containerColor = White)
                    ) {
                        Text(text = "Gallery",
                            color = Color.Black)
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
                        colors = ButtonDefaults.buttonColors(containerColor = White)
                    ) {
                        Text(text = "Camera",
                            color = Color.Black)
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
                        onClick = {
                            if (imageUri != null) {
                                toggleLoading(true)
                                Log.d("ScanFoodScreen", "Initiating scan with URI: $imageUri")
                                scanFoodViewModel.scanFood(
                                    imageUri!!,
                                    onSuccess = {
                                        Log.d("ScanFoodScreen", "Scan success")
                                        navController.navigate(Routes.PredictionScreen.routes)
                                    },
                                    onError = { errorMessage ->
                                        Log.e("ScanFoodScreen", "Scan error: $errorMessage")
                                        toggleLoading(false)
                                        Toast.makeText(context, "$errorMessage" +
                                                "Try again or check your connection", Toast.LENGTH_SHORT).show()
                                    }
                                )
                            } else {
                                Toast.makeText(context, "Please select an image", Toast.LENGTH_SHORT).show()
                            }
                        },
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
    }

private fun loadBitmapFromUri(context: Context, uri: Uri): Bitmap? {
    return if (Build.VERSION.SDK_INT < 28) {
        MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
    } else {
        val source = ImageDecoder.createSource(context.contentResolver, uri)
        ImageDecoder.decodeBitmap(source)
    }
}
private fun saveBitmapAsJpegAndGetUri(context: Context, bitmap: Bitmap): Uri {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_$timeStamp.jpg"

    // Create a new file in the external cache directory
    val imageFile = File(context.externalCacheDir, imageFileName)

    // Save the bitmap to the file
    FileOutputStream(imageFile).use { outputStream ->
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
    }

    // Return the URI of the newly created file
    return FileProvider.getUriForFile(
        context,
        BuildConfig.APPLICATION_ID + ".provider",
        imageFile
    )
}

