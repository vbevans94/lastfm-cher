package com.vbevans94.lastfm.data.api.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Albums {

    @SerializedName("topalbums")
    private final TopAlbums topAlbums;

    public Albums(TopAlbums topAlbums) {
        this.topAlbums = topAlbums;
    }

    public Albums() {
        this(null);
    }

    public TopAlbums getTopAlbums() {
        return topAlbums;
    }

    @Override
    public String toString() {
        return "Albums{" +
                "topAlbums=" + topAlbums +
                '}';
    }

    public static class TopAlbums {

        private final List<Album> album;

        private TopAlbums(List<Album> album) {
            this.album = album;
        }

        public TopAlbums() {
            this(null);
        }

        public List<Album> getAlbum() {
            return album;
        }
    }
}
