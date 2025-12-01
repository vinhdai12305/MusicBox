package com.aicloudflare.musicbox.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aicloudflare.musicbox.R;
import com.aicloudflare.musicbox.model.ArtistItem; // Import ArtistItem

import java.util.List;

public class ArtistCircleAdapter extends RecyclerView.Adapter<ArtistCircleAdapter.ArtistViewHolder> {
    private List<ArtistItem> artistList;

    public ArtistCircleAdapter(List<ArtistItem> artistList) {
        this.artistList = artistList;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Sử dụng layout item_artist_circle.xml
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_artist_circle, parent, false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        ArtistItem artist = artistList.get(position);
        holder.name.setText(artist.getName());
        // Gán hình ảnh từ Drawable ID
        holder.image.setImageResource(artist.getImageResource());
    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }

    public static class ArtistViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;

        public ArtistViewHolder(View itemView) {
            super(itemView);
            // Tìm các View trong item_artist_circle.xml
            image = itemView.findViewById(R.id.item_artist_image);
            name = itemView.findViewById(R.id.item_artist_name);
        }
    }
}