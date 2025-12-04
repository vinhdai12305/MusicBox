// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false

    // --- QUAN TRỌNG: THÊM DÒNG NÀY ĐỂ SỬA LỖI ---
    // Khai báo phiên bản cho Google Services (để module :app dùng được)
    id("com.google.gms.google-services") version "4.4.1" apply false
}