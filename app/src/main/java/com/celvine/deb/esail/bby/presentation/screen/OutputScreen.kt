//package com.celvine.deb.esail.bby.presentation.screen
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material3.Button
//import androidx.compose.material3.Surface
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import com.celvine.deb.esail.bby.R
//import com.celvine.deb.esail.bby.common.theme.BgColorNew
//import com.celvine.deb.esail.bby.presentation.components.FoodCardDetail
//
//@Composable
//fun OutputScreen() {
//    Surface(
//        color = BgColorNew,
//        modifier = Modifier.fillMaxSize()
//    ) {
//        Box(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            Image(
//                painter = painterResource(R.drawable.background),
//                contentDescription = "Background Image",
//                modifier = Modifier.fillMaxWidth(),
//                contentScale = ContentScale.Crop
//            )
//            Column(
//                modifier = Modifier
//                    .padding(start = 16.dp, top = 150.dp, end = 16.dp)
//                    .align(Alignment.TopCenter),
//                verticalArrangement = Arrangement.Top,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
////                Spacer(modifier = Modifier.height(16.dp))
//                FoodCardDetail(
//                    foodImageRes = R.drawable.almond,
//                    foodTitle = "Delicious Food",
//                    allergenIconRes = R.drawable.almond,
//                    allergenName = "Peanuts",
//                    description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make"
//                )
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                Row(
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Button(
//                        onClick = { /* Handle back button click */ },
//                        modifier = Modifier
//                            .weight(1f)
//                            .padding(end = 16.dp)
//                            .background(Color.White)
//                    ) {
//                        Image(
//                            painter = painterResource(R.drawable.close),
//                            contentDescription = "No Image",
//                            modifier = Modifier.size(24.dp)
//                        )
//                    }
//                    Button(
//                        onClick = { /* Handle next button click */ },
//                        modifier = Modifier
//                            .weight(1f)
//                            .background(Color.White),
//                    ) {
//                        Image(
//                            painter = painterResource(R.drawable.bookmark),
//                            contentDescription = "Yes Image",
//                            modifier = Modifier.size(24.dp)
//                        )
//                    }
//                }
//            }
//
//            // Floating Image
//            Spacer(modifier = Modifier.height(40.dp))
//            Box(
//                modifier = Modifier
//                    .size(100.dp)
//                    .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
//                    .align(Alignment.TopCenter)
//                    .background(Color.White, shape = CircleShape),
//                contentAlignment = Alignment.Center
//            ) {
//                Image(
//                    painter = painterResource(R.drawable.checklist),
//                    contentDescription = "Floating Image",
//                    modifier = Modifier.fillMaxSize(),
//                    contentScale = ContentScale.Fit
//                )
//            }
//        }
//    }
//}
//
//@Preview
//@Composable
//fun OutputScreenPreview() {
//    OutputScreen()
//}
