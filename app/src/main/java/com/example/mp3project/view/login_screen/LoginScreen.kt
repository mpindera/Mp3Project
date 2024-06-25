package com.example.mp3project.view.login_screen

import androidx.compose.foundation.Image
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mp3project.R
import com.example.mp3project.model.navigation.Screen
import com.example.mp3project.view.custom.CustomGradient.gradientBrush
import com.example.mp3project.viewmodel.LoginViewModel
import com.example.mp3project.viewmodel.auth.AuthManager
import com.example.mp3project.viewmodel.auth.rememberFirebaseAuthLauncher
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


@Composable
fun LoginScreen(
  navController: NavHostController,
  loginViewModel: LoginViewModel = hiltViewModel(),
) {
  var user by remember { mutableStateOf(Firebase.auth.currentUser) }
  val launcher = rememberFirebaseAuthLauncher(
    navController,
    onAuthComplete = { result ->
      user = result.user
    },
    onAuthError = {
      user = null
    }
  )
  val token = stringResource(R.string.default_web)
  val context = LocalContext.current


  Card(
    modifier = Modifier.fillMaxSize(), shape = RoundedCornerShape(0)
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
            modifier = Modifier
              .align(Alignment.TopCenter)
              .padding(25.dp),
            text = stringResource(id = R.string.login),
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            letterSpacing = 5.sp,
            color = Color.White,
            fontFamily = FontFamily.Cursive,
          )

          Column(modifier = Modifier.align(Alignment.Center)) {

            /**
             * Login
             * **/
            TextField(
              modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .shadow(elevation = 30.dp, shape = ShapeDefaults.Medium)
                .clip(RoundedCornerShape(30.dp)),
              value = loginViewModel.email,
              onValueChange = {
                loginViewModel.changeLoginText(it)
              },
              label = {
                Text(
                  fontSize = 20.sp,
                  text = stringResource(id = R.string.login_label),
                  fontFamily = FontFamily.Cursive
                )
              },
              maxLines = 1,
              colors = TextFieldDefaults.colors(Color.Black)
            )

            /**
             * Passsword
             * **/
            TextField(
              modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .shadow(elevation = 30.dp, shape = ShapeDefaults.Medium)
                .clip(RoundedCornerShape(30.dp)),
              value = loginViewModel.password,
              onValueChange = {
                loginViewModel.changePasswordText(it)
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

            ElevatedButton(modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp)
              .shadow(elevation = 30.dp)
              .clip(RoundedCornerShape(30.dp)),
              shape = ShapeDefaults.Medium,
              colors = ButtonDefaults.buttonColors(Color.White),
              onClick = {
                loginViewModel.checkValidationLogin(
                  navController = navController,
                  loginViewModel = loginViewModel
                )
              }) {
              Text(
                fontFamily = FontFamily.Cursive,
                fontSize = 20.sp,
                text = stringResource(id = R.string.login),
                color = Color.Black
              )
            }

            IconButton(modifier = Modifier.align(Alignment.CenterHorizontally), onClick = {
              val gso =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                  .requestIdToken(token)
                  .requestEmail()
                  .build()
              val googleSignInClient = GoogleSignIn.getClient(context, gso)
              launcher.launch(googleSignInClient.signInIntent)
            }) {
              Image(
                painter = painterResource(id = R.drawable.google_login), contentDescription = null
              )
            }

            TextButton(modifier = Modifier.fillMaxWidth(), onClick = {
              navController.navigate(Screen.RegisterScreen.route)
            }) {
              Text(
                text = stringResource(id = R.string.dont_have_account),
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
