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
class ApiViewmodel @Inject constructor(
  private val service: Service
) : ViewModel() {

  val feedLiveData: MutableLiveData<Feed> = MutableLiveData()
  val fullName: MutableState<String?> = mutableStateOf(null)
  val fullItem: MutableState<List<RSS>> = mutableStateOf(emptyList())

  fun getPost() {
    viewModelScope.launch {
      try {
        val response = service.fetchPolska()
        feedLiveData.value = response

        fullName.value = response.channelTitle
        fullItem.value = response.articleList

      } catch (e: Exception) {
        Log.d("ERROR", e.toString())
      }
    }
  }
}