package com.vbevans94.lastfm.data.api.error;

import android.app.Application;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import timber.log.Timber;

@Singleton
public class AppErrorHandler implements ErrorHandler {

    @Inject
    Application application;

    @Override
    public Throwable handleError(RetrofitError cause) {
        Timber.e(cause, "Error in the API call");
        return cause;
    }
}
