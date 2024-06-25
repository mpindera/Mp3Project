package com.example.mp3project.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mp3project.view.custom.TabsState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopAppBarViewModel @Inject constructor() : ViewModel() {

  var isVisibleRSSNews by mutableStateOf(false)
  var selectedTabIndex by mutableIntStateOf(0)
  var isVisibleTabs by mutableStateOf(TabsState.MAIN_VIEW)

  fun changeIsVisibleRSSNews(visible: Boolean) {
    isVisibleRSSNews = visible
  }

  fun changeSelectedTabIndex(selectedTab: Int) {
    selectedTabIndex = selectedTab
  }

  fun changeIsVisibleTabs(tabs: TabsState) {
    isVisibleTabs = tabs
  }
}