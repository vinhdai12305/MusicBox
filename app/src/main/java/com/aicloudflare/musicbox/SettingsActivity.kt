package com.aicloudflare.musicbox

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomnavigation.BottomNavigationView

class SettingsActivity : AppCompatActivity() {

    // Biến để điều khiển dòng chữ "Giao diện Tối/Sáng"
    private lateinit var txtDarkModeLabel: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. KIỂM TRA THEME KHI MỞ APP
        val sharedPref = getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        val isDarkMode = sharedPref.getBoolean("DARK_MODE", false)
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        setContentView(R.layout.activity_settings)

        // 2. TÌM CÁI DÒNG CHỮ ĐỂ TÍ NỮA ĐỔI TÊN (ID là lblDarkMode)
        txtDarkModeLabel = findViewById(R.id.lblDarkMode)
        updateDarkModeText(isDarkMode)

        // 3. CÀI ĐẶT MENU VÀ CHUYỂN TRANG
        setupBottomNavigation()
        setupNavigation()

        // 4. XỬ LÝ KHI BẤM VÀO MỤC DARK MODE (ID là tvDarkMode - Lớp phủ)
        val btnDarkMode = findViewById<View>(R.id.tvDarkMode)
        btnDarkMode.setOnClickListener {
            showThemeDialog(isDarkMode)
        }
    }

    // Hàm đổi chữ hiển thị
    private fun updateDarkModeText(isDark: Boolean) {
        if (isDark) {
            txtDarkModeLabel.text = getString(R.string.theme_dark) // "Giao diện Tối"
        } else {
            txtDarkModeLabel.text = getString(R.string.theme_light) // "Giao diện Sáng"
        }
    }

    // Hàm hiện Dialog chọn Theme
    private fun showThemeDialog(isCurrentDark: Boolean) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_theme)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val rgTheme = dialog.findViewById<RadioGroup>(R.id.rgTheme)
        val rbLight = dialog.findViewById<RadioButton>(R.id.rbLight)
        val rbDark = dialog.findViewById<RadioButton>(R.id.rbDark)
        val btnCancel = dialog.findViewById<View>(R.id.btnCancel)
        val btnApply = dialog.findViewById<View>(R.id.btnApply)

        // Tích sẵn vào ô đang chọn
        if (isCurrentDark) rbDark.isChecked = true else rbLight.isChecked = true

        btnCancel.setOnClickListener { dialog.dismiss() }

        btnApply.setOnClickListener {
            val isDarkSelected = (rgTheme.checkedRadioButtonId == R.id.rbDark)

            // Lưu và Áp dụng
            saveDarkModeState(isDarkSelected)
            updateDarkModeText(isDarkSelected)

            if (isDarkSelected) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

            dialog.dismiss()
        }
        dialog.show()
    }

    private fun saveDarkModeState(isDark: Boolean) {
        val sharedPref = getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("DARK_MODE", isDark)
            apply()
        }
    }

    private fun setupBottomNavigation() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.selectedItemId = R.id.nav_settings
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home, R.id.nav_favorites, R.id.nav_playlists, R.id.nav_settings -> true
                else -> false
            }
        }
    }

    private fun setupNavigation() {
        findViewById<View>(R.id.tvNotification).setOnClickListener {
            startActivity(Intent(this, NotificationActivity::class.java))
        }
        findViewById<View>(R.id.tvLanguage).setOnClickListener {
            startActivity(Intent(this, LanguageActivity::class.java))
        }
        findViewById<View>(R.id.tvQuit).setOnClickListener {
            showQuitDialog()
        }
    }

    private fun showQuitDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_quit)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val btnCancel = dialog.findViewById<View>(R.id.btnCancel)
        val btnConfirm = dialog.findViewById<View>(R.id.btnConfirmQuit)

        btnCancel.setOnClickListener { dialog.dismiss() }
        btnConfirm.setOnClickListener {
            dialog.dismiss()
            finishAffinity()
        }
        dialog.show()
    }
}