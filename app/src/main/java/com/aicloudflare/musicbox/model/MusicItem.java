package com.aicloudflare.musicbox.model;

public class MusicItem {
    private int id;
    private String title;
    private String artist;
    private int imageResource;

    public MusicItem(int id, String title, String artist, int imageResource) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.imageResource = imageResource;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public int getImageResource() { return imageResource; }
}