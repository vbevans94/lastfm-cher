package com.vbevans94.lastfm.data.api.entity;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class EntityFactory {

    private static final String EXTRA_NAMES = "extra_names";
    private static final String EXTRA_URLS = "extra_urls";
    private static final String EXTRA_LARGE_URLS = "extra_large_urls";
    private static final String EXTRA_PLAYCOUNTS = "extra_playcounts";
    private static final String EXTRA_DURATIONS = "extra_durations";
    private static final String EXTRA_ALBUM_NAME = "extra_album_name";
    private static final String EXTRA_LARGE_URL = "extra_large_url";

    @Inject
    public EntityFactory() {
    }

    /**
     * Populates intent with albums.
     *
     * @param intent to populate
     * @param albums to insert in the intent
     */
    public void populateIntentWithAlbums(Intent intent, List<Album> albums) {
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> urls = new ArrayList<>();
        ArrayList<String> largeUrls = new ArrayList<>();
        ArrayList<String> playcounts = new ArrayList<>();
        for (Album album : albums) {
            names.add(album.getName());
            playcounts.add(album.getPlaycount());
            urls.add(album.getImageUrl());
            largeUrls.add(album.getLargeImageUrl());
        }

        intent.putStringArrayListExtra(EXTRA_PLAYCOUNTS, playcounts)
                .putStringArrayListExtra(EXTRA_NAMES, names)
                .putStringArrayListExtra(EXTRA_LARGE_URLS, largeUrls)
                .putStringArrayListExtra(EXTRA_URLS, urls);
    }

    /**
     * Saves data from the intent and return list of parsed data.
     *
     * @param intent to get data from
     * @return list of albums in the intent
     */
    public List<Album> albumsFromIntent(Intent intent) {
        List<Album> albums = new ArrayList<>();
        // get data from intent
        List<String> names = intent.getStringArrayListExtra(EXTRA_NAMES);
        List<String> playcounts = intent.getStringArrayListExtra(EXTRA_PLAYCOUNTS);
        List<String> urls = intent.getStringArrayListExtra(EXTRA_URLS);
        List<String> largeUrls = intent.getStringArrayListExtra(EXTRA_LARGE_URLS);
        // build tracks to save in the database
        int position = 0;
        for (String name : names) {
            // save it
            Album album = new Album(name, null, playcounts.get(position), null);
            album.setImageUrl(urls.get(position));
            album.setLargeImageUrl(largeUrls.get(position));
            album.save();

            // add to result
            albums.add(album);

            position++;
        }

        return albums;
    }

    /**
     * Puts artist in the intent.
     * @param intent to put data into
     * @param album itself to put
     */
    public void populateIntentWithAlbum(Intent intent, Album album) {
        intent.putExtra(EXTRA_ALBUM_NAME, album.getName())
                .putExtra(EXTRA_LARGE_URL, album.getLargeImageUrl());
    }

    /**
     * Gets data from intent.
     * @param intent to get from
     * @return album
     */
    public Album albumFromIntent(Intent intent) {
        String albumName = intent.getStringExtra(EXTRA_ALBUM_NAME);
        String largeUrl = intent.getStringExtra(EXTRA_LARGE_URL);
        Album album = new Album(albumName, null, null, null);
        album.setLargeImageUrl(largeUrl);
        return album;
    }

    /**
     * Populates intent with tracks.
     *
     * @param intent to populate
     * @param album  of tracks
     * @param tracks to insert in the intent
     */
    public void populateIntentWithTracks(Intent intent, String album, List<Track> tracks) {
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> durations = new ArrayList<>();
        for (Track track : tracks) {
            names.add(track.getName());
            durations.add(track.getDuration());
        }

        intent.putStringArrayListExtra(EXTRA_DURATIONS, durations)
                .putStringArrayListExtra(EXTRA_NAMES, names)
                .putExtra(EXTRA_ALBUM_NAME, album);
    }

    /**
     * Saves data from the intent in the database and returns fetched list.
     *
     * @param intent to get data from
     * @return list of tracks
     */
    public List<Track> tracksFromIntent(Intent intent) {
        List<Track> tracks = new ArrayList<>();
        // get data from intent
        List<String> names = intent.getStringArrayListExtra(EXTRA_NAMES);
        List<String> durations = intent.getStringArrayListExtra(EXTRA_DURATIONS);
        String album = intent.getStringExtra(EXTRA_ALBUM_NAME);
        // build tracks to save in the database
        int position = 0;
        for (String name : names) {
            Track track = new Track(name, durations.get(position++));
            track.setAlbum(album);
            track.save();

            tracks.add(track);
        }

        return tracks;
    }
}
