package com.example.mp3project.model.retrofit

import com.example.mp3project.model.data.Feed
import com.example.mp3project.model.data.RSS
import retrofit2.http.GET

interface Service {

  @GET("polska.xml")
  suspend fun fetchPolska(): Feed

  @GET("biznes.xml")
  suspend fun fetchBiznes(): Feed

  @GET("technologie.xml")
  suspend fun fetchTechnologie(): Feed

  @GET("moto.xml")
  suspend fun fetchMoto(): Feed

  @GET("kultura.xml")
  suspend fun fetchKultura(): Feed

  @GET("sport.xml")
  suspend fun fetchSport(): Feed

  @GET("czysta-polska.xml")
  suspend fun fetchCzystaPolska(): Feed

  @GET("wideo.xml")
  suspend fun fetchWideo(): Feed

}