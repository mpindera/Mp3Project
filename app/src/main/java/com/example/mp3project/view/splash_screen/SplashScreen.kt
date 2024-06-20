package com.example.mp3project.view.splash_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.size.Scale
import com.example.mp3project.R
import com.example.mp3project.model.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
  var visible by remember {
    mutableStateOf(false)
  }

  val lighterRed = lerp(Color.Red, Color.White, 0.5f)
  val lighterBlue = lerp(Color.Black, Color.White, 0.2f)

  val gradientBrush = Brush.linearGradient(
    colors = listOf(
      lighterRed,
      lighterBlue
    ),
    start = Offset.Zero,
    end = Offset.Infinite
  )

  Box(
    contentAlignment = Alignment.Center, modifier = Modifier
      .background(gradientBrush)
      .fillMaxSize()
  ) {
    Column(
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      AnimatedVisibility(
        visible = visible,
        enter = expandVertically(animationSpec = tween(durationMillis = 2000)),
        exit = fadeOut(animationSpec = tween(durationMillis = 2000))
      ) {
        Image(
          painter = painterResource(id = R.drawable.baseline_newspaper_24),
          contentDescription = "RSS Image",
          modifier = Modifier.size(40.dp)
        )
      }
      AnimatedVisibility(
        visible = visible,
        enter = expandHorizontally(animationSpec = tween(durationMillis = 2000)),
        exit = fadeOut(animationSpec = tween(durationMillis = 2000))
      ) {
        Text(
          text = "RSS News",
          fontWeight = FontWeight.Bold,
          fontSize = 40.sp,
          letterSpacing = 5.sp,
          color = Color.White,
          fontFamily = FontFamily.Cursive,
        )
      }
    }

    LaunchedEffect(Unit) {
      delay(500)
      visible = true
      delay(3000)
      visible = false
      delay(1500)
      navController.navigate(Screen.LoginScreen.route) {
        popUpTo(Screen.SplashScreen.route) {
          inclusive = true
        }
      }
    }
  }
}

@Composable
@Preview(showBackground = true)
fun TestSplashScreen() {
  val navController = rememberNavController()
  SplashScreen(navController)
}