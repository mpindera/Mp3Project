plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.jetbrains.kotlin.android)
  id("com.google.gms.google-services")
  id("kotlin-kapt")
  id("com.google.dagger.hilt.android")
}

android {
  namespace = "com.example.mp3project"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.example.mp3project"
    minSdk = 24
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    vectorDrawables {
      useSupportLibrary = true
    }
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }
  buildFeatures {
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.5.1"
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.ui)
  implementation(libs.androidx.ui.graphics)
  implementation(libs.androidx.ui.tooling.preview)
  implementation(libs.androidx.material3)
  implementation(libs.firebase.auth)
  implementation(libs.androidx.espresso.core)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.ui.test.junit4)
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)

  /** Navigation **/
  implementation(libs.androidx.navigation.compose)

  /** ViewModel **/
  implementation(libs.androidx.lifecycle.viewmodel.compose)

  /** Coil **/
  implementation(libs.coil.compose)
  implementation(libs.accompanist.permissions)

  /** Retrofit **/
  implementation(libs.com.squareup.retrofit2.retrofit)
  implementation("com.squareup.retrofit2:converter-simplexml:2.9.0") {
    exclude(group = "stax")
    exclude(group = "stax-api")
    exclude(group = "xpp3")
  }

  implementation (libs.logging.interceptor)
  implementation(libs.jakewharton.retrofit2.kotlin.coroutines.adapter)

  /** Dagger - Hilt **/
  implementation( libs.hilt.android)
  kapt(libs.hilt.compiler)
  implementation(libs.androidx.hilt.navigation.compose)

}
kapt {
  correctErrorTypes = true
}