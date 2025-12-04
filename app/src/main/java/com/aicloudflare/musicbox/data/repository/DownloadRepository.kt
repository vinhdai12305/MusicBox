package com.aicloudflare.musicbox.data.repository

import android.content.Context
import android.util.Log
import com.aicloudflare.musicbox.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URL

class DownloadRepository(private val context: Context) {

    // Tạo thư mục riêng để lưu nhạc: /data/user/0/com.aicloudflare.musicbox/files/music_downloaded/
    private val musicDir: File by lazy {
        File(context.filesDir, "music_downloaded").apply {
            if (!exists()) mkdirs() // Nếu chưa có thì tạo thư mục mới
        }
    }

    // 1. Hàm tải nhạc từ URL về máy
    suspend fun downloadSong(songUrl: String, songId: String): Resource<String> {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL(songUrl)
                val connection = url.openConnection()
                connection.connect()

                val inputStream = connection.getInputStream()
                // Lưu tên file là [ID_bài_hát].mp3 để dễ tìm lại
                val file = File(musicDir, "$songId.mp3")

                // Ghi dữ liệu vào file
                file.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }

                Log.d("DownloadRepo", "Đã tải xong: ${file.absolutePath}")
                // Trả về đường dẫn file sau khi tải xong
                Resource.Success(file.absolutePath)
            } catch (e: Exception) {
                Log.e("DownloadRepo", "Download failed: $e")
                Resource.Error("Lỗi tải xuống: ${e.message}")
            }
        }
    }

    // 2. Kiểm tra xem bài hát này đã tải chưa (để hiện icon Check hay Download)
    fun isSongDownloaded(songId: String): Boolean {
        val file = File(musicDir, "$songId.mp3")
        return file.exists()
    }

    // 3. Lấy đường dẫn file trong máy (để Player phát offline)
    fun getLocalSongPath(songId: String): String? {
        val file = File(musicDir, "$songId.mp3")
        return if (file.exists()) file.absolutePath else null
    }

    // 4. Xóa bài hát (để giải phóng bộ nhớ)
    fun deleteDownloadedSong(songId: String): Boolean {
        val file = File(musicDir, "$songId.mp3")
        return if (file.exists()) {
            file.delete()
        } else {
            false
        }
    }
}