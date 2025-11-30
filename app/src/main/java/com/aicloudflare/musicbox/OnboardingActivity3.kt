package com.aicloudflare.musicbox

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class OnboardingActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_3)

        val btnNext = findViewById<Button>(R.id.btnNext3)

        btnNext.setOnClickListener {
            // ĐÂY LÀ BƯỚC QUAN TRỌNG: Chuyển vào Màn hình chính (Home)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            // Hiệu ứng chuyển trang mượt
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

            // Đóng toàn bộ luồng onboarding để user không back lại được
            finishAffinity()
        }
    }

    // Nếu muốn hiệu ứng lùi về trang 2 khi bấm nút Back điện thoại
    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right) // Giả sử bạn đã tạo file này, nếu chưa thì bỏ dòng này
    }
}