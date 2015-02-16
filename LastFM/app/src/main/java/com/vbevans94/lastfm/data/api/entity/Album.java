package com.vbevans94.lastfm.data.api.entity;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.List;

public class Album extends SugarRecord<Album> {

    private final String name;

    @Ignore
    private final List<Image> image;

    private String imageUrl;
    private String largeImageUrl;

    private final String playcount;

    @Ignore
    private final Tracks tracks;

    public Album(String name, List<Image> image, String playcount, Tracks tracks) {
        this.name = name;
        this.image = image;
        this.playcount = playcount;
        this.tracks = tracks;
    }

    /**
     * For Gson.
     */
    @SuppressWarnings("unused")
    public Album() {
        this(null, null, null, null);
    }

    public List<Image> getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getPlaycount() {
        return playcount;
    }

    public Tracks getTracks() {
        return tracks;
    }

    public String getImageUrl() {
        if (imageUrl == null) {
            imageUrl = getImage().get(2).getUrl();
        }
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLargeImageUrl() {
        if (largeImageUrl == null) {
            largeImageUrl = getImage().get(3).getUrl();
        }
        return largeImageUrl;
    }

    public void setLargeImageUrl(String largeImageUrl) {
        this.largeImageUrl = largeImageUrl;
    }

    @Override
    public String toString() {
        return "Album{" +
                "name='" + name + '\'' +
                ", image=" + image +
                '}';
    }
}
