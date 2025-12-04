package com.aicloudflare.musicbox.core

import android.content.Context
// Import đầy đủ các Repository
import com.aicloudflare.musicbox.data.repository.AuthRepository
import com.aicloudflare.musicbox.data.repository.DownloadRepository
import com.aicloudflare.musicbox.data.repository.PlaylistRepository // Import PlaylistRepository
import com.aicloudflare.musicbox.data.repository.SongRepository
import com.aicloudflare.musicbox.data.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AppContainer(private val context: Context) {

    // 1. Firebase Instances (Lazy Init)
    private val firestoreInstance: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val authInstance: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    // 2. Repositories (Cung cấp công cụ cho toàn App)

    val songRepository: SongRepository by lazy {
        SongRepository(firestore = firestoreInstance)
    }

    val userRepository: UserRepository by lazy {
        UserRepository(firestore = firestoreInstance, auth = authInstance)
    }

    val downloadRepository: DownloadRepository by lazy {
        DownloadRepository(context)
    }

    // Đã thêm PlaylistRepository
    val playlistRepository: PlaylistRepository by lazy {
        PlaylistRepository(firestore = firestoreInstance, auth = authInstance)
    }

    // Đã thêm AuthRepository (Login)
    val authRepository: AuthRepository by lazy {
        AuthRepository(auth = authInstance)
    }
}