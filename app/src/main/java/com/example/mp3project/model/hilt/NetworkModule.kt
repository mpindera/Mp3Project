package com.example.mp3project.model.hilt

import android.content.Context
import com.example.mp3project.model.retrofit.RetrofitInstance
import com.example.mp3project.model.retrofit.Service
import com.example.mp3project.viewmodel.auth.AuthManager
import com.example.mp3project.viewmodel.auth.AuthRepository
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
}