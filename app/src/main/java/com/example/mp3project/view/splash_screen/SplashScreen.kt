package com.example.mp3project.view.splash_screen

import android.content.SharedPreferences
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mp3project.R
import com.example.mp3project.model.navigation.Screen
import com.example.mp3project.view.custom.CustomGradient.gradientBrush
import com.example.mp3project.viewmodel.LoginViewModel
import com.example.mp3project.viewmodel.SplashViewModel
import com.example.mp3project.viewmodel.auth.AuthManager
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
  navController: NavHostController,
  sharedPreferences: SharedPreferences,
  splashViewModel: SplashViewModel,
  authManager: AuthManager,
  loginViewModel: LoginViewModel
) {
  var visible by remember {
    mutableStateOf(false)
  }

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
          text = stringResource(id = R.string.rss_news),
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
      splashViewModel.CheckLogged(sharedPreferences,authManager,navController,loginViewModel)
      delay(1000)
    }
  }
}
