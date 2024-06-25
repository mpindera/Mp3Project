package com.example.mp3project.viewmodel.auth

import android.content.Intent
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import com.example.mp3project.model.navigation.Screen
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun rememberFirebaseAuthLauncher(
  navController: NavHostController,
  onAuthComplete: (AuthResult) -> Unit,
  onAuthError: (ApiException) -> Unit
): ManagedActivityResultLauncher<Intent, ActivityResult> {
  val scope = rememberCoroutineScope()
  return rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
    try {
      scope.launch {
        val account = task.getResult(ApiException::class.java)!!
        val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
        val authResult = FirebaseAuth.getInstance().signInWithCredential(credential).await()
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        val userId = auth.currentUser?.uid

        if (userId != null) {
          val userDocRef = db.collection("users").document(userId)
          val user = hashMapOf("email" to auth.currentUser?.email)

          userDocRef.set(user).addOnSuccessListener {
              auth.currentUser?.email?.let { email ->

              }
            }.addOnFailureListener { e ->
              Log.w("FirebaseAuth", "Error adding document", e)
            }
        }
        onAuthComplete(authResult)
        navController.navigate(Screen.MainScreen.route) {
          popUpTo(Screen.RegisterScreen.route) { inclusive = true }
        }
      }
    } catch (e: ApiException) {
      onAuthError(e)
    }
  }
}