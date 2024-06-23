package com.example.mp3project.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class TopAppBarViewModel : ViewModel() {
  var selectedTabIndex by mutableIntStateOf(0)

  fun changeSelectedTabIndex(selectedTab: Int) {
    selectedTabIndex = selectedTab
  }
}