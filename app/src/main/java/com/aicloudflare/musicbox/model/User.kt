package com.aicloudflare.musicbox.data.model

data class User(
    val uid: String = "",
    val username: String = "",
    val email: String = "",
    // Dùng cho logic Favorites/Library của bạn
    val favorites: List<String> = emptyList(),
    val playlists: List<String> = emptyList()
)