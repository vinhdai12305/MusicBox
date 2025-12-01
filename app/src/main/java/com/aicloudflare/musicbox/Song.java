package com.aicloudflare.musicbox;

import java.io.Serializable;

// Đảm bảo class của bạn có "implements Serializable" để có thể truyền giữa các thành phần
public class Song implements Serializable {

    // 1. Khai báo các biến để lưu trữ thông tin
    private String title;
    private String artist;
    private String duration; // Biến để lưu thời lượng
    private int albumArtId; // Biến để lưu ID ảnh bìa
    private boolean isFavorite; // Biến để theo dõi trạng thái yêu thích

    // 2. TẠO CONSTRUCTOR ĐÚNG ĐỂ SỬA LỖI
    // Constructor này nhận chính xác 4 tham số mà bạn đang gọi trong MainActivity
    public Song(String title, String artist, String duration, int albumArtId) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.albumArtId = albumArtId;
        this.isFavorite = false; // Mặc định khi mới tạo, bài hát không phải là yêu thích
    }

    /*
     * Bạn có thể giữ lại các constructor cũ nếu vẫn cần dùng ở nơi khác.
     * Ví dụ:
     * public Song(String title, String artist) {
     *     this.title = title;
     *     this.artist = artist;
     *     this.duration = "00:00"; // Giá trị mặc định
     *     this.albumArtId = R.drawable.ic_logo; // Ảnh mặc định
     * }
     */

    // 3. TẠO CÁC PHƯƠNG THỨC GETTER
    // Các Adapter và BottomSheet sẽ dùng các phương thức này để lấy dữ liệu ra và hiển thị
    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getDuration() {
        return duration;
    }

    public int getAlbumArtId() {
        return albumArtId;
    }

    // 4. CÁC PHƯƠNG THỨC CHO TÍNH NĂNG "YÊU THÍCH"
    /**
     * Kiểm tra xem bài hát có phải là yêu thích không.
     * @return true nếu là yêu thích, ngược lại là false.
     */
    public boolean isFavorite() {
        return isFavorite;
    }

    /**
     * Đặt trạng thái yêu thích cho bài hát.
     * @param favorite true để đánh dấu là yêu thích, false để bỏ đánh dấu.
     */
    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
