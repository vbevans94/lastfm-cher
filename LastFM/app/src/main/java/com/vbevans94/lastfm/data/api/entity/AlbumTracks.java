package com.vbevans94.lastfm.data.api.entity;

public class AlbumTracks {

    private final Album album;

    public AlbumTracks(Album album) {
        this.album = album;
    }

    /**
     * For Gson.
     */
    public AlbumTracks() {
        this(null);
    }

    public Album getAlbum() {
        return album;
    }

    @Override
    public String toString() {
        return "AlbumTracks{" +
                "album=" + album +
                '}';
    }
}
