package com.example.mp3project.view.main_screen

import android.content.Intent
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import coil.compose.AsyncImage
import com.example.mp3project.R
import com.example.mp3project.model.data.FavoriteItem
import com.example.mp3project.model.navigation.Screen
import com.example.mp3project.view.custom.CustomGradient.gradientBrushL
import com.example.mp3project.view.custom.TabsState
import com.example.mp3project.view.favorite_screen.FavoriteScreen
import com.example.mp3project.view.login_screen.LoginScreen
import com.example.mp3project.view.top_bar_view.TopBarRSS
import com.example.mp3project.view.view_rss.AndroidViewRSS
import com.example.mp3project.viewmodel.ApiViewModel
import com.example.mp3project.viewmodel.FavoriteViewModel
import com.example.mp3project.viewmodel.MainViewModel
import com.example.mp3project.viewmodel.TopAppBarViewModel
import com.example.mp3project.viewmodel.auth.AuthManager
import com.example.mp3project.viewmodel.auth.AuthRepository
import com.example.mp3project.viewmodel.worker.ApiWorker
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@Composable
fun MainScreen(
  navController: NavHostController,
  authManager: AuthManager,
  authRepository: AuthRepository,
  topAppBarViewModel: TopAppBarViewModel = hiltViewModel(),
  fetchingViewModel: ApiViewModel = hiltViewModel(),
  mainViewModel: MainViewModel = hiltViewModel(),
  favoriteViewModel: FavoriteViewModel = hiltViewModel()
) {
  topAppBarViewModel.changeIsVisibleTabs(TabsState.MAIN_VIEW)
  val localContext = LocalContext.current
  val randomDelay = (1..5).random().toLong()
  val oneTimeWorkRequest = OneTimeWorkRequestBuilder<ApiWorker>()
    .setInitialDelay(randomDelay, TimeUnit.MINUTES)
    .build()

  WorkManager.getInstance(localContext).enqueue(oneTimeWorkRequest)

  Scaffold(
    topBar = {
      TopBarRSS(navController, authManager, authRepository)
    },
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .padding(innerPadding)
        .fillMaxSize()
        .background(brush = gradientBrushL)
    ) {
      LaunchedEffect(fetchingViewModel.fullItem.value) {
        mainViewModel.changeIsVisible(false)
        delay(400)
        mainViewModel.changeIsVisible(true)
        topAppBarViewModel.changeIsVisibleRSSNews(true)
        mainViewModel.fetchRead()
      }
      AnimatedVisibility(
        visible = mainViewModel.isVisible, enter = fadeIn(
          animationSpec = tween(durationMillis = 600)
        )
      ) {
        LazyColumn(contentPadding = PaddingValues(top = 20.dp)) {
          items(fetchingViewModel.fullItem.value) { i ->
            Card(
              modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .clickable {
                  mainViewModel.changeShowView(true)
                  mainViewModel.changeURL(i.link)
                  mainViewModel.addRead(i.title)
                },
              elevation = CardDefaults.elevatedCardElevation(50.dp),
              colors = CardDefaults.cardColors(mainViewModel.checkTitle(i.title))
            ) {
              Box(
                modifier = Modifier.fillMaxWidth()
              ) {
                IconButton(modifier = Modifier
                  .align(Alignment.TopStart)
                  .padding(10.dp), onClick = {
                  favoriteViewModel.addFavorite(
                    item = FavoriteItem(
                      i.title,
                      i.description,
                      i.link.replace("https://www.polsatnews.pl", ""),
                      i.enclosure[0].url,
                      i.pubDate.substring(0, 25)
                    )
                  )
                }) {
                  Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = null)
                }
                Text(
                  modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(10.dp),
                  text = i.pubDate.substring(0, 25),
                  fontWeight = FontWeight.Light,
                  textAlign = TextAlign.Start
                )
                IconButton(modifier = Modifier
                  .align(Alignment.TopEnd)
                  .padding(10.dp), onClick = {
                  mainViewModel.share(i.link, context = localContext)
                }) {
                  Icon(imageVector = Icons.Default.Share, contentDescription = null)
                }
              }

              Text(
                modifier = Modifier.padding(15.dp),
                text = i.title,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
              )
              AsyncImage(
                model = i.enclosure.getOrNull(1)?.url,
                contentDescription = "RSS_Image",
                placeholder = painterResource(id = R.drawable.placeholder),
                error = painterResource(id = R.drawable.placeholder),
                alignment = Alignment.Center,
                contentScale = ContentScale.Fit
              )
              Text(
                modifier = Modifier.padding(15.dp), text = i.description
              )

            }
          }
        }
      }
    }
  }
  if (mainViewModel.showViewState) {
    AndroidViewRSS(navController, authManager, authRepository)
  }

  when (topAppBarViewModel.selectedTabIndex) {
    0 -> fetchingViewModel.getPostsFromWszystkieRSS()
    1 -> fetchingViewModel.getPostsFromPolskaRSS()
    2 -> fetchingViewModel.getPostsFromSwiatRSS()
    3 -> fetchingViewModel.getPostsFromBiznesRSS()
    4 -> fetchingViewModel.getPostsFromTechnologieRSS()
    5 -> fetchingViewModel.getPostsFromMotoRSS()
    6 -> fetchingViewModel.getPostsFromKulturaRSS()
    7 -> fetchingViewModel.getPostsFromSportRSS()
    8 -> FavoriteScreen(navController, authManager, authRepository)
  }

}

@Composable
@Preview
fun TestMainScreen() {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(bottom = 10.dp)
      .clickable {

      }, elevation = CardDefaults.elevatedCardElevation(50.dp)
  ) {
    Text(
      modifier = Modifier.padding(15.dp),
      text = "i".substring(0, 25),
      fontWeight = FontWeight.Bold,
      textAlign = TextAlign.Start
    )
    Row {
      IconButton(onClick = {

      }) {
        Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = null)
      }
      Text(
        modifier = Modifier.padding(15.dp),
        text = "Sprzedadzą rzadką płytę Beatlesów. Album ma zaskakujący błąd",
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
      )
    }
    AsyncImage(
      model = "i.enclosure.getOrNull(1)?.url",
      contentDescription = "RSS_Image",
      placeholder = painterResource(id = R.drawable.placeholder),
      error = painterResource(id = R.drawable.placeholder),
      alignment = Alignment.Center,
      contentScale = ContentScale.Fit
    )
    Text(
      modifier = Modifier.padding(15.dp),
      text = "Ktoś się pomylił i podczas produkcji demonstracyjnej serii albumu Beatlesów źle zapisał nazwisko gwiazdy zespołu Paula McCartneya. To niejedyny powód, dlaczego płyta The Beatles wystawiona na aukcję w Anglii może zostać sprzedana nawet za niemal 10 tysięcy funtów. Otóż takich egzemplarzy na świecie jest co najwyżej 250."
    )
  }
}
