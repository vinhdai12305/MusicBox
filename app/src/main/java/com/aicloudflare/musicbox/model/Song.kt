package com.aicloudflare.musicbox.data.model

data class Song(
    val id: String = "",
    val title: String = "",
    val artist: String = "",
    val album: String = "",
    val duration: Long = 0L,
    val albumArtUrl: String = "",
    // Quan trọng cho Người C (ExoPlayer)
    val audioUrl: String = "",
    // Quan trọng cho thống kê Tải nhạc/Thịnh hành
    val downloadCount: Long = 0L
)