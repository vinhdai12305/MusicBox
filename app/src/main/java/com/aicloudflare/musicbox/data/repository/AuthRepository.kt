package com.aicloudflare.musicbox.data.repository

import com.aicloudflare.musicbox.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

// Interface này để Team UI (Java) nhận kết quả
interface AuthCallback {
    fun onSuccess(user: FirebaseUser?)
    fun onError(message: String)
}

class AuthRepository(private val auth: FirebaseAuth) {

    // 1. Kiểm tra xem user đã đăng nhập từ trước chưa
    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    // 2. Lấy thông tin user hiện tại (Email, ID)
    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    // 3. Đăng nhập (Hỗ trợ Java)
    fun login(email: String, pass: String, callback: AuthCallback) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // Chạy tác vụ mạng ở luồng IO
                val result = withContext(Dispatchers.IO) {
                    auth.signInWithEmailAndPassword(email, pass).await()
                }
                // Trả kết quả về luồng chính
                callback.onSuccess(result.user)
            } catch (e: Exception) {
                callback.onError(e.message ?: "Đăng nhập thất bại")
            }
        }
    }

    // 4. Đăng ký (Hỗ trợ Java)
    fun register(email: String, pass: String, callback: AuthCallback) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    auth.createUserWithEmailAndPassword(email, pass).await()
                }
                callback.onSuccess(result.user)
            } catch (e: Exception) {
                callback.onError(e.message ?: "Đăng ký thất bại")
            }
        }
    }

    // 5. Đăng xuất
    fun logout() {
        auth.signOut()
    }
}