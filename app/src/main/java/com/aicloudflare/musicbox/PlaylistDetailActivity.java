package com.aicloudflare.musicbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PlaylistDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_detail);

        // 1. Nhận dữ liệu tên Playlist từ màn hình trước gửi sang
        String playlistName = getIntent().getStringExtra("PLAYLIST_NAME");

        // 2. Tìm và hiển thị tên Playlist
        TextView tvTitle = findViewById(R.id.tvDetailTitle);
        if (playlistName != null) {
            tvTitle.setText(playlistName);
        }

        // 3. Xử lý sự kiện nút Back (Quay lại)
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Đóng màn hình này
            }
        });

        // 4. Xử lý sự kiện nút Menu 3 chấm (Góc trên phải)
        ImageView btnMenu = findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });

        // 5. Xử lý sự kiện nút Add Box (Dấu cộng hình vuông - Góc phải danh sách)
        // Nút này dùng để mở màn hình thêm bài hát mới
        ImageView btnAddBox = findViewById(R.id.btnAddBox);
        if (btnAddBox != null) {
            btnAddBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Chuyển sang màn hình Add Songs
                    Intent intent = new Intent(PlaylistDetailActivity.this, AddSongsActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    // Hàm hiển thị Menu tùy chọn (Edit/Delete)
    private void showPopupMenu(View view) {
        // Khởi tạo PopupMenu gắn vào view được bấm
        PopupMenu popup = new PopupMenu(this, view);

        // Nạp giao diện từ file menu resource (res/menu/menu_playlist_detail.xml)
        popup.getMenuInflater().inflate(R.menu.menu_playlist_detail, popup.getMenu());

        // Bắt sự kiện khi chọn các mục trong menu
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.action_edit) {
                    // Xử lý khi bấm Edit
                    Toast.makeText(PlaylistDetailActivity.this, "Edit Playlist Info", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.action_delete) {
                    // Xử lý khi bấm Delete
                    Toast.makeText(PlaylistDetailActivity.this, "Delete Playlist", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });

        // Hiển thị menu
        popup.show();
    }
}