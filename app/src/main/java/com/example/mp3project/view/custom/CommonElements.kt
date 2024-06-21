package com.example.mp3project.view.custom

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage

class CommonElements {
  val dbGet = Firebase.firestore.collection("users").get()
  val dbDocument = Firebase.firestore.collection("users")
  val mAuth = FirebaseAuth.getInstance().currentUser
  val storage = Firebase.storage.reference.child("pdfs")


}