// Resource.kt
package com.aicloudflare.musicbox.utils
// Dòng package sẽ tự động được thêm nếu bạn tạo đúng thư mục

/**
 * Lớp Sealed Class để bọc dữ liệu, giúp quản lý trạng thái của các tác vụ bất đồng bộ (như truy cập Firebase).
 * T (Generic Type) là kiểu dữ liệu mà bạn muốn trả về (ví dụ: FirebaseUser, Song, List<Song>).
 */
sealed class Resource<T>(val data: T? = null, val message: String? = null) {

    // Trạng thái thành công
    class Success<T>(data: T) : Resource<T>(data)

    // Trạng thái thất bại
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)

    // Trạng thái đang tải
    class Loading<T> : Resource<T>()
}