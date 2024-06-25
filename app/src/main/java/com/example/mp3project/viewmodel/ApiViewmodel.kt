package com.example.mp3project.viewmodel

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
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
import com.example.mp3project.viewmodel.report.Reports
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiViewModel @Inject constructor(
  private val service: Service,
  private val firestore: FirebaseFirestore,
  private val auth: FirebaseAuth,
  val context: Context,
) : ViewModel() {

  val feedLiveData: MutableLiveData<Feed> = MutableLiveData()
  val fullItem: MutableState<List<RSS>> = mutableStateOf(emptyList())
  val firstTitleText: MutableState<String> = mutableStateOf("")

  fun fetchFirstTitle(firstTitle: String) {
    val userId = auth.currentUser?.uid
    userId?.let { uid ->
      val userDocRef = firestore.collection("users").document(uid)
      val titleDocRef = userDocRef.collection("first_title").document(firstTitle)
      val data = mapOf("title" to firstTitle)


      userDocRef.collection("first_title").get().addOnSuccessListener { querySnapshot ->
        if (!querySnapshot.isEmpty) {
          val firstDocument = querySnapshot.documents[0]
          val documentId = firstDocument.id
          userId.let { uid ->
            val titleDocReff = userDocRef.collection("first_title").document(documentId)

            titleDocRef.get().addOnSuccessListener { document ->
              if (!document.exists()) {
                titleDocReff.delete().addOnSuccessListener {
                  userDocRef.collection("first_title").document(firstTitle).set(data)
                    .addOnSuccessListener {
                      viewModelScope.launch {
                        Reports(context = context).savedInDatabase()
                      }
                    }.addOnFailureListener { e ->
                      Log.e("ApiViewModel", "Error adding first title to Firestore", e)
                    }
                }.addOnFailureListener { exception ->
                  Log.e(
                    "ApiViewModel",
                    "Błąd podczas usuwania dokumentu $documentId: ",
                    exception
                  )
                }
              }
            }
          }
        } else {
          userDocRef.collection("first_title").document(firstTitle).set(data)
            .addOnSuccessListener {
              viewModelScope.launch {
                Reports(context = context).savedInDatabase()
              }
            }.addOnFailureListener { e ->
              Log.e("ApiViewModel", "Error adding first title to Firestore", e)
            }
        }
      }.addOnFailureListener { exception ->
        Log.e("ApiViewModel", "Błąd podczas pobierania dokumentów: ", exception)
      }
    }
  }


  fun responseGetFirstTitle(response: Feed) {
    feedLiveData.value = response
    fullItem.value = response.articleList
    fullItem.value.firstOrNull()?.title?.let { firstTitle ->
      fetchFirstTitle(firstTitle)
      firstTitleText.value = firstTitle
    }
  }

  fun getPostsFromWszystkieRSS() {
    viewModelScope.launch {
      resetState()
      try {
        val response = service.fetchWszystkie()
        feedLiveData.value = response
        fullItem.value = response.articleList
        responseGetFirstTitle(response)
      } catch (e: Exception) {
        Log.d("ERROR", e.toString())
      }
    }
  }

  fun getPostsFromPolskaRSS() {
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

  fun getPostsFromSwiatRSS() {
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

  fun getPostsFromBiznesRSS() {
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

  fun getPostsFromTechnologieRSS() {
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

  fun getPostsFromMotoRSS() {
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

  fun getPostsFromKulturaRSS() {
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

  fun getPostsFromSportRSS() {
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