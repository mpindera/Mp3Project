package com.example.mp3project.model.navigation

sealed class Screen(val route: String) {
  object MainScreen : Screen("main_screen")
  object SplashScreen : Screen("splash_screen")
  object LoginScreen : Screen("login_screen")
  object RegisterScreen : Screen("register_screen")
}