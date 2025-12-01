package com.aicloudflare.musicbox.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aicloudflare.musicbox.R;
import com.aicloudflare.musicbox.model.MusicItem; // Import MusicItem

import java.util.List;

public class MusicSquareAdapter extends RecyclerView.Adapter<MusicSquareAdapter.MusicViewHolder> {
    private List<MusicItem> musicList;

    public MusicSquareAdapter(List<MusicItem> musicList) {
        this.musicList = musicList;
    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Sử dụng layout item_music_square.xml
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_music_square, parent, false);
        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder, int position) {
        MusicItem music = musicList.get(position);
        holder.title.setText(music.getTitle());
        holder.artist.setText(music.getArtist());
        // Gán hình ảnh từ Drawable ID
        holder.image.setImageResource(music.getImageResource());
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    public static class MusicViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView artist;

        public MusicViewHolder(View itemView) {
            super(itemView);

            // 🛑 LỖI CŨ: image = itemView.findViewById(R.id.item_music_image);
            // ✅ SỬA: Dùng ID mới từ Layout XML
            image = itemView.findViewById(R.id.album_cover);

            // 🛑 LỖI CŨ: title = itemView.findViewById(R.id.item_music_title);
            // ✅ SỬA: Dùng ID mới từ Layout XML
            title = itemView.findViewById(R.id.song_title);

            // ✅ BỔ SUNG: Bạn nên thêm cả dòng này để hiển thị tên nghệ sĩ
            artist = itemView.findViewById(R.id.artist_name_small);
        }
    }
}