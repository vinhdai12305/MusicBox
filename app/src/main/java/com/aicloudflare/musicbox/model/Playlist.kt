package com.aicloudflare.musicbox.data.model

import com.google.firebase.Timestamp

data class Playlist(
    val id: String = "",
    val name: String = "",
    val userId: String = "",
    // Danh sách ID các bài hát có trong playlist này
    val songIds: List<String> = emptyList(),
    // Link ảnh bìa playlist (có thể lấy ảnh bài đầu tiên làm ảnh bìa)
    val coverUrl: String = "",
    val createdAt: Timestamp? = null
)