package com.example.mp3project.view.favorite_screen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Favorite
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.mp3project.R
import com.example.mp3project.model.data.FavoriteItem
import com.example.mp3project.view.custom.CustomGradient.gradientBrushL
import com.example.mp3project.view.top_bar_view.TopBarRSS
import com.example.mp3project.view.view_rss.AndroidViewRSS
import com.example.mp3project.viewmodel.FavoriteViewModel
import com.example.mp3project.viewmodel.MainViewModel
import com.example.mp3project.viewmodel.auth.AuthManager
import com.example.mp3project.viewmodel.auth.AuthRepository
import kotlinx.coroutines.delay

@Composable
fun FavoriteScreen(
  navController: NavHostController,
  authManager: AuthManager,
  authRepository: AuthRepository,
  favoriteViewModel: FavoriteViewModel = hiltViewModel(),
  mainViewModel: MainViewModel = hiltViewModel()
) {
  val favoriteMaps by favoriteViewModel.favoriteMaps.collectAsState()
  val context = LocalContext.current
  Scaffold(
    topBar = {
      TopBarRSS(
        navController = navController, authManager = authManager, authRepository = authRepository
      )
    },
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .padding(innerPadding)
        .fillMaxSize()
        .background(brush = gradientBrushL)
    ) {
      if (favoriteMaps.isEmpty()) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(innerPadding), contentAlignment = Alignment.Center
        ) {
          Card(modifier = Modifier.padding(15.dp)) {
            Text(
              modifier = Modifier.padding(5.dp),
              text = "List is empty, add to favorite",
              fontSize = 20.sp,
              textAlign = TextAlign.Center
            )
          }
        }
      }
      LazyColumn(contentPadding = PaddingValues(top = 20.dp)) {
        items(favoriteMaps) { favoriteItem ->
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(bottom = 10.dp)
              .clickable {
                mainViewModel.changeShowView(true)
                mainViewModel.changeURL("https://www.polsatnews.pl${favoriteItem["link"]}")
              },
            elevation = CardDefaults.elevatedCardElevation(50.dp),
            colors = CardDefaults.cardColors(mainViewModel.checkTitle("${favoriteItem["title"]}"))
          ) {
            Box(
              modifier = Modifier.fillMaxWidth()
            ) {
              IconButton(modifier = Modifier
                .align(Alignment.TopStart)
                .padding(10.dp), onClick = {
                favoriteViewModel.removeFavorite(favoriteItem["date"].toString().replace(" ", ""))
                favoriteViewModel.fetchFavorites()
              }) {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
              }
              Text(
                modifier = Modifier
                  .align(Alignment.TopCenter)
                  .padding(10.dp),
                text = "${favoriteItem["date"]}",
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Start
              )
              IconButton(modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(10.dp), onClick = {
                mainViewModel.share("${favoriteItem["link"]}", context = context)
              }) {
                Icon(imageVector = Icons.Default.Share, contentDescription = null)
              }
            }
            Text(
              modifier = Modifier.padding(15.dp),
              text = "${favoriteItem["title"]}",
              fontWeight = FontWeight.Bold,
              textAlign = TextAlign.Center
            )
            AsyncImage(
              modifier = Modifier.fillMaxSize(),
              model = "${favoriteItem["imageUrl"]}",
              contentDescription = "RSS_Image",
              placeholder = painterResource(id = R.drawable.placeholder),
              error = painterResource(id = R.drawable.placeholder),
              alignment = Alignment.Center,
              contentScale = ContentScale.Crop
            )
            Text(
              modifier = Modifier.padding(15.dp), text = "${favoriteItem["description"]}"
            )
          }
        }
      }
    }
  }
  if (mainViewModel.showViewState) {
    AndroidViewRSS(navController, authManager, authRepository)
  }
}

