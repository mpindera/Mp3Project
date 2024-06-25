package com.example.mp3project.view.top_bar_view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.compose.rememberNavController
import com.example.mp3project.R
import com.example.mp3project.model.data.Tabs
import com.example.mp3project.model.navigation.Screen
import com.example.mp3project.view.custom.CustomGradient.gradientBrush
import com.example.mp3project.view.custom.TabsState
import com.example.mp3project.viewmodel.MainViewModel
import com.example.mp3project.viewmodel.TopAppBarViewModel
import com.example.mp3project.viewmodel.auth.AuthManager
import com.example.mp3project.viewmodel.auth.AuthRepository
import com.example.mp3project.viewmodel.auth.AuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarRSS(
  navController: NavHostController,
  authManager: AuthManager,
  authRepository: AuthRepository,
  topAppBarViewModel: TopAppBarViewModel = hiltViewModel(),
  mainViewModel: MainViewModel = hiltViewModel()
) {
  val context = LocalContext.current
  Box(
    modifier = Modifier
      .background(brush = gradientBrush)
      .fillMaxWidth()
  ) {
    Column {
      TopAppBar(colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = Color.Transparent
      ), title = {
        Box(modifier = Modifier.fillMaxWidth()) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
          ) {
            AnimatedVisibility(
              visible = topAppBarViewModel.isVisibleRSSNews,
              enter = expandHorizontally(
                animationSpec = tween(durationMillis = 2000),
                expandFrom = Alignment.Start
              )
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
              visible = topAppBarViewModel.isVisibleRSSNews,
              enter = expandHorizontally(
                animationSpec = tween(durationMillis = 2000),
                expandFrom = Alignment.End
              )
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
      }, navigationIcon = {
        IconButton(onClick = {
          navController.navigate(Screen.MainScreen.route)
          mainViewModel.changeShowView(false)
        }) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "back_image"
          )
        }
      }, actions = {
        IconButton(onClick = {
          authManager.logout(authRepository, context, navController)
        }) {
          Icon(
            painter = painterResource(id = R.drawable.baseline_logout_24),
            contentDescription = "log_out_image",
            tint = Color.Black
          )
        }
      })
      if (topAppBarViewModel.isVisibleTabs == TabsState.MAIN_VIEW) {
        ScrollableTabRow(modifier = Modifier.padding(bottom = 10.dp, top = 10.dp),
          selectedTabIndex = topAppBarViewModel.selectedTabIndex,
          edgePadding = 16.dp,
          contentColor = Color.White,
          containerColor = Color.Transparent,
          indicator = { tabPositions ->
            SecondaryIndicator(
              color = Color.Black,
              modifier = Modifier
                .tabIndicatorOffset(tabPositions[topAppBarViewModel.selectedTabIndex])
                .fillMaxWidth()
            )
          }) {
          Tabs(context).tabs.forEachIndexed { index, tab ->
            Tab(
              selected = topAppBarViewModel.selectedTabIndex == index,
              onClick = {
                topAppBarViewModel.changeSelectedTabIndex(selectedTab = index)
              },
            ) {
              Text(
                text = tab,
                modifier = Modifier.padding(8.dp),
                color = if (topAppBarViewModel.selectedTabIndex == index) Color.Black else Color.White
              )
            }
          }
        }
      }
    }
  }
}

@Composable
@Preview
fun TestTopBar() {
  val navController = rememberNavController()
  val context = LocalContext.current
  val authRepository: AuthRepository =
    AuthRepositoryImpl(firebaseAuth = FirebaseAuth.getInstance())
  val authManager = AuthManager(authRepository, context)
  TopBarRSS(navController, authManager, authRepository)
}
