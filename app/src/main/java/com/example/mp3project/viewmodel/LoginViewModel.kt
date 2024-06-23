package com.example.mp3project.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.mp3project.viewmodel.auth.AuthManager
import com.example.mp3project.viewmodel.auth.AuthRepository
import com.example.mp3project.viewmodel.auth.AuthRepositoryImpl
import com.example.mp3project.viewmodel.report.Reports
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
  val context: Context, private val coroutineScope: CoroutineScope
) : ViewModel() {
  val authRepository: AuthRepository = AuthRepositoryImpl(firebaseAuth = FirebaseAuth.getInstance())
  val authManager = AuthManager(authRepository, context)
  var email by mutableStateOf("")
  var password by mutableStateOf("")

  fun changeLoginText(loginText: String) {
    email = loginText.lowercase()
  }

  fun changePasswordText(passwordText: String) {
    password = passwordText
  }

  fun checkValidationLogin(navController: NavHostController, loginViewModel: LoginViewModel) {
    if ((email.isNotEmpty() && email.isNotBlank() && password.isNotBlank() && password.isNotEmpty())) {
      coroutineScope.launch {
        authManager.login(email, password, navController, loginViewModel)
      }
    } else {
      viewModelScope.launch {
        Reports(context = context).errorRegisterPersonValidation()
      }
    }
  }

  fun clearFields() {
    email = ""
    password = ""
  }
}