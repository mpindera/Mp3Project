package com.example.mp3project.view.register_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mp3project.R
import com.example.mp3project.model.navigation.Screen
import com.example.mp3project.view.custom.CustomGradient.gradientBrush
import com.example.mp3project.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(
  navController: NavHostController,
  RegisterViewModel: RegisterViewModel= hiltViewModel()
) {

  Card(
    modifier = Modifier.fillMaxSize(), shape = RoundedCornerShape(0)
  ) {
    Box(modifier = Modifier.background(gradientBrush)) {
      ElevatedCard(
        modifier = Modifier
          .padding(top = 150.dp)
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
            modifier = Modifier
              .align(Alignment.TopCenter)
              .padding(25.dp),
            text = stringResource(id = R.string.register),
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            letterSpacing = 5.sp,
            color = Color.White,
            fontFamily = FontFamily.Cursive,
          )

          Column(modifier = Modifier.align(Alignment.Center)) {
            /**
             * Name
             * **/
            TextField(
              modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .shadow(elevation = 30.dp, shape = ShapeDefaults.Medium)
                .clip(RoundedCornerShape(30.dp)),
              value = RegisterViewModel.name,
              onValueChange = {
                RegisterViewModel.changeNameText(it)
              },
              label = {
                Text(
                  fontSize = 20.sp,
                  text = stringResource(id = R.string.name_text),
                  fontFamily = FontFamily.Cursive
                )
              },
              maxLines = 1,
              colors = TextFieldDefaults.colors(Color.Black)
            )

            /**
             * Email
             * **/
            TextField(
              modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .shadow(elevation = 30.dp, shape = ShapeDefaults.Medium)
                .clip(RoundedCornerShape(30.dp)),
              value = RegisterViewModel.email,
              onValueChange = {
                RegisterViewModel.changeEmailText(it)
              },
              label = {
                Text(
                  fontSize = 20.sp,
                  text = stringResource(id = R.string.email),
                  fontFamily = FontFamily.Cursive
                )
              },
              maxLines = 1,
              colors = TextFieldDefaults.colors(Color.Black)
            )

            /**
             * Password
             * **/
            TextField(
              modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .shadow(elevation = 30.dp, shape = ShapeDefaults.Medium)
                .clip(RoundedCornerShape(30.dp)),
              value = RegisterViewModel.password,
              onValueChange = {
                RegisterViewModel.changePasswordText(it)
              },
              label = {
                Text(
                  fontSize = 20.sp,
                  text = stringResource(id = R.string.password_label),
                  fontFamily = FontFamily.Cursive
                )
              },
              maxLines = 1,
              colors = TextFieldDefaults.colors(Color.Black),
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
              visualTransformation = PasswordVisualTransformation()

            )

            /**
             * Confirm Password
             * **/
            TextField(
              modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .shadow(elevation = 30.dp, shape = ShapeDefaults.Medium)
                .clip(RoundedCornerShape(30.dp)),
              value = RegisterViewModel.confirmPassword,
              onValueChange = {
                RegisterViewModel.changeConfirmPasswordText(it)
              },
              label = {
                Text(
                  fontSize = 20.sp,
                  text = stringResource(id = R.string.confirm_password_label),
                  fontFamily = FontFamily.Cursive
                )
              },
              maxLines = 1,
              colors = TextFieldDefaults.colors(Color.Black),
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
              visualTransformation = PasswordVisualTransformation()

            )

            ElevatedButton(modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp)
              .shadow(elevation = 30.dp)
              .clip(RoundedCornerShape(30.dp)),
              shape = ShapeDefaults.Medium,
              colors = ButtonDefaults.buttonColors(Color.White),
              onClick = {
                RegisterViewModel.checkValidation(navController, RegisterViewModel)
              }) {
              Text(
                fontFamily = FontFamily.Cursive,
                fontSize = 20.sp,
                text = stringResource(id = R.string.register),
                color = Color.Black
              )
            }

            TextButton(modifier = Modifier.fillMaxWidth(), onClick = {
              navController.navigate(Screen.LoginScreen.route)
            }) {
              Text(
                text = stringResource(id = R.string.have_account),
                fontSize = 20.sp,
                fontFamily = FontFamily.Cursive,
                color = Color.White
              )
            }
          }
        }
      }
    }
  }
}

@Composable
@Preview(showBackground = true)
fun RegisterScreenPreview() {
  val navController = rememberNavController()
  RegisterScreen(navController)
}