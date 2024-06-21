package com.example.mp3project

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mp3project.model.navigation.Screen
import com.example.mp3project.ui.theme.Mp3ProjectTheme
import com.example.mp3project.view.login_screen.LoginScreen
import com.example.mp3project.view.main_screen.MainScreen
import com.example.mp3project.view.register_screen.RegisterScreen
import com.example.mp3project.view.splash_screen.SplashScreen
import com.example.mp3project.viewmodel.ApiViewModel
import com.example.mp3project.viewmodel.LoginViewModel
import com.example.mp3project.viewmodel.RegisterViewModel
import com.example.mp3project.viewmodel.SplashViewModel
import com.example.mp3project.viewmodel.auth.AuthManager
import com.example.mp3project.viewmodel.auth.AuthRepository
import com.example.mp3project.viewmodel.auth.AuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  private val fetchingViewModel by viewModels<ApiViewModel>()
  private val loginViewModel by viewModels<LoginViewModel>()
  private val registerViewModel by viewModels<RegisterViewModel>()
  private val splashViewModel by viewModels<SplashViewModel>()

  @SuppressLint("CoroutineCreationDuringComposition")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      val navController = rememberNavController()
      val context = LocalContext.current
      val authRepository: AuthRepository =
        AuthRepositoryImpl(firebaseAuth = FirebaseAuth.getInstance())
      val authManager = AuthManager(authRepository, context)


      val sharedPreferences = getSharedPreferences("user_credentials", Context.MODE_PRIVATE)



      Mp3ProjectTheme {
        Surface(modifier = Modifier.fillMaxSize())
        {
          NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
            composable(Screen.SplashScreen.route) {
              SplashScreen(navController,sharedPreferences,splashViewModel,authManager,loginViewModel)
            }
            composable(Screen.LoginScreen.route) {
              LoginScreen(loginViewModel, navController, authManager)
            }
            composable(Screen.MainScreen.route) {
              MainScreen()
            }
            composable(Screen.RegisterScreen.route) {
              RegisterScreen(registerViewModel, navController)
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
  modifier: PaddingValues, coroutine: CoroutineScope, fetchingViewModel: ApiViewModel
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