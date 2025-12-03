package com.aicloudflare.musicbox;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AddSongsAdapter extends RecyclerView.Adapter<AddSongsAdapter.SongViewHolder> {

    private List<Song> songList;

    public AddSongsAdapter(List<Song> songList) {
        this.songList = songList;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song_add, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song song = songList.get(position);
        holder.tvTitle.setText(song.getTitle());
        holder.tvArtist.setText(song.getArtist());
        holder.imgCover.setImageResource(song.getCoverResId());

        // --- LOGIC QUAN TRỌNG: Đổi icon dựa trên trạng thái isAdded ---
        if (song.isAdded()) {
            holder.btnAddCheck.setImageResource(R.drawable.ic_addcheck); // Đã thêm -> Hiện dấu tích
        } else {
            holder.btnAddCheck.setImageResource(R.drawable.ic_addbox);   // Chưa thêm -> Hiện dấu cộng
        }

        // Xử lý sự kiện khi bấm vào nút add/check
        holder.btnAddCheck.setOnClickListener(v -> {
            // Đảo ngược trạng thái (true -> false, false -> true)
            song.setAdded(!song.isAdded());
            // Thông báo cho adapter load lại dòng này để cập nhật icon mới
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvArtist;
        ImageView imgCover, btnAddCheck;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvSongTitle);
            tvArtist = itemView.findViewById(R.id.tvSongArtist);
            imgCover = itemView.findViewById(R.id.imgSongCover);
            btnAddCheck = itemView.findViewById(R.id.btnAddCheck);
        }
    }
}