package com.vbevans94.lastfm;

import android.content.Context;

import com.orm.SugarApp;

import java.util.Collections;
import java.util.List;

import dagger.ObjectGraph;
import timber.log.Timber;

public class MainApp extends SugarApp {

    private ObjectGraph applicationGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        applicationGraph = ObjectGraph.create(getModules().toArray());
    }

    private List<Object> getModules() {
        return Collections.<Object>singletonList(new AppModule(this));
    }

    public ObjectGraph getApplicationGraph() {
        return applicationGraph;
    }

    public static MainApp get(Context context) {
        return (MainApp) context.getApplicationContext();
    }
}
