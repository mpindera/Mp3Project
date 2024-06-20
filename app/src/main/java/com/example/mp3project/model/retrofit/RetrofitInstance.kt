package com.example.mp3project.model.retrofit

import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

object RetrofitInstance {
  private val BASE_URL = "https://www.polsatnews.pl/rss/";

  val api: Service by lazy {
    val retrofit = Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(SimpleXmlConverterFactory.create())
      .build()
    retrofit.create(Service::class.java)
  }
}