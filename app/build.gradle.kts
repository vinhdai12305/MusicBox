// build.gradle.kts (Module :app)

plugins {
    // Đảm bảo bạn có apply plugin Google Services ở đây nếu sử dụng phiên bản cũ của Gradle
    // Nếu dùng phiên bản hiện tại, chỉ cần khai báo plugin ở build.gradle.kts (project)
    // Của bạn có vẻ đang dùng phiên bản hiện đại, nên không cần chỉnh sửa ở đây.
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
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
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // --- FIREBASE VÀ KOTLIN ---

    // Sử dụng Firebase BOM để quản lý phiên bản
    implementation(platform("com.google.firebase:firebase-bom:32.7.4"))

    // Thư viện Firestore (Cần cho SongRepository và UserRepository)
    implementation("com.google.firebase:firebase-firestore-ktx")

    // Thư viện Coroutines Play Services (CẦN THIẾT cho .await() trong Repository)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")

    // (Tùy chọn) Thư viện Auth (nếu bạn làm tiếp logic Đăng nhập/Đăng ký)
    implementation("com.google.firebase:firebase-auth-ktx")

    // --- THƯ VIỆN ANDROID CƠ BẢN ---

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.core.ktx)

    // Thư viện ViewModel (RẤT NÊN DÙNG cho Người A, B, C, F)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")

    // --- TESTING ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}