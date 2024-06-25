package com.example.mp3project

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.work.BackoffPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.evernote.android.job.JobManager
import com.example.mp3project.model.navigation.Screen
import com.example.mp3project.ui.theme.Mp3ProjectTheme
import com.example.mp3project.view.favorite_screen.FavoriteScreen
import com.example.mp3project.view.login_screen.LoginScreen
import com.example.mp3project.view.main_screen.MainScreen
import com.example.mp3project.view.register_screen.RegisterScreen
import com.example.mp3project.view.splash_screen.SplashScreen
import com.example.mp3project.viewmodel.auth.AuthManager
import com.example.mp3project.viewmodel.auth.AuthRepository
import com.example.mp3project.viewmodel.auth.AuthRepositoryImpl
import com.example.mp3project.viewmodel.worker.ApiWorker
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import java.time.Duration
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  @RequiresApi(Build.VERSION_CODES.O)
  @SuppressLint("CoroutineCreationDuringComposition")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      val navController = rememberNavController()
      val context = LocalContext.current
      val authRepository: AuthRepository =
        AuthRepositoryImpl(firebaseAuth = FirebaseAuth.getInstance())
      val authManager = AuthManager(authRepository, context)

      requestNotificationPermission()

      Mp3ProjectTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
          NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
            composable(Screen.SplashScreen.route) {
              SplashScreen(navController, authManager, context)
            }
            composable(Screen.LoginScreen.route) {
              LoginScreen(navController)
            }
            composable(Screen.MainScreen.route) {
              MainScreen(navController, authManager, authRepository)
            }
            composable(Screen.RegisterScreen.route) {
              RegisterScreen(navController)
            }
            composable(Screen.FavoriteScreen.route) {
              FavoriteScreen(navController, authManager, authRepository)
            }
          }
        }
      }
    }
  }

  private var requestPermissionLauncher: ActivityResultLauncher<String> =
    registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
      if (!isGranted) {
        Log.d("POST_NOTIFICATION_PERMISSION", "USER DENIED PERMISSION")
      } else {
        Log.d("POST_NOTIFICATION_PERMISSION", "USER GRANTED PERMISSION")
      }
    }

  private fun requestNotificationPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      val permission = Manifest.permission.POST_NOTIFICATIONS
      when {
        ContextCompat.checkSelfPermission(
          this, permission
        ) == PackageManager.PERMISSION_GRANTED -> {
          Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show()
        }

        shouldShowRequestPermissionRationale(permission) -> {
          Toast.makeText(this, "Permission denied permanently", Toast.LENGTH_LONG).show()
        }

        else -> {
          requestPermissionLauncher.launch(permission)
        }
      }
    }
  }
}

