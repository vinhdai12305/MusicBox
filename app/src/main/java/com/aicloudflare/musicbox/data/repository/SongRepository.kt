package com.aicloudflare.musicbox.data.repository

import android.util.Log
import com.aicloudflare.musicbox.data.model.Song
import com.aicloudflare.musicbox.utils.Resource
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

// =================================================================
// INTERFACE CHO JAVA (Để team Java nhận được dữ liệu)
// =================================================================
interface JavaCallback<T> {
    fun onSuccess(data: T)
    fun onError(message: String)
}

class SongRepository(private val firestore: FirebaseFirestore) {

    private val songsCollection = firestore.collection("songs")

    // -----------------------------------------------------------------
    // PHẦN 1: HÀM GỐC (Dành cho Kotlin / ViewModel)
    // -----------------------------------------------------------------

    // 1. Lấy tất cả (Kotlin)
    suspend fun getAllSongs(): Resource<List<Song>> {
        return try {
            val snapshot = songsCollection
                .orderBy("title", Query.Direction.ASCENDING)
                .get()
                .await()
            val songs = snapshot.toObjects(Song::class.java)
            Resource.Success(songs)
        } catch (e: Exception) {
            Log.e("SongRepo", "Error getAllSongs: $e")
            Resource.Error(e.message ?: "Lỗi không xác định")
        }
    }

    // 2. Tìm kiếm (Kotlin)
    suspend fun searchSongs(query: String): Resource<List<Song>> {
        return try {
            val startStr = query
            val endStr = query + "\uf8ff"
            val snapshot = songsCollection
                .orderBy("title")
                .startAt(startStr)
                .endAt(endStr)
                .get()
                .await()
            val songs = snapshot.toObjects(Song::class.java)
            Resource.Success(songs)
        } catch (e: Exception) {
            Resource.Error("Không tìm thấy")
        }
    }

    // 3. Lấy chi tiết (Kotlin)
    suspend fun getSongById(songId: String): Resource<Song> {
        return try {
            val snapshot = songsCollection.document(songId).get().await()
            val song = snapshot.toObject(Song::class.java)
            if (song != null) Resource.Success(song)
            else Resource.Error("Không tồn tại")
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Lỗi mạng")
        }
    }

    // 4. Trending (Kotlin)
    suspend fun getTrendingSongs(): Resource<List<Song>> {
        return try {
            val snapshot = songsCollection
                .orderBy("downloadCount", Query.Direction.DESCENDING)
                .limit(10)
                .get()
                .await()
            val songs = snapshot.toObjects(Song::class.java)
            Resource.Success(songs)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Lỗi")
        }
    }

    // 5. Update lượt tải (Kotlin)
    suspend fun updateDownloadCount(songId: String) {
        try {
            songsCollection.document(songId)
                .update("downloadCount", FieldValue.increment(1))
                .await()
        } catch (e: Exception) {
            Log.e("SongRepo", "Lỗi update: $e")
        }
    }

    // -----------------------------------------------------------------
    // PHẦN 2: CẦU NỐI CHO JAVA (Dành cho Team UI Java)
    // Team B và C sẽ gọi các hàm này
    // -----------------------------------------------------------------

    // 1. Lấy nhạc (Java)
    fun getAllSongsJava(callback: JavaCallback<List<Song>>) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = withContext(Dispatchers.IO) { getAllSongs() }
            if (result is Resource.Success) {
                callback.onSuccess(result.data ?: emptyList())
            } else {
                callback.onError(result.message ?: "Lỗi tải nhạc")
            }
        }
    }

    // 2. Tìm kiếm (Java)
    fun searchSongsJava(query: String, callback: JavaCallback<List<Song>>) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = withContext(Dispatchers.IO) { searchSongs(query) }
            if (result is Resource.Success) {
                callback.onSuccess(result.data ?: emptyList())
            } else {
                callback.onError(result.message ?: "Lỗi tìm kiếm")
            }
        }
    }
}