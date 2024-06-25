package com.example.mp3project

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.mp3project.model.retrofit.Service
import com.example.mp3project.viewmodel.worker.ApiWorker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.assisted.Assisted
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApp : Application(), Configuration.Provider {

  @Inject
  lateinit var workerFactory: CustomWorkerFactory

  override val workManagerConfiguration: Configuration
    get() = Configuration.Builder()
      .setMinimumLoggingLevel(Log.DEBUG)
      .setWorkerFactory(workerFactory)
      .build()
}

class CustomWorkerFactory @Inject constructor(
  private val api: Service, private val firestore: FirebaseFirestore, private val auth: FirebaseAuth
) : WorkerFactory() {
  override fun createWorker(
    appContext: Context,
    workerClassName: String,
    workerParameters: WorkerParameters
  ): ListenableWorker? = ApiWorker(api, appContext, workerParameters, firestore, auth)

}