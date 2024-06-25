package com.example.mp3project.viewmodel

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mp3project.R
import com.example.mp3project.model.data.FavoriteItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val firestore: FirebaseFirestore,
  private val auth: FirebaseAuth
) : ViewModel() {
  var selectedURL by mutableStateOf("")
  var showViewState by mutableStateOf(false)
  var isVisible by mutableStateOf(false)

  private val _readItems = MutableStateFlow<List<String>>(emptyList())
  val readItems: StateFlow<List<String>> = _readItems

  fun changeIsVisible(visible: Boolean) {
    isVisible = visible
  }

  fun changeShowView(showDialog: Boolean) {
    showViewState = showDialog
  }

  fun changeURL(url: String) {
    selectedURL = url
  }

  fun fetchRead() {
    val userId = auth.currentUser?.uid
    if (userId != null) {
      viewModelScope.launch {
        try {
          val userDocRef = firestore.collection("users").document(userId)
          val snapshot = userDocRef.collection("read").get().await()
          val listString = snapshot.documents.map { it.data?.get("title").toString() }
          _readItems.value = listString
        } catch (e: Exception) {
          Log.e(TAG, "Error fetching 'read' items", e)
        }
      }
    }
  }

  fun addRead(item: String) {
    val userId = auth.currentUser?.uid
    val data = mapOf("title" to item)
    userId?.let { uid ->
      val userDocRef = firestore.collection("users").document(uid)
      userDocRef.collection("read").document(item).set(data)
        .addOnSuccessListener {
          Log.d(TAG, "ADDED")
          fetchRead()
        }
        .addOnFailureListener { e ->
          Log.d(TAG, e.toString())
        }
    }
  }

  fun checkTitle(titleName: String) =
    if (readItems.value.contains(titleName)) Color(0xFFD3D3D3) else Color(0xFFFFFFFF)

  fun share(text: String, context: Context) {
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
      putExtra(Intent.EXTRA_TEXT, text)
      type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(context, shareIntent, null)
  }
}