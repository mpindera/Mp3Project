package com.example.mp3project.view.splash_screen

import android.content.Context
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mp3project.R
import com.example.mp3project.view.custom.CustomGradient.gradientBrush
import com.example.mp3project.view.custom.UiState
import com.example.mp3project.viewmodel.LoginViewModel
import com.example.mp3project.viewmodel.SplashViewModel
import com.example.mp3project.viewmodel.auth.AuthManager
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
  navController: NavHostController,
  authManager: AuthManager,
  context: Context,
  splashViewModel: SplashViewModel = hiltViewModel(),
  loginViewModel: LoginViewModel = hiltViewModel()
) {

  val sharedPreferences = context.getSharedPreferences(
    "user_credentials",
    Context.MODE_PRIVATE
  )

  val offsetY by animateDpAsState(
    targetValue = if (splashViewModel.visible) 45.dp else 0.dp,
    animationSpec = tween(durationMillis = 3000), label = ""
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
        visible = splashViewModel.visible,

        ) {
        Image(
          painter = painterResource(id = R.drawable.baseline_newspaper_24),
          contentDescription = "RSS Image",
          modifier = Modifier
            .offset(y = offsetY, x = (-5).dp)
            .size(40.dp)
        )
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
      ) {
        AnimatedVisibility(
          visible = splashViewModel.visible,
          enter = expandHorizontally(
            animationSpec = tween(durationMillis = 2000),
            expandFrom = Alignment.Start
          ),
          exit = slideOutHorizontally(
            targetOffsetX = { fullWidth -> -fullWidth },
            animationSpec = tween(durationMillis = 2000)
          )+ fadeOut(tween(durationMillis = 2000))
        ) {
          Text(
            text = stringResource(id = R.string.rss),
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            letterSpacing = 5.sp,
            color = Color.White,
            fontFamily = FontFamily.Cursive,
          )
        }

        AnimatedVisibility(
          visible = splashViewModel.visible,
          enter = expandHorizontally(
            animationSpec = tween(durationMillis = 2000),
            expandFrom = Alignment.End
          ),
          exit = slideOutHorizontally(
            targetOffsetX = { fullWidth -> fullWidth },
            animationSpec = tween(durationMillis = 2000)
          ) + fadeOut(tween(durationMillis = 2000))
        ) {
          Text(
            text = stringResource(id = R.string.news),
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            letterSpacing = 5.sp,
            color = Color.White,
            fontFamily = FontFamily.Cursive,
          )

        }
      }
    }

    LaunchedEffect(Unit) {
      delay(500)
      splashViewModel.changeIsVisibleAnimation(true)
      delay(3000)
      splashViewModel.changeIsVisibleAnimation(false)
      delay(1500)
      splashViewModel.CheckLogged(sharedPreferences, authManager, navController, loginViewModel)
    }
  }
}


