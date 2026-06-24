plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

   // alias(libs.plugins.ksp)
   // alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.newsit"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.newsit"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Splash Screen API
// Displays a splash screen while the app is loading.
    implementation(libs.androidx.core.splashscreen)

// Navigation Compose
// Handles navigation between Compose screens.
    implementation(libs.androidx.navigation.compose)

// Hilt Dependency Injection
// Creates and provides app dependencies automatically.
    //implementation(libs.hilt.android)
   // ksp(libs.hilt.compiler)

// Allows Hilt to work with Navigation Compose.
    //implementation(libs.androidx.hilt.navigation.compose)

// Retrofit
// Performs HTTP requests and communicates with REST APIs.
    //implementation(libs.retrofit)

// Gson Converter
// Converts JSON responses into Kotlin objects.
   // implementation(libs.retrofit.gson)

// Coil
// Loads and displays images from URLs in Compose.
    //implementation(libs.coil.compose)

// DataStore
// Stores small pieces of persistent data such as settings and preferences.
    //implementation(libs.androidx.datastore)

// Paging 3
// Loads large datasets efficiently page by page.
    //implementation(libs.androidx.paging.runtime)

// Compose integration for Paging 3.
    //implementation(libs.androidx.paging.compose)

// Room Database
// Provides a local SQLite database with Kotlin-friendly APIs.
    //implementation(libs.androidx.room.runtime)

// Adds Kotlin Coroutines support for Room.
    //implementation(libs.androidx.room.ktx)

// Generates Room database code at build time.
   // ksp(libs.androidx.room.compiler)
}