package com.example.mp3project.view.view_rss

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mp3project.view.custom.TabsState
import com.example.mp3project.view.top_bar_view.TopBarRSS
import com.example.mp3project.viewmodel.MainViewModel
import com.example.mp3project.viewmodel.TopAppBarViewModel
import com.example.mp3project.viewmodel.auth.AuthManager
import com.example.mp3project.viewmodel.auth.AuthRepository

@Composable
fun AndroidViewRSS(
  navController: NavHostController,
  authManager: AuthManager,
  authRepository: AuthRepository,
  mainViewModel: MainViewModel = hiltViewModel(),
  topAppBarViewModel: TopAppBarViewModel = hiltViewModel()
) {
  topAppBarViewModel.changeIsVisibleTabs(TabsState.WEB_VIEW)
  Scaffold(
    topBar = {
      TopBarRSS(navController, authManager, authRepository)
    },
  ) { innerPadding ->
    AndroidView(modifier = Modifier.padding(innerPadding), factory = {
      WebView(it).apply {
        layoutParams = ViewGroup.LayoutParams(
          ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        webViewClient = WebViewClient()
        loadUrl(mainViewModel.selectedURL)
      }
    }, update = {
      it.loadUrl(mainViewModel.selectedURL)
    })
  }
}