package com.example.mp3project.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
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
class RegisterViewModel @Inject constructor(
  val context: Context, private val coroutineScope: CoroutineScope
) : ViewModel() {

  val authRepository: AuthRepository = AuthRepositoryImpl(firebaseAuth = FirebaseAuth.getInstance())
  val authManager = AuthManager(authRepository, context)

  var name by mutableStateOf("")
  var email by mutableStateOf("")
  var password by mutableStateOf("")
  var confirmPassword by mutableStateOf("")

  fun changeNameText(nameText: String) {
    name = nameText
  }

  fun changeEmailText(mailText: String) {
    email = mailText.lowercase()
  }

  fun changePasswordText(passwordText: String) {
    password = passwordText
  }

  fun changeConfirmPasswordText(confirmPasswordText: String) {
    confirmPassword = confirmPasswordText
  }

  fun checkValidation(navController: NavHostController, RegisterViewModel: RegisterViewModel) {
    if ((name.isNotBlank() && name.isNotEmpty() && email.isNotEmpty() && email.isNotBlank() && password.isNotBlank() && password.isNotEmpty() && confirmPassword.isNotEmpty() && confirmPassword.isNotBlank() && password == confirmPassword)) {
      if (password.length >= 6) {
        coroutineScope.launch {
          authManager.register(email, password, navController, RegisterViewModel)
        }
      } else {
        coroutineScope.launch {
          Reports(context = context).errorPasswordInvalid()
        }
      }
    } else {
      coroutineScope.launch {
        Reports(context = context).errorRegisterPersonValidation()
      }
    }
  }

  fun clearFields() {
    name = ""
    email = ""
    password = ""
    confirmPassword = ""
  }
}
