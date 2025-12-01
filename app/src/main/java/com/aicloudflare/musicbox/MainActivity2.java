package com.aicloudflare.musicbox;

import android.os.Bundle;
import android.view.ViewGroup; // Import bị thiếu cho Adapter nội bộ (nếu giữ)

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

// IMPORTS CÁC LỚP BẠN ĐÃ TẠO (MODEL & ADAPTER)
import com.aicloudflare.musicbox.model.MusicItem;
import com.aicloudflare.musicbox.model.ArtistItem;
import com.aicloudflare.musicbox.adapter.MusicSquareAdapter;
import com.aicloudflare.musicbox.adapter.ArtistCircleAdapter;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.layout_homescreen);

        // Đảm bảo R.id.main_layout tồn tại trong layout_homescreen.xml
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // --- KHỞI TẠO CÁC VIEW CƠ BẢN ---
        setupTabLayout();
        setupBottomNavigationView();

        // --- BỎ COMMENT VÀ CHẠY HÀM SETUP RECYCLERVIEW ---
        setupRecentlyPlayed();
        setupArtists();
        setupMostPlayed();
    }

    private void setupTabLayout() {
        TabLayout tabLayout = findViewById(R.id.tab_layout);
    }

    private void setupBottomNavigationView() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
    }

    // --- CÁC HÀM THIẾT LẬP RECYCLERVIEW ---
    private void setupRecentlyPlayed() {
        RecyclerView recycler = findViewById(R.id.recycler_recently_played);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        MusicSquareAdapter adapter = new MusicSquareAdapter(createDummyMusicData());
        recycler.setAdapter(adapter);
    }

    private void setupArtists() {
        RecyclerView recycler = findViewById(R.id.recycler_artists);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ArtistCircleAdapter adapter = new ArtistCircleAdapter(createDummyArtistData());
        recycler.setAdapter(adapter);
    }

    private void setupMostPlayed() {
        RecyclerView recycler = findViewById(R.id.recycler_most_played);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        MusicSquareAdapter adapter = new MusicSquareAdapter(createDummyMostPlayedData());
        recycler.setAdapter(adapter);
    }

    // --- CÁC HÀM TẠO DỮ LIỆU GIẢ (DUMMY DATA) VÀ THAM CHIẾU HÌNH ẢNH ---
    private List<MusicItem> createDummyMusicData() {
        List<MusicItem> data = new ArrayList<>();
        // SỬ DỤNG R.drawable.xxx
        data.add(new MusicItem(1, "Không Thời Gian", "Dương Domic", R.drawable.khong_thoi_gian));
        data.add(new MusicItem(2, "Đánh Đổi", "Obito", R.drawable.danh_doi));
        data.add(new MusicItem(3, "Năm Ấy", "Đức Phúc", R.drawable.nam_ay)); // Dùng tạm ảnh có sẵn
        data.add(new MusicItem(4, "Còn Gì Đẹp Hơn", "Nguyễn Hùng", R.drawable.con_gi_dep_hon)); // Dùng tạm ảnh có sẵn
        return data;
    }

    private List<ArtistItem> createDummyArtistData() {
        List<ArtistItem> data = new ArrayList<>();
        // SỬ DỤNG R.drawable.xxx
        data.add(new ArtistItem(101, "Rhymastic", R.drawable.rhym)); // Giả định có ảnh lyhan
        data.add(new ArtistItem(102, "Bray", R.drawable.bray)); // Bạn đã có ảnh bray.png
        data.add(new ArtistItem(103, "Huslang Robber", R.drawable.robber)); // Giả định có ảnh huslang_rok
        data.add(new ArtistItem(104, "MCK", R.drawable.mck)); // Giả định có ảnh mck
        return data;
    }

    private List<MusicItem> createDummyMostPlayedData() {
        List<MusicItem> data = new ArrayList<>();
        // SỬ DỤNG R.drawable.xxx
        data.add(new MusicItem(201, "Ghé Qua", "Dick & PC & Tofu", R.drawable.ghe_qua));
        data.add(new MusicItem(202, "Còn Gì Đẹp Hơn", "Nguyễn Hùng", R.drawable.con_gi_dep_hon));
        data.add(new MusicItem(203, "Y6U", "Nghệ sĩ", R.drawable.y6u)); // Giả định có ảnh y6u
        data.add(new MusicItem(204, "1000 Ánh Mắt", "Shiki", R.drawable.anhmat)); // Bạn đã có ảnh anhmat.jpg
        return data;
    }
}