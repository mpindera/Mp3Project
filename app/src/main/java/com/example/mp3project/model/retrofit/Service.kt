package com.example.mp3project.model.retrofit

import com.example.mp3project.model.data.Feed
import com.example.mp3project.model.data.RSS
import retrofit2.http.GET

interface Service {

  @GET("polska.xml")
  suspend fun fetchPolska(): Feed

}