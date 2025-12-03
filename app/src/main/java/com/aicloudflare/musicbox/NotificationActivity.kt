package com.aicloudflare.musicbox

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class NotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        // Xử lý sự kiện bấm nút Back
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish() // Đóng màn hình này lại để quay về màn hình trước
        }
    }
}