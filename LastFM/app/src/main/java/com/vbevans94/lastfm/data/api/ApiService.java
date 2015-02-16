package com.vbevans94.lastfm.data.api;

import com.vbevans94.lastfm.data.api.entity.AlbumTracks;
import com.vbevans94.lastfm.data.api.entity.Albums;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface ApiService {

    /**
     * Requests list of top albums by artist.
     *
     * @param artist   to filter by
     * @param callback to handle the result
     */
    @GET("/?method=artist.gettopalbums&format=json")
    void listAlbums(@Query("artist") String artist, Callback<Albums> callback);

    /**
     * Requests list of tracks by album and artist.
     *
     * @param artist   to filter by
     * @param album    to get tracks for
     * @param callback to handle result
     */
    @GET("/?method=album.getinfo&format=json")
    void listTracks(@Query("artist") String artist, @Query("album") String album, Callback<AlbumTracks> callback);
}
