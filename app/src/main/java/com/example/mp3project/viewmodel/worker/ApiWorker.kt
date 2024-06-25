package com.example.mp3project.viewmodel.worker

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Icon
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker

import androidx.work.WorkerParameters
import com.example.mp3project.MainActivity
import com.example.mp3project.R
import com.example.mp3project.model.retrofit.Service
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import kotlin.random.Random

@HiltWorker
class ApiWorker @AssistedInject constructor(
  @Assisted private val api: Service,
  @Assisted context: Context,
  @Assisted workerParameters: WorkerParameters,
  @Assisted private val firestore: FirebaseFirestore,
  @Assisted private val auth: FirebaseAuth
) : CoroutineWorker(context, workerParameters) {

  override suspend fun doWork(): Result {
    return try {
      val response = api.fetchWszystkie()
      val checkFirst = response.articleList.first().title
      val checkFirstLink = response.articleList.first().link
      val checkFirstImg = response.articleList.first().enclosure[0].url ?: ""

      val userId = auth.currentUser?.uid
      userId?.let { uid ->
        val userDocRef = firestore.collection("users").document(uid)
        val titleDocRef = userDocRef.collection("first_title").document(checkFirst)

        titleDocRef.get().addOnSuccessListener { document ->
          if (!document.exists()) {
            CoroutineScope(Dispatchers.Main).launch {
              showNotification(checkFirst, checkFirstLink, checkFirstImg)
            }
          }
        }.addOnFailureListener { e ->
          Log.e("ApiWorker", "Error checking document $checkFirst in Firestore", e)
        }
      }
      Result.success()
    } catch (e: Exception) {
      Log.e("ApiWorker", "Error during API job execution", e)
      Result.failure()
    }
  }

  private suspend fun showNotification(title: String, message: String, imageUrl: String) {
    val notificationManager =
      applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val channel = NotificationChannel(
        CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
      )
      notificationManager.createNotificationChannel(channel)
    }

    val intent = Intent(applicationContext, MainActivity::class.java).apply {
      flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val pendingIntent: PendingIntent = PendingIntent.getActivity(
      applicationContext,
      0,
      intent,
      PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val bitmap = withContext(Dispatchers.IO) {
      loadImageFromUrl(imageUrl)
    }
    val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
      .setContentTitle(title)
      .setContentText(message)
      .setPriority(NotificationCompat.PRIORITY_DEFAULT)
      .setAutoCancel(true)
      .setContentIntent(pendingIntent)
      .setStyle(NotificationCompat.BigPictureStyle()
        .bigPicture(bitmap)
        .bigLargeIcon(bitmap))
      .build()


    notificationManager.notify(NOTIFICATION_ID, notification)
  }

  private fun loadImageFromUrl(url: String): Bitmap? {
    return try {
      val inputStream = URL(url).openStream()
      BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
      Log.e("ApiWorker", "Error loading image from URL", e)
      null
    }
  }

  companion object {
    private const val CHANNEL_ID = "notification_channel"
    private const val CHANNEL_NAME = "Notification Channel"
    private const val NOTIFICATION_ID = 1
  }
}