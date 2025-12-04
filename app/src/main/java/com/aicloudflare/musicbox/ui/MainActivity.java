package com.aicloudflare.musicbox.ui;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.aicloudflare.musicbox.R;
// Import Firebase để đảm bảo khởi động an toàn (Bắt buộc phải có)
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Khai báo Adapter và List ở cấp độ class để dùng sau này
    private SongAdapter songAdapter;
    private ArrayList<Song> songList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // -----------------------------------------------------------------
        // 1. BẢO HIỂM: Đảm bảo Firebase luôn được bật ngay khi mở App
        // Dòng này giúp tránh lỗi crash "Default FirebaseApp is not initialized"
        // -----------------------------------------------------------------
        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this);
        }

        // 2. Thiết lập giao diện
        setContentView(R.layout.favorites);

        // 3. Ánh xạ View
        ListView listViewSongs = findViewById(R.id.list_songs);

        // 4. Khởi tạo danh sách RỖNG
        // Hiện tại list này chưa có bài hát nào nên màn hình sẽ TRỐNG.
        // Đây là chỗ sau này Team UI sẽ viết hàm gọi Backend để đổ dữ liệu thật vào.
        songList = new ArrayList<>();

        // 5. Gắn Adapter vào ListView
        songAdapter = new SongAdapter(this, songList);
        listViewSongs.setAdapter(songAdapter);

        // 6. Xử lý sự kiện click (Logic giữ nguyên, chờ có dữ liệu sẽ hoạt động)
        listViewSongs.setOnItemClickListener((parent, view, position, id) -> {
            // Kiểm tra an toàn để tránh lỗi khi list rỗng
            if (position >= 0 && position < songList.size()) {
                Song selectedSong = songList.get(position);

                // Mở BottomSheet tùy chọn
                SongOptionsBottomSheet bottomSheet = SongOptionsBottomSheet.newInstance(selectedSong);

                // Lắng nghe sự kiện cập nhật
                bottomSheet.setOnFavoriteStateChangedListener(() -> {
                    if (songAdapter != null) {
                        songAdapter.notifyDataSetChanged();
                    }
                });

                bottomSheet.show(getSupportFragmentManager(), "SongOptionsBottomSheet");
            }
        });
    }
}