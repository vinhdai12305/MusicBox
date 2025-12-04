package com.aicloudflare.musicbox.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat; // Bổ sung import này

import com.aicloudflare.musicbox.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class SongOptionsBottomSheet extends BottomSheetDialogFragment {

    // (1) BỔ SUNG: "CẦU NỐI" ĐỂ GIAO TIẾP VỚI ACTIVITY
    public interface OnFavoriteStateChangedListener {
        void onFavoriteStateChanged();
    }

    private OnFavoriteStateChangedListener listener;

    public void setOnFavoriteStateChangedListener(OnFavoriteStateChangedListener listener) {
        this.listener = listener;
    }


    // --- Code cũ của bạn (không đổi) ---
    public static SongOptionsBottomSheet newInstance(Song song) {
        SongOptionsBottomSheet fragment = new SongOptionsBottomSheet();
        Bundle args = new Bundle();
        args.putSerializable("song_data", song);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.song_favourites_option_menu, container, false);

        // --- Code cũ của bạn (không đổi) ---
        final Song song; // Dùng final để truy cập được trong listener
        if (getArguments() != null) {
            song = (Song) getArguments().getSerializable("song_data");
        } else {
            dismiss();
            return view;
        }

        // --- Code cũ của bạn (không đổi) ---
        ImageView songArt = view.findViewById(R.id.iv_song_art_bottom_sheet);
        TextView songTitle = view.findViewById(R.id.tv_song_title_bottom_sheet);
        TextView songArtist = view.findViewById(R.id.tv_song_artist_bottom_sheet);
        TextView songDuration = view.findViewById(R.id.tv_song_duration_bottom_sheet);

        // (2) BỔ SUNG: ÁNH XẠ CHO ICON TRÁI TIM
        // ID này lấy từ file XML của bạn
        ImageView favoriteIcon = view.findViewById(R.id.iv_favorite_icon);

        // --- Code cũ của bạn và bổ sung logic mới ---
        if (song != null) {
            songArt.setImageResource(song.getAlbumArtId());
            songTitle.setText(song.getTitle());
            songArtist.setText(song.getArtist());
            songDuration.setText(song.getDuration());

            // (3) BỔ SUNG: LOGIC XỬ LÝ TRÁI TIM
            // 3.1: Cập nhật giao diện trái tim lúc mới mở
            updateFavoriteIcon(favoriteIcon, song.isFavorite());

            // 3.2: Lắng nghe sự kiện bấm vào trái tim
            favoriteIcon.setOnClickListener(v -> {
                // Đảo ngược trạng thái
                boolean isNowFavorite = !song.isFavorite();
                song.setFavorite(isNowFavorite);

                // Cập nhật lại giao diện icon ngay lập tức
                updateFavoriteIcon(favoriteIcon, isNowFavorite);

                // Báo cho Activity biết để cập nhật danh sách nếu cần
                if (listener != null) {
                    listener.onFavoriteStateChanged();
                }

                // Hiển thị thông báo cho người dùng
                if (isNowFavorite) {
                    Toast.makeText(getContext(), "Added to Favorites", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Removed from Favorites", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // --- Code cũ của bạn (không đổi) ---
        ListView optionsListView = view.findViewById(R.id.lv_options);
        List<OptionItem> optionItems = new ArrayList<>();
        optionItems.add(new OptionItem(R.drawable.ic_play_logo, "Play Next"));
        optionItems.add(new OptionItem(R.drawable.ic_playlist_gray, "Add to Playing Queue"));
        optionItems.add(new OptionItem(R.drawable.ic_playlist_gray, "Add to Playlist"));
        optionItems.add(new OptionItem(R.drawable.ic_home_orange, "Go to Album"));
        optionItems.add(new OptionItem(R.drawable.ic_home_orange, "Go to Artist"));
        optionItems.add(new OptionItem(R.drawable.ic_setting_gray, "Details"));
        optionItems.add(new OptionItem(R.drawable.ic_setting_gray, "Set as Ringtone"));
        optionItems.add(new OptionItem(R.drawable.ic_tim_cam, "Add to Blacklist"));
        optionItems.add(new OptionItem(R.drawable.ic_shuffle, "Share"));
        optionItems.add(new OptionItem(R.drawable.ic_search, "Delete from Device"));

        OptionAdapter optionAdapter = new OptionAdapter(getContext(), optionItems);
        optionsListView.setAdapter(optionAdapter);

        // --- Code cũ của bạn (không đổi) ---
        optionsListView.setOnItemClickListener((parent, itemView, position, id) -> {
            String selectedText = optionItems.get(position).getText();
            Toast.makeText(getContext(), selectedText, Toast.LENGTH_SHORT).show();
            dismiss();
        });

        return view;
    }

    // (4) BỔ SUNG: PHƯƠNG THỨC HỖ TRỢ ĐỂ CẬP NHẬT GIAO DIỆN ICON
    private void updateFavoriteIcon(ImageView icon, boolean isFavorite) {
        if (isFavorite) {
            // Nếu đã thích: Đổi sang icon trái tim cam và xóa màu tô (nếu có)
            icon.setImageResource(R.drawable.ic_tim_cam); // Bạn cần có icon này
            icon.clearColorFilter(); // Xóa tint để hiển thị màu gốc của icon
        } else {
            // Nếu chưa thích: Đổi sang icon trái tim rỗng và tô màu cam
            icon.setImageResource(R.drawable.ic_tim_rong);
            if (getContext() != null) {
                // Dùng màu accent_orange từ file XML của bạn
                icon.setColorFilter(ContextCompat.getColor(getContext(), R.color.accent_orange));
            }
        }
    }
}
