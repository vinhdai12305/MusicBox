plugins {
    id("com.google.gms.google-services") version "4.4.4" apply false
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.aicloudflare.musicbox"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.aicloudflare.musicbox"
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
}

dependencies {
    implementation(platform("com.google.firebase:firebase-bom:32.7.4"))

    // Kotlin Extensions cho Firebase Auth & Firestore
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")

    // Thư viện Coroutines Play Services (quan trọng để dùng .await() gọn gàng)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")

    // Lifecycle (Nếu bạn dùng ViewModel, rất nên dùng)
    // implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    // ...
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}