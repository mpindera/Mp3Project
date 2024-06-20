package com.example.mp3project.model.hilt

import com.example.mp3project.model.retrofit.RetrofitInstance
import com.example.mp3project.model.retrofit.Service
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @Singleton
  @Provides
  fun provideService(): Service {
    return RetrofitInstance.api
  }
}