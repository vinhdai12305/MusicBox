package com.aicloudflare.musicbox.data.repository

import android.util.Log
import com.aicloudflare.musicbox.data.model.User
import com.aicloudflare.musicbox.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth // Thêm dòng này để khớp với AppContainer
) {
    private val usersCollection = firestore.collection("users")

    fun getCurrentUserId(): String? = auth.currentUser?.uid

    suspend fun getUserFavorites(uid: String): Resource<List<String>> {
        return try {
            val snapshot = usersCollection.document(uid).get().await()
            val user = snapshot.toObject(User::class.java)
            user?.let { Resource.Success(it.favorites) } ?: Resource.Error("Chưa có hồ sơ.")
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Lỗi tải Favorites")
        }
    }

    suspend fun toggleFavorite(uid: String, songId: String, isAdding: Boolean): Resource<Boolean> {
        return try {
            val update = if (isAdding) FieldValue.arrayUnion(songId) else FieldValue.arrayRemove(songId)
            usersCollection.document(uid).update("favorites", update).await()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Lỗi cập nhật Favorites")
        }
    }
}