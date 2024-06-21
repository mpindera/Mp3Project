package com.example.mp3project.view.main_screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mp3project.model.TopBarRSS
import com.example.mp3project.model.data.Tabs
import com.example.mp3project.view.custom.CustomGradient.gradientBrush
import com.example.mp3project.view.custom.CustomGradient.gradientBrushL

@Composable
fun MainScreen() {
  Scaffold(
    topBar = {
      TopBarRSS()
    },
  ) { innerPadding ->
    val context = LocalContext.current
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column(
      modifier = Modifier
        .padding(innerPadding)
        .fillMaxSize()
        .background(brush = gradientBrushL)

    ) {
      LazyColumn(contentPadding = PaddingValues(top = 20.dp)) {
        item {
          repeat(30) {
            Card(
              modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
            ) {

              Text(modifier = Modifier.padding(15.dp), text = "Item $it")
            }
          }
        }
      }
    }
  }
}

@Composable
@Preview
fun testMainScreen() {
  MainScreen()
}
