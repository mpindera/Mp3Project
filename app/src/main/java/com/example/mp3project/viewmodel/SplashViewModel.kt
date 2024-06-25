package com.example.mp3project.viewmodel

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.mp3project.model.navigation.Screen
import com.example.mp3project.viewmodel.auth.AuthManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
  private val coroutineScope: CoroutineScope
) : ViewModel() {
  var visible by mutableStateOf(false)
  fun changeIsVisibleAnimation(visibleAnimation: Boolean){
    visible = visibleAnimation
  }

  fun CheckLogged(
    sharedPreferences: SharedPreferences,
    authManager: AuthManager,
    navController: NavHostController,
    loginViewModel: LoginViewModel
  ) {

    val savedEmail = sharedPreferences.getString("email", null)
    val savedPassword = sharedPreferences.getString("password", null)

    if (savedEmail != null && savedPassword != null) {
      coroutineScope.launch {
        authManager.login(
          savedEmail,
          savedPassword,
          navController,
          loginViewModel
        )
      }
    } else {
      navController.navigate(Screen.LoginScreen.route) {
        popUpTo(Screen.SplashScreen.route) {
          inclusive = true
        }
      }
    }
  }
}