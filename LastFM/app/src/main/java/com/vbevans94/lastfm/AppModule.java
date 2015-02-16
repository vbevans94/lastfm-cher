package com.vbevans94.lastfm;

import android.app.Application;

import com.vbevans94.lastfm.data.DataModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {
        DataModule.class
}, complete = false)
public final class AppModule {

    private final MainApp mainApplication;

    public AppModule(MainApp application) {
        this.mainApplication = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mainApplication;
    }
}
