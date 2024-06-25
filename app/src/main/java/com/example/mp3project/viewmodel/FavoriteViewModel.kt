package com.example.mp3project.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mp3project.model.data.FavoriteItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel @Inject constructor(
  private val firestore: FirebaseFirestore,
  private val auth: FirebaseAuth
) : ViewModel() {

  private val _favoriteMaps = MutableStateFlow<List<Map<String, Any>>>(emptyList())
  val favoriteMaps: StateFlow<List<Map<String, Any>>> = _favoriteMaps

  init {
    fetchFavorites()
  }

  fun fetchFavorites() {
    val userId = auth.currentUser?.uid
    userId?.let { uid ->
      val userDocRef = firestore.collection("users").document(uid)
      userDocRef.collection("favorites").get()
        .addOnSuccessListener { documents ->
          val favoriteMaps = mutableListOf<Map<String, Any>>()

          for (document in documents) {
            val dataMap = document.data
            favoriteMaps.add(dataMap)
          }
          Log.d("test", "$favoriteMaps")
          _favoriteMaps.value = favoriteMaps

        }
        .addOnFailureListener { e ->
          Log.e(TAG, "Error fetching 'favorites' items", e)
        }
    }
  }

  fun addFavorite(item: FavoriteItem) {
    val userId = auth.currentUser?.uid
    userId?.let { uid ->
      val userDocRef = firestore.collection("users").document(uid)
      userDocRef.collection("favorites").document(item.date.replace(" ", "")).set(item)
        .addOnSuccessListener {
          Log.d(TAG, "ADDED")
          fetchFavorites()
        }
        .addOnFailureListener { e ->
          Log.d(TAG, e.toString())
        }
    }
  }

  fun removeFavorite(itemId: String) {
    val userId = auth.currentUser?.uid
    userId?.let { uid ->
      val userDocRef = firestore.collection("users").document(uid)
      userDocRef.collection("favorites").document(itemId).delete()
        .addOnSuccessListener {
          Log.d(TAG, "REMOVED")
          _favoriteMaps.value = _favoriteMaps.value.filterNot { it["date"] == itemId }
          fetchFavorites()
        }
        .addOnFailureListener { e ->
          Log.d(TAG, e.toString())
        }
    }
  }
}

