package com.aicloudflare.musicbox.data.repository

import android.util.Log
import com.aicloudflare.musicbox.data.model.Playlist
import com.aicloudflare.musicbox.data.model.Song
import com.aicloudflare.musicbox.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID

// Interface để Java gọi được (giống bên SongRepository)
interface PlaylistCallback<T> {
    fun onSuccess(data: T)
    fun onError(message: String)
}

class PlaylistRepository(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {

    private val playlistCollection = firestore.collection("playlists")
    private val songsCollection = firestore.collection("songs")

    // Lấy User ID hiện tại
    private val currentUserId: String
        get() = auth.currentUser?.uid ?: ""

    // =========================================================================
    // PHẦN 1: CÁC HÀM XỬ LÝ PLAYLIST (TẠO, SỬA, XÓA, LẤY DANH SÁCH)
    // =========================================================================

    // 1. Tạo Playlist mới
    suspend fun createPlaylist(name: String): Resource<Boolean> {
        return try {
            if (currentUserId.isEmpty()) return Resource.Error("Chưa đăng nhập")

            val newId = UUID.randomUUID().toString()
            val newPlaylist = Playlist(
                id = newId,
                name = name,
                userId = currentUserId,
                songIds = emptyList(),

            )

            playlistCollection.document(newId).set(newPlaylist).await()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Lỗi tạo playlist")
        }
    }

    // 2. Lấy tất cả Playlist của User hiện tại
    suspend fun getUserPlaylists(): Resource<List<Playlist>> {
        return try {
            if (currentUserId.isEmpty()) return Resource.Error("Chưa đăng nhập")

            val snapshot = playlistCollection
                .whereEqualTo("userId", currentUserId)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .await()

            val playlists = snapshot.toObjects(Playlist::class.java)
            Resource.Success(playlists)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Lỗi lấy danh sách playlist")
        }
    }

    // 3. Xóa Playlist
    suspend fun deletePlaylist(playlistId: String): Resource<Boolean> {
        return try {
            playlistCollection.document(playlistId).delete().await()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error("Lỗi xóa: ${e.message}")
        }
    }

    // =========================================================================
    // PHẦN 2: QUẢN LÝ BÀI HÁT TRONG PLAYLIST
    // =========================================================================

    // 4. Thêm bài hát vào Playlist
    suspend fun addSongToPlaylist(playlistId: String, songId: String): Resource<Boolean> {
        return try {
            playlistCollection.document(playlistId)
                .update("songIds", FieldValue.arrayUnion(songId))
                .await()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error("Lỗi thêm bài hát: ${e.message}")
        }
    }

    // 5. Xóa bài hát khỏi Playlist
    suspend fun removeSongFromPlaylist(playlistId: String, songId: String): Resource<Boolean> {
        return try {
            playlistCollection.document(playlistId)
                .update("songIds", FieldValue.arrayRemove(songId))
                .await()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error("Lỗi xóa bài hát: ${e.message}")
        }
    }

    // 6. Lấy chi tiết các bài hát trong 1 Playlist (Convert từ ID -> Song Object)
    // Hàm này hơi phức tạp: Nó lấy list ID, sau đó query từng bài hát về
    suspend fun getSongsInPlaylist(playlistId: String): Resource<List<Song>> {
        return try {
            // Bước 1: Lấy Playlist để xem danh sách ID
            val playlistDoc = playlistCollection.document(playlistId).get().await()
            val playlist = playlistDoc.toObject(Playlist::class.java) ?: return Resource.Error("Playlist không tồn tại")

            if (playlist.songIds.isEmpty()) {
                return Resource.Success(emptyList())
            }

            // Bước 2: Lấy thông tin chi tiết từng bài hát dựa trên ID
            // Lưu ý: Firestore giới hạn 'in' query tối đa 10 phần tử.
            // Nên nếu list dài, ta phải chia nhỏ hoặc fetch từng cái.
            // Ở đây dùng cách fetch từng cái cho đơn giản logic
            val songList = mutableListOf<Song>()
            for (songId in playlist.songIds) {
                val songSnapshot = songsCollection.document(songId).get().await()
                val song = songSnapshot.toObject(Song::class.java)
                if (song != null) {
                    songList.add(song)
                }
            }

            Resource.Success(songList)

        } catch (e: Exception) {
            Log.e("PlaylistRepo", "Lỗi lấy bài hát: $e")
            Resource.Error("Lỗi tải bài hát: ${e.message}")
        }
    }

    // =========================================================================
    // PHẦN 3: HÀM HỖ TRỢ JAVA (CHO NGƯỜI D)
    // =========================================================================

    // Java: Tạo Playlist
    fun createPlaylistJava(name: String, callback: PlaylistCallback<Boolean>) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = withContext(Dispatchers.IO) { createPlaylist(name) }
            if (result is Resource.Success) callback.onSuccess(true)
            else callback.onError(result.message ?: "Error")
        }
    }

    // Java: Lấy danh sách Playlist của tôi
    fun getUserPlaylistsJava(callback: PlaylistCallback<List<Playlist>>) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = withContext(Dispatchers.IO) { getUserPlaylists() }
            if (result is Resource.Success) callback.onSuccess(result.data ?: emptyList())
            else callback.onError(result.message ?: "Error")
        }
    }

    // Java: Thêm bài hát vào Playlist
    fun addSongToPlaylistJava(playlistId: String, songId: String, callback: PlaylistCallback<Boolean>) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = withContext(Dispatchers.IO) { addSongToPlaylist(playlistId, songId) }
            if (result is Resource.Success) callback.onSuccess(true)
            else callback.onError(result.message ?: "Error")
        }
    }

    // Java: Lấy bài hát trong Playlist
    fun getSongsInPlaylistJava(playlistId: String, callback: PlaylistCallback<List<Song>>) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = withContext(Dispatchers.IO) { getSongsInPlaylist(playlistId) }
            if (result is Resource.Success) callback.onSuccess(result.data ?: emptyList())
            else callback.onError(result.message ?: "Error")
        }
    }
}