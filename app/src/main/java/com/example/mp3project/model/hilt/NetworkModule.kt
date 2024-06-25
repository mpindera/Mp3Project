package com.example.mp3project.model.hilt

import android.content.Context
import androidx.work.WorkManager
import com.example.mp3project.model.retrofit.RetrofitInstance
import com.example.mp3project.model.retrofit.Service
import com.example.mp3project.viewmodel.auth.AuthManager
import com.example.mp3project.viewmodel.auth.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @Singleton
  @Provides
  fun provideService(): Service {
    return RetrofitInstance.api
  }

  @Provides
  @Singleton
  fun provideContext(@ApplicationContext context: Context): Context {
    return context
  }

  @Singleton
  @Provides
  fun provideCoroutineScope(): CoroutineScope {
    return CoroutineScope(SupervisorJob() + Dispatchers.IO)
  }

  @Provides
  @Singleton
  fun provideAuthManager(authRepository: AuthRepository, @ApplicationContext context: Context): AuthManager {
    return AuthManager(authRepository, context)
  }

  @Provides
  @Singleton
  fun provideFirebaseFirestore(): FirebaseFirestore {
    return FirebaseFirestore.getInstance()
  }

  @Provides
  @Singleton
  fun provideFirebaseAuth(): FirebaseAuth {
    return FirebaseAuth.getInstance()
  }

  @Provides
  @Singleton
  fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
    return WorkManager.getInstance(context)
  }
}