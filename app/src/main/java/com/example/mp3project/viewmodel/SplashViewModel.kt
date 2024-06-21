package com.example.mp3project.viewmodel

import android.content.SharedPreferences
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