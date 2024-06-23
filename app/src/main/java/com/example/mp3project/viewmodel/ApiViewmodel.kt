package com.example.mp3project.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mp3project.model.data.Enclosure
import com.example.mp3project.model.data.Feed
import com.example.mp3project.model.data.RSS
import com.example.mp3project.model.retrofit.RetrofitInstance
import com.example.mp3project.model.retrofit.Service
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiViewModel @Inject constructor(
  private val service: Service
) : ViewModel() {

  val feedLiveData: MutableLiveData<Feed> = MutableLiveData()
  val fullItem: MutableState<List<RSS>> = mutableStateOf(emptyList())

  fun getPostsFromWszystkieRSS() {
    viewModelScope.launch {
      resetState()
      try {
        val response = service.fetchWszystkie()
        feedLiveData.value = response
        fullItem.value = response.articleList

      } catch (e: Exception) {
        Log.d("ERROR", e.toString())
      }
    }
  }

  fun getPostFromPolskaRSS() {
    viewModelScope.launch {
      resetState()
      try {
        val response = service.fetchPolska()
        feedLiveData.value = response
        fullItem.value = response.articleList

      } catch (e: Exception) {
        Log.d("ERROR", e.toString())
      }
    }
  }

  fun getPostFromSwiatRSS() {
    viewModelScope.launch {
      resetState()
      try {
        val response = service.fetchSwiat()
        feedLiveData.value = response
        fullItem.value = response.articleList

      } catch (e: Exception) {
        Log.d("ERROR", e.toString())
      }
    }
  }

  fun getPostFromBiznesRSS() {
    viewModelScope.launch {
      resetState()
      try {
        val response = service.fetchBiznes()
        feedLiveData.value = response
        fullItem.value = response.articleList

      } catch (e: Exception) {
        Log.d("ERROR", e.toString())
      }
    }
  }

  fun getPostFromTechnologieRSS() {
    viewModelScope.launch {
      resetState()
      try {
        val response = service.fetchTechnologie()
        feedLiveData.value = response
        fullItem.value = response.articleList

      } catch (e: Exception) {
        Log.d("ERROR", e.toString())
      }
    }
  }

  fun getPostFromMotoRSS() {
    viewModelScope.launch {
      resetState()
      try {
        val response = service.fetchMoto()
        feedLiveData.value = response
        fullItem.value = response.articleList

      } catch (e: Exception) {
        Log.d("ERROR", e.toString())
      }
    }
  }

  fun getPostFromKulturaRSS() {
    viewModelScope.launch {
      resetState()
      try {
        val response = service.fetchKultura()
        feedLiveData.value = response
        fullItem.value = response.articleList

      } catch (e: Exception) {
        Log.d("ERROR", e.toString())
      }
    }
  }

  fun getPostFromSportRSS() {
    viewModelScope.launch {
      resetState()
      try {
        val response = service.fetchSport()
        feedLiveData.value = response
        fullItem.value = response.articleList

      } catch (e: Exception) {
        Log.d("ERROR", e.toString())
      }
    }
  }

  private fun resetState() {
    feedLiveData.value = null
    fullItem.value = emptyList()
  }
}