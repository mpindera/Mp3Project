package com.example.mp3project.model

import android.graphics.Color
import android.graphics.drawable.Icon
import android.provider.CalendarContract.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mp3project.R
import com.example.mp3project.view.custom.CustomGradient.gradientBrush
import com.example.mp3project.view.main_screen.MainScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarRSS() {
  Box(
    modifier = Modifier
      .background(brush = gradientBrush)
      .fillMaxWidth()

  ) {
    TopAppBar(
      colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = androidx.compose.ui.graphics.Color.Transparent
      ),
      title = {
        Box(modifier = Modifier.fillMaxWidth()) {
          Text(
            text = stringResource(id = R.string.rss_news),
            fontSize = 30.sp,
            fontFamily = FontFamily.Cursive,
            modifier = Modifier.align(Alignment.Center)
          )
        }
      },
      navigationIcon = {
        Icon(
          painter = painterResource(id = R.drawable.baseline_logout_24),
          contentDescription = "log_out_image"
        )
      }
    )
  }
}

@Composable
@Preview
fun testTopBar() {
  TopBarRSS()
}
