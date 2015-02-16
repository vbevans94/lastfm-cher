package com.vbevans94.lastfm.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.vbevans94.lastfm.data.api.entity.Album;
import com.vbevans94.lastfm.data.api.entity.EntityFactory;
import com.vbevans94.lastfm.data.api.entity.Track;
import com.vbevans94.lastfm.utils.BusUtils;

import java.util.List;

public class DbService extends IntentService {

    private static final int LOAD_ALBUMS = 0;
    private static final int LOAD_TRACKS_BY_ALBUM = 1;
    public static final int SAVE_ALBUMS = 2;
    public static final int SAVE_TRACKS = 3;
    private static final String EXTRA_TYPE = "extra_type";
    private static final String EXTRA_ALBUM = "extra_album";

    private static final EntityFactory entityFactory = new EntityFactory();

    public DbService() {
        super(DbService.class.getSimpleName());

        setIntentRedelivery(true);
    }

    public static void saveAlbums(Context context, List<Album> albums) {
        Intent intent = new Intent(context, DbService.class);
        entityFactory.populateIntentWithAlbums(intent, albums);
        context.startService(intent.putExtra(EXTRA_TYPE, SAVE_ALBUMS));
    }

    public static void saveTracks(Context context, String album, List<Track> tracks) {
        Intent intent = new Intent(context, DbService.class);
        entityFactory.populateIntentWithTracks(intent, album, tracks);

        context.startService(intent.putExtra(EXTRA_TYPE, SAVE_TRACKS));
    }

    public static void loadAlbums(Context context) {
        context.startService(new Intent(context, DbService.class)
                .putExtra(EXTRA_TYPE, LOAD_ALBUMS));
    }

    public static void loadTracksByAlbum(String album, Context context) {
        context.startService(new Intent(context, DbService.class)
                .putExtra(EXTRA_TYPE, LOAD_TRACKS_BY_ALBUM)
                .putExtra(EXTRA_ALBUM, album));
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int type = intent.getIntExtra(EXTRA_TYPE, LOAD_ALBUMS);

        switch (type) {
            case SAVE_ALBUMS: {
                List<Album> albums = entityFactory.albumsFromIntent(intent);
                BusUtils.postToUi(new AlbumLoadedEvent(albums));

                break;
            }

            case SAVE_TRACKS: {
                List<Track> tracks = entityFactory.tracksFromIntent(intent);
                BusUtils.postToUi(new TracksLoadedEvent(tracks));

                break;
            }

            case LOAD_ALBUMS:
                List<Album> albums = Album.listAll(Album.class);
                BusUtils.postToUi(new AlbumLoadedEvent(albums));

                break;

            case LOAD_TRACKS_BY_ALBUM:
                String album = intent.getStringExtra(EXTRA_ALBUM);
                List<Track> tracks = Track.find(Track.class, "ALBUM = ?", album);
                BusUtils.postToUi(new TracksLoadedEvent(tracks));

                break;
        }
    }

    public static class AlbumLoadedEvent extends BusUtils.Event<List<Album>> {

        public AlbumLoadedEvent(List<Album> object) {
            super(object);
        }
    }

    public static class TracksLoadedEvent extends BusUtils.Event<List<Track>> {

        public TracksLoadedEvent(List<Track> object) {
            super(object);
        }
    }
}
