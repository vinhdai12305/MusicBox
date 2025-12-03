package com.aicloudflare.musicbox;

public class Song {
    private String title;
    private String artist;
    private int coverResId;
    private boolean isAdded; // Biến quan trọng để xác định icon

    public Song(String title, String artist, int coverResId, boolean isAdded) {
        this.title = title;
        this.artist = artist;
        this.coverResId = coverResId;
        this.isAdded = isAdded;
    }

    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public int getCoverResId() { return coverResId; }
    public boolean isAdded() { return isAdded; }
    public void setAdded(boolean added) { isAdded = added; }
}