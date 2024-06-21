package com.example.mp3project.viewmodel.auth

import android.content.Context
import android.util.Log
import androidx.navigation.NavHostController
import com.example.mp3project.model.navigation.Screen
import com.example.mp3project.view.custom.CommonElements
import com.example.mp3project.viewmodel.LoginViewModel
import com.example.mp3project.viewmodel.RegisterViewModel
import com.example.mp3project.viewmodel.report.Reports
import com.google.firebase.auth.FirebaseAuth
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
  /*
          try {
          withContext(Dispatchers.Main) {
            CommonElements().dbGet
              .addOnSuccessListener { documents ->
                for (document in documents) {
                  val userData = document.data
                  val userEmail = userData["email"].toString()
                  val name = userData["name"].toString()

                  if (userEmail == email) {



                  }
                }

              }.addOnFailureListener {
                Reports(context = context).errorFetchFromDatabase()
              }
          }
        } catch (e: Exception) {
          Log.e("Error Login", "Error Login", e)
        }
  * */

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


  suspend fun register(
    email: String,
    password: String,
    navController: NavHostController,
    RegisterViewModel: RegisterViewModel
  ) {
    val user = hashMapOf(
      "email" to email,
      "name" to RegisterViewModel.name
    )
    try {
      when (val result = authRepository.registerUser(email, password)) {
        is Resource.Success -> {
          Log.d("AuthManager", "Registration successful")
          withContext(Dispatchers.Main) {
            saveUserCredentials(email, password)
            CommonElements().dbDocument.add(user).addOnSuccessListener {
              Log.d("test", "Siedzi")
            }.addOnFailureListener { e ->
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
    val sharedPreferences =
      context.getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("email", email)
    editor.putString("password", password)
    editor.apply()
  }
}
