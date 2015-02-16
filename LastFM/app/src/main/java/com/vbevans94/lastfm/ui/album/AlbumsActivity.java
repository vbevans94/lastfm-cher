package com.vbevans94.lastfm.ui.album;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;
import com.vbevans94.lastfm.MainApp;
import com.vbevans94.lastfm.R;
import com.vbevans94.lastfm.data.api.ApiService;
import com.vbevans94.lastfm.data.api.entity.Album;
import com.vbevans94.lastfm.data.api.entity.Albums;
import com.vbevans94.lastfm.service.DbService;
import com.vbevans94.lastfm.ui.RefreshModule;
import com.vbevans94.lastfm.ui.track.TracksActivity;
import com.vbevans94.lastfm.utils.BusUtils;
import com.vbevans94.lastfm.utils.UiUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import dagger.ObjectGraph;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class AlbumsActivity extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener, Callback<Albums> {

    private static final String CHER = "Cher";

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

    private AlbumsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_albums);

        ButterKnife.inject(this);
        ObjectGraph graph = MainApp.get(this).getApplicationGraph();
        graph.plus(new RefreshModule(swipeToRefresh, this)).inject(this);

        adapter = new AlbumsAdapter(this, picasso);
        listAlbums.setAdapter(adapter);

        setTitle(getString(R.string.title_artist, CHER));
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (adapter.getCount() == 0) {
            requestAlbums();
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

    private void requestAlbums() {
        UiUtils.show(swipeToRefresh);
        UiUtils.hide(textError);
        refreshController.refresh();
    }

    @Override
    public void onRefresh() {
        // start from loading local data
        DbService.loadAlbums(this);
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void onAlbumsLoaded(DbService.AlbumLoadedEvent event) {
        List<Album> albums = event.getData();
        if (albums.isEmpty()) {
            // if there is no local data start loading from the intenet
            apiService.listAlbums(CHER, this);
        } else {
            showAlbums(albums);
        }
    }

    @Override
    public void success(Albums albums, Response response) {
        List<Album> albumList = albums.getTopAlbums().getAlbum();
        // show albums in the UI
        showAlbums(albumList);
        // and cache in the local storage
        DbService.saveAlbums(this, albumList);
    }

    private void showAlbums(List<Album> albums) {
        adapter.addAll(albums);
        refreshController.onStopRefresh();
    }

    @Override
    public void failure(RetrofitError error) {
        UiUtils.show(textError);
        UiUtils.hide(swipeToRefresh);
        refreshController.onStopRefresh();
    }

    @OnItemClick(R.id.list_items)
    @SuppressWarnings("unused")
    void onAlbumClicked(int position) {
        Album album = adapter.getItem(position);
        TracksActivity.start(this, CHER, album);
    }

    @OnClick(R.id.text_error)
    @SuppressWarnings("unused")
    void onRetryClicked() {
        requestAlbums();
    }
}
