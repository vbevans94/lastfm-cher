package com.vbevans94.lastfm.ui.track;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;
import com.vbevans94.lastfm.MainApp;
import com.vbevans94.lastfm.R;
import com.vbevans94.lastfm.data.api.ApiService;
import com.vbevans94.lastfm.data.api.entity.Album;
import com.vbevans94.lastfm.data.api.entity.AlbumTracks;
import com.vbevans94.lastfm.data.api.entity.EntityFactory;
import com.vbevans94.lastfm.data.api.entity.Track;
import com.vbevans94.lastfm.service.DbService;
import com.vbevans94.lastfm.ui.RefreshModule;
import com.vbevans94.lastfm.utils.BusUtils;
import com.vbevans94.lastfm.utils.UiUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import dagger.ObjectGraph;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class TracksActivity extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener, Callback<AlbumTracks> {

    private final static EntityFactory entityFactory = new EntityFactory();
    private static final String EXTRA_ARTIST = "extra_artist";

    @InjectView(R.id.image_artwork)
    ImageView imageArtwork;

    @InjectView(R.id.list_items)
    ListView listAlbums;

    @InjectView(R.id.swipe_to_refresh)
    SwipeRefreshLayout swipeToRefresh;

    @InjectView(R.id.text_error)
    View textError;

    @Inject
    ApiService apiService;

    @Inject
    Picasso picasso;

    @Inject
    RefreshModule.RefreshController refreshController;

    private TracksAdapter adapter;
    private Album album;
    private String artist;

    public static void start(Context context, String artist, Album album) {
        Intent intent = new Intent(context, TracksActivity.class);
        entityFactory.populateIntentWithAlbum(intent, album);
        intent.putExtra(EXTRA_ARTIST, artist);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracks);

        ButterKnife.inject(this);
        ObjectGraph graph = MainApp.get(this).getApplicationGraph();
        graph.plus(new RefreshModule(swipeToRefresh, this)).inject(this);

        adapter = new TracksAdapter(this);
        listAlbums.setAdapter(adapter);

        album = entityFactory.albumFromIntent(getIntent());
        artist = getIntent().getStringExtra(EXTRA_ARTIST);
        setTitle(getString(R.string.title_album, artist, album.getName()));
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (adapter.getCount() == 0) {
            requestTracks();

            String imageUrl = album.getLargeImageUrl();
            if (!TextUtils.isEmpty(imageUrl)) {
                picasso.load(imageUrl)
                        .into(imageArtwork);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        BusUtils.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        BusUtils.unregister(this);
    }

    private void requestTracks() {
        UiUtils.show(swipeToRefresh);
        UiUtils.hide(textError);
        refreshController.refresh();
    }

    @Override
    public void onRefresh() {
        DbService.loadTracksByAlbum(album.getName(), this);
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void onTracksLoadedEvent(DbService.TracksLoadedEvent event) {
        List<Track> tracks = event.getData();
        if (tracks.isEmpty()) {
            apiService.listTracks(artist, album.getName(), this);
        } else {
            showTracks(tracks);
        }
    }

    @Override
    public void success(AlbumTracks albumTracks, Response response) {
        List<Track> tracks = albumTracks.getAlbum().getTracks().getTrack();
        // display tracks
        showTracks(tracks);
        // cache in the database
        DbService.saveTracks(this, album.getName(), tracks);
    }

    private void showTracks(List<Track> tracks) {
        adapter.addAll(tracks);
        refreshController.onStopRefresh();
    }

    @Override
    public void failure(RetrofitError error) {
        UiUtils.show(textError);
        UiUtils.hide(swipeToRefresh);
        refreshController.onStopRefresh();
    }

    @OnClick(R.id.text_error)
    @SuppressWarnings("unused")
    void onRetryClicked() {
        requestTracks();
    }
}
