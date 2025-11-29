package com.aicloudflare.musicbox;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // (1) BỔ SUNG: Khai báo Adapter ở cấp độ class
    // Để có thể truy cập và gọi notifyDataSetChanged() từ nhiều nơi
    private SongAdapter songAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Sử dụng file layout của bạn
        setContentView(R.layout.favorites);

        // --- BẮT ĐẦU CODE ĐÃ ĐƯỢC SẮP XẾP LẠI ---

        // 1. Tìm ListView trong layout bằng ID của nó
        ListView listViewSongs = findViewById(R.id.list_songs);

        // 2. Tạo danh sách dữ liệu mẫu (ArrayList các đối tượng Song)
        ArrayList<Song> songList = new ArrayList<>();
        // Lưu ý: Các tên trong R.drawable phải tồn tại trong thư mục res/drawable của bạn.
        // Ví dụ, bạn cần có file khong_buong.png (hoặc .jpg, .xml) trong drawable.
        songList.add(new Song("Không Buông", "Hngle, Ari", "03:50", R.drawable.khong_buong)); // Đổi thành R.drawable.khong_buong nếu bạn có
        songList.add(new Song("Người Đầu Tiên", "Juky San, buitruonglinh", "04:10", R.drawable.nguoi_dau_tien)); // Đổi thành R.drawable.nguoi_dau_tien nếu có
        songList.add(new Song("Thanh Xuân", "Da LAB", "05:20", R.drawable.da_lab));
        songList.add(new Song("Dẫu Có Lỗi Lầm ", "Huy Bảo (cover)", "04:00", R.drawable.ic_playlist_gray));
        songList.add(new Song("Bình Yên", "Nguyên Hà, Quốc Bảo", "04:30", R.drawable.ic_setting_gray));
        songList.add(new Song("Tầng Thượng 102", "Wxrdie", "03:15", R.drawable.ic_search));
        songList.add(new Song("Ngày Đầu Tiên", "Đức Phúc", "03:30", R.drawable.ic_shuffle));
        songList.add(new Song("Waiting For You", "MONO", "04:25", R.drawable.ic_logo));
        songList.add(new Song("See Tình", "Hoàng Thùy Linh", "03:05", R.drawable.ic_tim_rong));
        songList.add(new Song("Ánh Nắng Của Anh", "Đức Phúc", "04:15", R.drawable.ic_home_orange));
        // Thêm bao nhiêu bài hát tùy ý

        // 3. Tạo một đối tượng từ lớp SongAdapter của bạn
        // Sử dụng biến songAdapter đã khai báo ở trên thay vì tạo biến cục bộ
        songAdapter = new SongAdapter(this, songList);

        // 4. Gắn Adapter này vào ListView (Chỉ cần làm một lần)
        listViewSongs.setAdapter(songAdapter);

        // 5. Thiết lập trình lắng nghe sự kiện click cho mỗi item trong ListView
        listViewSongs.setOnItemClickListener((parent, view, position, id) -> {
            // Lấy bài hát được click
            Song selectedSong = songList.get(position);

            // Tạo và hiển thị Bottom Sheet
            SongOptionsBottomSheet bottomSheet = SongOptionsBottomSheet.newInstance(selectedSong);

            // (2) BỔ SUNG: "KẾT NỐI" VỚI BOTTOMSHEET TRƯỚC KHI HIỂN THỊ
            // Lắng nghe tín hiệu từ BottomSheet để cập nhật giao diện
            bottomSheet.setOnFavoriteStateChangedListener(() -> {
                // Khi trạng thái yêu thích thay đổi, báo cho adapter vẽ lại ListView
                if (songAdapter != null) {
                    songAdapter.notifyDataSetChanged();
                }
            });

            // Hiển thị BottomSheet sau khi đã kết nối listener
            bottomSheet.show(getSupportFragmentManager(), "SongOptionsBottomSheet");
        });

        // --- KẾT THÚC CODE TRONG ONCREATE ---
    }
}
