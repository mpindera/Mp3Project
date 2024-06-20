package com.example.mp3project

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mp3project.model.navigation.Screen
import com.example.mp3project.ui.theme.Mp3ProjectTheme
import com.example.mp3project.view.login_screen.LoginScreen
import com.example.mp3project.view.main_screen.MainScreen
import com.example.mp3project.view.splash_screen.SplashScreen
import com.example.mp3project.viewmodel.ApiViewmodel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  private val fetchingViewModel by viewModels<ApiViewmodel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      val navController = rememberNavController()
      val coroutine = rememberCoroutineScope()
      Mp3ProjectTheme {
        Surface(modifier = Modifier.fillMaxSize())
        {
          NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
            composable(Screen.SplashScreen.route) {
              SplashScreen(navController)
            }
            composable(Screen.LoginScreen.route) {
              LoginScreen()
            }
            composable(Screen.MainScreen.route) {
              MainScreen()
            }
          }
        }
      }
    }
  }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Greeting(
  modifier: PaddingValues, coroutine: CoroutineScope, fetchingViewModel: ApiViewmodel
) {
  coroutine.launch {
    fetchingViewModel.getPost()
  }
  Text(
    modifier = Modifier.padding(modifier), text = fetchingViewModel.fullName.value.toString()
  )
  LazyColumn(modifier = Modifier.padding(modifier)) {
    items(fetchingViewModel.fullItem.value) { i ->
      Text(text = i.title)
      Text(text = i.description)
      Text(text = i.link)
    }
  }
}