package com.example.mp3project.viewmodel.auth

import android.content.Context
import android.util.Log
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
  private val firebaseAuth: FirebaseAuth
) : AuthRepository {
  override suspend fun loginUser(email: String, password: String): Resource<AuthResult> {
    return try {
      val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
      Resource.Success(authResult)
    } catch (e: Exception) {
      Resource.Error("Login failed: ${e.message}")
    }
  }

  override suspend fun registerUser(email: String, password: String): Resource<AuthResult> {
    return try {
      val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
      Log.d("AuthRepositoryImpl", "User registered successfully")
      Resource.Success(authResult)
    } catch (e: Exception) {
      Log.e("AuthRepositoryImpl", "Registration failed: ${e.message}")
      Resource.Error("Registration failed: ${e.message}")
    }
  }

  override fun logoutUser() {
    FirebaseAuth.getInstance().signOut()
  }

  override suspend fun resetPassword(email: String) {
    FirebaseAuth.getInstance().sendPasswordResetEmail(email)
  }
}
