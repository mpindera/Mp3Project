package com.example.mp3project.viewmodel.auth

import com.google.firebase.auth.AuthResult

interface AuthRepository {
  suspend fun loginUser(email: String, password: String): Resource<AuthResult>
  suspend fun registerUser(email: String, password: String): Resource<AuthResult>
  fun logoutUser()
  suspend fun resetPassword(email: String)
}