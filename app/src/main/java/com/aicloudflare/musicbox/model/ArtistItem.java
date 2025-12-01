package com.aicloudflare.musicbox.model;

public class ArtistItem {
    private int id;
    private String name;
    private int imageResource;

    public ArtistItem(int id, String name, int imageResource) {
        this.id = id;
        this.name = name;
        this.imageResource = imageResource;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getImageResource() { return imageResource; }
}