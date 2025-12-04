package com.aicloudflare.musicbox

import android.app.Application
import android.util.Log
import com.aicloudflare.musicbox.core.AppContainer
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions

class MusicBoxApp : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()

        // =========================================================================
        // CẤU HÌNH THỦ CÔNG (HARDCODE) - KHẮC PHỤC LỖI 100%
        // =========================================================================

        // 1. Tạo cấu hình từ chính thông tin file JSON bạn vừa gửi
        val myOptions = FirebaseOptions.Builder()
            .setApiKey("AIzaSyAarZyrcVjBKqIhz4rp07HMai_V7IlDkgQ") // current_key
            .setApplicationId("1:692051298480:android:b6495a80748f5a988b5375") // mobilesdk_app_id
            .setProjectId("music-box-app-2309") // project_id
            .setStorageBucket("music-box-app-2309.firebasestorage.app") // storage_bucket
            .build()

        // 2. Kiểm tra và khởi động Firebase
        // Dùng try-catch để an toàn, tránh crash nếu đã khởi động rồi
        if (FirebaseApp.getApps(this).isEmpty()) {
            try {
                FirebaseApp.initializeApp(this, myOptions)
                Log.d("FIREBASE_INIT", "✅ Đã khởi động Firebase THỦ CÔNG thành công!")
            } catch (e: Exception) {
                Log.e("FIREBASE_INIT", "❌ Lỗi khởi động: ${e.message}")
            }
        }

        // 3. Tạo Container chứa các Repository
        container = AppContainer(this)
    }
}