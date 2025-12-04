package com.aicloudflare.musicbox.ui;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.aicloudflare.musicbox.R;
// Import Firebase để đảm bảo khởi động an toàn
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;

public class MainActivityBackUp extends AppCompatActivity {

    // Khai báo Adapter ở cấp độ class
    private SongAdapter songAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // -----------------------------------------------------------------
        // BẢO HIỂM: Đảm bảo Firebase luôn được bật, tránh mọi lỗi Crash
        // -----------------------------------------------------------------
        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this);
        }

        // Thiết lập giao diện
        setContentView(R.layout.favorites);

        // =================================================================
        // CODE UI CHÍNH THỨC CỦA BẠN
        // =================================================================

        // 1. Tìm ListView
        ListView listViewSongs = findViewById(R.id.list_songs);

        // 2. Tạo danh sách dữ liệu mẫu (Hardcode UI)
        // Lưu ý: Sau này Người B sẽ thay thế đoạn này bằng data lấy từ SongRepository
        ArrayList<Song> songList = new ArrayList<>();

        // Đảm bảo các resource ảnh (R.drawable.xxx) tồn tại trong dự án của bạn
        songList.add(new Song("Không Buông", "Hngle, Ari", "03:50", R.drawable.khong_buong));
        songList.add(new Song("Người Đầu Tiên", "Juky San, buitruonglinh", "04:10", R.drawable.nguoi_dau_tien));
        songList.add(new Song("Thanh Xuân", "Da LAB", "05:20", R.drawable.da_lab));
        songList.add(new Song("Dẫu Có Lỗi Lầm ", "Huy Bảo (cover)", "04:00", R.drawable.ic_playlist_gray));
        songList.add(new Song("Bình Yên", "Nguyên Hà, Quốc Bảo", "04:30", R.drawable.ic_setting_gray));
        songList.add(new Song("Tầng Thượng 102", "Wxrdie", "03:15", R.drawable.ic_search));
        songList.add(new Song("Ngày Đầu Tiên", "Đức Phúc", "03:30", R.drawable.ic_shuffle));
        songList.add(new Song("Waiting For You", "MONO", "04:25", R.drawable.ic_logo));
        songList.add(new Song("See Tình", "Hoàng Thùy Linh", "03:05", R.drawable.ic_tim_rong));
        songList.add(new Song("Ánh Nắng Của Anh", "Đức Phúc", "04:15", R.drawable.ic_home_orange));

        // 3. Tạo Adapter
        songAdapter = new SongAdapter(this, songList);

        // 4. Gắn Adapter vào ListView
        listViewSongs.setAdapter(songAdapter);

        // 5. Sự kiện click vào bài hát
        listViewSongs.setOnItemClickListener((parent, view, position, id) -> {
            Song selectedSong = songList.get(position);

            // Tạo Bottom Sheet tùy chọn
            SongOptionsBottomSheet bottomSheet = SongOptionsBottomSheet.newInstance(selectedSong);

            // Lắng nghe sự kiện thả tim để cập nhật giao diện
            bottomSheet.setOnFavoriteStateChangedListener(() -> {
                if (songAdapter != null) {
                    songAdapter.notifyDataSetChanged();
                }
            });

            bottomSheet.show(getSupportFragmentManager(), "SongOptionsBottomSheet");
        });
    }
}