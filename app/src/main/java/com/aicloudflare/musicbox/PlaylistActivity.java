package com.aicloudflare.musicbox; // Đảm bảo tên package đúng với dự án của bạn

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.button.MaterialButton;

public class PlaylistActivity extends AppCompatActivity {

    // 1. Khai báo các biến giao diện
    private LinearLayout layoutAddPlaylist; // Nút bấm "Add New Playlist"
    private LinearLayout llPlaylistContainer; // Cái hộp chứa toàn bộ danh sách playlist

    private View viewDim; // Lớp nền tối mờ
    private ConstraintLayout containerNewPlaylist; // Khung nhập liệu màu trắng (Popup)

    private MaterialButton btnCancel;
    private MaterialButton btnCreate;
    private EditText etPlaylistName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        // 2. Ánh xạ View (Tìm các thẻ trong XML bằng ID)
        initViews();

        // 3. Thiết lập các sự kiện bấm nút
        setupListeners();

        // 4. Xử lý nút Back (nút quay lại của điện thoại)
        handleBackPress();
    }

    private void initViews() {
        layoutAddPlaylist = findViewById(R.id.layoutAddPlaylist);
        llPlaylistContainer = findViewById(R.id.llPlaylistContainer); // Quan trọng: Hộp chứa danh sách

        viewDim = findViewById(R.id.viewDim);
        containerNewPlaylist = findViewById(R.id.containerNewPlaylist);

        btnCancel = findViewById(R.id.btnCancel);
        btnCreate = findViewById(R.id.btnCreate);
        etPlaylistName = findViewById(R.id.etPlaylistName);
    }

    private void setupListeners() {
        // --- Sự kiện 1: Bấm vào dòng "Add New Playlist" thì hiện Popup ---
        layoutAddPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleOverlay(true); // Hiện
            }
        });

        // --- Sự kiện 2: Bấm nút Cancel thì ẩn Popup ---
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleOverlay(false); // Ẩn
            }
        });

        // --- Sự kiện 3: Bấm ra vùng tối bên ngoài cũng ẩn Popup ---
        viewDim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleOverlay(false); // Ẩn
            }
        });

        // --- Sự kiện 4 (QUAN TRỌNG): Bấm nút Create ---
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy tên người dùng nhập vào
                String playlistName = etPlaylistName.getText().toString().trim();

                if (!playlistName.isEmpty()) {
                    // === BẮT ĐẦU LOGIC THÊM DÒNG MỚI ===

                    // Bước A: Tạo ra một View mới từ file thiết kế item_playlist.xml
                    View newPlaylistView = getLayoutInflater().inflate(R.layout.item_playlist, llPlaylistContainer, false);

                    // Bước B: Tìm các TextView trong View mới đó để sửa chữ
                    TextView tvName = newPlaylistView.findViewById(R.id.tvPlaylistName);
                    TextView tvSub = newPlaylistView.findViewById(R.id.tvPlaylistSub);

                    // Bước C: Gán tên playlist vừa nhập vào
                    if (tvName != null) {
                        tvName.setText(playlistName);
                    }
                    if (tvSub != null) {
                        tvSub.setText("0 songs"); // Mặc định là 0 bài hát
                    }

                    // Bước D: Thêm sự kiện click cho chính dòng Playlist mới này
                    // Để khi bấm vào nó sẽ mở sang màn hình PlaylistDetailActivity
                    newPlaylistView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Chuyển sang màn hình chi tiết
                            Intent intent = new Intent(PlaylistActivity.this, PlaylistDetailActivity.class);
                            intent.putExtra("PLAYLIST_NAME", playlistName); // Gửi tên sang bên kia
                            startActivity(intent);
                        }
                    });

                    // Bước E: Nhét View mới vào danh sách
                    // Số 1 nghĩa là chèn vào vị trí thứ 2 (ngay dưới nút Add New Playlist)
                    llPlaylistContainer.addView(newPlaylistView, 1);

                    // === KẾT THÚC LOGIC THÊM ===

                    Toast.makeText(PlaylistActivity.this, "Created playlist: " + playlistName, Toast.LENGTH_SHORT).show();

                    // Dọn dẹp: Xóa trắng ô nhập và ẩn popup
                    etPlaylistName.setText("");
                    toggleOverlay(false);

                } else {
                    Toast.makeText(PlaylistActivity.this, "Please enter a name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Hàm phụ trợ để ẩn/hiện Popup
    private void toggleOverlay(boolean show) {
        if (show) {
            viewDim.setVisibility(View.VISIBLE);
            containerNewPlaylist.setVisibility(View.VISIBLE);
            etPlaylistName.requestFocus(); // Tự động trỏ chuột vào ô nhập
        } else {
            viewDim.setVisibility(View.GONE);
            containerNewPlaylist.setVisibility(View.GONE);
        }
    }

    // Xử lý nút Back cứng của điện thoại
    private void handleBackPress() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Nếu Popup đang hiện thì ẩn nó đi trước
                if (containerNewPlaylist.getVisibility() == View.VISIBLE) {
                    toggleOverlay(false);
                } else {
                    // Nếu Popup không hiện thì thoát màn hình như bình thường
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                }
            }
        });
    }
}