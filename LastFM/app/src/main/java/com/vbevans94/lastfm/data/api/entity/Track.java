package com.vbevans94.lastfm.data.api.entity;

import com.orm.SugarRecord;

public class Track extends SugarRecord<Track> {

    private final String name;

    private final String duration;

    private String album;

    public Track(String name, String duration) {
        this.name = name;
        this.duration = duration;
    }

    /**
     * For Gson.
     */
    @SuppressWarnings("unused")
    public Track() {
        this(null, null);
    }

    public String getName() {
        return name;
    }

    public String getDuration() {
        return duration;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    @Override
    public String toString() {
        return "Track{" +
                "name='" + name + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}
