package com.vbevans94.lastfm.data.api.auth;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.RequestInterceptor;

@Singleton
public class AuthInterceptor implements RequestInterceptor {

    @Inject
    @ApiKey
    String apiKey;

    @Override
    public void intercept(RequestFacade request) {
        request.addQueryParam("api_key", apiKey);
    }
}
