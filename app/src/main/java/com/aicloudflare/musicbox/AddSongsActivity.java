package com.aicloudflare.musicbox;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class AddSongsActivity extends AppCompatActivity {

    private RecyclerView rvAddSongs;
    private AddSongsAdapter adapter;
    private List<Song> songList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_songs);

        // Xử lý nút Back
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        // Khởi tạo RecyclerView
        rvAddSongs = findViewById(R.id.rvAddSongs);
        rvAddSongs.setLayoutManager(new LinearLayoutManager(this));

        // Tạo dữ liệu giả (Demo)
        createDummyData();

        // Gán Adapter
        adapter = new AddSongsAdapter(songList);
        rvAddSongs.setAdapter(adapter);
    }

    private void createDummyData() {
        songList = new ArrayList<>();
        // Bài chưa thêm (isAdded = false) -> Hiện dấu +
        songList.add(new Song("Không Buông", "Hngle, Ari", R.drawable.ic_playlist_gray, false));
        songList.add(new Song("Người Đầu Tiên", "Juky San", R.drawable.ic_playlist_gray, false));

        // Bài ĐÃ thêm (isAdded = true) -> Hiện dấu tích
        songList.add(new Song("Thanh Xuân", "Da LAB", R.drawable.ic_playlist_gray, true));

        songList.add(new Song("Mộng Yu", "AMEE, RPT MCK", R.drawable.ic_playlist_gray, false));
        songList.add(new Song("Bình Yên", "Vũ., Binz", R.drawable.ic_playlist_gray, true));
        songList.add(new Song("Tầng Thượng 102", "Cá Hồi Hoang", R.drawable.ic_playlist_gray, false));
    }
}