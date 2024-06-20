package com.example.mp3project.view.login_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mp3project.R


@Composable
fun LoginScreen() {
  val lighterRed = lerp(Color.Red, Color.White, 0.5f)
  val lighterBlue = lerp(Color.Black, Color.White, 0.2f)

  val gradientBrush = Brush.linearGradient(
    colors = listOf(
      lighterRed,
      lighterBlue
    ),
    start = Offset.Zero,
    end = Offset.Infinite
  )

  Card(
    modifier = Modifier
      .fillMaxSize(),
    shape = RoundedCornerShape(0)
  ) {
    Box(modifier = Modifier.background(gradientBrush)) {
      ElevatedCard(
        modifier = Modifier
          .padding(top = 200.dp)
          .fillMaxSize()
          .clip(RoundedCornerShape(topStart = 150.dp)),
        elevation = CardDefaults.elevatedCardElevation(250.dp)
      ) {
        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
        ) {
          Text(
            modifier = Modifier.align(Alignment.TopCenter),
            text = "Login",
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            letterSpacing = 5.sp,
            color = Color.White,
            fontFamily = FontFamily.Cursive,
          )

          Column(modifier = Modifier.align(Alignment.Center)) {
            TextField(
              modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
              value = "Email",
              onValueChange = {

              },
              placeholder = { Text(text = stringResource(id = R.string.email)) },
              maxLines = 1,
              colors = TextFieldDefaults.colors(unfocusedIndicatorColor = Color.Transparent)
            )


            TextField(
              modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
              value = "Password",
              onValueChange = {

              },
              placeholder = { Text(text = stringResource(id = R.string.password)) },
              maxLines = 1,
              colors = TextFieldDefaults.colors(unfocusedIndicatorColor = Color.Transparent)
            )
          }
        }
      }
    }
  }
}

@Composable
@Preview(showBackground = true)
fun LoginScreenPreview() {
  LoginScreen()
}