package com.vbevans94.lastfm.data.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.vbevans94.lastfm.data.api.auth.ApiKey;
import com.vbevans94.lastfm.data.api.auth.AuthInterceptor;
import com.vbevans94.lastfm.data.api.error.AppErrorHandler;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

@Module(library = true, complete = false)
public class ApiModule {

    public static final String API_URL = "http://ws.audioscrobbler.com/2.0/";
    public static final String API_KEY = "e0098ec62479cebee34cf0bc56789821";

    @Provides
    @Singleton
    ApiService provideApiService(RestAdapter adapter) {
        return adapter.create(ApiService.class);
    }

    @Provides
    @Singleton
    RestAdapter provideRestAdapter(Gson gson, AppErrorHandler errorHandler, Endpoint endpoint, Client client, AuthInterceptor interceptor) {
        return new RestAdapter.Builder()
                .setClient(client)
                .setRequestInterceptor(interceptor)
                .setErrorHandler(errorHandler)
                .setConverter(new GsonConverter(gson))
                .setEndpoint(endpoint)
                .build();
    }

    @Provides
    @Singleton
    Endpoint provideEndpoint() {
        return Endpoints.newFixedEndpoint(API_URL);
    }

    @Singleton
    @Provides
    @ApiKey
    String provideApiKey() {
        return API_KEY;
    }

    @Provides
    @Singleton
    Client provideClient(OkHttpClient client) {
        return new OkClient(client);
    }

    @Provides
    @Singleton
    Gson provideAutoParcelGson() {
        return new GsonBuilder()
                /*.registerTypeAdapterFactory(new AutoParcelAdapterFactory())*/
                .create();
    }
}
