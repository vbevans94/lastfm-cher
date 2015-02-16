package com.vbevans94.lastfm.ui;

import android.support.v4.widget.SwipeRefreshLayout;

import com.vbevans94.lastfm.AppModule;
import com.vbevans94.lastfm.ui.album.AlbumsActivity;
import com.vbevans94.lastfm.ui.track.TracksActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(library = true, complete = false, addsTo = AppModule.class, injects = {
        AlbumsActivity.class, TracksActivity.class
})
public class RefreshModule {

    private final SwipeRefreshLayout swipeToRefresh;
    private final SwipeRefreshLayout.OnRefreshListener refreshListener;

    public RefreshModule(SwipeRefreshLayout swipeToRefresh, SwipeRefreshLayout.OnRefreshListener listener) {
        this.swipeToRefresh = swipeToRefresh;
        this.refreshListener = listener;
        this.swipeToRefresh.setOnRefreshListener(listener);
    }

    @Singleton
    @Provides
    public RefreshController provideSwipeController() {
        return new RefreshController() {
            @Override
            public void onStartRefresh() {
                swipeToRefresh.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeToRefresh.setRefreshing(true);
                    }
                });
            }

            @Override
            public void onStopRefresh() {
                swipeToRefresh.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeToRefresh.setRefreshing(false);
                    }
                });
            }

            @Override
            public void refresh() {
                onStartRefresh();
                // call after because in logic onStopRefresh can be called
                refreshListener.onRefresh();
            }
        };
    }

    public interface RefreshController {

        void onStartRefresh();
        void onStopRefresh();
        void refresh();
    }
}