package com.example.mp3project.viewmodel.auth

import android.content.Context
import android.util.Log
import androidx.navigation.NavHostController
import com.example.mp3project.model.navigation.Screen
import com.example.mp3project.viewmodel.LoginViewModel
import com.example.mp3project.viewmodel.RegisterViewModel
import com.example.mp3project.viewmodel.report.Reports
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthManager(
  private val authRepository: AuthRepository,
  private val context: Context,
) {
  suspend fun login(
    email: String,
    password: String,
    navController: NavHostController,
    LoginViewModel: LoginViewModel,
  ) {
    when (authRepository.loginUser(email, password)) {
      is Resource.Success -> {
        withContext(Dispatchers.Main) {
          saveUserCredentials(email, password)
          navController.navigate(Screen.MainScreen.route) {
            popUpTo(Screen.LoginScreen.route) { inclusive = true }
          }
          LoginViewModel.clearFields()
        }
      }

      is Resource.Error -> {
        try {
          withContext(Dispatchers.Main) {
            Reports(context = context).errorEmailDoesNotExists()
          }
        } catch (e: Exception) {
          Log.e("Error", "Error", e)
        }
      }
    }
  }
  /* suspend fun resetPasswordPatient(
     email: String
   ) = coroutineScope {
     try {
       var foundMatch = false

       for (document in CommonElements().dbGet.await()) {
         val userData = document.data
         val userEmail = userData["email"].toString()

         if (email == userEmail) {
           foundMatch = true
         }
       }

       if (foundMatch) {
         authRepository.resetPassword(email = email)
       } else {
         Reports(context = context).errorEmailDoesNotExists()
       }
     } catch (e: Exception) {
       e.printStackTrace()
     }
   }*/


  fun logout(
    authRepository: AuthRepository,
    context: Context,
    navController: NavHostController,
  ) {
    try {
      navController.navigate(Screen.SplashScreen.route) {
        popUpTo(Screen.SplashScreen.route) { inclusive = true }
      }
      authRepository.logoutUser()
      val sharedPreferences = context.getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
      val editor = sharedPreferences.edit()
      editor.clear()
      editor.apply()
    } catch (e: Exception) {
      e.toString()
    }
  }

  suspend fun register(
    email: String,
    password: String,
    navController: NavHostController,
    RegisterViewModel: RegisterViewModel
  ) {
    val user = hashMapOf(
      "email" to email, "name" to RegisterViewModel.name
    )
    try {
      when (val result = authRepository.registerUser(email, password)) {
        is Resource.Success -> {
          Log.d("AuthManager", "Registration successful")
          withContext(Dispatchers.Main) {
            val auth = FirebaseAuth.getInstance()
            val db = FirebaseFirestore.getInstance()
            val userId = auth.currentUser?.uid
            val userDocRef = userId?.let { db.collection("users").document(it) }
            userDocRef?.set(user)
              ?.addOnSuccessListener {
                saveUserCredentials(email, password)
              }
              ?.addOnFailureListener { e ->
                Log.w("test", "Error adding document", e)
              }
            navController.navigate(Screen.MainScreen.route) {
              popUpTo(Screen.RegisterScreen.route) { inclusive = true }
            }
            RegisterViewModel.clearFields()
          }
        }

        is Resource.Error -> {
          Log.e("AuthManager", "Registration failed: ${result.message}")
          withContext(Dispatchers.Main) {
            Reports(context).errorRegisterPerson()
          }
        }
      }
    } catch (e: Exception) {
      Log.e("AuthManager", "Error Register", e)
    }
  }

  private fun saveUserCredentials(email: String, password: String) {
    val sharedPreferences = context.getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("email", email)
    editor.putString("password", password)
    editor.apply()
  }
}
