package com.aicloudflare.musicbox.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aicloudflare.musicbox.R;

import java.util.ArrayList;

public class SongAdapter extends ArrayAdapter<Song> {

    private AppCompatActivity activity;

    public SongAdapter(@NonNull Context context, @NonNull ArrayList<Song> songList) {
        super(context, 0, songList);
        if (context instanceof AppCompatActivity) {
            this.activity = (AppCompatActivity) context;
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_song, parent, false);
        }

        Song currentSong = getItem(position);

        TextView tvTitle = listItemView.findViewById(R.id.tv_song_title);
        TextView tvArtist = listItemView.findViewById(R.id.tv_song_artist);
        ImageView ivAlbumArt = listItemView.findViewById(R.id.iv_album_art);
        ImageView ivMoreOptions = listItemView.findViewById(R.id.iv_more_options);

        // Chỉ đổ dữ liệu, không xử lý logic phức tạp ở đây
        if (currentSong != null) {
            tvTitle.setText(currentSong.getTitle());
            tvArtist.setText(currentSong.getArtist());
            ivAlbumArt.setImageResource(currentSong.getAlbumArtId());
        }

        // Listener cho nút "..." để mở BottomSheet
        ivMoreOptions.setOnClickListener(v -> {
            if (activity != null) {
                Song songForThisRow = getItem(position);
                if (songForThisRow != null) {
                    showOptionsBottomSheet(songForThisRow);
                }
            }
        });

        // CÓ THỂ bạn muốn bấm vào cả hàng cũng mở ra BottomSheet
        // Nếu muốn, hãy bỏ comment đoạn code này
        /*
        listItemView.setOnClickListener(v -> {
            if (activity != null) {
                Song songForThisRow = getItem(position);
                if (songForThisRow != null) {
                    showOptionsBottomSheet(songForThisRow);
                }
            }
        });
        */

        return listItemView;
    }

    private void showOptionsBottomSheet(Song song) {
        SongOptionsBottomSheet bottomSheet = SongOptionsBottomSheet.newInstance(song);
        bottomSheet.show(activity.getSupportFragmentManager(), "SongOptionsBottomSheet");
    }
}
