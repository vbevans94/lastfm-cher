package com.vbevans94.lastfm.data.api.entity;

import java.util.List;

public class Tracks {
    
    private final List<Track> track;

    public Tracks(List<Track> track) {
        this.track = track;
    }

    /**
     * For Gson.
     */
    @SuppressWarnings("unused")
    public Tracks() {
        this(null);
    }

    public List<Track> getTrack() {
        return track;
    }

    @Override
    public String toString() {
        return "Tracks{" +
                "track=" + track +
                '}';
    }
}
