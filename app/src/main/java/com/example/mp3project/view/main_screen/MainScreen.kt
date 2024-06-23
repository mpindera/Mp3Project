package com.example.mp3project.view.main_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mp3project.R
import com.example.mp3project.model.retrofit.RetrofitInstance
import com.example.mp3project.model.retrofit.Service
import com.example.mp3project.view.top_bar_view.TopBarRSS
import com.example.mp3project.view.custom.CustomGradient.gradientBrushL
import com.example.mp3project.viewmodel.ApiViewModel
import com.example.mp3project.viewmodel.TopAppBarViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun MainScreen(topAppBarViewModel: TopAppBarViewModel, fetchingViewModel: ApiViewModel) {
  Scaffold(
    topBar = {
      TopBarRSS(topAppBarViewModel)
    },
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .padding(innerPadding)
        .fillMaxSize()
        .background(brush = gradientBrushL)
    ) {
      LazyColumn(contentPadding = PaddingValues(top = 20.dp)) {
        items(fetchingViewModel.fullItem.value) { i ->
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(bottom = 10.dp),
            elevation= CardDefaults.elevatedCardElevation(50.dp)
          ) {
            Text(
              modifier = Modifier.padding(15.dp),
              text = i.title, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center
            )
            AsyncImage(model = i.enclosure[1].url, contentDescription ="photo", alignment = Alignment.Center, contentScale = ContentScale.Fit)
            Text(
              modifier = Modifier.padding(15.dp),
              text = i.description
            )
          }
        }
      }
    }
  }


  when (topAppBarViewModel.selectedTabIndex) {
    0 -> fetchingViewModel.getPostsFromWszystkieRSS()
    1 -> fetchingViewModel.getPostFromPolskaRSS()
    2 -> fetchingViewModel.getPostFromSwiatRSS()
    3 -> fetchingViewModel.getPostFromBiznesRSS()
    4 -> fetchingViewModel.getPostFromTechnologieRSS()
    5 -> fetchingViewModel.getPostFromMotoRSS()
    6 -> fetchingViewModel.getPostFromKulturaRSS()
    7 -> fetchingViewModel.getPostFromSportRSS()
  }

}

@Composable
@Preview
fun testMainScreen() {
  /*val topAppBarViewModel = TopAppBarViewModel()
  val fetchingViewModel = ApiViewModel(service = RetrofitInstance.api)
  MainScreen(topAppBarViewModel, fetchingViewModel)*/

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(bottom = 10.dp)
  ) {
      Text(
      modifier = Modifier.padding(15.dp),
      text = "Andrzej Duda przyleciał do Chin. Uroczyste powitanie na lotnisku"
    )
    Text(
      modifier = Modifier.padding(15.dp),
      text = "Byli wojskowi w odświętnych strojach, dzieci z bukietami kwiatów i przedstawiciele władz. Para prezydencka dotarła w sobotę do Chin na pokładzie rządowego samolotu. Andrzej Duda chce w Państwie Środka porozmawiać z Xi Jinpingiem m.in. o zagrożeniu ze strony Rosji, jak również przekonać Chińczyków, że warto inwestować nad Wisłą."
    )
  }
}
